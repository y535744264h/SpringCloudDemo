package com.yanhao.server1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class server1 {
    public static void main(String[] args) {
        SpringApplication.run(server1.class,args);
    }
}
