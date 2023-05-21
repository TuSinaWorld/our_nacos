package com.our_nacos.web.bean;

import java.util.Map;


/*
这个类的作用为保存心跳信息
 */
//注释请参考client中同名类
public class BeatInfo {

    public BeatInfo() {
    }

    private int port;

    private String ip;

    private double weight = 1;

    private String serviceName;

    private String cluster = null;

    private Map<String, String> metadata = null;

    private volatile boolean scheduled = true;

    //心跳间隔时间(ms)
    private volatile long period = 5000;

    private volatile boolean stopped = false;

    private String serverIp = "127.0.0.1";

    private Integer severPort = -1;

    public String getServerIp() {
        return serverIp;
    }

    public void setServerIp(String serverIp) {
        this.serverIp = serverIp;
    }

    public Integer getSeverPort() {
        return severPort;
    }

    public void setSeverPort(Integer severPort) {
        this.severPort = severPort;
    }

    public int getPort() {
        return port;
    }

    public BeatInfo setPort(int port) {
        this.port = port;
        return this;
    }

    public String getIp() {
        return ip;
    }

    public BeatInfo setIp(String ip) {
        this.ip = ip;
        return this;
    }

    public double getWeight() {
        return weight;
    }

    public BeatInfo setWeight(double weight) {
        this.weight = weight;
        return this;
    }

    public String getServiceName() {
        return serviceName;
    }

    public BeatInfo setServiceName(String serviceName) {
        this.serviceName = serviceName;
        return this;
    }

    public String getCluster() {
        return cluster;
    }

    public BeatInfo setCluster(String cluster) {
        this.cluster = cluster;
        return this;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public BeatInfo setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
        return this;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public BeatInfo setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
        return this;
    }

    public long getPeriod() {
        return period;
    }

    public BeatInfo setPeriod(long period) {
        this.period = period;
        return this;
    }

    public boolean isStopped() {
        return stopped;
    }

    public BeatInfo setStopped(boolean stopped) {
        this.stopped = stopped;
        return this;
    }

    @Override
    public String toString() {
        return "BeatInfo{" +
                "port=" + port +
                ", ip='" + ip + '\'' +
                ", weight=" + weight +
                ", serviceName='" + serviceName + '\'' +
                ", cluster='" + cluster + '\'' +
                ", metadata=" + metadata +
                ", scheduled=" + scheduled +
                ", period=" + period +
                ", stopped=" + stopped +
                '}';
    }
}
