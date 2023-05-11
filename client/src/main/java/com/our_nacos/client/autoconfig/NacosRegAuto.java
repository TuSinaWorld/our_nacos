package com.our_nacos.client.autoconfig;

import com.our_nacos.client.listener.Mylistener;
import com.our_nacos.client.reg.Instance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * @Author: 乐哥
 * @Date: 2023/5/10
 * @Time: 10:36
 * @Description:   服务注册自动加载类
 */

public class NacosRegAuto extends Mylistener {

    @Autowired
    Instance instance;

    @Override
    public void register(Integer port) {
        if (!this.instance.getNacosDiscoveryProperties().isEnabled()) {
            System.out.println("注册未开启.......");
            return;
        }
        if (this.instance.getPort() < 0) {
            this.instance.setPort(port);
        }
        if (StringUtils.isEmpty(this.instance.getNacosDiscoveryProperties().getService())) {
            System.out.println("没有服务注册在nacos客户端上......");
            return;
        }
        super.register(port);

    }
}
