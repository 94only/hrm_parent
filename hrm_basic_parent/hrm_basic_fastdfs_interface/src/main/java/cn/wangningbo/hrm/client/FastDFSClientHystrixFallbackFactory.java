package cn.wangningbo.hrm.client;

import cn.wangningbo.hrm.util.AjaxResult;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FastDFSClientHystrixFallbackFactory implements FallbackFactory<FastDFSClient> {
    @Override
    public FastDFSClient create(Throwable throwable) {
        return new FastDFSClient() {
            @Override
            public String upload(MultipartFile file) {
                return null;
            }

            @Override
            public AjaxResult delete(String path) {
                return null;
            }

            @Override
            public void download(String path) {

            }
        };
    }
}
