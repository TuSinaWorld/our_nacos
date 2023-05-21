package com.our_nacos.client.listener;

import com.our_nacos.client.reg.NacosRegAuto;
import com.our_nacos.client.reg.Instance;
import com.our_nacos.client.reg.NacosDiscoveryProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author: 乐哥
 * @Date: 2023/5/10
 * @Time: 18:38
 * @Description:
 */
public abstract class Mylistener implements ApplicationListener<WebServerInitializedEvent> {


    private boolean isEnable;

    @Autowired
    Instance instance;

    public boolean isEnable() {
        return true;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    protected abstract void register(NacosDiscoveryProperties nacosDiscoveryProperties,Integer port);




    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {

        NacosDiscoveryProperties nacosDiscoveryProperties = instance.getNacosDiscoveryProperties();
        if(!isEnable()){
            System.out.println("服务配置未启用....");
            return;
        }
        this.register(nacosDiscoveryProperties,event.getWebServer().getPort());
    }
}
