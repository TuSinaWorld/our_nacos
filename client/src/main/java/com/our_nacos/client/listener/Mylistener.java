package com.our_nacos.client.listener;

import com.our_nacos.client.autoconfig.NacosRegAuto;
import com.our_nacos.client.reg.Instance;
import com.our_nacos.client.reg.NacosDiscoveryProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.context.WebServerInitializedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.WebApplicationInitializer;

/**
 * @Author: 乐哥
 * @Date: 2023/5/10
 * @Time: 18:38
 * @Description:
 */
@Component
public abstract class Mylistener implements ApplicationListener<WebServerInitializedEvent> {

    private boolean isEnable;

    @Autowired
    Instance instance;

    @Autowired
    NacosRegAuto nacosRegAuto;

    NacosDiscoveryProperties nacosDiscoveryProperties = instance.getNacosDiscoveryProperties();

    public boolean isEnable() {
        return nacosDiscoveryProperties.isEnabled();
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    protected void register(Integer port) {
        this.register(port);
    }

    @Override
    public void onApplicationEvent(WebServerInitializedEvent event) {
        if(!isEnable()){
            System.out.println("服务配置未启用....");
            return;
        }
        nacosRegAuto.register(event.getWebServer().getPort());
    }
}
