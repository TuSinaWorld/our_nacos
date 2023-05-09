package com.our_nacos.client.reg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: 乐哥
 * @Date: 2023/5/9
 * @Time: 22:40
 * @Description:
 */

@AutoConfigureAfter(ServerProperties.class)
@Component
public class Serverport {

    @Autowired
    ServerProperties serverProperties;
    private Integer port;

    public Integer getPort() {
        System.out.println("端口号为:"+serverProperties.getPort());
        return serverProperties.getPort();
    }

    public void setPort(Integer port) {
        this.port = port;
    }

}