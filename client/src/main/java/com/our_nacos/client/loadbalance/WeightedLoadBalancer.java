package com.our_nacos.client.loadbalance;

import com.our_nacos.client.beat.BeatInfo;
import org.springframework.stereotype.Component;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import static com.our_nacos.client.common.Constants.REQUEST_HEAD;

public class WeightedLoadBalancer implements MyLoadBalance{

    public String choose(List<BeatInfo> beatInfos,String rawPath){
        // 计算平均响应时间和总权重
        double totalWeight = 0;
        double[] avgWeight = new double[beatInfos.size()];
        for (int i=0;i<beatInfos.size();i++) {
            BeatInfo beatInfo = beatInfos.get(i);
                totalWeight += beatInfo.getWeight();
                avgWeight[i] = totalWeight;
        }

        // 计算权重值
        double[] weights = new double[beatInfos.size()];
        double sum=0.0;
        for (int i = 0; i < beatInfos.size(); i++) {
            BeatInfo beatInfo = beatInfos.get(i);
                weights[i] = (totalWeight - beatInfo.getWeight())+sum;
                sum=weights[i];
        }

        // 选择服务器
        double randomWeight = ThreadLocalRandom.current().nextDouble(totalWeight);
        double weightSum = 0;
        for (int i = 0; i < beatInfos.size(); i++) {
            weightSum += weights[i];
            if (randomWeight < weightSum) {
                return REQUEST_HEAD+beatInfos.get(i).getIp()+":"+beatInfos.get(i).getPort()+rawPath;
            }
        }
         return null;
       }
    }
