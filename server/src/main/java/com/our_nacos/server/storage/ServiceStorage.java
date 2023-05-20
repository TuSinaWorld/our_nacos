package com.our_nacos.server.storage;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.bean.NacosDiscoveryProperties;
import com.our_nacos.server.common.Constants;
import com.our_nacos.server.exception.NullBeatInfoException;

import java.util.HashMap;
import java.util.Map;


public abstract class ServiceStorage {
    protected volatile Map<String,Map<String,BeatInfo>> servicesMap = new HashMap<>();

    public Map<String,BeatInfo> getBeatInfoList(String serviceName){
        if(!servicesMap.containsKey(serviceName)){
            throw new NullBeatInfoException();
        }
        return servicesMap.get(serviceName);
    }

    public abstract ServiceStorage regNewService(NacosDiscoveryProperties nacosDiscoveryProperties);

    public abstract ServiceStorage addServiceByBeat(BeatInfo  beatInfo);

    public abstract ServiceStorage stopServiceByBeat(BeatInfo beatInfo);

    public abstract Map<String,Map<String,BeatInfo>> getAllServicesInfo();

    public abstract Map<String,BeatInfo> getServicesByServiceName(String serviceName);


    protected String buildBeatInfoKey(BeatInfo beatInfo){
        return beatInfo.getServiceName() + Constants.SEPARATE_NAME_ATTRIBUTE
                + beatInfo.getIp() + Constants.SEPARATE_NAME_ATTRIBUTE
                + beatInfo.getPort();
    }
}
