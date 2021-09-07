package com.orainge.utils.encrypt;

import com.orainge.utils.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Random;

/**
 * 加密工具
 *
 * @author orainge
 * @since 2021/9/7
 */
public class EncryptUtils {
    /**
     * 加密密码
     *
     * @param password 原始密码
     * @return 加密后的密码
     */
    public static String encrypt(String password) throws Exception {
        // 输出随机 head 字节数组
        byte[] head = new byte[PasswordUtils.HEAD_LENGTH];
        for (int i = 0; i < PasswordUtils.HEAD_LENGTH; ++i) {
            head[i] = (byte) (new Random()).nextInt(127);
        }

        // 加密密码
        byte[] encryptData = encryptByDes(password.getBytes(StandardCharsets.UTF_8), head);

        // 拼接 head 和加密密码
        byte[] result = StringUtils.concatByteArray(head, encryptData);

        // 对加密结果进行 Base64 编码后返回
        return StringUtils.base64Encode(result, StandardCharsets.UTF_8);
    }

    /**
     * 使用 DES 进行加密
     */
    private static byte[] encryptByDes(byte[] data, byte[] head) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(PasswordUtils.generateKey(head));
        SecretKey secretKey = SecretKeyFactory.getInstance("DES").generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(1, secretKey, secureRandom);
        return cipher.doFinal(data);
    }
}