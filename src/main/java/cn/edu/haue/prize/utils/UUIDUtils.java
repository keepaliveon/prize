package cn.edu.haue.prize.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class UUIDUtils {
    public static String randomId() {
        return UUID.randomUUID().toString().replaceAll("-", "") + "-" + new Date().getTime();
    }

    public static String SerialNum() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        int a = (int) (Math.random() * 26) + 'A';
        return simpleDateFormat.format(new Date()) + (char) a;
    }
}
