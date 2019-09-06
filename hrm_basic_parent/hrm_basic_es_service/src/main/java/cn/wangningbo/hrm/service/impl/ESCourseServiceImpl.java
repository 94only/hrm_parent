package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.doc.ESCourse;
import cn.wangningbo.hrm.query.ESCourseQuery;
import cn.wangningbo.hrm.repository.CourseRepository;
import cn.wangningbo.hrm.service.IESCourseService;
import cn.wangningbo.hrm.util.PageList;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ESCourseServiceImpl implements IESCourseService {

    @Autowired
    private CourseRepository courseRepository;

    @Override
    public void insert(ESCourse esCourse) {
        courseRepository.save(esCourse);
    }

    @Override
    public void updateById(ESCourse esCourse) {
        courseRepository.save(esCourse);
    }

    @Override
    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public ESCourse selectById(Long id) {
        return courseRepository.findById(id).get();
    }

    @Override
    public List<ESCourse> selectList(Object o) {
        Page page = (Page) courseRepository.findAll();
        return page.getContent();
    }

    @Override
    public PageList<ESCourse> selectListPage(ESCourseQuery query) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        BoolQueryBuilder bool = QueryBuilders.boolQuery();
        //模糊查询 @TODO
        bool.must(QueryBuilders.matchQuery("intro", "zhang"));
        //精确过滤 @TODO
        List<QueryBuilder> filters = bool.filter();
        filters.add(QueryBuilders.rangeQuery("age").gte(0).lte(200));
        builder.withQuery(bool); //query bool must(filter)
        //排序 @TODO
        builder.withSort(SortBuilders.fieldSort("age").order(SortOrder.ASC));
        //分页 当前页从0开始
        builder.withPageable(PageRequest.of(query.getPage() - 1, query.getRows()));
        //构造查询条件
        NativeSearchQuery esQuery = builder.build();
        //查询
        Page<ESCourse> page = courseRepository.search(esQuery);
        return new PageList<>(page.getTotalElements(), page.getContent());
    }
}
