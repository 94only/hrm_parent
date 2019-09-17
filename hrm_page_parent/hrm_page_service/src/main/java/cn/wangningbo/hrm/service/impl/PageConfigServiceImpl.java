package cn.wangningbo.hrm.service.impl;


import cn.wangningbo.hrm.client.FastDFSClient;
import cn.wangningbo.hrm.client.RedisClient;
import cn.wangningbo.hrm.config.RabbitmqConstants;
import cn.wangningbo.hrm.domain.PageConfig;
import cn.wangningbo.hrm.domain.Pager;
import cn.wangningbo.hrm.domain.Site;
import cn.wangningbo.hrm.dto.CourseTypeDto;
import cn.wangningbo.hrm.mapper.PageConfigMapper;
import cn.wangningbo.hrm.mapper.PagerMapper;
import cn.wangningbo.hrm.mapper.SiteMapper;
import cn.wangningbo.hrm.service.IPageConfigService;
import cn.wangningbo.hrm.util.VelocityUtils;
import cn.wangningbo.hrm.util.ZipUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import feign.Response;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.IOUtils;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wangningbo
 * @since 2019-09-09
 */
@Service
public class PageConfigServiceImpl extends ServiceImpl<PageConfigMapper, PageConfig> implements IPageConfigService {
    @Autowired
    private PagerMapper pagerMapper;
    @Autowired
    private FastDFSClient fastDFSClient;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private PageConfigMapper pageConfigMapper;
    @Autowired
    private SiteMapper siteMapper;
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void staticPage(String dataKey, String pageName) {
        //一、页面静态化
        // 根据name去数据库中查询name记录，获取到第一个
        Pager pager = pagerMapper.selectList(new EntityWrapper<Pager>().eq("name", pageName)).get(0);
        // 获取到fastdfs中模板压缩包的路径
        String templateUrl = pager.getTemplateUrl();
        // 获取要执行的模板文件
        String templateName = pager.getTemplateName();
        // 下载fastdfs上面的模板压缩包
        Response response = fastDFSClient.download(templateUrl);
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        try {
            //获得输入流资源
            inputStream = response.body().asInputStream();
            // 所有静态化中数据都写入操作系统的临时目录（这个可以跨操作系统，而且临时目录的东西会自动维护，无需手动删除）
            String tempdir = System.getProperty("java.io.tempdir");
            System.out.println(tempdir + "-------------------------------临时目录");
            // 要下载压缩包的路径
            String zipPath = tempdir + "/temp.zip";
            // 要解压到的路径
            String unZipPath = tempdir + "/temp/";
            // 获得输出流资源
            fileOutputStream = new FileOutputStream(zipPath);
            // 保存到本地 //import org.apache.commons.io.IOUtils;
            IOUtils.copy(inputStream, fileOutputStream); //保存到本地

            // 解压缩
            ZipUtil.unzip(zipPath, unZipPath);
            // 获取到模板 //模板路径 temp/home.vm
            String templatePath = unZipPath + "/" + templateName;
            System.out.println(templatePath + "-----------------------------------模板路径");
            // 获取到生成生成静态页面的路径 //本地静态页面的地址
            String templatePagePath = templatePath + ".html";
            System.out.println(templatePagePath + "----------------------------------本地静态页面的地址");

            // 生成页面需要的数据 //从redis中获取
            String courseTypes = redisClient.get("courseTypes");
            // json字符串转list
            List<CourseTypeDto> courseTypeDtos = JSONArray.parseArray(courseTypes, CourseTypeDto.class);
            // 封装两个参数成一个对象传入进去
            HashMap<String, Object> map = new HashMap<>();
            map.put("staticRoot", unZipPath);
            map.put("courseTypes", courseTypeDtos);

            // 进行页面静态化
            VelocityUtils.staticByTemplate(map, templatePath, templatePagePath);
            // 传递到fastdfs
            String pageUrl = fastDFSClient.upload(
                    new CommonsMultipartFile(createFileItem(new File(templatePagePath), "file")));

            // 二 PageConfig 并且进行保存
            PageConfig config = new PageConfig();
            config.setTemplateUrl(templateUrl);
            config.setTemplateName(templateName);
            config.setDataKey(dataKey);
            config.setPhysicalPath(pager.getPhysicalPath());
            config.setDfsType(0L); //0表示fastDfs
            config.setPageUrl(pageUrl);
            config.setPageId(pager.getId());
            pageConfigMapper.insert(config);

            // 三、往消息队列放入消息
            String routingKey = siteMapper
                    .selectList(new EntityWrapper<Site>().eq("id", pager.getSiteId())).get(0).getSn();
            System.out.println(routingKey + "----------------------------routingKey");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("fileSysType", 0);
            jsonObject.put("staticPageUrl", pageUrl);
            jsonObject.put("physicalPath", pager.getPhysicalPath());
            System.out.println(jsonObject.toJSONString() + "---------------------------------------------jsonObject.toJSONString()");
            rabbitTemplate.convertAndSend(
                    RabbitmqConstants.EXCHANGE_DIRECT_INFORM, routingKey, jsonObject.toJSONString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 创建FileItem
     *
     * @param file
     * @param filedName
     * @return
     */
    private FileItem createFileItem(File file, String filedName) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem(filedName, "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }
}
