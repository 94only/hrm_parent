package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.VipGrowLog;
import cn.wangningbo.hrm.query.VipGrowLogQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangningbo
 * @since 2019-09-16
 */
@Component
public class VipGrowLogClientHystrixFallbackFactory implements FallbackFactory<VipGrowLogClient> {

    @Override
    public VipGrowLogClient create(Throwable throwable) {
        return new VipGrowLogClient() {
            @Override
            public AjaxResult save(VipGrowLog vipGrowLog) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public VipGrowLog get(Long id) {
                return null;
            }

            @Override
            public List<VipGrowLog> list() {
                return null;
            }

            @Override
            public PageList<VipGrowLog> json(VipGrowLogQuery query) {
                return null;
            }
        };
    }
}
