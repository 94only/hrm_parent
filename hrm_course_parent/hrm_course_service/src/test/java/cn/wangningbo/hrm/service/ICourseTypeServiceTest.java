package cn.wangningbo.hrm.service;

import cn.wangningbo.hrm.Course9002Application;
import cn.wangningbo.hrm.domain.CourseType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest(classes = Course9002Application.class)
@RunWith(SpringRunner.class)
public class ICourseTypeServiceTest {

    @Autowired
    private ICourseTypeService courseTypeService;
    @Test
    public void getCrumbs() {
        List<Map<String, Object>> crumbs = courseTypeService.getCrumbs(1040L);
        for (Map<String, Object> crumb : crumbs) {

            CourseType owner = (CourseType) crumb.get("owner");
            List<CourseType> otherCourseTypes = (List<CourseType>) crumb.get("otherCourseTypes");
            System.out.println(owner);
            System.out.println("jjjjjjjjjjjjjjjjjjj");
            for (CourseType otherCourseType : otherCourseTypes) {
                System.out.println(otherCourseType);
            }
            System.out.println("======================================");
        }
    }
}