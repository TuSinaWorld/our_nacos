package com.our_nacos.client.reg.reg_send;

import com.our_nacos.client.common.MyRestTemplate;
import com.our_nacos.client.common.Constants;
import com.our_nacos.client.common.ResponseBean;
import com.our_nacos.client.reg.NacosDiscoveryProperties;
import org.springframework.beans.factory.annotation.Autowired;

public class RestTemplateRegSend extends RegSend {
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    private MyRestTemplate myRestTemplate;

    public RestTemplateRegSend() {
        super();
    }

    public RestTemplateRegSend(NacosDiscoveryProperties nacosDiscoveryProperties){
        super(nacosDiscoveryProperties);
    }

    private void sendInfo(String url){
        ResponseBean responseBean;
        try {
            //向指定url发送注册信息,接收为ResponseBean
            responseBean = myRestTemplate.postForObject(url, nacosDiscoveryProperties, ResponseBean.class);
        }catch (Exception e){
            throw new RuntimeException("请求:"+ url +"发送信息时出现错误:",e);
        }
        if(responseBean == null || responseBean.getCode() == null){
            throw new NullPointerException("请求:"+ url +"接收回应时出现错误:未接收到回应对象!");
        }
        if(responseBean.getCode() != 1){
            throw new RuntimeException("接收回应时出现错误:" + responseBean.getMsg());
        }
    }

    private String getRegUrl(){
        return Constants.REQUEST_HEAD + serverIp +
                Constants.SEPARATE_IP_PORT + serverPort +
                Constants.REST_TEMPLATE_REG_SEND_SUFFIX;
    }

    private String getRemoveUrl(){
        return Constants.REQUEST_HEAD + serverIp +
                Constants.SEPARATE_IP_PORT + serverPort +
                Constants.REST_TEMPLATE_REMOVE_SEND_SUFFIX;
    }

    @Override
    public void sendRegInfo() {
        sendInfo(getRegUrl());
    }

    @Override
    public void revocationRegInfo() {
        sendInfo(getRemoveUrl());
    }


}
