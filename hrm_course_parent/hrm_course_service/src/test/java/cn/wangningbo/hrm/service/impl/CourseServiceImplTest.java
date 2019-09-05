package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.Course9002Application;
import cn.wangningbo.hrm.domain.Course;
import cn.wangningbo.hrm.query.CourseQuery;
import cn.wangningbo.hrm.service.ICourseService;
import cn.wangningbo.hrm.util.PageList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Course9002Application.class)
public class CourseServiceImplTest {

    @Autowired
    private ICourseService courseService;
    @Test
    public void selectPageList() {
        PageList<Course> coursePageList = courseService.selectPageList(new CourseQuery());
        System.out.println(coursePageList.getTotal());
        List<Course> rows = coursePageList.getRows();
        System.out.println(rows.size());
        for (Course course : rows) {
            System.out.println(course);
        }
    }
}