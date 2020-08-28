package com.example.appofzhejiang.Login;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {
    //md5 加密算法
    public static String md5(String text) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("md5");
            // 数组 byte[] result -> digest.digest( );  文本 text.getBytes();
            byte[] result = digest.digest(text.getBytes());
            //创建StringBuilder对象 然后建议StringBuffer，安全性高
            //StringBuilder sb = new StringBuilder();
            StringBuffer sBuffer = new StringBuffer();
            // result数组，digest.digest ( ); -> text.getBytes();
            // for 循环数组byte[] result;
            for (byte b : result){
                // 0xff 为16进制
                int number = b & 0xff;
                // number值 转换 字符串 Integer.toHexString( );
                String hex = Integer.toHexString(number);
                if (hex.length() == 1){
                    sBuffer.append("0"+hex);
                }else {
                    sBuffer.append(hex);
                }
            }
            //sb StringBuffer sb = new StringBuffer();对象实例化
            return sBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //发送异常return空字符串
            return "";
        }
    }
}
