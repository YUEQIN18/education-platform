package com.qy.service.edu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author qinyue
 * @create 2022-10-01 20:04:00
 */
@SpringBootApplication
@EnableDiscoveryClient //Nacos
@EnableFeignClients //Feign
@ComponentScan(basePackages = {"com.qy"})
public class EduApplication {

    public static void main(String[] args) {
        SpringApplication.run(EduApplication.class, args);
    }
}
