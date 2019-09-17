package cn.wangningbo.hrm.handler;

import cn.wangningbo.hrm.client.FastDFSClient;
import cn.wangningbo.hrm.config.RabbitmqConstants;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import feign.Response;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Component
public class StaticPageDownloadHandler {
    //注入fastdfs，用于从fastdfs下载模板
    @Autowired
    private FastDFSClient fastDFSClient;

    @RabbitListener(queues = RabbitmqConstants.QUEUE_INFORM_PAGESTATIC)
    private void receiveHomeSite(String msg, Message message, Channel channel) {
        JSONObject jsonObject = JSONObject.parseObject(msg);
        // 获取到文件系统的类型，比如fastdfs，又或者其他的分布式文件系统
        Integer fileSysType = jsonObject.getInteger("fileSysType");
        // 获取到静态页面的地址
        String staticPageUrl = jsonObject.getString("staticPageUrl");
        // 获取到输出页面的物理路径
        String physicalPath = jsonObject.getString("physicalPath");
        // 根据分布式文件系统类型判断使用哪种分布式文件系统，我这里定义0就是fastdfs
        switch (fileSysType) {
            // fastdfs
            case 0:
                // 通过fastdfs下载文件，并拷贝到指定目录。 参数1：静态页面路径。参数2：物理路径。
                fastDfsDownloadAndCopy(staticPageUrl, physicalPath);
                break;
            // hdfs
            case 1:
                //@TODO hdfs的实现方法
                break;
            default:
                break;
        }
    }

    /**
     * 通过fastdfs下载文件,并且拷贝到特定的目录
     *
     * @param staticPageUrl
     * @param physicalPath
     */
    private void fastDfsDownloadAndCopy(String staticPageUrl, String physicalPath) {
        Response download = fastDFSClient.download(staticPageUrl);
        InputStream is = null;
        OutputStream os = null;
        try {
            is = download.body().asInputStream();
            os = new FileOutputStream(physicalPath);
            IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
