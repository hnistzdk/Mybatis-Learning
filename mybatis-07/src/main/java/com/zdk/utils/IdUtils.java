package com.zdk.utils;

import org.junit.Test;

import java.util.UUID;

/**
 * @author zdk
 * @date 2021/4/26 20:43
 */
public class IdUtils {
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Test
    public void test(){
        String uuid = IdUtils.getUUID();
        System.out.println(uuid);
    }
}
