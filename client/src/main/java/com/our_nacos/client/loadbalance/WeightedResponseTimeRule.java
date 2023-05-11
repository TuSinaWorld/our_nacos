package com.our_nacos.client.loadbalance;

import com.our_nacos.client.beat.BeatInfo;


import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class WeightedResponseTimeRule extends PollingLoadBalancer{

    public BeatInfo WeightedLoadBalancer(List<BeatInfo> beatInfos){


        // 计算平均响应时间和总权重
        double totalWeight = 0;
        double[] avgWeight = new double[beatInfos.size()];
        for (int i=0;i<beatInfos.size();i++) {
            BeatInfo beatInfo = beatInfos.get(i);
            boolean stopped = beatInfo.isStopped();
            if(stopped==false) {
                totalWeight += beatInfo.getWeight();
//            avgResponseTime[i] = calculateAvgResponseTime(server);
                avgWeight[i] = totalWeight;
            }else {
                continue;
            }
        }


        // 计算权重值
        double[] weights = new double[beatInfos.size()];
        for (int i = 0; i < beatInfos.size(); i++) {
            BeatInfo beatInfo = beatInfos.get(i);
            boolean stopped = beatInfo.isStopped();
            if(stopped==false) {
                weights[i] = (totalWeight - beatInfo.getWeight());
            }else {
                continue;
            }
        }

        // 选择服务器
        double randomWeight = ThreadLocalRandom.current().nextDouble(totalWeight);
        double weightSum = 0;
        for (int i = 0; i < beatInfos.size(); i++) {
            weightSum += weights[i];
            if (randomWeight < weightSum) {
                return beatInfos.get(i);
            }
        }
         return null;
       }
    }
