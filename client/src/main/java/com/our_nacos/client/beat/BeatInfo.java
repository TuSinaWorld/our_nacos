package com.our_nacos.client.beat;

import java.util.Map;


/*
这个类的作用为保存心跳信息
 */
public class BeatInfo {

    public BeatInfo() {
    }

    private int port;

    private String ip;

    //TODO:权重
    private double weight = 1;

    private String serviceName;

    //TODO:服务集群
    private String cluster = null;

    //TODO:服务元数据
    private Map<String, String> metadata = null;

    //TODO:判断是否加入心跳计时器
    private volatile boolean scheduled = false;

    //心跳间隔时间(ms)
    //TODO:根据配置文件获取心跳间隔时间
    private volatile long period = 5000;

    //判断心跳是否停止
    private volatile boolean stopped = false;

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
