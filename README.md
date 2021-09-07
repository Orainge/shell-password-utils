# 1 项目说明

该项目可以执行以下操作：

- 加密主机密码，生成符合 finalshell/xshell 配置文件格式的密码。

- 解密存储在 finalshell/xshell 配置文件中的密码

# 2 示例

以 Mac 系统的 Finalshell 为例，解密某一主机的密码：

- 进入目录 `/Users/$USER/Library/FinalShell/conn`

- 替换`主机名字` 为你在 Finalshell 配置的主机的名字，执行以下命令：

  ```sh
  # 不要忘记命令结尾的 * 号
  # 结尾 grep password 是为了高亮 password 字段，方便查找
  grep -rni "主机名字" * | grep password
  ```
  
- 将密码放入到 `Main.java` 代码中对应的位置，执行 `main` 即可解密。

  ```java
  import com.orainge.utils.ShellPasswordUtils;
  
  public class Main {
      public static void main(String[] args) {
          // 省略加密密码代码
  
          // 2.解密密码
          /* 加密后的密码 */
          String pwd2 = "将密码放到这里";
          String str2 = ShellPasswordUtils.decrypt(pwd2);
          if (str2 != null) {
              System.out.println("解密结果: " + str2);
          }
      }
  }
  ```

- 加密密码同理，替换相应位置的代码即可。  

