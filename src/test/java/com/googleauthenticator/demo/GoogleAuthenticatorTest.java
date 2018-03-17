package com.googleauthenticator.demo;

import com.googleauthenticator.demo.utils.GoogleAuthenticator;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

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
}
