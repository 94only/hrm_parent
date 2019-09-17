package cn.wangningbo.hrm.service.impl;

import cn.wangningbo.hrm.client.RedisClient;
import cn.wangningbo.hrm.service.IImageValidateCodeService;
import cn.wangningbo.hrm.util.VerifyCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class ImageValidateCodeImpl implements IImageValidateCodeService {
    @Autowired
    private RedisClient redisClient;

    @Override
    public String getCode(String uuid) {
        // 生成6位验证码,并将所有英文字母转换为小写字母
        String code = VerifyCodeUtils.generateVerifyCode(6).toLowerCase();
        // 把验证码存放到redis，key使用前端传过来的uuid，并设置过期时间为5分钟
        redisClient.set(uuid, code, 300);
        // 输出到图片
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 设置图片的长度，宽度，图片显示的内容
            VerifyCodeUtils.outputImage(100, 30, data, code);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 把图片加密返回
        return new BASE64Encoder().encode(data.toByteArray());
    }
}
