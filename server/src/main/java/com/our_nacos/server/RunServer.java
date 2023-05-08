package com.our_nacos.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//服务端运行方法
@SpringBootApplication
public class RunServer {
    public static void main(String[] args) {
        SpringApplication.run(RunServer.class,args);
    }
}
