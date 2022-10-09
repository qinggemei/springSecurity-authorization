package com.star.controller;

import com.google.code.kaptcha.Producer;
import com.star.common.ResultVo;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @author liuxing
 * @description
 * @create: 2022-10-07 23:47
 */
@RestController
public class LoginController {


    @Resource
    private Producer producer;

    @GetMapping("/vc.jpg")
    public ResultVo getVerifyCode(HttpServletResponse response, HttpSession session) throws IOException {
        response.setContentType("image/png");
        // 生成验证码
        String code = producer.createText();
        session.setAttribute("kaptcha", code);//可以更换成 redis 实现
        System.out.println("验证码：" + code);
        BufferedImage bi = producer.createImage(code);
        // 写入内存
        FastByteArrayOutputStream fos = new FastByteArrayOutputStream();
        ImageIO.write(bi, "png", fos);
        // 生成base64
        return ResultVo.success(Base64.encodeBase64String(fos.toByteArray()));
    }
}
