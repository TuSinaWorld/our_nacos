package com.our_nacos.test.tyl.testloadbalance;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.our_nacos.client.loadRestTemplate.MineRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URISyntaxException;

@RestController
public class Demo {

    @Autowired
    private MineRestTemplate mineRestTemplate;

    @RequestMapping("demo")
    public String get() throws JsonProcessingException, URISyntaxException {
//        URI uri=new URI("http://res-food/test");
        String uri="http://res-food/test";
        String serviceName = mineRestTemplate.getForObject(uri, String.class);
        return serviceName;
    }

    @RequestMapping("/test")
    public String test(){
        return "ok";
    }
}
