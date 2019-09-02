package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.domain.Employee;
import cn.wangningbo.hrm.mapper.EmployeeMapper;
import cn.wangningbo.hrm.service.IEmployeeService;
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
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

}
