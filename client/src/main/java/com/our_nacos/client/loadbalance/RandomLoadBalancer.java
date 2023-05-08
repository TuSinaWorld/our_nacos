package com.our_nacos.client.loadbalance;

import com.our_nacos.client.beat.BeatInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

import static com.our_nacos.client.common.Constants.REQUEST_HEAD;

//TODO:自动配置,托管条件
@Component
public class RandomLoadBalancer  {

    public String random(List< BeatInfo > beatInfos){
        int serverCount= beatInfos.size();
        Random random=new Random();
        int index = random.nextInt(serverCount);
        BeatInfo beatInfo = beatInfos.get(index);
        if(!beatInfo.isStopped()){
            throw new RuntimeException("此服务已关闭");
        }
        //RestTemplate中url 第一种写法
        String url=REQUEST_HEAD+beatInfo.getIp()+":"+beatInfo.getPort()+"/";
        //RestTemplate中url 第二种写法
//        String url=REQUEST_HEAD+beatInfo.getServiceName()+"/";
        return url;
    }

}
