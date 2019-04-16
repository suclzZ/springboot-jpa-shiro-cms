package com.test.sbjms;

import org.apache.shiro.codec.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author sucl
 * @date 2019/4/12
 */
public class MyTest {

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {

//        byte[] code = Base64.decode("03RvItlW1tAE6QOkgaVtjA==");
//        System.out.println(new String(code));
    }

    private static String randomBase64() throws NoSuchAlgorithmException {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecretKey deskey = keygen.generateKey();
        String b64Str = Base64.encodeToString(deskey.getEncoded());
        System.out.println(b64Str);
        return b64Str;
    }
}
