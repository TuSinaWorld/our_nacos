package com.our_nacos.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.our_nacos.client"})
public class StartRun {
    public static void main(String[] args) {
        SpringApplication.run(StartRun.class);
    }
}
