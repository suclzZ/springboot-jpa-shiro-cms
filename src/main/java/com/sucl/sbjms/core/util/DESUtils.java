package com.sucl.sbjms.core.util;

import com.sucl.sbjms.core.rem.BusException;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * @author sucl
 * @date 2019/4/20
 */
public class DESUtils {
    private static final String ALGORITHM_DESEDE = "DESede";
    private static final String DECRYPT_ENCODING = "UTF-8";

    public static String encrypt(String key, String info) {
        byte[] desKey;
        try {
            desKey = build3DesKey(key);
        } catch (Exception e) {
            throw new RuntimeException("秘钥构建失败：" + e.getMessage());
        }
        SecretKey secretKey = new SecretKeySpec(desKey, ALGORITHM_DESEDE);
        Cipher cipher = null;
        byte[] byteResult = (byte[]) null;
        try {
            cipher = Cipher.getInstance(ALGORITHM_DESEDE);
        } catch (NoSuchAlgorithmException e) {
            throw new BusException(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            throw new BusException(e.getMessage(), e);
        }

        if (cipher != null) {
            try {
                cipher.init(1, secretKey);
            } catch (InvalidKeyException e) {
                throw new BusException(e.getMessage(), e);
            }

            try {
                byteResult = cipher.doFinal(info.getBytes(DECRYPT_ENCODING));
            } catch (IllegalBlockSizeException e) {
                throw new BusException(e.getMessage(), e);
            } catch (BadPaddingException e) {
                throw new BusException(e.getMessage(), e);
            } catch (UnsupportedEncodingException e) {
                throw new BusException(e.getMessage(), e);
            }
        }

        if (byteResult == null) {
            return "";
        }

        return byte2HexStr(byteResult);
    }

    public static String decrypt(String dest, String key)
            throws IllegalBlockSizeException, BadPaddingException {
        SecretKey secretKey = new SecretKeySpec(build3DesKey(key), "DESede");

        Cipher cipher = null;
        try {
            cipher = Cipher.getInstance("DESede");
        } catch (NoSuchAlgorithmException e) {
            throw new BusException(e.getMessage(), e);
        } catch (NoSuchPaddingException e) {
            throw new BusException(e.getMessage(), e);
        }

        byte[] byteResult = (byte[]) null;
        if (cipher != null) {
            try {
                cipher.init(2, secretKey);
            } catch (InvalidKeyException e) {
                throw new BusException(e.getMessage(), e);
            }
            byteResult = cipher.doFinal(str2ByteArray(dest));
        }
        try {
            return new String(byteResult, DECRYPT_ENCODING);
        } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
        }
        return "";
    }

    private static String byte2HexStr(byte[] b) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String s = Integer.toHexString(b[i] & 0xFF);
            if (s.length() == 1) {
                sb.append("0");
            }
            sb.append(s.toUpperCase());
        }
        return sb.toString();
    }

    private static byte[] str2ByteArray(String s) {
        int byteArrayLength = s.length() / 2;
        byte[] b = new byte[byteArrayLength];
        for (int i = 0; i < byteArrayLength; i++) {
            byte b0 = (byte) Integer.valueOf(s.substring(i * 2, i * 2 + 2), 16).intValue();
            b[i] = b0;
        }
        return b;
    }

    private static byte[] build3DesKey(String keyStr) {
        byte[] key = new byte[24];
        try {
            byte[] temp = keyStr.getBytes(DECRYPT_ENCODING);
            if (key.length > temp.length)
                System.arraycopy(temp, 0, key, 0, temp.length);
            else
                System.arraycopy(temp, 0, key, 0, key.length);
        } catch (UnsupportedEncodingException localUnsupportedEncodingException) {
        }
        return key;
    }
}
