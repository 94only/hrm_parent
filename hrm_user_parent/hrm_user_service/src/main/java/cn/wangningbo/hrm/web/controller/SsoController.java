package

    cn.wangningbo.hrm.web.controller;

import cn.wangningbo.hrm.service.ISsoService;
import cn.wangningbo.hrm.domain.Sso;
import cn.wangningbo.hrm.query.SsoQuery;
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
@RequestMapping("/sso" )
public class SsoController {
    @Autowired
    public ISsoService ssoService;

    /**
     * 保存和修改公用的
     * @param sso 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Sso sso) {
        try {
            if (sso.getId() != null){
                    ssoService.updateById(sso);
            }else{
                    ssoService.insert(sso);
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
                ssoService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取用户
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Sso get(@PathVariable("id" ) Long id) {
        return ssoService.selectById(id);
    }


    /**
    * 查看所有的员工信息
    * @return
    */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Sso> list() {

        return ssoService.selectList(null);
    }


    /**
    * 分页查询数据
    *
    * @param query 查询对象
    * @return PageList 分页对象
    */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<Sso> json(@RequestBody SsoQuery query) {
        Page<Sso> page = new Page<Sso>(query.getPage(), query.getRows());
        page = ssoService.selectPage(page);
        return new PageList<Sso>(page.getTotal(), page.getRecords());
    }
}
