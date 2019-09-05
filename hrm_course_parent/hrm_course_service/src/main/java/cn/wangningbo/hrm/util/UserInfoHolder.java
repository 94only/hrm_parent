package cn.wangningbo.hrm.util;


import cn.wangningbo.hrm.domain.Employee;
import cn.wangningbo.hrm.domain.Tenant;

public class UserInfoHolder {
    public static Tenant getTenant() {
        Tenant currentLoginUserTenant = new Tenant();
        currentLoginUserTenant.setId(26L);
        currentLoginUserTenant.setCompanyName("源码时代");
        return currentLoginUserTenant;
    }

    public static Employee getLoginUser() {
        Employee employee = new Employee();
        employee.setId(45L);
        employee.setUsername("二狗");
        return employee;
    }
}
