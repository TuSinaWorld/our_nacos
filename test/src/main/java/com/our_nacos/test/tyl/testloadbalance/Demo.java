package com.our_nacos.test.tyl.testloadbalance;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.our_nacos.client.discovery.LoadbalanceURL;
import com.our_nacos.client.loadRestTemplate.MIneRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
public class Demo {

    @Autowired
    private LoadbalanceURL loadbalanceURL;

    @Autowired
    private MIneRestTemplate mIneRestTemplate;

    @RequestMapping("demo")
    public String get() throws JsonProcessingException, URISyntaxException {
//        URI uri=new URI("http://res-food/test");
        String uri="http://res-food/test";
        String serviceName = mIneRestTemplate.getForObject(uri, String.class);
        return serviceName;
    }

    @RequestMapping("/test")
    public String test(){
        return "ok";
    }
}
