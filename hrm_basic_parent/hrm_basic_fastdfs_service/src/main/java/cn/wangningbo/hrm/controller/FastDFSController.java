package cn.wangningbo.hrm.controller;

import cn.wangningbo.hrm.util.AjaxResult;
import cn.wangningbo.hrm.util.FastDfsApiOpr;
import com.sun.xml.internal.messaging.saaj.util.ByteInputStream;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.nio.ch.IOUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/fastdfs")
public class FastDFSController {
    // 使用slf4j的logger记录错误到文件里 在每处报错的地方使用error方法记录错误
    Logger logger = LoggerFactory.getLogger(FastDFSController.class);

    /**
     * 上传
     *
     * @param file
     * @return
     */
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) {
        try {
            //拿到上传的文件名
            String fileName = file.getOriginalFilename();
            //获取文件的后缀名
            String extName = fileName.substring(fileName.lastIndexOf(".") + 1);
            System.out.println(extName);
            //上传文件 文件的字节数组和后缀名
            return FastDfsApiOpr.upload(file.getBytes(), extName);
        } catch (Exception e) {
            e.printStackTrace();
            //记录错误到日志文件
            logger.error("upload_error..." + e.getMessage());
        }
        return null;
    }

    /**
     * 删除
     *
     * @param path
     * @return
     */
    @DeleteMapping("/delete")
    public AjaxResult delete(@RequestParam("path") String path) {
        //假设前台传过来是/group1/xxx
        try {
            //获取相对路径    // 去掉路径的第1个字符 //拿到的就是group1/xxx //
            String pathTemp = path.substring(1);
            // 获取组名     //以/进行分割，拿到分割后的第一块
            String groupName = pathTemp.substring(0, pathTemp.indexOf("/"));
            //indexOf("/)返回"/"在字符串中第一次出现处的索引
            //substring(数字n)去掉pathTemp这个字符串前面的n个字符
            String remotePath = pathTemp.substring(pathTemp.indexOf("/") + 1);
            System.out.println(groupName);
            System.out.println(remotePath);
            FastDfsApiOpr.delete(groupName, remotePath);
            return AjaxResult.me();
        } catch (Exception e) {
            e.printStackTrace();
            //记录错误到日志文件
            logger.error("delete_error..." + e.getMessage());
            return AjaxResult.me().setSuccess(false).setMessage(e.getMessage());
        }
    }

    /**
     * 下载
     *
     * @param path
     * @param response
     */
    @GetMapping("/download")
    public void download(@RequestParam("path") String path, HttpServletResponse response) {
        //获取相对路径    // 去掉路径的第1个字符 //拿到的就是group1/xxx //
        String pathTemp = path.substring(1);
        //获取第一个"/"之前的字符 //获取组名
        String groupName = pathTemp.substring(0, pathTemp.indexOf("/"));
        //indexOf("/)返回"/"在字符串中第一次出现处的索引
        //substring(数字n)去掉pathTemp这个字符串前面的n个字符
        String remotePath = pathTemp.substring(pathTemp.indexOf("/") + 1);
        System.out.println(groupName);
        System.out.println(remotePath);
        //注意关流
        OutputStream os = null;
        InputStream is = null;
        try {
            byte[] datas = FastDfsApiOpr.download(groupName, remotePath);
            os = response.getOutputStream();//直接以流的方式返回
            is = new ByteInputStream(datas, datas.length);
            IOUtils.copy(is, os);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("download_error..." + e.getMessage());
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
