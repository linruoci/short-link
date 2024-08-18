package com.ruoci.shortlink.project;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @Author: ruoci
 **/
@SpringBootApplication
@MapperScan("com.ruoci.shortlink.project.dao.mapper")
@EnableDiscoveryClient
public class ShortLinkApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShortLinkApplication.class,args);
    }
}
