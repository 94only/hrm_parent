package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.VipCourseView;
import cn.wangningbo.hrm.query.VipCourseViewQuery;
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
public class VipCourseViewClientHystrixFallbackFactory implements FallbackFactory<VipCourseViewClient> {

    @Override
    public VipCourseViewClient create(Throwable throwable) {
        return new VipCourseViewClient() {
            @Override
            public AjaxResult save(VipCourseView vipCourseView) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public VipCourseView get(Long id) {
                return null;
            }

            @Override
            public List<VipCourseView> list() {
                return null;
            }

            @Override
            public PageList<VipCourseView> json(VipCourseViewQuery query) {
                return null;
            }
        };
    }
}
