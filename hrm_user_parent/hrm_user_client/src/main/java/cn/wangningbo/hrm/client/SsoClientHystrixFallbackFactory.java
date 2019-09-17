package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.Sso;
import cn.wangningbo.hrm.query.SsoQuery;
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
public class SsoClientHystrixFallbackFactory implements FallbackFactory<SsoClient> {

    @Override
    public SsoClient create(Throwable throwable) {
        return new SsoClient() {
            @Override
            public AjaxResult save(Sso sso) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public Sso get(Long id) {
                return null;
            }

            @Override
            public List<Sso> list() {
                return null;
            }

            @Override
            public PageList<Sso> json(SsoQuery query) {
                return null;
            }
        };
    }
}
