package com.tomsun.utils;

import java.util.Random;

/**
 * Created by Administrator on 2018/7/10.
 */
public class KeyUtil {

    /*生成主键*/
    public static synchronized String getUniqueKey(){
        Random random = new Random();
        /*6位随机数*/
        Integer a = random.nextInt(900000) + 100000;

        return System.currentTimeMillis()+a.toString();
    }
}
