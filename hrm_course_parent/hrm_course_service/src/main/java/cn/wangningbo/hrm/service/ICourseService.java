package cn.wangningbo.hrm.service;

import cn.wangningbo.hrm.domain.Course;
import cn.wangningbo.hrm.query.CourseQuery;
import cn.wangningbo.hrm.util.PageList;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-04
 */
public interface ICourseService extends IService<Course> {

    //分页+高级查询+关联查询
    PageList<Course> selectPageList(CourseQuery query);

    void onLine(Long[] ids);

    void offLine(Long[] ids);
}
