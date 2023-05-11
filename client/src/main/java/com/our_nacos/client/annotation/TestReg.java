package com.our_nacos.client.annotation;


import com.our_nacos.client.reg.NacosDiscoveryProperties;
import com.our_nacos.client.reg.RegReactor;
import org.springframework.beans.factory.annotation.Autowired;

public class TestReg {
    @Autowired
    public void sout(@Autowired RegReactor regReactor,
                     @Autowired NacosDiscoveryProperties nacosDiscoveryProperties) {
        regReactor.addReg(nacosDiscoveryProperties);
    }


}
