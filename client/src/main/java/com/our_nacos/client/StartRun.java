package com.our_nacos.client;

import com.our_nacos.client.annotation.Loadbalance;
import com.our_nacos.client.annotation.OurEnableDiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.our_nacos.client"})
@OurEnableDiscoveryClient
@Loadbalance
public class StartRun {
    public static void main(String[] args) {
        SpringApplication.run(StartRun.class);
    }
}
