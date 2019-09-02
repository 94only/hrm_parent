package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.Permission;
import cn.wangningbo.hrm.query.PermissionQuery;
import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.PageList;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author wangningbo
 * @since 2019-09-02
 */
@Component
public class PermissionClientHystrixFallbackFactory implements FallbackFactory<PermissionClient> {

    @Override
    public PermissionClient create(Throwable throwable) {
        return new PermissionClient() {
            @Override
            public AjaxResult save(Permission permission) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public Permission get(Long id) {
                return null;
            }

            @Override
            public List<Permission> list() {
                return null;
            }

            @Override
            public PageList<Permission> json(PermissionQuery query) {
                return null;
            }
        };
    }
}
