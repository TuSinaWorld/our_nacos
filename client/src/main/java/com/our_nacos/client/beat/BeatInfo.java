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
    private volatile long period = 5000;

    //TODO:根据心跳信息判断服务是否停止
    private volatile boolean stopped = false;

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public boolean isScheduled() {
        return scheduled;
    }

    public void setScheduled(boolean scheduled) {
        this.scheduled = scheduled;
    }

    public long getPeriod() {
        return period;
    }

    public void setPeriod(long period) {
        this.period = period;
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
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
