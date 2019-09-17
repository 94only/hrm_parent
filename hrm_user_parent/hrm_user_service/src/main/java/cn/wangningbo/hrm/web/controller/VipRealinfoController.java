package

    cn.wangningbo.hrm.web.controller;

import cn.wangningbo.hrm.service.IVipRealinfoService;
import cn.wangningbo.hrm.domain.VipRealinfo;
import cn.wangningbo.hrm.query.VipRealinfoQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangningbo
 * @since 2019-09-16
 */
@RestController
@RequestMapping("/vipRealinfo" )
public class VipRealinfoController {
    @Autowired
    public IVipRealinfoService vipRealinfoService;

    /**
     * 保存和修改公用的
     * @param vipRealinfo 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody VipRealinfo vipRealinfo) {
        try {
            if (vipRealinfo.getId() != null){
                    vipRealinfoService.updateById(vipRealinfo);
            }else{
                    vipRealinfoService.insert(vipRealinfo);
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
                vipRealinfoService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public VipRealinfo get(@PathVariable("id" ) Long id) {
        return vipRealinfoService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<VipRealinfo> list() {

        return vipRealinfoService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<VipRealinfo> json(@RequestBody VipRealinfoQuery query) {
        Page<VipRealinfo> page = new Page<VipRealinfo>(query.getPage(), query.getRows());
        page = vipRealinfoService.selectPage(page);
        return new PageList<VipRealinfo>(page.getTotal(), page.getRecords());
    }
}
