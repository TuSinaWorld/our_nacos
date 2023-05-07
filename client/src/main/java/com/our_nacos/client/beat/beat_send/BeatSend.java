package com.our_nacos.client.beat.beat_send;

import com.our_nacos.client.beat.BeatInfo;

public abstract class BeatSend {

    protected BeatInfo beatInfo;

    //TODO:从配置文件获取ourNacos服务ip
    protected String serverIp = "127.0.0.1";

    //TODO:从配置文件获取ourNacos服务端口
    protected Integer serverPort = 25544;

    public BeatSend(){}
    public BeatSend(BeatInfo beatInfo){
        this.beatInfo = beatInfo;
    }
    public BeatSend setBeatInfo(BeatInfo beatInfo) {
        this.beatInfo = beatInfo;
        return this;
    }

    //TODO:回传信息
    public abstract void send();
}
