package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.domain.Menu;
import cn.wangningbo.hrm.query.MenuQuery;
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
public class MenuClientHystrixFallbackFactory implements FallbackFactory<MenuClient> {

    @Override
    public MenuClient create(Throwable throwable) {
        return new MenuClient() {
            @Override
            public AjaxResult save(Menu menu) {
                return null;
            }

            @Override
            public AjaxResult delete(Integer id) {
                return null;
            }

            @Override
            public Menu get(Long id) {
                return null;
            }

            @Override
            public List<Menu> list() {
                return null;
            }

            @Override
            public PageList<Menu> json(MenuQuery query) {
                return null;
            }
        };
    }
}
