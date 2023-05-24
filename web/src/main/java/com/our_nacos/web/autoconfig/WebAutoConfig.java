package com.our_nacos.web.autoconfig;

import com.our_nacos.web.bean.ServerInfo;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
@Order(0)
public class WebAutoConfig {
    @EnableConfigurationProperties(ServerInfo.class)
    @Order(1)
    protected static class EnableServerInfo{
        @Bean
        public ServerInfo serverInfo(){
            return new ServerInfo();
        }
    }
}
