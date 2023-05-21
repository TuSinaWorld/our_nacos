package com.our_nacos.client.reg;

import com.our_nacos.client.reg.util.InetAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: 乐哥
 * @Date: 2023/5/10
 * @Time: 18:02
 * @Description:  进行实例信息整合
 */
@ConfigurationProperties(prefix = "my.nacos.discovery")
@AutoConfigureAfter({Serverport.class,NacosDiscoveryProperties.class})
public class Instance {

    static NacosDiscoveryProperties nacosDiscoveryProperties=new NacosDiscoveryProperties();
    @Autowired
    Serverport serverport;

    public Instance() {
    }

    /**
     * nacos客户端的地址
     */
    private String serverAddr;

    /**
     * nacos用户名
     */
    private String username;

    /**
     * nacos密码
     */
    private String password;

    /**
     * 服务的域名,通过该域名可以动态显示服务器地址
     */
    private String endpoint = "";

    /**
     * 命名空间
     */
    private String namespace;

    /**
     * 从Nacos服务器拉取新服务的持续时间。
     */
    private long watchDelay = 30000;

    /**
     * nacos 日志名
     */
    private String logName;

    /**
     * 服务注册名
     */
    @Value("${my.nacos.discovery.service:${my.application.name:}}")
    private String service;

    /**
     * 权重
     */
    private float weight = 1;

    /**
     * 集群名称
     */
    private String clusterName = "DEFAULT";

    /**
     * nacos分组名
     */
    private String group = "DEFAULT_GROUP";

    /**
     * 要注册的额外元数据
     */
    private Map<String, String> metadata = new HashMap<>();

    /**
     * 如果只想订阅，但不想注册服务，请将其设置为 false
     */
    private boolean registerEnabled = true;

    /**
     * 要为服务实例注册的 IP 地址，通过工具类得出获取主机ip
     */
    private String ip = InetAddressUtil.getHostIp();

    /**
     * 要注册的网络接口的 IP。
     */
    private String networkInterface = "";

    /**
     * The port your want to register for your service instance, needn't to set it if the
     * auto detect port works well.
     */
    private int port = -1;

    private boolean isEnabled=true;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public long getWatchDelay() {
        return watchDelay;
    }

    public void setWatchDelay(long watchDelay) {
        this.watchDelay = watchDelay;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public boolean isRegisterEnabled() {
        return registerEnabled;
    }

    public void setRegisterEnabled(boolean registerEnabled) {
        this.registerEnabled = registerEnabled;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getNetworkInterface() {
        return networkInterface;
    }

    public void setNetworkInterface(String networkInterface) {
        this.networkInterface = networkInterface;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public NacosDiscoveryProperties getNacosDiscoveryProperties() {
        nacosDiscoveryProperties.setPort(serverport.getPort());
        nacosDiscoveryProperties.setIp(getIp());
        nacosDiscoveryProperties.setUsername(getUsername());
        nacosDiscoveryProperties.setPassword(getPassword());
        nacosDiscoveryProperties.setServerAddr(getServerAddr());
        nacosDiscoveryProperties.setGroup(getGroup());
        nacosDiscoveryProperties.setMetadata(getMetadata());
        nacosDiscoveryProperties.setEndpoint(getEndpoint());
        nacosDiscoveryProperties.setNamespace(getNamespace());
        nacosDiscoveryProperties.setNamespace(getNamespace());
        nacosDiscoveryProperties.setLogName(getLogName());
        nacosDiscoveryProperties.setService(getService());
        nacosDiscoveryProperties.setNetworkInterface(getNetworkInterface());
        nacosDiscoveryProperties.setEnabled(isEnabled());//是否启用
        return nacosDiscoveryProperties;
    }

}
