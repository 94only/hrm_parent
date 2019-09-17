package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.VipBase;
import cn.wangningbo.hrm.query.VipBaseQuery;
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
public class VipBaseClientHystrixFallbackFactory implements FallbackFactory<VipBaseClient> {

    @Override
    public VipBaseClient create(Throwable throwable) {
        return new VipBaseClient() {
            @Override
            public AjaxResult save(VipBase vipBase) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public VipBase get(Long id) {
                return null;
            }

            @Override
            public List<VipBase> list() {
                return null;
            }

            @Override
            public PageList<VipBase> json(VipBaseQuery query) {
                return null;
            }
        };
    }
}
