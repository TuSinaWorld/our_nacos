package com.our_nacos.client.reg;

import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * @Author: 乐哥
 * @Date: 2023/5/9
 * @Time: 22:32
 * @Description:
 */

public class NacosDiscoveryProperties {

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
    private String ip ="0.0.0.0";

    /**
     * 要注册的网络接口的 IP。
     */
    private String networkInterface = "";

    /**
     * The port your want to register for your service instance, needn't to set it if the
     * auto detect port works well.
     */
    private int port = -1;


    private boolean isEnabled;

    public boolean isEnabled() {
        return isEnabled;
    }

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
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

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
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

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
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

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
    }

    public String getServerAddr() {
        return serverAddr;
    }

    public void setServerAddr(String serverAddr) {
        this.serverAddr = serverAddr;
    }


    public long getWatchDelay() {
        return watchDelay;
    }

    public void setWatchDelay(long watchDelay) {
        this.watchDelay = watchDelay;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NacosDiscoveryProperties that = (NacosDiscoveryProperties) o;
        return Objects.equals(serverAddr, that.serverAddr)
                && Objects.equals(username, that.username)
                && Objects.equals(password, that.password)
                && Objects.equals(endpoint, that.endpoint)
                && Objects.equals(namespace, that.namespace)
                && Objects.equals(logName, that.logName)
                && Objects.equals(service, that.service)
                && Objects.equals(clusterName, that.clusterName)
                && Objects.equals(group, that.group) && Objects.equals(ip, that.ip)
                && Objects.equals(port, that.port)
                && Objects.equals(networkInterface, that.networkInterface);
    }

    @Override
    public int hashCode() {
        return Objects.hash(serverAddr, username, password, endpoint, namespace,
                watchDelay, logName, service, weight, clusterName, group,
                registerEnabled, ip, networkInterface, port);
    }

    @Override
    public String toString() {
        return "NacosDiscoveryProperties{" + "serverAddr='" + serverAddr + '\''
                + ", endpoint='" + endpoint + '\'' + ", namespace='" + namespace + '\''
                + ", watchDelay=" + watchDelay + ", logName='" + logName + '\''
                + ", service='" + service + '\'' + ", weight=" + weight
                + ", clusterName='" + clusterName + '\'' + ", group='" + group + '\''
                + ", metadata=" + metadata + ", registerEnabled=" + registerEnabled
                + ", ip='" + ip + '\'' + ", networkInterface='" + networkInterface + '\''
                + ", port=" + getPort() + '}';
    }
}
