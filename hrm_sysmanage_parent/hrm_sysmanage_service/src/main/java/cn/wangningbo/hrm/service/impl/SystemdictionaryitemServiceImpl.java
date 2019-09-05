package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.domain.Systemdictionary;
import cn.wangningbo.hrm.domain.Systemdictionaryitem;
import cn.wangningbo.hrm.mapper.SystemdictionaryMapper;
import cn.wangningbo.hrm.mapper.SystemdictionaryitemMapper;
import cn.wangningbo.hrm.service.ISystemdictionaryitemService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-01
 */
@Service
public class SystemdictionaryitemServiceImpl extends ServiceImpl<SystemdictionaryitemMapper, Systemdictionaryitem> implements ISystemdictionaryitemService {

    private Logger logger = LoggerFactory.getLogger(SystemdictionaryitemServiceImpl.class);
    @Autowired
    private SystemdictionaryMapper systemdictionaryMapper;
    @Autowired
    private SystemdictionaryitemMapper systemdictionaryitemMapper;

    @Override
    public List<Systemdictionaryitem> listByParentSn(String sn) {
        Wrapper<Systemdictionary> wrapper = new EntityWrapper<Systemdictionary>().eq("sn", sn);
        List<Systemdictionary> systemdictionaries = systemdictionaryMapper.selectList(wrapper);
        if (systemdictionaries == null || systemdictionaries.size() != 1) {
            logger.error("systemdictionaries error!!!");
            return null;
        }
        Systemdictionary systemdictionary = systemdictionaries.get(0);
        EntityWrapper<Systemdictionaryitem> wrapper1 = new EntityWrapper<>();

        wrapper1.eq("parent_id", systemdictionary.getId());
        return systemdictionaryitemMapper.selectList(wrapper1);
    }
}
