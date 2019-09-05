package cn.wangningbo.hrm.mapper;

import cn.wangningbo.hrm.domain.CourseType;
import cn.wangningbo.hrm.query.CourseTypeQuery;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 课程目录 Mapper 接口
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-04
 */
public interface CourseTypeMapper extends BaseMapper<CourseType> {

    List<CourseType> loadListPage(Page page, @Param("query") CourseTypeQuery query);
}
