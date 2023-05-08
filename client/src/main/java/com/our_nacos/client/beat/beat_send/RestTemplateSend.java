package com.our_nacos.client.beat.beat_send;

import com.our_nacos.client.beat.BeatInfo;
import com.our_nacos.client.common.Constants;
import com.our_nacos.client.common.ResponseBean;
import org.springframework.web.client.RestTemplate;


//使用成熟框架RestTemplate发包
public class RestTemplateSend extends BeatSend{
    RestTemplate restTemplate;

    private final String url;

    public RestTemplateSend(){
        super();
        restTemplate = new RestTemplate();
        url = getUrl();
    }

    public RestTemplateSend(BeatInfo beatInfo){
        super(beatInfo);
        restTemplate = new RestTemplate();
        url = getUrl();
    }

    @Override
    public void send() {
        ResponseBean responseBean = null;
        try {
            responseBean = restTemplate.postForObject(url, beatInfo, ResponseBean.class);
        }catch (Exception e){
            throw new RuntimeException("发送心跳时出现错误:",e);
        }
        if(responseBean == null || responseBean.getCode() == null){
            throw new NullPointerException("接收心跳回应时出现错误:未接收到回应对象!");
        }
        if(responseBean.getCode() != 1){
            throw new RuntimeException("接收心跳回应时出现错误:" + responseBean.getMsg());
        }
    }

    private String getUrl(){
        return Constants.REQUEST_HEAD + serverIp +
                Constants.SEPARATE_IP_PORT + serverPort +
                Constants.REST_TEMPLATE_BEAT_SEND_SUFFIX;
    }


}
