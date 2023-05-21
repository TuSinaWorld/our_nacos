package com.our_nacos.client.beat.beat_send;

import com.our_nacos.client.beat.BeatInfo;

public abstract class BeatSend {

    protected BeatInfo beatInfo;

    protected String serverIp = "127.0.0.1";

    protected Integer serverPort=-1;

    public BeatSend(){}
    public BeatSend(BeatInfo beatInfo){
        setBeatInfo(beatInfo);
    }
    public BeatSend setBeatInfo(BeatInfo beatInfo) {
        this.beatInfo = beatInfo;
        this.serverIp = beatInfo.getServerIp();
        this.serverPort = beatInfo.getSeverPort();
        return this;
    }

    //TODO:回传信息
    public abstract void send();

    public BeatInfo getBeatInfo() {
        return beatInfo;
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
}
