package com.atguigu.atcrowdfunding.util;

/**
 * 判断字符串是否为空的util
 */
public class StringUtil {
    /*判断传过来的字符串不等于空*/
    public static boolean isNotEmpty(String inStr) {
        return !isEmpty(inStr);
    }

    /*判断传过来的字符串是否为空*/
    public static boolean isEmpty(String inStr) {
        return inStr==null||"".equals(inStr);
    }
}
