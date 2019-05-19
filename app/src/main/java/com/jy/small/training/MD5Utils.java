package com.jy.small.training;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/*
 * created by taofu on 2019/5/9
 **/
public class MD5Utils {

    public static void main(String args[] ){

      //  System.out.println(md5("123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456"));
        //System.out.println(md5_2("123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456"));
        //System.out.println(md5_3("123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456123456"));

        System.out.println(md5_3("zhangsan@163.com9bad41710724cf6511abde2a52416881" ));
        System.out.println(md5("lisi@163.com872d0f7c57e81f756ec9f819d40a7301"));
    }



    //写一个md5加密的方法
    public static String md5(String plainText) {
        long start = System.currentTimeMillis();
        //定义一个字节数组
        byte[] secretBytes = null;
        try {
            // 生成一个MD5加密计算摘要
            MessageDigest md = MessageDigest.getInstance("MD5");
            //对字符串进行加密
            md.update(plainText.getBytes());
            //获得加密后的数据
            secretBytes = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有md5这个算法！");
        }
        //将加密后的数据转换为16进制数字
        String md5code = new BigInteger(1, secretBytes).toString(16);// 16进制数字
        // 如果生成数字未满32位，需要前面补0
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        System.out.println("tiem = " + (System.currentTimeMillis() - start));
        return md5code;
    }
    public static String md5_2(String password) {
        long start = System.currentTimeMillis();
        try {
            // 得到一个信息摘要器
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer buffer = new StringBuffer();
            // 把每一个byte 做一个与运算 0xff;
            for (byte b : result) {
                // 与运算
                int number = b & 0xff;// 加盐
                String str = Integer.toHexString(number);
                if (str.length() == 1) {
                    buffer.append("0");
                }
                buffer.append(str);
            }
            System.out.println("tiem = " + (System.currentTimeMillis() - start));
            // 标准的md5加密后的结果
            return buffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }

    }

    public static String md5_3(String key) {

        char hexDigits[] = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
        };
        long start = System.currentTimeMillis();
        try {
            byte[] btInput = key.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("md5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            System.out.println("tiem = " + (System.currentTimeMillis() - start));
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

}
