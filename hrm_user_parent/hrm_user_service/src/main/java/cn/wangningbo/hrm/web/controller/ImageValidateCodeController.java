package cn.wangningbo.hrm.web.controller;

import cn.wangningbo.hrm.service.IImageValidateCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 图形验证码
 */
@RestController
@RequestMapping("/imgCode")
public class ImageValidateCodeController {

    @Autowired
    private IImageValidateCodeService iImageValidateCodeService;

    // 获取图片验证码
    @GetMapping
    public String getCode(String uuid) {
        return iImageValidateCodeService.getCode(uuid);
    }
}
