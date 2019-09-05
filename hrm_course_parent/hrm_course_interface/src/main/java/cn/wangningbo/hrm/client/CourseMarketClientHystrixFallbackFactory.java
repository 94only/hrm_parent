package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.CourseMarket;
import cn.wangningbo.hrm.query.CourseMarketQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangningbo
 * @since 2019-09-04
 */
@Component
public class CourseMarketClientHystrixFallbackFactory implements FallbackFactory<CourseMarketClient> {

    @Override
    public CourseMarketClient create(Throwable throwable) {
        return new CourseMarketClient() {
            @Override
            public AjaxResult save(CourseMarket courseMarket) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public CourseMarket get(Long id) {
                return null;
            }

            @Override
            public List<CourseMarket> list() {
                return null;
            }

            @Override
            public PageList<CourseMarket> json(CourseMarketQuery query) {
                return null;
            }
        };
    }
}
