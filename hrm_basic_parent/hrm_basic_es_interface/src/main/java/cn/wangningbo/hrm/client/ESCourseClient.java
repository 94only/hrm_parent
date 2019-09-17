package cn.wangningbo.hrm.client;


import cn.wangningbo.hrm.doc.ESCourse;
import cn.wangningbo.hrm.query.ESCourseQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@FeignClient(value = "HRM-ES", configuration = FeignClientsConfiguration.class,
        fallbackFactory = EsCourseClientHystrixFallbackFactory.class)
@RequestMapping("/esCourse")
public interface ESCourseClient {
    /**
     * 保存和修改公用的
     *
     * @param esCourse 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    AjaxResult save(ESCourse esCourse);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    AjaxResult delete(@PathVariable("id") Integer id);

    //获取用户
    @RequestMapping("/{id}")
    ESCourse get(@RequestParam(value = "id", required = true) Long id);


    /**
     * 查看所有信息
     *
     * @return
     */
    @RequestMapping("/list")
    public List<ESCourse> list();

    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    PageList<ESCourse> json(@RequestBody ESCourseQuery query);

    //批量上线
    @PostMapping("/online")
    AjaxResult batchSave(List<ESCourse> esCourseList);

    //批量下线
    @PostMapping("/offline")
    void batchDel(List<ESCourse> esCourseList);

    /**
     * 从es中查询
     * @param params
     * @return
     */
    @PostMapping("/query")
    PageList<Map<String,Object>> query(@RequestBody Map<String, Object> params);
}
