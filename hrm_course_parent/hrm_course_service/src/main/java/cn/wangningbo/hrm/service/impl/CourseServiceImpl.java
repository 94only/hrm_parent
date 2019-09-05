package cn.wangningbo.hrm.service.impl;

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

import java.util.List;

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

    @Override
    public PageList<Course> selectPageList(CourseQuery query) {
        Page page = new Page<>(query.getPage(), query.getRows());
        List<Course> list = courseMapper.loadPageList(page, query);
        return new PageList<>(page.getTotal(), list);
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
}
