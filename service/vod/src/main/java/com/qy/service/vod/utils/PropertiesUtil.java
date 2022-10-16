package com.qy.service.vod.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author qinyue
 * @create 2022-10-07 23:14:00
 */
@Configuration
public class PropertiesUtil implements InitializingBean {
    @Value("${aliyun.vod.file.keyid}")
    private String keyID;

    @Value("${aliyun.vod.file.keysecret}")
    private String keySecret;

    public static String KEY_ID;
    public static String KEY_SECRET;

    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = keyID;
        KEY_SECRET = keySecret;
    }
}
