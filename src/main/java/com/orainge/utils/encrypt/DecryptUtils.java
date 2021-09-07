package com.orainge.utils.encrypt;

import com.orainge.utils.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

/**
 * 解密工具
 *
 * @author orainge
 * @since 2021/9/7
 */
public class DecryptUtils {
    /**
     * 解密密码
     *
     * @param password 加密后的密码
     * @return 原始密码
     */
    public static String decrypt(String password) throws Exception {
        if (password == null) {
            return null;
        } else {
            String result = "";
            if (!StringUtils.isEmptyAfterTrim(password)) {
                // 对密文进行 Base64 解码
                byte[] buf = StringUtils.base64Decode(password, StandardCharsets.UTF_8);

                // 从密文中获取 head
                byte[] head = new byte[PasswordUtils.HEAD_LENGTH];
                System.arraycopy(buf, 0, head, 0, head.length);

                // 从密文中获取加密密码
                byte[] encryptData = new byte[buf.length - head.length];
                System.arraycopy(buf, head.length, encryptData, 0, encryptData.length);

                // 解密并返回原始密码
                byte[] bt = decryptByDes(encryptData, PasswordUtils.generateKey(head));
                result = new String(bt);
            }

            return result;
        }
    }

    /**
     * 使用 DES 进行解密
     */
    public static byte[] decryptByDes(byte[] data, byte[] key) throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        DESKeySpec dks = new DESKeySpec(key);
        SecretKey secretkey = SecretKeyFactory.getInstance("DES").generateSecret(dks);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(2, secretkey, secureRandom);
        return cipher.doFinal(data);
    }
}
