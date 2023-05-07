package com.our_nacos.client.Loadbalance;

import com.our_nacos.client.beat.BeatInfo;

import java.util.List;
import java.util.Random;

import static com.our_nacos.client.common.Constants.REQUEST_HEAD;

public class RandomLoadBalancer {

    public String random(List< BeatInfo > beatInfos){
        int serverCount= beatInfos.size();
        Random random=new Random();
        int index = random.nextInt(serverCount);
        BeatInfo beatInfo = beatInfos.get(index);
        //RestTemplate中url 第一种写法
        String url=REQUEST_HEAD+beatInfo.getIp()+":"+beatInfo.getPort()+"/";
        //RestTemplate中url 第二种写法
//        String url=REQUEST_HEAD+beatInfo.getServiceName()+"/";
        return url;
    }

}
