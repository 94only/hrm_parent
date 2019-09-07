package cn.wangningbo.hrm.service;


import cn.wangningbo.hrm.doc.ESCourse;
import cn.wangningbo.hrm.query.ESCourseQuery;
import cn.wangningbo.hrm.util.PageList;

import java.util.List;

/**
 * @author wangningbo
 * @since 2019-09-06
 */
public interface IESCourseService {
    //添加
    void insert(ESCourse esCourse);

    //修改
    void updateById(ESCourse esCourse);

    //删除
    void deleteById(Long id);

    //查询一个
    ESCourse selectById(Long id);

    //查询所有
    List<ESCourse> selectList(Object o);

    //dsl高级查询
    PageList<ESCourse> selectListPage(ESCourseQuery query);

    //批量保存
    void batchSave(List<ESCourse> ids);

    //批量删除
    void batchDel(List<ESCourse> esCourseList);
}
