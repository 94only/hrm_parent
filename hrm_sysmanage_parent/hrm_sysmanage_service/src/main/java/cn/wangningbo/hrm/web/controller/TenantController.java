package

    cn.wangningbo.hrm.web.controller;

import cn.wangningbo.hrm.service.ITenantService;
import cn.wangningbo.hrm.domain.Tenant;
import cn.wangningbo.hrm.query.TenantQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangningbo
 * @since 2019-09-02
 */
@RestController
@RequestMapping("/tenant" )
public class TenantController {
    @Autowired
    public ITenantService tenantService;

    /**
     * 保存和修改公用的
     * @param tenant 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Tenant tenant) {
        try {
            if (tenant.getId() != null){
                    tenantService.updateById(tenant);
            }else{
                    tenantService.insert(tenant);
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
                tenantService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Tenant get(@PathVariable("id" ) Long id) {
        return tenantService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Tenant> list() {

        return tenantService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<Tenant> json(@RequestBody TenantQuery query) {
        Page<Tenant> page = new Page<Tenant>(query.getPage(), query.getRows());
        page = tenantService.selectPage(page);
        return new PageList<Tenant>(page.getTotal(), page.getRecords());
    }
}
