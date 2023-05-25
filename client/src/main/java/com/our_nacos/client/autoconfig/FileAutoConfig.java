package com.our_nacos.client.autoconfig;

import com.our_nacos.client.file.controller.FileController;
import com.our_nacos.client.file.environment.EnvironmentSpace;
import com.our_nacos.client.reg.RegReactor;
import com.our_nacos.client.reg.RegReactorImpl;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@AutoConfigureAfter(BeatAutoConfig.class)
@Order(0)
public class FileAutoConfig {
    @Bean
    @Order(0)
    public EnvironmentSpace environmentSpace(){
        return new EnvironmentSpace();
    }

    @Configuration(proxyBeanMethods = false)
    @AutoConfigureAfter(EnvironmentSpace.class)
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    protected static class LoadRegReactor{
        @Bean
        public FileController fileController(){
            return new FileController();
        }
    }
}
