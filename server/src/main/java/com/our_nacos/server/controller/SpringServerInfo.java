package com.our_nacos.server.controller;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.storage.ServiceStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    //日志
    Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    ServiceStorage storage;

    //根据服务名获取服务类别的mvc方法
    @RequestMapping("/list")
    public Map<String, BeatInfo> getInstanceList(@RequestParam String serviceName){
        Map<String, BeatInfo> beatInfoList = storage.getBeatInfoList(serviceName);
        if(beatInfoList==null && beatInfoList.isEmpty()){
            logger.error("该服务名未注册,无法获取相关实例信息...");
        }
        return beatInfoList;
    }

    @RequestMapping("/listall")
    public Map<String, Map<String, BeatInfo>> getInstanceListAll(){
        Map<String, Map<String, BeatInfo>> servicesMap = storage.getServicesMap();
        if(servicesMap==null && servicesMap.isEmpty()){
            logger.error("该服务名未注册,无法获取相关实例信息...");
        }
        return servicesMap;
    }

    @RequestMapping("/getURl")
    public BeatInfo geturl(){
        BeatInfo beatInfo=null;
        try {
            beatInfo = getService.GetServiceName("res-food");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return beatInfo;
    }

    @RequestMapping("/getUrl/{fileName}")
    public String getUrl(@PathVariable("fileName") String fileName, @RequestParam String serviceName){
        if("".equals(serviceName) || serviceName.length()==0){
            logger.error("获取服务名失败");
            return "获取的服务名为空";
        }
        if("".equals(fileName) || fileName.length()==0 ){
            logger.error("获取到的文件名为空!");
            return "获取到的文件名为空";
        }
        Map<String, Map<String, BeatInfo>> fileMap = storage.getFileMap();
        Map<String, BeatInfo> beatInfoMap = fileMap.get(serviceName);
        BeatInfo beatInfo = beatInfoMap.get(fileName);
        // http:// ip :port/ 服务名/ download/文件名
        String url=Constants.HTTP+beatInfo.getServerIp()+Constants.URL_SEPARATE+beatInfo.getPort()
                +Constants.HTTP_SEPARATE+"download"+Constants.HTTP_SEPARATE;
        logger.info("获取到的url:"+url);
        return url;
    }
}
