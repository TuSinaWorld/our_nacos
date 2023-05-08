package com.our_nacos.server.controller;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.bean.ResponseBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/beat")
public class SpringServer {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping("/accept")
    public ResponseBean accept(@RequestBody BeatInfo beatInfo){
        //TODO:根据心跳注册服务,维持服务等等.
        logger.info(String.valueOf(beatInfo));
        return new ResponseBean(1,"",beatInfo);
    }
}