import com.orainge.utils.ShellPasswordUtils;

public class Main {
    public static void main(String[] args) {
        // 1.加密密码
        /* 原始密码 */
        String pwd1 = "123456";
        String str1 = ShellPasswordUtils.encrypt(pwd1);
        if (str1 != null) {
            System.out.println("加密结果: " + str1);
        }

        // 2.解密密码
        /* 加密后的密码 */
        String pwd2 = "OmMQOhxZXDOQpv5c2Go1eQ==";
        String str2 = ShellPasswordUtils.decrypt(pwd2);
        if (str2 != null) {
            System.out.println("解密结果: " + str2);
        }
    }
}
