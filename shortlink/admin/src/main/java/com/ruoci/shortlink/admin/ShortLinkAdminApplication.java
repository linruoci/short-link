package com.ruoci.shortlink.admin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author: ruoci
 **/
@SpringBootApplication
@MapperScan("com.ruoci.shortlink.admin.dao.mapper")
@EnableDiscoveryClient
@EnableFeignClients("com.ruoci.shortlink.admin.remote")
public class ShortLinkAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShortLinkAdminApplication.class,args);
    }

}
