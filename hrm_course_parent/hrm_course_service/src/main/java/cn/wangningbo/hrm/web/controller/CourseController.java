package

        cn.wangningbo.hrm.web.controller;

import cn.wangningbo.hrm.service.ICourseService;
import cn.wangningbo.hrm.domain.Course;
import cn.wangningbo.hrm.query.CourseQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import cn.wangningbo.hrm.util.UserInfoHolder;
import com.baomidou.mybatisplus.plugins.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wangningbo
 * @since 2019-09-04
 */
@RestController
@RequestMapping("/course")
public class CourseController {
    @Autowired
    public ICourseService courseService;

    private Logger logger = LoggerFactory.getLogger(CourseController.class);

    /**
     * 保存和修改公用的
     *
     * @param course 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Course course) {
        try {
            System.out.println("-----------=========================+++++++++++++++++++++++");
            // @TODO 以后登录成功都能获取,现在适用holder来模拟
            course.setTenantId(UserInfoHolder.getTenant().getId());
            course.setTenantName(UserInfoHolder.getTenant().getCompanyName());
            course.setUserId(UserInfoHolder.getLoginUser().getId());
            course.setUserName(UserInfoHolder.getLoginUser().getUsername());
            if (course.getId() != null) {
                courseService.updateById(course);
            } else {
                courseService.insert(course);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            courseService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Course get(@PathVariable("id") Long id) {
        return courseService.selectById(id);
    }


    /**
     * 查看所有的员工信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Course> list() {

        return courseService.selectList(null);
    }


    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<Course> json(@RequestBody CourseQuery query) {
        return courseService.selectPageList(query);
    }

    /**
     * 商品上线
     *
     * @param ids
     * @return
     */
    @PostMapping("/onLine")
    public AjaxResult onLine(@RequestBody Long[] ids) {
        try {
            courseService.onLine(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("onLine failed!"+e);
            return AjaxResult.me().setSuccess(false)
                    .setMessage("上线失败!"+e.getMessage());
        }
    }

    /**
     * 商品课程下线
     *
     * @param ids
     * @return
     */
    @PostMapping("/offLine")
    public AjaxResult offLine(@RequestBody Long[] ids) {
        try {
            courseService.offLine(ids);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("offLine failed!"+e);
            return AjaxResult.me().setSuccess(false)
                    .setMessage("下线失败!"+e.getMessage());
        }
    }
    /**
     * 高级搜索
     */
    @PostMapping("/queryCourses")
    public PageList<Map<String,Object>> queryCourses(@RequestBody Map<String,Object> query){
        return courseService.queryCourses(query);
    }
}
