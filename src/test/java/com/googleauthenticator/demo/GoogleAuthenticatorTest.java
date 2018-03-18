package com.googleauthenticator.demo;

import com.googleauthenticator.demo.utils.GoogleAuthenticator;
import com.googleauthenticator.demo.utils.QRUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RunWith(SpringRunner.class)
public class GoogleAuthenticatorTest {

    private String secret = "3FRUFW6NLOGPVYKK";

    /**
     * 生产 二维码地址，双方约定的秘钥
     */
    @Test
    public void genSecretTest() {
        String QRCode = GoogleAuthenticator.getQRBarcode("Bonismo", secret);
        System.out.println(" QRCode : " + QRCode + " key : " + secret);
    }

    @Test
    public void verifyTest() {
        String code = "256513";
        long t = System.currentTimeMillis();
        GoogleAuthenticator g = new GoogleAuthenticator();
        g.setWindowSize(5);
        boolean verify = g.check_code(secret, code, t);
        System.out.println("验证结果" + verify);
    }

    @Test
    public void generateQRCode() {
        HttpServletResponse response = null;
        // 生成秘钥，该秘钥需要存储到 User 的字段内，将来验证的时候需要取出来
        String secret = GoogleAuthenticator.generateSecretKey();
        // 生成二维码的 otpauth:// 形式链接
        String QRCode = GoogleAuthenticator.getQRBarcode("Bonismo", secret);

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            QRUtil.writeToStream(QRCode, outputStream, 300, 300);
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
