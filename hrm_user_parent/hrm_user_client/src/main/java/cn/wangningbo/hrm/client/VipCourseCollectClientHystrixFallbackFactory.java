package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.VipCourseCollect;
import cn.wangningbo.hrm.query.VipCourseCollectQuery;
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
public class VipCourseCollectClientHystrixFallbackFactory implements FallbackFactory<VipCourseCollectClient> {

    @Override
    public VipCourseCollectClient create(Throwable throwable) {
        return new VipCourseCollectClient() {
            @Override
            public AjaxResult save(VipCourseCollect vipCourseCollect) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public VipCourseCollect get(Long id) {
                return null;
            }

            @Override
            public List<VipCourseCollect> list() {
                return null;
            }

            @Override
            public PageList<VipCourseCollect> json(VipCourseCollectQuery query) {
                return null;
            }
        };
    }
}
