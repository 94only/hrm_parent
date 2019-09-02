package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.domain.CourseType;
import cn.wangningbo.hrm.mapper.CourseTypeMapper;
import cn.wangningbo.hrm.query.CourseTypeQuery;
import cn.wangningbo.hrm.service.ICourseTypeService;
import cn.wangningbo.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程目录 服务实现类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-01
 */
@Service
public class CourseTypeServiceImpl extends ServiceImpl<CourseTypeMapper, CourseType> implements ICourseTypeService {

    @Autowired
    private CourseTypeMapper courseTypeMapper;

    @Override
    public PageList<CourseType> selectPageList(CourseTypeQuery query) {
        Page page = new Page(query.getPage(), query.getRows());
        List<CourseType> list = courseTypeMapper.loadListPage(page, query);
        return new PageList<>(page.getTotal(), list);
    }
}
