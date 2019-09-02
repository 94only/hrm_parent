package cn.wangningbo.hrm.service;

import cn.wangningbo.hrm.domain.CourseType;
import cn.wangningbo.hrm.query.CourseTypeQuery;
import cn.wangningbo.hrm.util.PageList;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 课程目录 服务类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-01
 */
public interface ICourseTypeService extends IService<CourseType> {

    PageList<CourseType> selectPageList(CourseTypeQuery query);
}