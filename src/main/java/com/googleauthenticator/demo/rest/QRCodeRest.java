package com.googleauthenticator.demo.rest;

import com.google.zxing.WriterException;
import com.googleauthenticator.demo.utils.GoogleAuthenticator;
import com.googleauthenticator.demo.utils.QRUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@RequestMapping("/api")
public class QRCodeRest {

    /**
     * 显示 Google Authenticator 二维码
     * @param content
     * @param width
     * @param height
     * @param response
     */
    @RequestMapping("/generate-QRCode")
    public void generateQRCode(String content,
                               @RequestParam(defaultValue = "300",required = false) int width,
                               @RequestParam(defaultValue = "300",required = false) int height,
                               HttpServletResponse response) {

        // 生成秘钥，该秘钥需要存储到 User 的字段内，将来验证的时候需要取出来
        String secret = GoogleAuthenticator.generateSecretKey();
        // 生成二维码的 otpauth:// 形式链接
        String QRCode = GoogleAuthenticator.getQRBarcode("Bonismo", secret);

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            QRUtil.writeToStream(QRCode, outputStream, width, height);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void main(String[] args) throws WriterException {
        ServletOutputStream outputStream = null;
        HttpServletResponse response = null;

        // 1、返回一个 BufferedImage 对象
        BufferedImage bufferedImage = QRUtil.toBufferedImage("otpauth://totp/Bonismo?secret=3FRUFW6NLOGPVYKK", 300, 300);
        System.out.println(bufferedImage);

        // 2、生成一个二维码留
        try {
            outputStream = response.getOutputStream();
            QRUtil.writeToStream("otpauth://totp/Bonismo?secret=3FRUFW6NLOGPVYKK", outputStream, 300, 300);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

