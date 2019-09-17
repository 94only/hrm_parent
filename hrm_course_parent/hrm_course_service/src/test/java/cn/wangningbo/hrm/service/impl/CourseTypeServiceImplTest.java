package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.Course9002Application;
import cn.wangningbo.hrm.domain.CourseType;
import cn.wangningbo.hrm.query.CourseTypeQuery;
import cn.wangningbo.hrm.service.ICourseTypeService;
import cn.wangningbo.hrm.util.PageList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Course9002Application.class)
public class CourseTypeServiceImplTest {
    @Autowired
    private ICourseTypeService courseTypeService;
    @Test
    public void selectListPage() {
        PageList<CourseType> courseTypePageList = courseTypeService.selectListPage(new CourseTypeQuery());
        System.out.println(courseTypePageList.getTotal());
        List<CourseType> rows = courseTypePageList.getRows();
        System.out.println(rows.size());
        for (CourseType courseType : rows) {
            System.out.println(courseType);
        }
    }
    
    @Test
    public void testInit() throws Exception {
        courseTypeService.InitCourseSiteIndex();
    }

    @Test
    public void testQueryTree() throws Exception {
        courseTypeService.queryTypeTree(0L);
    }
}