package com.example.shortlink.admin.toolkit;

import java.security.SecureRandom;

public class RandomStringUtils {

    // 定义包含数字和字母的字符集
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 生成指定长度的随机字符串，字符串包含大写字母、小写字母和数字。
     *
     * @param length 生成的字符串长度
     * @return 随机字符串
     */
    public static String generateRandomString(int length) {
        if (length <= 0) {
            throw new IllegalArgumentException("长度必须大于0");
        }

        StringBuilder sb = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }

        return sb.toString();
    }

    /**
     * 生成默认长度（6位）的随机字符串，字符串包含大写字母、小写字母和数字。
     *
     * @return 随机字符串
     */
    public static String generateRandomString() {
        return generateRandomString(6);
    }

    public static void main(String[] args) {
        // 使用工具类生成随机字符串
        System.out.println("6位随机字符串: " + generateRandomString());

        // 自定义长度的随机字符串
        System.out.println("10位随机字符串: " + generateRandomString(10));
    }
}

