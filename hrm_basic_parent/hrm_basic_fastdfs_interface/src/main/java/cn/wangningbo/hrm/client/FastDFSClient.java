package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrmconfig.FeignMultipartSupportConfig;
import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.FeignClientsConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author wangningbo
 * @since 2019-09-04
 */
//@FeignClient(value = "ZUUL-FASTDFS", configuration = FeignClientsConfiguration.class,
@FeignClient(value = "ZUUL-GATEWAY", configuration = FeignMultipartSupportConfig.class,
        fallbackFactory = FastDFSClientHystrixFallbackFactory.class)
@RequestMapping("/fastdfs")
public interface FastDFSClient {
    /**
     * 上传
     *
     * @param file
     * @return
     */
//    @RequestMapping(value = "/upload", method = RequestMethod.POST)
//    String upload(@RequestBody MultipartFile file);
    @PostMapping(value="/upload", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}
            , consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    String upload(@RequestPart("file") MultipartFile file);
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
    @GetMapping(value = "/download", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    //直接把流写到Response // 注意：这个Response是feign的
    Response download(@RequestParam("path") String path);

}