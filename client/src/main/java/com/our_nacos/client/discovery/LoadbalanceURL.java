package com.our_nacos.client.discovery;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.our_nacos.client.beat.BeatInfo;
import com.our_nacos.client.loadbalance.MyLoadBalance;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LoadbalanceURL {
    @Autowired
    private ServiceDiscovery serviceDiscovery;

    //获得经过负载均衡的url
    public String GetURl(String serviceName,String rawPath) throws JsonProcessingException {
        List<BeatInfo> list1=new ArrayList<BeatInfo>();
        Map<String, BeatInfo> all = serviceDiscovery.findAll(serviceName);
        ObjectMapper mapper=new ObjectMapper();
        CollectionType listType =
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, BeatInfo.class);
        String JSON = null;
        try {
            JSON = mapper.writeValueAsString(all.values());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<BeatInfo> list = mapper.readValue(JSON, listType);

        //删除不健康的服务
        for(int i=0;i<list.size();i++){
            if(list.get(i).isStopped()==false){
                list1.add(list.get(i));
            }
        }

        MyLoadBalance selectloadbalancer = serviceDiscovery.selectloadbalancer();
        String url = selectloadbalancer.choose(list1,rawPath);
        return url;
    }
}
