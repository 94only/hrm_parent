package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.VipAddress;
import cn.wangningbo.hrm.query.VipAddressQuery;
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
public class VipAddressClientHystrixFallbackFactory implements FallbackFactory<VipAddressClient> {

    @Override
    public VipAddressClient create(Throwable throwable) {
        return new VipAddressClient() {
            @Override
            public AjaxResult save(VipAddress vipAddress) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public VipAddress get(Long id) {
                return null;
            }

            @Override
            public List<VipAddress> list() {
                return null;
            }

            @Override
            public PageList<VipAddress> json(VipAddressQuery query) {
                return null;
            }
        };
    }
}
