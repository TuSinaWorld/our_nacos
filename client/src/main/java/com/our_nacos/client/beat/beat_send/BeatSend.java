package com.our_nacos.client.beat.beat_send;

import com.our_nacos.client.beat.BeatInfo;

public abstract class BeatSend {

    private BeatInfo beatInfo;
    //TODO:从配置文件获取ourNacos服务ip
    private String serverIp;
    //TODO:从配置文件获取ourNacos服务端口
    private Integer serverPort;

    public BeatSend(){}
    public BeatSend(BeatInfo beatInfo){
        this.beatInfo = beatInfo;
    }

    public abstract void send();
}
