package cn.wangningbo.hrm.mapper;

import cn.wangningbo.hrm.domain.Course;
import cn.wangningbo.hrm.query.CourseQuery;
import cn.wangningbo.hrm.util.PageList;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-04
 */
public interface CourseMapper extends BaseMapper<Course> {

    List<Course> loadPageList(Page page, @Param("query") CourseQuery query);
}
