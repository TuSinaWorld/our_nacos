package com.our_nacos.test;

import com.our_nacos.client.annotation.OurEnableDiscoveryClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Author: 乐哥
 * @Date: 2023/5/21
 * @Time: 10:20
 * @Description:
 */
@SpringBootApplication
@OurEnableDiscoveryClient
public class TestApp {
    public static void main(String[] args) {
        SpringApplication.run(TestApp.class);
    }
}
