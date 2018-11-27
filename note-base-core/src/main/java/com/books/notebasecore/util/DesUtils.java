package com.books.notebasecore.util;

import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * des 加密解密工具类
 *                       
 * @Filename: DesUtils.java
 * @Version: 1.0
 * @Author: longsha
 * @Email: longsha@mightcloud.com
 *
 */
public class DesUtils {
    private static AlgorithmParameterSpec iv  = null; // 加密算法的参数接口，IvParameterSpec是它的一个实现  
    private static Key                    key = null;

    //    private static final String           keyTpl = "cd7be671";
    //    private static final String           ivTpl  = "1899b9ec4fde4248896519fe5be94f71"; //忽略
    //
    //    public static void main(String[] args) throws Exception {
    //        DesUtils tools = new DesUtils(keyTpl, ivTpl);
    //        System.out.println("加密:" + tools.encode("UId:24507"));
    //        System.out.println("解密:" + tools.decode("OlLzWkvSC1wFwrTCXWbHkw=="));
    //    }

    public DesUtils(String desKey, String desIv) throws Exception {
        DESKeySpec keySpec = new DESKeySpec(desKey.getBytes());// 设置密钥参数  
        iv = new IvParameterSpec((desIv.substring(0, 8)).getBytes());// 设置向量  
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");// 获得密钥工厂  
        key = keyFactory.generateSecret(keySpec);// 得到密钥对象  

    }

    /**
     * 加密方法
     * @param data
     * @throws Exception
     */
    public String encode(String data) throws Exception {
        Cipher enCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");// 得到加密对象Cipher  
        enCipher.init(Cipher.ENCRYPT_MODE, key, iv);// 设置工作模式为加密模式，给出密钥和向量  
        byte[] pasByte = enCipher.doFinal(data.getBytes("utf-8"));
        BASE64Encoder base64Encoder = new BASE64Encoder();
        return base64Encoder.encode(pasByte);
    }

    /**
     * 解密方法
     * @param data
     * @return
     * @throws Exception
     */
    public String decode(String data) throws Exception {
        Cipher deCipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        deCipher.init(Cipher.DECRYPT_MODE, key, iv);
        BASE64Decoder base64Decoder = new BASE64Decoder();
        byte[] pasByte = deCipher.doFinal(base64Decoder.decodeBuffer(data));
        return new String(pasByte, "UTF-8");
    }

}