package com.our_nacos.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WebStart {
    public static void main(String[] args) {
        SpringApplication.run(WebStart.class,args);
    }
}
