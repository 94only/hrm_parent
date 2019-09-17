package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.VipMsg;
import cn.wangningbo.hrm.query.VipMsgQuery;
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
public class VipMsgClientHystrixFallbackFactory implements FallbackFactory<VipMsgClient> {

    @Override
    public VipMsgClient create(Throwable throwable) {
        return new VipMsgClient() {
            @Override
            public AjaxResult save(VipMsg vipMsg) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public VipMsg get(Long id) {
                return null;
            }

            @Override
            public List<VipMsg> list() {
                return null;
            }

            @Override
            public PageList<VipMsg> json(VipMsgQuery query) {
                return null;
            }
        };
    }
}
