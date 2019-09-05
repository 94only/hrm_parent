package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.domain.Employee;
import cn.wangningbo.hrm.domain.Tenant;
import cn.wangningbo.hrm.mapper.EmployeeMapper;
import cn.wangningbo.hrm.mapper.TenantMapper;
import cn.wangningbo.hrm.service.ITenantService;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-02
 */
@Service
public class TenantServiceImpl extends ServiceImpl<TenantMapper, Tenant> implements ITenantService {
    //注入租户
    @Autowired
    private TenantMapper tenantMapper;
    //注入用户
    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public boolean insert(Tenant tenant) {
        //添加机构
        tenant.setRegisterTime(new Date());
        tenant.setState(false);
        tenantMapper.insert(tenant);
        System.out.println("添加后返回的id："+tenant.getId());
        //添加管理员
        Employee adminUser = tenant.getAdminUser();
        adminUser.setInputTime(new Date());
        adminUser.setTenantId(tenant.getId());
        adminUser.setType(true);//是否是租户管理员
        adminUser.setState(0);
        employeeMapper.insert(adminUser);
        //添加套餐中间表
        tenantMapper.saveTenantMeals(tenant.getMealsMap());
        return true;
    }

    @Override
    public boolean deleteById(Serializable id) {
        //删除机构
        tenantMapper.deleteById(id);
        //删除管理员 //根据条件删除
        Wrapper<Employee> wapper = new EntityWrapper<>();
        wapper.eq("tenant_id",id);
        employeeMapper.delete(wapper);
        //删除中间表
        tenantMapper.removeTenantMeal(id);
        return true;
    }

    @Override
    public boolean updateById(Tenant tenant) {
        //修改机构
        tenantMapper.updateById(tenant);
        //修改管理员
        employeeMapper.updateById(tenant.getAdminUser());
        //修改中间表 //这里采用先删除后添加的方式
        tenantMapper.removeTenantMeal(tenant.getId());
        tenantMapper.saveTenantMeals(tenant.getMealsMap());
        return true;
    }
}
