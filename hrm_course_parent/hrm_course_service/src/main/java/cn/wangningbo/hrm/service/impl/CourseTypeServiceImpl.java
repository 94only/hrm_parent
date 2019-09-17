package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.cache.CourseTypeCache;
import cn.wangningbo.hrm.client.PageConfigClient;
import cn.wangningbo.hrm.client.RedisClient;
import cn.wangningbo.hrm.domain.CourseType;
import cn.wangningbo.hrm.mapper.CourseTypeMapper;
import cn.wangningbo.hrm.query.CourseTypeQuery;
import cn.wangningbo.hrm.service.ICourseTypeService;
import cn.wangningbo.hrm.util.PageList;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-04
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    @Autowired
    private CourseTypeCache courseTypeCache;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private PageConfigClient pageConfigClient;

    @Override
    public PageList<CourseType> selectListPage(CourseTypeQuery query) {
        Page page = new Page(query.getPage(), query.getRows());
        List<CourseType> courseTypes = courseTypeMapper.loadListPage(page, query);
        return new PageList<>(page.getTotal(), courseTypes);
    }

    @Override
    public List<CourseType> queryTypeTree(Long pid) {
        // 从redis缓存中获取课程类型树
        List<CourseType> courseTypes = courseTypeCache.getCourseTypes();
        // 判断redis中有没有获取到课程类型树
        if (courseTypes == null || courseTypes.size() < 1) {
            // 进入到了if里面就说明缓存中没有，要从db库中获取课程类型树 // 调用循环的方式获取课程类型树
            List<CourseType> courseTypesDb = getCourseTypesLoop(pid);
            // 判断数据库中是否查到了数据
            if (courseTypesDb == null || courseTypesDb.size() < 1)
                // 如果数据库中没有查到，就返回一个空回去 // 并设置一个很短的过期时间，我这里过期时间为5分钟
                courseTypesDb = new ArrayList<>();
            // 把查询的结果放入缓存中
            courseTypeCache.setCourseTypes(courseTypesDb);
            return courseTypesDb;
        }
        // 返回redis缓存中的课程类型树
        return courseTypes;
    }

    /**
     * 循环
     *
     * @param pid
     * @return
     */
    private List<CourseType> getCourseTypesLoop(Long pid) {
        List<CourseType> result = new ArrayList<>();
        //1 查询所有类型
        List<CourseType> allTypes = courseTypeMapper.selectList(null);
        //建立id和CourseType的关联关系
        Map<Long, CourseType> allTypesDto = new HashMap<>();
        for (CourseType allType : allTypes) {
            allTypesDto.put(allType.getId(), allType);
        }
        //2 遍历判断是否是第一级  pid为传入id,
        for (CourseType type : allTypes) {
            Long pidTmp = type.getPid();
            //2.1 是。直接加入返回列表
            if (pidTmp.longValue() == pid.longValue()) {
                result.add(type);
            } else {
                //2.2 不是。要把自己作为父亲儿子
                //方案:通过pid获取父亲。通过map获取
                CourseType parent = allTypesDto.get(pidTmp);
                //获取父亲儿子集合,把自己加进去
                parent.getChildren().add(type);
            }
        }
        return result;
    }

    /**
     * 递归
     *
     * @param pid
     * @return
     */
    private List<CourseType> getCourseTypesRecursion(Long pid) {
        // 方案1:递归-自己调用自己,要有出口
        List<CourseType> children = courseTypeMapper.selectList(new EntityWrapper<CourseType>().eq("pid", pid));
        // 出口
        if (children == null || children.size() < 1)
            return null;
        for (CourseType child : children) {
            // 自己调用自己
            List<CourseType> courseTypes = queryTypeTree(child.getId());
            child.setChildren(courseTypes);
        }
        return children;
    }

    @Override
    public boolean insert(CourseType entity) {
        courseTypeMapper.insert(entity);
        // 重新查询一下课程类型树，更新到redis缓存
        List<CourseType> courseTypes = queryTypeTree(0L);
        courseTypeCache.setCourseTypes(courseTypes);
        return true;
    }

    @Override
    public boolean deleteById(Serializable id) {
        courseTypeMapper.deleteById(id);
        // 重新查询一下课程类型树，更新到redis缓存
        List<CourseType> courseTypes = queryTypeTree(0L);
        courseTypeCache.setCourseTypes(courseTypes);
        return true;
    }

    @Override
    public boolean updateById(CourseType entity) {
        courseTypeMapper.updateById(entity);
        // 重新查询一下课程类型树，更新到redis缓存
        List<CourseType> courseTypes = queryTypeTree(0L);
        courseTypeCache.setCourseTypes(courseTypes);
        return true;
    }

    @Override
    public void InitCourseSiteIndex() {
        //1 准备模板,并且上传fastdfs
        //2存放数据到redis
        List<CourseType> courseTypes = queryTypeTree(0L);
        String dataKey = "courseTypes";
        redisClient.set(dataKey, JSONArray.toJSONString(courseTypes));
        //3调用静态化接口产生静态页面,并且放入fastdfs
        String pageName = "CourseIndex";
        //本来应该通过PageName获取page后设置pageconfig传递,由于数据在查询端,还不如直接传入pageName到那边查询.
        Map<String,String> map = new HashMap<>();
        map.put("dataKey",dataKey);
        map.put("pageName",pageName);
        pageConfigClient.staticPage(map);
        //4往消息队列放一个消息,让pageAgent来下载静态页面
    }

    @Override
    public List<Map<String, Object>> getCrumbs(Long courseTypeId) {
        List<Map<String,Object>> result = new ArrayList<>();
        //1 获取path 1.2.3
        CourseType courseType = courseTypeMapper.selectById(courseTypeId);
        String path = courseType.getPath();

        //2 截取path中各个节点自己  1 2 3
        String[] paths = path.split("\\.");

        //3 获取自己节点兄弟封装Map,放入List中进行返回
        for (String ownerIdStr : paths) {
            Map<String,Object> map = new HashMap<>();

            Long ownerId = Long.valueOf(ownerIdStr);

            System.out.println(ownerId);
            //获取每个自己
            CourseType owner =  courseTypeMapper.selectById(ownerId);
            map.put("owner",owner);
            //查询兄弟
            //获取父亲所有儿子
            List<CourseType> allChildren = courseTypeMapper
                    .selectList(new EntityWrapper<CourseType>().eq("pid",owner.getPid()));

            //干掉自己-边遍历边操作(正删改),要用迭代器
            Iterator<CourseType> iterator = allChildren.iterator();
            while (iterator.hasNext()){
                CourseType currentType = iterator.next();
                if (currentType.getId().longValue()==owner.getId().longValue()){
                    iterator.remove();
                    continue; //跳出当前循环
                }
            }
            map.put("otherCourseTypes",allChildren);
            result.add(map);
        }
        return result;
    }

}
