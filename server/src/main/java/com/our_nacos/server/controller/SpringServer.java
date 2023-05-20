package com.our_nacos.server.controller;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.bean.NacosDiscoveryProperties;
import com.our_nacos.server.bean.ResponseBean;
import com.our_nacos.server.common.Constants;
import com.our_nacos.server.storage.ServiceStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/beat")
public class SpringServer {

    @Autowired
    ServiceStorage storage;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //新增方法
    @RequestMapping("/accept")
    public ResponseBean accept(@RequestBody BeatInfo beatInfo){
        //TODO:根据心跳注册服务,维持服务等等.
        Thread thread = new Thread(() -> {
            logger.info(String.valueOf(beatInfo));
            storage.addServiceByBeat(beatInfo);
        });
        thread.setDaemon(true);
        thread.setName(buildThreadName(beatInfo));
        thread.start();
        return new ResponseBean(1,"",null);
    }



    //注册方法
    @RequestMapping("/reg")
    public ResponseBean reg(@RequestBody NacosDiscoveryProperties nacosDiscoveryProperties){
        Thread thread = new Thread(() -> {
            logger.info(String.valueOf(nacosDiscoveryProperties));
            storage.regNewService(nacosDiscoveryProperties);
        });
        thread.setDaemon(true);
        thread.setName(buildThreadName(nacosDiscoveryProperties));
        thread.start();
        return new ResponseBean(1,"",null);
    }

    @RequestMapping("/remove")
    public ResponseBean remove(@RequestBody NacosDiscoveryProperties nacosDiscoveryProperties){
        return new ResponseBean(1,"",null);
    }

    @RequestMapping("/test")
    public ResponseBean test(){
        storage.getAllServicesInfo().forEach((st,map) -> {
            System.out.println(st);
            map.forEach((st2,beatInfo) -> {
                System.out.println(st2);
                System.out.println(beatInfo);
            });
        });
        return null;
    }

    private String buildThreadName(BeatInfo beatInfo){
        return buildThreadName("BeatThread",beatInfo.getServiceName(),beatInfo.getIp(), beatInfo.getPort());
    }

    private String buildThreadName(NacosDiscoveryProperties nacosDiscoveryProperties){
        return buildThreadName("NacosPropertiesThread",nacosDiscoveryProperties.getService()
        ,nacosDiscoveryProperties.getIp(),nacosDiscoveryProperties.getPort());
    }

    private String buildThreadName(String name,String serviceName,String ip,Integer port){
        return name + Constants.SEPARATE_NAME_ATTRIBUTE + serviceName
                + Constants.SEPARATE_NAME_ATTRIBUTE + ip
                + Constants.SEPARATE_NAME_ATTRIBUTE + port;
    }
}
