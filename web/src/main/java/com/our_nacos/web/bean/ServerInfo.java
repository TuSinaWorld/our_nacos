package com.our_nacos.web.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties(prefix = "our-nacos.server-info")
public class ServerInfo {
    private String serverIp;
    private Integer serverPort;

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

    @Override
    public String toString() {
        return "ServerInfo{" +
                "serverIp='" + serverIp + '\'' +
                ", serverPort=" + serverPort +
                '}';
    }
}
