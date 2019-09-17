package cn.wangningbo.hrm.service;

import cn.wangningbo.hrm.domain.PageConfig;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-09
 */
public interface IPageConfigService extends IService<PageConfig> {

    void staticPage(String dataKey, String pageName);
}
