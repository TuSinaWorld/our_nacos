package com.our_nacos.client.reg.reg_send;

import com.our_nacos.client.common.Constants;
import com.our_nacos.client.reg.NacosDiscoveryProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class RegSend {
    Logger log = LoggerFactory.getLogger(RegSend.class);
    protected NacosDiscoveryProperties nacosDiscoveryProperties;

    //TODO: 根据配置文件获取~
    protected String serverIp;
    protected Integer serverPort;

    public RegSend() {
    }

    public RegSend(NacosDiscoveryProperties nacosDiscoveryProperties) {
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
        setIpAndPort();
    }

    public NacosDiscoveryProperties getNacosDiscoveryProperties() {
        return nacosDiscoveryProperties;
    }

    public void setNacosDiscoveryProperties(NacosDiscoveryProperties nacosDiscoveryProperties){
        this.nacosDiscoveryProperties = nacosDiscoveryProperties;
        setIpAndPort();
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

    private void setIpAndPort() {
        String serverAddr = nacosDiscoveryProperties.getServerAddr();
        this.serverIp = serverAddr.substring(0,serverAddr.indexOf(":"));
        this.serverPort = Integer.valueOf(serverAddr.substring(serverAddr.indexOf(":") + 1));
    }
}
