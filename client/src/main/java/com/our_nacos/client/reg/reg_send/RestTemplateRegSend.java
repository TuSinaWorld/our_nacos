package com.our_nacos.client.reg.reg_send;

import com.our_nacos.client.reg.NacosDiscoveryProperties;

public class RestTemplateRegSend extends RegSend {
    public RestTemplateRegSend() {
        super();
    }

    public RestTemplateRegSend(NacosDiscoveryProperties nacosDiscoveryProperties){
        super(nacosDiscoveryProperties);
    }

    @Override
    public void sendRegInfo() {
        log.info("模拟发送注册信息");
    }

    @Override
    public void revocationRegInfo() {
        log.info("模拟发送销毁信息");
    }
}
