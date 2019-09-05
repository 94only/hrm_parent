package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.util.AjaxResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wangningbo
 * @since 2019-09-04
 */
@FeignClient(value = "ZUUL-GATEWAY", configuration = FeignClientsConfiguration.class,
        fallbackFactory = FastDFSClientHystrixFallbackFactory.class)
@RequestMapping("/fastdfs")
public interface FastDFSClient {
    /**
     * 上传
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    String upload(@RequestBody MultipartFile file);

    /**
     * 删除
     *
     * @param path
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    AjaxResult delete(@RequestParam("path") String path);

    /**
     * 下载
     *
     * @param path
     */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    void download(@RequestParam("path") String path);//直接把流写到response

}