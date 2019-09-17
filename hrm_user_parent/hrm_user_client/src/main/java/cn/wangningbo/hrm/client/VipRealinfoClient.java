package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.VipRealinfo;
import cn.wangningbo.hrm.query.VipRealinfoQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangningbo
 * @since 2019-09-16
 */
@FeignClient(value = "HRM-USER", configuration = FeignClientsConfiguration.class,
        fallbackFactory = VipRealinfoClientHystrixFallbackFactory.class)
@RequestMapping("/vipRealinfo" )
public interface VipRealinfoClient {
    /**
     * 保存和修改公用的
     * @param vipRealinfo 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    AjaxResult save(VipRealinfo vipRealinfo);

    /**
     * 删除对象信息
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    AjaxResult delete(@PathVariable("id" ) Integer id);

    //获取用户
    @RequestMapping("/{id}" )
        VipRealinfo get(@RequestParam(value = "id", required = true) Long id);


    /**
     * 查看所有
     * @return
     */
    @RequestMapping("/list" )
    public List<VipRealinfo> list();

    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    PageList<VipRealinfo> json(@RequestBody VipRealinfoQuery query);
}
