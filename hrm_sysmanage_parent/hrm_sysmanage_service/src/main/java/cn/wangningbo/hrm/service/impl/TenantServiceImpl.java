package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.domain.Tenant;
import cn.wangningbo.hrm.mapper.TenantMapper;
import cn.wangningbo.hrm.service.ITenantService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-02
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {

}
