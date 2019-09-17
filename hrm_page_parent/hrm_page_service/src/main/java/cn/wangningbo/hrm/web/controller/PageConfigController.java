package

    cn.wangningbo.hrm.web.controller;

import cn.wangningbo.hrm.service.IPageConfigService;
import cn.wangningbo.hrm.domain.PageConfig;
import cn.wangningbo.hrm.query.PageConfigQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author wangningbo
 * @since 2019-09-09
 */
@RestController
@RequestMapping("/pageConfig" )
public class PageConfigController {
    @Autowired
    public IPageConfigService pageConfigService;

    /**
     * 保存和修改公用的
     * @param pageConfig 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody PageConfig pageConfig) {
        try {
            if (pageConfig.getId() != null){
                    pageConfigService.updateById(pageConfig);
            }else{
                    pageConfigService.insert(pageConfig);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
    * 删除对象信息
    * @param id
    * @return
    */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id" ) Long id) {
        try {
                pageConfigService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PageConfig get(@PathVariable("id" ) Long id) {
        return pageConfigService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<PageConfig> list() {

        return pageConfigService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<PageConfig> json(@RequestBody PageConfigQuery query) {
        Page<PageConfig> page = new Page<PageConfig>(query.getPage(), query.getRows());
        page = pageConfigService.selectPage(page);
        return new PageList<PageConfig>(page.getTotal(), page.getRecords());
    }

    /**
     * 静态化页面
     * @param map
     * @return
     */
    @PostMapping("/staticPage")
    AjaxResult staticPage(Map<String, String> map){
        String dataKey = map.get("dataKey");
        String pageName = map.get("pageName");
        try {
            pageConfigService.staticPage(dataKey,pageName);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setSuccess(false).setMessage("静态化失败!"+e.getMessage());
        }
    }
}
