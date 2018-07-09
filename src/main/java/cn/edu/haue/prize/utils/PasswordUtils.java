package cn.edu.haue.prize.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by 杨晋升 on 2018-07-02.
 */
public class PasswordUtils {
    public static BCryptPasswordEncoder getPasswordEncrypter() {
        return new BCryptPasswordEncoder();
    }
}
