package com.yanhao.server2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class server2 {
    public static void main(String[] args) {
        SpringApplication.run(server2.class,args);
    }
}
