package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.domain.Permission;
import cn.wangningbo.hrm.mapper.PermissionMapper;
import cn.wangningbo.hrm.service.IPermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements IPermissionService {

}
