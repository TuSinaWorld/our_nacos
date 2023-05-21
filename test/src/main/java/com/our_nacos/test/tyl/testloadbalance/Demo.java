package com.our_nacos.test.tyl.testloadbalance;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.our_nacos.client.discovery.LoadbalanceURL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Demo {

    @Autowired
    private LoadbalanceURL loadbalanceURL;
    @RequestMapping("demo")
    public String get() throws JsonProcessingException {
        String s="res-food";
        String url =loadbalanceURL.GetURl(s);
        return url;
    }
}
