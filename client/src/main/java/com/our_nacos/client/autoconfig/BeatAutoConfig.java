package com.our_nacos.client.autoconfig;


import com.our_nacos.client.beat.BeatReactor;
import com.our_nacos.client.beat.BeatReactorImpl;
import com.our_nacos.client.beat.beat_send.BeatSend;
import com.our_nacos.client.beat.beat_send.MyRestTemplate;
import com.our_nacos.client.beat.beat_send.RestTemplateSend;
import com.our_nacos.client.exception.NoDependence;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

//心跳自动配置类
@Configuration
@Order(0)
public class BeatAutoConfig {
    //确保必须加载springmvc相关依赖
    @Configuration(proxyBeanMethods = false)
    @ConditionalOnMissingClass("org.springframework.web.servlet.DispatcherServlet")
    @Order(1)
    protected static class SpringMvcMissingFromClasspathConfiguration{
        public SpringMvcMissingFromClasspathConfiguration(){throw new NoDependence("spring-mvc(web)");}
    }

    //托管网络发送依赖
    @Configuration(proxyBeanMethods = false)
    @AutoConfigureAfter(SpringMvcMissingFromClasspathConfiguration.class)
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    @ConditionalOnMissingBean(MyRestTemplate.class)
    @Order(1)
    protected static class LoadNetwork{
        //TODO:按需加载对应依赖
        @Bean
        public MyRestTemplate myRestTemplate(){
            return new MyRestTemplate();
        }
    }


    //托管心跳发送类
    @Configuration(proxyBeanMethods = false)
    @AutoConfigureAfter(LoadNetwork.class)
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    @Order(1)
    protected static class LoadBeatSend{
        //TODO:根据配置文件加载对应类

        @Bean
        public BeatSend beatSend(){
            return new RestTemplateSend();
        }
    }

    //托管心跳控制类
    @Configuration(proxyBeanMethods = false)
    @AutoConfigureAfter(LoadBeatSend.class)
    @ConditionalOnClass(name = "org.springframework.web.servlet.DispatcherServlet")
    @Order(1)
    protected static class LoadBeatReactor{
        //TODO:新增更多同实现类并根据选择托管相应类

        @Bean
        public BeatReactor beatReactor(){
            return new BeatReactorImpl();
        }
    }



}
