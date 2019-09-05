package cn.wangningbo.hrm.service;

import cn.wangningbo.hrm.domain.Systemdictionaryitem;
import com.baomidou.mybatisplus.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-01
 */
public interface ISystemdictionaryitemService extends IService<Systemdictionaryitem> {

    List<Systemdictionaryitem> listByParentSn(String sn);
}
