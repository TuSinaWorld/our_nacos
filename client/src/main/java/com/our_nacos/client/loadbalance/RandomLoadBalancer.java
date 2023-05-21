package com.our_nacos.client.loadbalance;

import com.our_nacos.client.beat.BeatInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

import static com.our_nacos.client.common.Constants.REQUEST_HEAD;

//TODO:自动配置,托管条件
public class RandomLoadBalancer implements MyLoadBalance {

    public String choose(List< BeatInfo > beatInfos,String rawPath){
        int serverCount= beatInfos.size();
        Random random=new Random();
        int index = random.nextInt(serverCount);
        BeatInfo beatInfo = beatInfos.get(index);
        //RestTemplate中url 第一种写法
        String url=REQUEST_HEAD+beatInfo.getIp()+":"+beatInfo.getPort()+rawPath;
        //RestTemplate中url 第二种写法
//        String url=REQUEST_HEAD+beatInfo.getServiceName()+"/";
        return url;
    }

}
