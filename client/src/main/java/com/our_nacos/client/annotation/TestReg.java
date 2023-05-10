package com.our_nacos.client.annotation;


import com.our_nacos.client.beat.beat_send.MyRestTemplate;
import com.our_nacos.client.reg.NacosDiscoveryProperties;
import com.our_nacos.client.reg.RegReactor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
public class TestReg {
    @Autowired
    RegReactor regReactor;
    @Autowired
    NacosDiscoveryProperties nacosDiscoveryProperties;
    public TestReg(){

    }

    @Autowired
    public void sout() {
        regReactor.addReg(nacosDiscoveryProperties);
    }


}
