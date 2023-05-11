package com.our_nacos.client.autoconfig;

import com.our_nacos.client.listener.Mylistener;
import com.our_nacos.client.reg.Instance;
import com.our_nacos.client.reg.RegProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import static org.springframework.util.ReflectionUtils.rethrowRuntimeException;

/**
 * @Author: 乐哥
 * @Date: 2023/5/10
 * @Time: 10:36
 * @Description:   服务注册自动加载类
 */

public class NacosRegAuto extends Mylistener {

    @Autowired
    Instance instance;

    RegProxy regProxy;

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

        try {
            // 开始注册服务
            regProxy.register(this.instance.getNacosDiscoveryProperties());
            System.out.println("服务注册成功......");
        }
        catch (Exception e) {
            System.out.println("服务注册失败......");
            e.printStackTrace();
        }
    }
}
