package com.our_nacos.client.reg.reg_send;

import com.our_nacos.client.reg.NacosDiscoveryProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class RegSend {
    Logger log = LoggerFactory.getLogger(RegSend.class);
    private NacosDiscoveryProperties nacosDiscoveryProperties;
    //TODO:配置文件获取~
    private String serverIp = "127.0.0.1";
    private Integer serverPort = 25544;

    public RegSend() {
    }

    public RegSend(NacosDiscoveryProperties nacosDiscoveryProperties) {
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
    }

    public NacosDiscoveryProperties getNacosDiscoveryProperties() {
        return nacosDiscoveryProperties;
    }

    public void setNacosDiscoveryProperties(NacosDiscoveryProperties nacosDiscoveryProperties) {
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
    }

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getServerPort() {
        return serverPort;
    }

    public void setServerPort(Integer serverPort) {
        this.serverPort = serverPort;
    }

    public abstract void sendRegInfo();
    public abstract void revocationRegInfo();
}
