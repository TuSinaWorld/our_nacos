package com.our_nacos.client.loadbalance;

import com.our_nacos.client.beat.BeatInfo;
import com.our_nacos.client.exception.NullBeatInfoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.our_nacos.client.common.Constants.REQUEST_HEAD;

//简单版
//TODO:自动配置,托管条件
public class PollingLoadBalancer  implements MyLoadBalance{
    private static AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);

    public String choose(List<BeatInfo> beatInfos,String rawPath){
       if(beatInfos == null ||beatInfos.size() == 0){
           throw new NullBeatInfoException();
       }
       int serverCount=beatInfos.size();
       int nextServerIndex = incrementAndGetModulo(serverCount,nextServerCyclicCounter);
       BeatInfo beatInfo = beatInfos.get(nextServerIndex);
//       http://localhost:8080/
//       http://namespace/
       //RestTemplate中url 第一种写法
       String url=REQUEST_HEAD+beatInfo.getIp()+":"+beatInfo.getPort()+rawPath;
       //RestTemplate中url 第二种写法
//        String url=REQUEST_HEAD+beatInfo.getServiceName()+"/";
       return url;
   }

    private int incrementAndGetModulo(int modulo,AtomicInteger nextServerCyclicCounter) {
        int current;
        int next;
        do {
            current = nextServerCyclicCounter.get(); // nextServerCyclicCounter.get() 自增
            next = (current+1) % modulo;
        } while (!nextServerCyclicCounter.compareAndSet(current, next));
        return next;
    }
}
