package cn.wangningbo.hrm.mapper;

import cn.wangningbo.hrm.domain.Tenant;
import com.baomidou.mybatisplus.mapper.BaseMapper;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-02
 */
public interface TenantMapper extends BaseMapper<Tenant> {

    //保存中间表信息
    void saveTenantMeals(List<Map<String, Long>> mealsMap);

    //删除中间表信息
    void removeTenantMeal(Serializable id);
}
