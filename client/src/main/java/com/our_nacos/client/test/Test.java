package com.our_nacos.client.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.our_nacos.client.beat.BeatInfo;
import com.our_nacos.client.discovery.ServiceDiscovery;
import com.our_nacos.client.loadbalance.MyLoadBalance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class Test {

    @Autowired
    private ServiceDiscovery serviceDiscovery;

    @RequestMapping("/test")
    public String test() throws JsonProcessingException {
        Map<String, BeatInfo> all = serviceDiscovery.findAll();
        ObjectMapper mapper=new ObjectMapper();
        CollectionType listType =
                mapper.getTypeFactory().constructCollectionType(ArrayList.class, BeatInfo.class);
        String JSON = mapper.writeValueAsString(all.values());
        List<BeatInfo> list = mapper.readValue(JSON, listType);

//        Map<String, BeatInfo> all = serviceDiscovery.findAll();
//        List<BeatInfo> list  = new ArrayList<BeatInfo>(all.values());
        MyLoadBalance selectloadbalancer = serviceDiscovery.selectloadbalancer();
        String url = selectloadbalancer.choose(list);
        return url;
    }
}
