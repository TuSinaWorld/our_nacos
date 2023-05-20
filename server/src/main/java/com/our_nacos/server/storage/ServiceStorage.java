package com.our_nacos.server.storage;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.bean.NacosDiscoveryProperties;
import com.our_nacos.server.common.Constants;
import com.our_nacos.server.exception.NullBeatInfoException;

import java.util.HashMap;
import java.util.Map;


public abstract class ServiceStorage {
    //服务储存map
    protected volatile Map<String,Map<String,BeatInfo>> servicesMap = new HashMap<>();

    //根据服务名获取服务列表
    public Map<String,BeatInfo> getBeatInfoList(String serviceName){
        return servicesMap.getOrDefault(serviceName, null);
    }

    //注册服务方法
    //TODO:账号密码检测等等功能
    public abstract ServiceStorage regNewService(NacosDiscoveryProperties nacosDiscoveryProperties);

    //接收到服务心跳后的处理(请忽略方法名的问题)
    public abstract ServiceStorage addServiceByBeat(BeatInfo  beatInfo);

    //根据心跳类终止服务(其实应该private的说?)
    public abstract ServiceStorage stopServiceByBeat(BeatInfo beatInfo);

    //获取所有服务信息
    public abstract Map<String,Map<String,BeatInfo>> getAllServicesInfo();

    //根据服务信息构建键名
    protected String buildBeatInfoKey(BeatInfo beatInfo){
        return beatInfo.getServiceName() + Constants.SEPARATE_NAME_ATTRIBUTE
                + beatInfo.getIp() + Constants.SEPARATE_NAME_ATTRIBUTE
                + beatInfo.getPort();
    }
}
