package com.our_nacos.server.storage;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.bean.NacosDiscoveryProperties;
import com.our_nacos.server.exception.NoRegException;
import com.our_nacos.server.exception.NullBeatInfoException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//TODO:自动配置类
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ServiceStorageImpl extends ServiceStorage {
    @Override
    public ServiceStorage regNewService(NacosDiscoveryProperties nacosDiscoveryProperties) {
        if(!servicesMap.containsKey(nacosDiscoveryProperties.getService())){
            Map<String,BeatInfo> beatMap = new HashMap<>();
            servicesMap.put(nacosDiscoveryProperties.getService(),beatMap);
        }
        return this;
    }

    @Override
    public ServiceStorage addServiceByBeat(BeatInfo beatInfo) {
        if(!servicesMap.containsKey(beatInfo.getServiceName())){
            throw new NoRegException(beatInfo.getServiceName());
        }
        Map<String, BeatInfo> beatInfoMap = servicesMap.get(beatInfo.getServiceName());
        if(!beatInfoMap.containsKey(buildBeatInfoKey(beatInfo))) {
            beatInfoMap.put(buildBeatInfoKey(beatInfo), beatInfo);
            //增加指定线程接收心跳维持功能
        }else{
            beatInfoMap.replace(buildBeatInfoKey(beatInfo), beatInfo);
        }

        return this;
    }

    @Override
    public ServiceStorage stopServiceByBeat(BeatInfo beatInfo) {
        if(!servicesMap.containsKey(beatInfo.getServiceName())){
            throw new NoRegException(beatInfo.getServiceName());
        }
        Map<String, BeatInfo> stringBeatInfoMap = servicesMap.get(beatInfo.getServiceName());
        if(!stringBeatInfoMap.containsKey(buildBeatInfoKey(beatInfo))) {
            throw new NullBeatInfoException();
        }
        stringBeatInfoMap.get(buildBeatInfoKey(beatInfo)).setStopped(true);
        return this;
    }

    @Override
    public Map<String, Map<String, BeatInfo>> getAllServicesInfo() {
        return servicesMap;
    }

    @Override
    public Map<String, BeatInfo> getServicesByServiceName(String serviceName) {
        return servicesMap.getOrDefault(serviceName, null);
    }


}
