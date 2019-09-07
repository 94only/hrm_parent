package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.CourseType;
import cn.wangningbo.hrm.query.CourseTypeQuery;
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
public class CourseTypeClientHystrixFallbackFactory implements FallbackFactory<CourseTypeClient> {

    @Override
    public CourseTypeClient create(Throwable throwable) {
        return new CourseTypeClient() {
            @Override
            public AjaxResult save(CourseType courseType) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public CourseType get(Long id) {
                return null;
            }

            @Override
            public List<CourseType> list() {
                return null;
            }

            @Override
            public PageList<CourseType> json(CourseTypeQuery query) {
                return null;
            }
        };
    }
}
