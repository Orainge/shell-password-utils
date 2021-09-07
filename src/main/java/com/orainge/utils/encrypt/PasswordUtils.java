package com.orainge.utils.encrypt;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Random;

/**
 * 密码工具类
 *
 * @author orainge
 * @date 2021/9/7
 */
public class PasswordUtils {
    /**
     * 密码中 Head 的长度
     */
    public static final int HEAD_LENGTH = 8;

    public static final Long NUMBER = 3680984568597093857L;

    /**
     * 创建加/解密密钥
     *
     * @param head head 字节数组
     * @return 加/解密密钥
     */
    public static byte[] generateKey(byte[] head) {
        long ks = NUMBER / (long) (new Random((long) head[5])).nextInt(127);
        Random random = new Random(ks);
        int t = head[0];

        for (int i = 0; i < t; ++i) {
            random.nextLong();
        }

        long n = random.nextLong();
        Random r2 = new Random(n);
        long[] ld = new long[]{(long) head[4], r2.nextLong(), (long) head[7], (long) head[3], r2.nextLong(), (long) head[1], random.nextLong(), (long) head[2]};
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            for (long l : ld) {
                dos.writeLong(l);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                dos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        byte[] keyData = bos.toByteArray();
        keyData = DigestUtils.md5(keyData);
        return keyData;
    }
}
