package cn.wangningbo.hrm.web.controller;

import cn.wangningbo.hrm.doc.ESCourse;
import cn.wangningbo.hrm.query.ESCourseQuery;
import cn.wangningbo.hrm.service.IESCourseService;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/esCourse")
public class ESCourseController {
    @Autowired
    public IESCourseService esCourseService;

    /**
     * 保存和修改公用的
     *
     * @param esCourse 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody ESCourse esCourse) {
        try {
            if (esCourse.getId() != null) {
                esCourseService.updateById(esCourse);
            } else {
                esCourseService.insert(esCourse);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            esCourseService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ESCourse get(@PathVariable("id") Long id) {
        return esCourseService.selectById(id);
    }


    /**
     * 查看所有信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<ESCourse> list() {

        return esCourseService.selectList(null);
    }


    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<ESCourse> json(@RequestBody ESCourseQuery query) {
        return esCourseService.selectListPage(query);
    }

    /**
     * 批量保存到es库，批量上线
     * @param esCourseList
     * @return
     */
    @PostMapping("/online")
    AjaxResult batchSave(@RequestBody List<ESCourse> esCourseList){
        try {
            esCourseService.batchSave(esCourseList);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("批量添加失败!"+e.getMessage());
        }
    }

    @PostMapping("/offline")
    AjaxResult batchDel(@RequestBody List<ESCourse> esCourseList){
        try {
            esCourseService.batchDel(esCourseList);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("批量删除失败!"+e.getMessage());
        }
    }

    @PostMapping("/query")
    PageList<Map<String, Object>> query(@RequestBody Map<String, Object> params) {
        return esCourseService.query(params);
    }
}
