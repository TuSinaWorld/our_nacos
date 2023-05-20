package com.our_nacos.client.reg;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * @Author: 乐哥
 * @Date: 2023/5/20
 * @Time: 19:31
 * @Description:
 */
@ConfigurationProperties(prefix = "my.nacos.server")
public class NacosServerProperties {
    private Integer port = -1;

    private String serverAdd="loacalhost";

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServerAdd() {
        return serverAdd;
    }

    public void setServarAdd(String servarAdd) {
        this.serverAdd = serverAdd;
    }

    public NacosServerProperties(Integer port, String serverAdd) {
        this.port = port;
        this.serverAdd = serverAdd;
    }

    public NacosServerProperties() {
    }
}
