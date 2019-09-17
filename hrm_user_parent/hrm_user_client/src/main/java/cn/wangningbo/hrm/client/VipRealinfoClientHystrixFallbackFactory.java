package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.VipRealinfo;
import cn.wangningbo.hrm.query.VipRealinfoQuery;
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
public class VipRealinfoClientHystrixFallbackFactory implements FallbackFactory<VipRealinfoClient> {

    @Override
    public VipRealinfoClient create(Throwable throwable) {
        return new VipRealinfoClient() {
            @Override
            public AjaxResult save(VipRealinfo vipRealinfo) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public VipRealinfo get(Long id) {
                return null;
            }

            @Override
            public List<VipRealinfo> list() {
                return null;
            }

            @Override
            public PageList<VipRealinfo> json(VipRealinfoQuery query) {
                return null;
            }
        };
    }
}
