package

        cn.wangningbo.hrm.web.controller;

import cn.wangningbo.hrm.service.ISystemdictionaryitemService;
import cn.wangningbo.hrm.domain.Systemdictionaryitem;
import cn.wangningbo.hrm.query.SystemdictionaryitemQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wangningbo
 * @since 2019-09-01
 */
@RestController
@RequestMapping("/systemdictionaryitem")
public class SystemdictionaryitemController {
    @Autowired
    public ISystemdictionaryitemService systemdictionaryitemService;

    /**
     * 保存和修改公用的
     *
     * @param systemdictionaryitem 传递的实体
     * @return Ajaxresult转换结果
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public AjaxResult save(@RequestBody Systemdictionaryitem systemdictionaryitem) {
        try {
            if (systemdictionaryitem.getId() != null) {
                systemdictionaryitemService.updateById(systemdictionaryitem);
            } else {
                systemdictionaryitemService.insert(systemdictionaryitem);
            }
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("保存对象失败！" + e.getMessage());
        }
    }

    /**
     * 删除对象信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public AjaxResult delete(@PathVariable("id") Long id) {
        try {
            systemdictionaryitemService.deleteById(id);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            return AjaxResult.me().setMessage("删除对象失败！" + e.getMessage());
        }
    }

    //获取一个（根据id）
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Systemdictionaryitem get(@PathVariable("id") Long id) {
        return systemdictionaryitemService.selectById(id);
    }

    //获取一个（根据sn）
    @RequestMapping(value = "/listSn", method = RequestMethod.GET)
    public List<Systemdictionaryitem> listByParentSn(String sn) {
        return systemdictionaryitemService.listByParentSn(sn);
    }

    /**
     * 查看所有的员工信息
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public List<Systemdictionaryitem> list() {

        return systemdictionaryitemService.selectList(null);
    }


    /**
     * 分页查询数据
     *
     * @param query 查询对象
     * @return PageList 分页对象
     */
    @RequestMapping(value = "/json", method = RequestMethod.POST)
    public PageList<Systemdictionaryitem> json(@RequestBody SystemdictionaryitemQuery query) {
        Page<Systemdictionaryitem> page = new Page<Systemdictionaryitem>(query.getPage(), query.getRows());
        page = systemdictionaryitemService.selectPage(page);
        return new PageList<Systemdictionaryitem>(page.getTotal(), page.getRecords());
    }
}
