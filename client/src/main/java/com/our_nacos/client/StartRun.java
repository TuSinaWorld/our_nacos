package com.our_nacos.client;

import com.our_nacos.client.annotation.OurEnableDiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OurEnableDiscoveryClient
public class StartRun {
    public static void main(String[] args) {
        SpringApplication.run(StartRun.class);
    }
}
