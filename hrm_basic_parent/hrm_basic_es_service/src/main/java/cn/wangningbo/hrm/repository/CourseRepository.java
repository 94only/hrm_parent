package cn.wangningbo.hrm.repository;


import cn.wangningbo.hrm.doc.ESCourse;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CourseRepository extends ElasticsearchRepository<ESCourse, Long> {
}
