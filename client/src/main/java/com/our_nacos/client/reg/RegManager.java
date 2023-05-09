package com.our_nacos.client.reg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Author: 乐哥
 * @Date: 2023/5/9
 * @Time: 23:25
 * @Description:
 */
@Component
public class RegManager {
    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    static RegProxy r = new RegProxy();

    public void register(NacosDiscoveryProperties nacosDiscoveryProperties){
        r.register(nacosDiscoveryProperties);
    }
}
