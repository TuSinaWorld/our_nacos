package com.our_nacos.client.adapter;

import com.our_nacos.client.beat.BeatInfo;
import com.our_nacos.client.reg.NacosDiscoveryProperties;

//适配器类,适配注册与心跳bean
public class BeatInfoAdapterNacosDiscoveryProperties extends BeatInfo {
    public BeatInfoAdapterNacosDiscoveryProperties(NacosDiscoveryProperties nacosDiscoveryProperties){
        setIp(nacosDiscoveryProperties.getIp());
        setPort(nacosDiscoveryProperties.getPort());
        setServiceName(nacosDiscoveryProperties.getService());
        setCluster(nacosDiscoveryProperties.getClusterName());
        setWeight(nacosDiscoveryProperties.getWeight());
        setMetadata(nacosDiscoveryProperties.getMetadata());
        //TODO:设置更多参数
    }
}
