package com.our_nacos.client.reg;

import com.our_nacos.client.beat.BeatInfo;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author: 乐哥
 * @Date: 2023/5/9
 * @Time: 23:14
 * @Description:
 */
public class RegProxy {

    public boolean register(NacosDiscoveryProperties nacosDiscoveryProperties){
        BeatInfo beatInfo =new BeatInfo();
        beatInfo.setIp(nacosDiscoveryProperties.getIp());
        beatInfo.setPort(nacosDiscoveryProperties.getPort());
        beatInfo.setCluster(nacosDiscoveryProperties.getClusterName());
        beatInfo.setServiceName(nacosDiscoveryProperties.getServerAddr());
        return false;

    }
}
