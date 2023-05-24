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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/beat")
public class SpringServer {

    @Autowired
    ServiceStorage storage;

    //日志
    Logger logger = LoggerFactory.getLogger(this.getClass());

    //根据接收心跳信息进行下一步操作
    @RequestMapping("/accept")
    public ResponseBean accept(@RequestBody BeatInfo beatInfo){
        //使用线程进行下一步操作
        Thread thread = new Thread(() -> {
//            logger.info(String.valueOf(beatInfo));
            //具体的下一步操作(请无视方法名的问题)
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
        //使用线程进行下一步操作
        Thread thread = new Thread(() -> {
//            logger.info(String.valueOf(nacosDiscoveryProperties));
            storage.regNewService(nacosDiscoveryProperties);
        });
        thread.setDaemon(true);
        thread.setName(buildThreadName(nacosDiscoveryProperties));
        thread.start();
        return new ResponseBean(1,"",null);
    }

    @RequestMapping("/regByName")
    public ResponseBean regByName(@RequestParam String serviceName){
        //使用线程进行下一步操作
        Thread thread = new Thread(() -> {
//            logger.info(String.valueOf(nacosDiscoveryProperties));
            storage.regNewService(serviceName);
        });
        thread.setDaemon(true);
        Random random = new Random();
        thread.setName("web_thread_"+serviceName + random.nextInt());
        System.out.println("web_thread_"+serviceName + "_" + random.nextInt());
        thread.start();
        return new ResponseBean(1,"",null);
    }

    //TODO:通过接口结束相应服务
    @RequestMapping("/remove")
    public ResponseBean remove(@RequestBody NacosDiscoveryProperties nacosDiscoveryProperties){
        return new ResponseBean(1,"",null);
    }

    //构建心跳线程名
    private String buildThreadName(BeatInfo beatInfo){
        return buildThreadName("BeatThread",beatInfo.getServiceName(),beatInfo.getIp(), beatInfo.getPort());
    }

    //构建注册线程名
    private String buildThreadName(NacosDiscoveryProperties nacosDiscoveryProperties){
        return buildThreadName("NacosPropertiesThread",nacosDiscoveryProperties.getService()
        ,nacosDiscoveryProperties.getIp(),nacosDiscoveryProperties.getPort());
    }

    //构建线程名通用方法
    private String buildThreadName(String name,String serviceName,String ip,Integer port){
        return name + Constants.SEPARATE_NAME_ATTRIBUTE + serviceName
                + Constants.SEPARATE_NAME_ATTRIBUTE + ip
                + Constants.SEPARATE_NAME_ATTRIBUTE + port;
    }
}
