package com.yanhao.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class discovery {
    public static void main(String[] args) {
        SpringApplication.run(discovery.class,args);
    }


}
