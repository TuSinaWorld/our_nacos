package com.our_nacos.client.autoconfig;

import com.our_nacos.client.beat.BeatReactor;
import com.our_nacos.client.beat.BeatReactorImpl;
import com.our_nacos.client.beat.beat_send.BeatSend;
import com.our_nacos.client.beat.beat_send.RestTemplateSend;
import com.our_nacos.client.exception.NoDependence;
import com.our_nacos.client.reg.NacosDiscoveryProperties;
import com.our_nacos.client.reg.RegReactor;
import com.our_nacos.client.reg.RegReactorImpl;
import com.our_nacos.client.reg.reg_send.RegSend;
import com.our_nacos.client.reg.reg_send.RestTemplateRegSend;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureAfter(BeatAutoConfig.class)
public class RegAutoConfig {

    @Configuration(proxyBeanMethods = false)
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    protected static class LoadRegSend{
        //TODO:根据配置文件加载对应类
        @Bean
        public RegSend regSend(){
            return new RestTemplateRegSend();
        }
    }

    @Configuration(proxyBeanMethods = false)
    @AutoConfigureAfter(BeatAutoConfig.LoadBeatSend.class)
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    protected static class LoadRegReactor{
        //TODO:新增更多同实现类并根据选择托管相应类
        @Bean
        public RegReactor regReactor(){
            return new RegReactorImpl();
        }
    }
}
