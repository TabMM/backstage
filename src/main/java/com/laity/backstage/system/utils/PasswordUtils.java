package com.laity.backstage.system.utils;

import com.laity.backstage.system.entity.User;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

/**
 * @author D.F Douglas
 * @version 1.0.0
 * @ClassName PasswordUtils
 * @Description TODO
 * @createTime 2019/6/7/17:22
 */
public class PasswordUtils {

    public static User encryptPassword(User user) {
        String algorithmName = "md5";
        int hashIterations = 2;
        String newPassword = new SimpleHash(algorithmName, user.getPassword(), ByteSource.Util.bytes(user.getUsername()), hashIterations).toHex();
        user.setPassword(newPassword);
        return user;
    }
    public static String encryptPwd(char[] pwd,String username) {

        String algorithmName = "md5";
        int hashIterations = 2;
        String newPassword = new SimpleHash(algorithmName, String.valueOf(pwd), ByteSource.Util.bytes(username), hashIterations).toHex();
        return newPassword;
    }
}
