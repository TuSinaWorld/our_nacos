package com.our_nacos.client.beat.beat_send;

import com.our_nacos.client.beat.BeatInfo;
import com.our_nacos.client.common.Constants;
import com.our_nacos.client.common.MyRestTemplate;
import com.our_nacos.client.common.ResponseBean;
import com.our_nacos.client.file.environment.EnvironmentSpace;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;


//使用成熟框架RestTemplate发包
public class RestTemplateSend extends BeatSend{

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    MyRestTemplate myRestTemplate;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    EnvironmentSpace environmentSpace;


    //调用无参抽象构造方法同时新建RestTemplate并拼接url
    public RestTemplateSend(){
        super();
    }

    //调用带参抽象构造方法同时新建RestTemplate并拼接url
    public RestTemplateSend(BeatInfo beatInfo){
        super(beatInfo);
    }

    @Override
    public void send() {
        ResponseBean responseBean;
        setSpace();
        try {
            //向指定url发送心跳信息,接收为ResponseBean
            responseBean = myRestTemplate.postForObject(getUrl(), beatInfo, ResponseBean.class);
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

    //url的拼接方法
    private String getUrl(){
        return Constants.REQUEST_HEAD + serverIp +
                Constants.SEPARATE_IP_PORT + serverPort +
                Constants.REST_TEMPLATE_BEAT_SEND_SUFFIX;
    }

    private void setSpace(){
        Map<String , Long> memory = environmentSpace.getMemory();
        beatInfo.setFreeSpace(memory.get("free"));
        beatInfo.setTotalSpace(memory.get("total"));
    }


}
