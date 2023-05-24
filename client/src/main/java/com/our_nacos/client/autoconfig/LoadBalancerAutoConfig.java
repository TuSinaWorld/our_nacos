package com.our_nacos.client.autoconfig;

import com.our_nacos.client.common.MyRestTemplate;
import com.our_nacos.client.discovery.LoadbalanceURL;
import com.our_nacos.client.discovery.ServiceDiscovery;
import com.our_nacos.client.environment.EnvironmentSpace;
import com.our_nacos.client.loadRestTemplate.MineRestTemplate;
import com.our_nacos.client.loadbalance.PollingLoadBalancer;
import com.our_nacos.client.loadbalance.RandomLoadBalancer;
import com.our_nacos.client.loadbalance.WeightedLoadBalancer;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * @Author: 乐哥
 * @Date: 2023/5/21
 * @Time: 11:01
 * @Description:
 */
@Configuration
@AutoConfigureAfter(RegAutoConfig.class)
@Order(0)
public class LoadBalancerAutoConfig {

    @Bean
    public PollingLoadBalancer pollingLoadBalancer(){
        return new PollingLoadBalancer();
    }
    @Bean
    public RandomLoadBalancer randomLoadBalancer(){
        return new RandomLoadBalancer();
    }
    @Bean
    public WeightedLoadBalancer weightedLoadBalancer(){
        return new WeightedLoadBalancer();
    }
    @Bean
    public ServiceDiscovery serviceDiscovery(){
        return new ServiceDiscovery();
    }
    @Bean
    public LoadbalanceURL loadbalanceURL(){
        return new LoadbalanceURL();
    }
    @Bean
    @ConditionalOnClass(MyRestTemplate.class)
    public MineRestTemplate mIneRestTemplate(){
        return new MineRestTemplate();
    }
    @Bean
    public EnvironmentSpace environmentSpace(){
        return new EnvironmentSpace();
    }
}
