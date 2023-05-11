package com.our_nacos.server.controller;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.bean.NacosDiscoveryProperties;
import com.our_nacos.server.bean.ResponseBean;
import com.our_nacos.server.storage.ServiceStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beat")
public class SpringServer {

    @Autowired
    ServiceStorage serviceStorage;
    @Autowired
    Test2 test2;

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //新增方法
    @RequestMapping("/accept")
    public ResponseBean accept(@RequestBody BeatInfo beatInfo){
        //TODO:根据心跳注册服务,维持服务等等.
        logger.info(String.valueOf(beatInfo));
        return new ResponseBean(1,"",null);
    }

    @RequestMapping("/reg")
    public ResponseBean reg(@RequestBody NacosDiscoveryProperties nacosDiscoveryProperties){
        return new ResponseBean(1,"",null);
    }

    @RequestMapping("/remove")
    public ResponseBean remove(@RequestBody NacosDiscoveryProperties nacosDiscoveryProperties){
        return new ResponseBean(1,"",null);
    }
}
