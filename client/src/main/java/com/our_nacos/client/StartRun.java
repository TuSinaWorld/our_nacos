package com.our_nacos.client;

import com.our_nacos.client.annotation.Loadbalance;
import com.our_nacos.client.annotation.OurEnableDiscoveryClient;
import com.our_nacos.client.discovery.ServiceDiscovery;
import com.our_nacos.client.loadbalance.RandomLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;

@SpringBootApplication(exclude = {MultipartAutoConfiguration.class})
@OurEnableDiscoveryClient
@Loadbalance()
public class StartRun {
    public static void main(String[] args) {
        SpringApplication.run(StartRun.class);
    }
}
