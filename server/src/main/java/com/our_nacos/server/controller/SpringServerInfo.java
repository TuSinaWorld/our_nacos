package com.our_nacos.server.controller;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.storage.ServiceStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author: 乐哥
 * @Date: 2023/5/20
 * @Time: 15:56
 * @Description:
 */
@RestController
@RequestMapping("/get")
public class SpringServerInfo {

    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ServiceStorage storage;

    @RequestMapping("list")
    public Map<String, BeatInfo> getInstanceList(String serviceName){
        Map<String, BeatInfo> beatInfoList = storage.getBeatInfoList(serviceName);
        if(beatInfoList==null && beatInfoList.isEmpty()){
            logger.error("该服务名未注册,无法获取相关实例信息...");
        }
        return beatInfoList;
    }
}
