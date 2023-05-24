package com.our_nacos.web.controller;

import com.our_nacos.web.bean.BeatInfo;
import com.our_nacos.web.bean.ResponseBean;
import com.our_nacos.web.feign.OurNacosServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/web")
public class WebController {

    @Autowired
    OurNacosServer ourNacosServer;

    @RequestMapping("/getAllServices")
    public ResponseBean getAllServices(){
        Map<String, Map<String, BeatInfo>> allServices = ourNacosServer.getAllServices();
        return new ResponseBean(1,"",allServices);
    }

    @RequestMapping("/getServicesByServiceName")
    public ResponseBean getServicesByServiceName(@RequestParam String serviceName){
        Map<String, BeatInfo> servicesByServiceName = ourNacosServer.getServicesByServiceName(serviceName);
        return new ResponseBean(1,"",servicesByServiceName);
    }

    @RequestMapping("/regByServiceName")
    public ResponseBean regByServiceName(@RequestParam String serviceName){
        return ourNacosServer.regByName(serviceName);
    }
}
