package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.VipLoginLog;
import cn.wangningbo.hrm.query.VipLoginLogQuery;
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
public class VipLoginLogClientHystrixFallbackFactory implements FallbackFactory<VipLoginLogClient> {

    @Override
    public VipLoginLogClient create(Throwable throwable) {
        return new VipLoginLogClient() {
            @Override
            public AjaxResult save(VipLoginLog vipLoginLog) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public VipLoginLog get(Long id) {
                return null;
            }

            @Override
            public List<VipLoginLog> list() {
                return null;
            }

            @Override
            public PageList<VipLoginLog> json(VipLoginLogQuery query) {
                return null;
            }
        };
    }
}
