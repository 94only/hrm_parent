package cn.wangningbo.hrm;

import cn.wangningbo.hrm.doc.ESCourse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ElasticSearch9004Application.class)
public class IESCourseServiceTest {
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Test
    public void testInit() throws Exception {
        elasticsearchTemplate.createIndex(ESCourse.class);
        elasticsearchTemplate.putMapping(ESCourse.class);
    }
}
