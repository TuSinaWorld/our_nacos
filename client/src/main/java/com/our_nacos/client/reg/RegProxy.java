package com.our_nacos.client.reg;

import com.our_nacos.client.beat.BeatInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: 乐哥
 * @Date: 2023/5/9
 * @Time: 23:14
 * @Description:
 */
@Component
public class RegProxy {

    @Autowired
    RegReactor regReactor;

    public boolean register(NacosDiscoveryProperties nacosDiscoveryProperties){
        BeatInfo beatInfo =new BeatInfo();
        beatInfo.setIp(nacosDiscoveryProperties.getIp());
        beatInfo.setPort(nacosDiscoveryProperties.getPort());
        beatInfo.setCluster(nacosDiscoveryProperties.getClusterName());
        beatInfo.setServiceName(nacosDiscoveryProperties.getServerAddr());
        //开始发请求到服务器
        regReactor.addReg(nacosDiscoveryProperties);

        return true;

    }
}
