package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.doc.ESCourse;
import cn.wangningbo.hrm.query.ESCourseQuery;
import cn.wangningbo.hrm.repository.CourseRepository;
import cn.wangningbo.hrm.service.IESCourseService;
import cn.wangningbo.hrm.util.PageList;
import org.apache.commons.lang.StringUtils;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    //批量保存
    @Override
    public void batchSave(List<ESCourse> esCourseList) {
        courseRepository.saveAll(esCourseList);
    }

    //批量删除
    @Override
    public void batchDel(List<ESCourse> esCourseList) {
        courseRepository.deleteAll(esCourseList);
    }

    @Override
    public PageList<Map<String, Object>> query(Map<String, Object> params) {
        // keyword CourseyType brandId priceMin priceMax sortField sortType page rows
        String keyword = (String) params.get("keyword"); //查询
        String sortField = (String) params.get("sortField"); //排序
        String sortType = (String) params.get("sortType");//排序

        Long courseType = params.get("CourseType") != null ? Long.valueOf(params.get("CourseType").toString()) : null;//过滤
        Long priceMin = params.get("priceMin") != null ? Long.valueOf(params.get("priceMin").toString()) * 100 : null;//过滤
        Long priceMax = params.get("priceMax") != null ? Long.valueOf(params.get("priceMax").toString()) * 100 : null;//过滤
        Long page = params.get("page") != null ? Long.valueOf(params.get("page").toString()) : null; //分页
        Long rows = params.get("rows") != null ? Long.valueOf(params.get("rows").toString()) : null;//分页

        //构建器
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //设置查询条件=查询+过滤
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if (StringUtils.isNotBlank(keyword)) {
            boolQuery.must(QueryBuilders.matchQuery("all", keyword));
        }
        List<QueryBuilder> filter = boolQuery.filter();
        if (courseType != null) { //类型
            filter.add(QueryBuilders.termQuery("courseTypeId", courseType));
        }
        //最大价格 最小价格
        //minPrice <= priceMax && maxPrice>=priceMin
        if (priceMax != null && priceMin != null) {
            filter.add(QueryBuilders.rangeQuery("price").gte(priceMin).lte(priceMax));
        }
        builder.withQuery(boolQuery);
        //排序
        SortOrder defaultSortOrder = SortOrder.DESC;
        if (StringUtils.isNotBlank(sortField)) {//销量 新品 价格 人气 评论
            //如果传入的不是降序改为升序
            if (StringUtils.isNotBlank(sortType) && !sortType.equals(SortOrder.DESC)) {
                defaultSortOrder = SortOrder.ASC;
            }
            // 价格  索引库有两个字段 最大,最小
            //如果用户按照升序就像买便宜的,就用最小价格,如果用户按照降序想买贵的,用最大价格
            if (sortField.equals("jg")) {
                builder.withSort(SortBuilders.fieldSort("price").order(defaultSortOrder));
            }
        }
        //分页
        Long pageTmp = page - 1; //从0开始
        builder.withPageable(PageRequest.of(pageTmp.intValue(), rows.intValue()));
        //截取字段 @TODO
        //封装数据
        Page<ESCourse> CourseDocs = courseRepository.search(builder.build());
        List<Map<String, Object>> datas = esCourses2ListMap(CourseDocs.getContent());
        return new PageList<>(CourseDocs.getTotalElements(), datas);
    }

    /**
     * 数据转换
     *
     * @param content
     * @return
     */
    private List<Map<String, Object>> esCourses2ListMap(List<ESCourse> content) {
        List<Map<String, Object>> result = new ArrayList<>();
        for (ESCourse esCourse : content) {
            result.add(esCourse2Map(esCourse));
        }
        return result;
    }

    private Map<String, Object> esCourse2Map(ESCourse esCourse) {
        Map<String, Object> result = new HashMap<>();
        result.put("id", esCourse.getId());
        result.put("name", esCourse.getName());
        result.put("users", esCourse.getUsers());
        result.put("courseTypeId", esCourse.getCourseTypeId());
        result.put("courseTypeName", esCourse.getCourseTypeName());
        result.put("gradeId", esCourse.getGradeId());
        result.put("gradeName", esCourse.getGradeName());
        result.put("status", esCourse.getStatus());
        result.put("tenantId", esCourse.getTenantId());
        result.put("tenantName", esCourse.getTenantName());
        result.put("userId", esCourse.getUserId());
        result.put("userName", esCourse.getUserName());
        result.put("startTime", esCourse.getStartTime());
        result.put("endTime", esCourse.getEndTime());
        result.put("expires", esCourse.getExpires());
        result.put("priceOld", esCourse.getPriceOld());
        result.put("price", esCourse.getPrice());
        result.put("intro", esCourse.getIntro());
        result.put("qq", esCourse.getQq());
        result.put("resources", esCourse.getResources());
        return result;
    }
}
