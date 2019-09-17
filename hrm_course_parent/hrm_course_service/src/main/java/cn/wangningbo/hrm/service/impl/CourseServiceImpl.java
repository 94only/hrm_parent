package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.client.ESCourseClient;
import cn.wangningbo.hrm.doc.ESCourse;
import cn.wangningbo.hrm.domain.Course;
import cn.wangningbo.hrm.mapper.CourseDetailMapper;
import cn.wangningbo.hrm.mapper.CourseMapper;
import cn.wangningbo.hrm.query.CourseQuery;
import cn.wangningbo.hrm.service.ICourseService;
import cn.wangningbo.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-04
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements ICourseService {
    @Autowired
    private CourseMapper courseMapper;
    @Autowired
    private CourseDetailMapper courseDetailMapper;
    @Autowired
    private ESCourseClient esCourseClient;

    @Override
    public PageList<Course> selectPageList(CourseQuery query) {
        Page page = new Page<>(query.getPage(), query.getRows());
        List<Course> list = courseMapper.loadPageList(page, query);
        return new PageList<>(page.getTotal(), list);
    }

    /**
     * 商品课程上线
     * @param ids
     */
    @Override
    public void onLine(Long[] ids) {
        //批量操作数据库状态字段 //类似于这种update t_course set status = 1,start_time=xxx where id in (1,2,3)
        ArrayList<Map<String, Object>> listMap = new ArrayList<>();
        if (ids != null || ids.length > 0) {
            for (Long id : ids) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("id", id);
                map.put("start_time", new Date());
                listMap.add(map);
            }
        }
        //批量修改db状态字段
        courseMapper.batchOnline(listMap);
        //批量操作索引库
        //从数据库中查出来需要上线的商品课程
        List<Course> courseList = courseMapper.selectBatchIds(Arrays.asList(ids));
        //把从数据库中查出来的商品课程转化为es的doc
        List<ESCourse> esCourseList = courseList2EsCourse(courseList);
        //批量操作把doc添加到es库 //批量添加到es库中的方法没有，自己实现一个
        esCourseClient.batchSave(esCourseList);
    }

    @Override
    public void offLine(Long[] ids) {
        //批量修改db库中商品课程的状态为下线状态
        courseMapper.batchOffline(Arrays.asList(ids));
        //批量删除es库中的商品课程 //时间方面是使用mysql的语法生成的
        List<Course> courseList = courseMapper.selectBatchIds(Arrays.asList(ids));
        List<ESCourse> esCourseList = courseList2EsCourse(courseList);
        esCourseClient.batchDel(esCourseList);
    }

    /**
     * db库的domain转化为es的doc
     *
     * @param courseList
     * @return
     */
    private List<ESCourse> courseList2EsCourse(List<Course> courseList) {
        ArrayList<ESCourse> list = new ArrayList<>();
        courseList.forEach(course -> list.add(course2EsCourse(course)));
        return list;
    }

    @Override
    public boolean insert(Course course) {
        //课程表
        course.setStatus(0);
        System.out.println(course);
        courseMapper.insert(course);
        //课程详情
        course.getDetail().setCourseId(course.getId());
        System.out.println(course.getDetail());
        courseDetailMapper.insert(course.getDetail());
        return true;
    }

    @Override
    public boolean deleteById(Serializable id) {
        //删除数据库的同时也要判断状态是否要删除es库
        courseMapper.deleteById(id);
        Course course = courseMapper.selectById(id);
        if (course.getStatus() == 1)
            esCourseClient.delete(Integer.valueOf(id.toString()));
        return true;
    }

    // @TODO 不同服务,反3Fn设计冗余字段
    // @TODO 相同服务,关联查询
    //Course转ESCourse //根据自己的需求和库中的表设计
    private ESCourse course2EsCourse(Course course) {
        ESCourse result = new ESCourse();
        result.setId(course.getId());
        result.setName(course.getName());
        result.setUsers(course.getUsers());
        result.setCourseTypeId(course.getCourseTypeId());
        //type-同库
        if (course.getCourseType() != null)
            result.setCourseTypeName(course.getCourseType().getName());
        //跨服务操作
        result.setGradeId(course.getGrade());
        result.setGradeName(null);
        result.setStatus(course.getStatus());
        result.setTenantId(course.getTenantId());
        result.setTenantName(course.getTenantName());
        result.setUserId(course.getUserId());
        result.setUserName(course.getUserName());
        result.setStartTime(course.getStartTime());
        result.setEndTime(course.getEndTime());
        //Detail
        result.setIntro(null);
        //resource
        result.setResources(null);
        //market
        result.setExpires(null);
        result.setPrice(null);
        result.setPriceOld(null);
        result.setQq(null);
        return result;
    }

    @Override
    public boolean updateById(Course entity) {
        //修改数据库的时候也要根据状态判断是否操作修改es库
        courseMapper.updateById(entity);
        Course course = courseMapper.selectById(entity.getId());
        if (course.getStatus() == 1)
            esCourseClient.save(course2EsCourse(entity));
        return true;
    }

    /**
     * 高级搜索
     * @param query
     * @return
     */
    @Override
    public PageList<Map<String, Object>> queryCourses(Map<String, Object> query) {
        return esCourseClient.query(query);
    }
}
