package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.Pager;
import cn.wangningbo.hrm.query.PagerQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangningbo
 * @since 2019-09-09
 */
@Component
public class PagerClientHystrixFallbackFactory implements FallbackFactory<PagerClient> {

    @Override
    public PagerClient create(Throwable throwable) {
        return new PagerClient() {
            @Override
            public AjaxResult save(Pager pager) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public Pager get(Long id) {
                return null;
            }

            @Override
            public List<Pager> list() {
                return null;
            }

            @Override
            public PageList<Pager> json(PagerQuery query) {
                return null;
            }
        };
    }
}
