package com.our_nacos.client.reg.reg_send;

import com.our_nacos.client.common.MyRestTemplate;
import com.our_nacos.client.common.Constants;
import com.our_nacos.client.reg.NacosDiscoveryProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class RestTemplateRegSend extends RegSend {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MyRestTemplate myRestTemplate;

    private final String url;
    public RestTemplateRegSend() {
        super();
        this.url = getUrl();
    }

    public RestTemplateRegSend(NacosDiscoveryProperties nacosDiscoveryProperties){
        super(nacosDiscoveryProperties);
        this.url = getUrl();
    }

    @Override
    public void sendRegInfo() {
        log.info("模拟发送注册信息");
    }

    @Override
    public void revocationRegInfo() {
        log.info("模拟发送销毁信息");
    }

    private String getUrl(){
        return Constants.REQUEST_HEAD + serverIp +
                Constants.SEPARATE_IP_PORT + serverPort +
                Constants.REST_TEMPLATE_BEAT_SEND_SUFFIX;
    }
}
