package com.qy.service.cms;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author qinyue
 * @create 2022-10-09 02:18:00
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({"com.qy"})
@MapperScan("com.qy.service.cms.mapper")
public class CMSApplication {
    public static void main(String[] args) {
        SpringApplication.run(CMSApplication.class, args);
    }
}
