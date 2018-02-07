package com.example.ruoxuanfu.retrofitrxjavafromentrytogiveup.utils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/***
 * 3DES加密
 *
 * @version v1.0
 */
public class DESUtils {

    public static final String KEY = "313262387ff47c8f0b4a3b4920917fea0d5d4654837fc7c2";

    private DESUtils() {

    }

    /**
     * 加密:
     *
     * @param data 密文的utf-8
     * @return String
     */
    public static String encrypt3DES(String data) {
        try {
            byte[] dataByte = data.getBytes("UTF-8");
            //恢复密钥
            SecretKey secretKey = new SecretKeySpec(hexStringToBytes(KEY), "DESede");
            //Cipher完成加密
            Cipher cipher = Cipher.getInstance("DESede");
            //cipher初始化
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return bytesToHexString(cipher.doFinal(dataByte));
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    /**
     * 解密
     *
     * @param data 密文的utf-8
     * @return String
     */
    public static String decrypt3DES(String data) {
        try {
            byte[] dataByte = hexStringToBytes(data);
            //恢复密钥
            SecretKey secretKey = new SecretKeySpec(hexStringToBytes(KEY), "DESede");
            //Cipher完成解密
            Cipher cipher = Cipher.getInstance("DESede");
            //初始化cipher
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(dataByte), "UTF-8");
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    public static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));

        }
        return d;
    }

    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
