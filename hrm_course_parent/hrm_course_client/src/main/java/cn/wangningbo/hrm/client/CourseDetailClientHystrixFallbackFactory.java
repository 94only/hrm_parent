package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.CourseDetail;
import cn.wangningbo.hrm.query.CourseDetailQuery;
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
public class CourseDetailClientHystrixFallbackFactory implements FallbackFactory<CourseDetailClient> {

    @Override
    public CourseDetailClient create(Throwable throwable) {
        return new CourseDetailClient() {
            @Override
            public AjaxResult save(CourseDetail courseDetail) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public CourseDetail get(Long id) {
                return null;
            }

            @Override
            public List<CourseDetail> list() {
                return null;
            }

            @Override
            public PageList<CourseDetail> json(CourseDetailQuery query) {
                return null;
            }
        };
    }
}
