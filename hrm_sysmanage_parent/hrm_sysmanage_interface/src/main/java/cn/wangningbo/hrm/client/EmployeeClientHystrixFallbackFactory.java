package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.Employee;
import cn.wangningbo.hrm.query.EmployeeQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangningbo
 * @since 2019-09-02
 */
@Component
public class EmployeeClientHystrixFallbackFactory implements FallbackFactory<EmployeeClient> {

    @Override
    public EmployeeClient create(Throwable throwable) {
        return new EmployeeClient() {
            @Override
            public AjaxResult save(Employee employee) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public Employee get(Long id) {
                return null;
            }

            @Override
            public List<Employee> list() {
                return null;
            }

            @Override
            public PageList<Employee> json(EmployeeQuery query) {
                return null;
            }
        };
    }
}
