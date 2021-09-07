package com.orainge.utils;

import com.orainge.utils.encrypt.DecryptUtils;
import com.orainge.utils.encrypt.EncryptUtils;

/**
 * Shell 密码加解密工具类<br>
 * 支持 finalshell/xshell 的密码加解密
 *
 * @author orainge
 * @since 2021/9/7
 */
public class ShellPasswordUtils {
    /**
     * 加密密码
     *
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encrypt(String password) {
        String result = null;
        try {
            result = EncryptUtils.encrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 解密密码
     *
     * @param password 加密后的密码
     * @return 原始密码
     */
    public static String decrypt(String password) {
        String result = null;
        try {
            result = DecryptUtils.decrypt(password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
