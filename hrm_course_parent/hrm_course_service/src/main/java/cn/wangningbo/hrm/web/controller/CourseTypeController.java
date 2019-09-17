package

    cn.wangningbo.hrm.web.controller;

import cn.wangningbo.hrm.domain.CourseType;
import cn.wangningbo.hrm.query.CourseTypeQuery;
import cn.wangningbo.hrm.service.ICourseTypeService;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wangningbo
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/courseType" )
public class CourseTypeController {
    @Autowired
    public ICourseTypeService courseTypeService;

    /**
     * 保存和修改公用的
     * @param courseType 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody CourseType courseType) {
        try {
            if (courseType.getId() != null){
                    courseTypeService.updateById(courseType);
            }else{
                    courseTypeService.insert(courseType);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id" ) Long id) {
        try {
                courseTypeService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CourseType get(@PathVariable("id" ) Long id) {
        return courseTypeService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<CourseType> list() {

        return courseTypeService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<CourseType> json(@RequestBody CourseTypeQuery query) {
        return courseTypeService.selectListPage(query);
    }

    //类型树
    @RequestMapping(value = "/treeData",method = RequestMethod.GET)
    public List<CourseType> treeData(){
        //数据库中0就是顶级
        return courseTypeService.queryTypeTree(0L);
    }

    /**
     *  通过类型查询面包屑数据
     *     有层次(Node): path
     *     Node: 自己和兄弟  path里面就是自己 通过自己查询父亲,再通过父亲找到儿子,删除自己就ok
     * @return
     */
    @RequestMapping(value = "/crumbs",method = RequestMethod.GET)
    public List<Map<String,Object>> getCrumbs(Long courseTypeId){
        return courseTypeService.getCrumbs(courseTypeId);
    }
}
