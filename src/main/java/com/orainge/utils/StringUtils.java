package com.orainge.utils;

import java.nio.charset.Charset;

/**
 * 字符串工具类
 *
 * @author orainge
 * @since 2021/9/7
 */
public class StringUtils {
    /**
     * 对字符串进行 Base64 编码
     *
     * @param byteData 字节数组
     * @param charset  编码类型
     * @return 编码结果
     */
    public static String base64Encode(byte[] byteData, Charset charset) {
        if (byteData == null) {
            throw new IllegalArgumentException("byteData cannot be null");
        } else {
            return new String(base64Encode(byteData), charset);
        }
    }

    /**
     * 对字符串进行 Base64 解码
     *
     * @param encoded 字节数组
     * @param charset 编码类型
     * @return 编码结果
     */
    public static byte[] base64Decode(String encoded, Charset charset) {
        if (encoded == null) {
            throw new IllegalArgumentException("encoded cannot be null");
        } else {
            return base64Decode(encoded.getBytes(charset));
        }
    }

    /**
     * 对字符串进行 Base64 编码
     *
     * @param byteData 未编码的字节数组
     * @return 编码结果
     */
    public static byte[] base64Encode(byte[] byteData) {
        if (byteData == null) {
            throw new IllegalArgumentException("byteData cannot be null");
        } else {
            byte[] byteDest = new byte[(byteData.length + 2) / 3 * 4];
            int iSrcIdx = 0;

            int iDestIdx;
            for (iDestIdx = 0; iSrcIdx < byteData.length - 2; iSrcIdx += 3) {
                byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx] >>> 2 & 63);
                byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx + 1] >>> 4 & 15 | byteData[iSrcIdx] << 4 & 63);
                byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx + 2] >>> 6 & 3 | byteData[iSrcIdx + 1] << 2 & 63);
                byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx + 2] & 63);
            }

            if (iSrcIdx < byteData.length) {
                byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx] >>> 2 & 63);
                if (iSrcIdx < byteData.length - 1) {
                    byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx + 1] >>> 4 & 15 | byteData[iSrcIdx] << 4 & 63);
                    byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx + 1] << 2 & 63);
                } else {
                    byteDest[iDestIdx++] = (byte) (byteData[iSrcIdx] << 4 & 63);
                }
            }

            for (iSrcIdx = 0; iSrcIdx < iDestIdx; ++iSrcIdx) {
                if (byteDest[iSrcIdx] < 26) {
                    byteDest[iSrcIdx] = (byte) (byteDest[iSrcIdx] + 65);
                } else if (byteDest[iSrcIdx] < 52) {
                    byteDest[iSrcIdx] = (byte) (byteDest[iSrcIdx] + 97 - 26);
                } else if (byteDest[iSrcIdx] < 62) {
                    byteDest[iSrcIdx] = (byte) (byteDest[iSrcIdx] + 48 - 52);
                } else if (byteDest[iSrcIdx] < 63) {
                    byteDest[iSrcIdx] = 43;
                } else {
                    byteDest[iSrcIdx] = 47;
                }
            }

            while (iSrcIdx < byteDest.length) {
                byteDest[iSrcIdx] = 61;
                ++iSrcIdx;
            }

            return byteDest;
        }
    }

    /**
     * 对字符串进行 Base64 解码
     *
     * @param byteData 编码的字节数组
     * @return 解码结果
     */
    public static byte[] base64Decode(byte[] byteData) {
        if (byteData == null) {
            throw new IllegalArgumentException("byteData cannot be null");
        } else {
            byte[] byteTemp = new byte[byteData.length];

            int reviSrcIdx;
            for (reviSrcIdx = byteData.length; reviSrcIdx - 1 > 0 && byteData[reviSrcIdx - 1] == 61; --reviSrcIdx) {
            }

            if (reviSrcIdx - 1 == 0) {
                return null;
            } else {
                byte[] byteDest = new byte[reviSrcIdx * 3 / 4];

                int iSrcIdx;
                for (iSrcIdx = 0; iSrcIdx < reviSrcIdx; ++iSrcIdx) {
                    if (byteData[iSrcIdx] == 43) {
                        byteTemp[iSrcIdx] = 62;
                    } else if (byteData[iSrcIdx] == 47) {
                        byteTemp[iSrcIdx] = 63;
                    } else if (byteData[iSrcIdx] < 58) {
                        byteTemp[iSrcIdx] = (byte) (byteData[iSrcIdx] + 52 - 48);
                    } else if (byteData[iSrcIdx] < 91) {
                        byteTemp[iSrcIdx] = (byte) (byteData[iSrcIdx] - 65);
                    } else if (byteData[iSrcIdx] < 123) {
                        byteTemp[iSrcIdx] = (byte) (byteData[iSrcIdx] + 26 - 97);
                    }
                }

                iSrcIdx = 0;

                int iDestIdx;
                for (iDestIdx = 0; iSrcIdx < reviSrcIdx && iDestIdx < byteDest.length / 3 * 3; iSrcIdx += 4) {
                    byteDest[iDestIdx++] = (byte) (byteTemp[iSrcIdx] << 2 & 252 | byteTemp[iSrcIdx + 1] >>> 4 & 3);
                    byteDest[iDestIdx++] = (byte) (byteTemp[iSrcIdx + 1] << 4 & 240 | byteTemp[iSrcIdx + 2] >>> 2 & 15);
                    byteDest[iDestIdx++] = (byte) (byteTemp[iSrcIdx + 2] << 6 & 192 | byteTemp[iSrcIdx + 3] & 63);
                }

                if (iSrcIdx < reviSrcIdx) {
                    if (iSrcIdx < reviSrcIdx - 2) {
                        byteDest[iDestIdx++] = (byte) (byteTemp[iSrcIdx] << 2 & 252 | byteTemp[iSrcIdx + 1] >>> 4 & 3);
                        byteDest[iDestIdx++] = (byte) (byteTemp[iSrcIdx + 1] << 4 & 240 | byteTemp[iSrcIdx + 2] >>> 2 & 15);
                    } else {
                        if (iSrcIdx >= reviSrcIdx - 1) {
                            throw new IllegalArgumentException("Warning: 1 input bytes left to process. This was not Base64 input");
                        }

                        byteDest[iDestIdx++] = (byte) (byteTemp[iSrcIdx] << 2 & 252 | byteTemp[iSrcIdx + 1] >>> 4 & 3);
                    }
                }

                return byteDest;
            }
        }
    }

    /**
     * 检验字符串是否为空<br>
     * 去除字符串前后多余的空格后才检查
     *
     * @param str 字符串
     * @return ture: 字符串为空; false: 字符串不为空
     */
    public static boolean isEmptyAfterTrim(String str) {
        return str == null || "".equals(str.trim());
    }

    /**
     * 连接两个字节数组
     *
     * @param a 第一个字节数组
     * @param b 第二个字节数组
     * @return 拼接后的字节数组
     */
    public static byte[] concatByteArray(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }
}
