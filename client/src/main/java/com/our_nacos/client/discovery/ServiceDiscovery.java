package com.our_nacos.client.discovery;

import com.our_nacos.client.loadbalance.PollingLoadBalancer;
import com.our_nacos.client.loadbalance.RandomLoadBalancer;
import com.our_nacos.client.beat.BeatInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class ServiceDiscovery {
    public static void main(String[] args) {
         AtomicInteger nextServerCyclicCounter = new AtomicInteger(0);
        PollingLoadBalancer polling=new PollingLoadBalancer();
        RandomLoadBalancer random=new RandomLoadBalancer();

        Map<String, List<BeatInfo>> map=new HashMap<String,List<BeatInfo>>();
        List<BeatInfo> list1=new ArrayList<>();
        List<BeatInfo> list2=new ArrayList<>();

        //模拟 服务
        BeatInfo beatlnfo1=new BeatInfo();
        beatlnfo1.setServiceName("service-A");
        beatlnfo1.setPort(8080);
        beatlnfo1.setIp("localhost");

        BeatInfo beatlnfo2=new BeatInfo();
        beatlnfo2.setServiceName("service-A");
        beatlnfo2.setPort(8081);
        beatlnfo2.setIp("localhost");

        BeatInfo beatlnfo3=new BeatInfo();
        beatlnfo3.setServiceName("service-A");
        beatlnfo3.setPort(8083);
        beatlnfo3.setIp("localhost");

        BeatInfo beatlnfo4=new BeatInfo();
        beatlnfo4.setServiceName("service-B");
        beatlnfo4.setPort(8084);
        beatlnfo4.setIp("localhost");

        list1.add(beatlnfo1);
        list1.add(beatlnfo2);
        list1.add(beatlnfo3);
        list2.add(beatlnfo4);

        map.put("service-A",list1);
        map.put("service-B",list2);

          List<BeatInfo> discover = ServiceDiscovery.discover(map, "service-A");

           for(BeatInfo beatInfo:discover){
               System.out.println(beatInfo.getPort());
           }
     //轮询 简单版
//        for(int i=0;i<5;i++) {
//            polling.polling(discover,nextServerCyclicCounter);
//        }
        //随机 算法
        for(int i=0;i<5;i++) {
            String url = random.random(discover);
            System.out.println(url);
        }


     }
    public  static List<BeatInfo> discover(Map<String,List<BeatInfo>> map ,String serviceName) {
        return map.get(serviceName);
    }
}
