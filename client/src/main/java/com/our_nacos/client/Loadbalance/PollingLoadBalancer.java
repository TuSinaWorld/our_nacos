package com.our_nacos.client.Loadbalance;

import com.our_nacos.client.beat.BeatInfo;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.our_nacos.client.common.Constants.REQUEST_HEAD;

//简单版
public class PollingLoadBalancer {
//    private AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);

   public  void test(List<BeatInfo> beatInfos,AtomicInteger nextServerCyclicCounter){
       if(beatInfos.size()<0){
           System.out.println("没有服务");
       }
       int serverCount=beatInfos.size();
       int nextServerIndex = incrementAndGetModulo(serverCount,nextServerCyclicCounter);
       BeatInfo beatInfo = beatInfos.get(nextServerIndex);
//       http://localhost:8080/
        System.out.println(REQUEST_HEAD+beatInfo.getIp()+":"+beatInfo.getPort()+"/");
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
