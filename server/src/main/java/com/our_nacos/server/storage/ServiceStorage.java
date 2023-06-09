package com.our_nacos.server.storage;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.bean.NacosDiscoveryProperties;
import com.our_nacos.server.common.Constants;
import com.our_nacos.server.exception.NullBeatInfoException;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public abstract class ServiceStorage {
    //服务储存map
    protected volatile Map<String,Map<String,BeatInfo>> servicesMap = new HashMap<>();

    //总文件列表  两层map结构:第一层服务名作为键,第二层文件名作为键,实例信息作为值
    protected Map<String,Map<String,BeatInfo>> fileMap = new HashMap<>();

    public Map<String, Map<String, BeatInfo>> getFileMap() {
        return fileMap;
    }

    public void setFileMap(Map<String, Map<String, BeatInfo>> fileMap) {
        this.fileMap = fileMap;
    }

    public void addFileService(String serviceName){
        if(!fileMap.containsKey(serviceName)){
            fileMap.put(serviceName, new HashMap<>());
        }
    }

    public void addFileByBeatInfo(BeatInfo beatInfo){
        Map<String, BeatInfo> fileByServiceName = getFileByServiceName(beatInfo.getServiceName());
        if(fileByServiceName == null){
            throw new RuntimeException("非法的:服务未注册!");
        }
        List<String> files = beatInfo.getFiles();
        for (String file : files) {
            if(fileByServiceName.containsKey(file)){
                fileByServiceName.replace(file,beatInfo);
            }else{
                fileByServiceName.put(file,beatInfo);
            }
        }
    }

    public Map<String, BeatInfo> getFileByServiceName(String serviceName){
        if(fileMap.containsKey(serviceName)){
            return fileMap.get(serviceName);
        }
        return null;
    }

    public BeatInfo getFileBeatInfo(String serviceName,String fileName){
        Map<String, BeatInfo> fileByServiceName = getFileByServiceName(serviceName);
        if(fileByServiceName != null && fileByServiceName.containsKey(fileName)){
            return fileByServiceName.get(fileName);
        }
        return null;
    }

    public BeatInfo getFileBeatInfo(String serviceName,File file){
        return getFileBeatInfo(serviceName,file.getName());
    }

    //根据服务名获取服务列表
    public Map<String,BeatInfo> getBeatInfoList(String serviceName){
        return servicesMap.getOrDefault(serviceName, null);
    }

    public Map<String, Map<String, BeatInfo>> getServicesMap() {
        return servicesMap;
    }

    //注册服务方法
    //TODO:账号密码检测等等功能
    public abstract ServiceStorage regNewService(NacosDiscoveryProperties nacosDiscoveryProperties);
    //提供只根据名字的注册方法,满足网页端需求
    public abstract ServiceStorage regNewService(String serverName);

    //接收到服务心跳后的处理(请忽略方法名的问题)
    public abstract ServiceStorage addServiceByBeat(BeatInfo  beatInfo);

    //根据心跳类终止服务(其实应该private的说?)
    public abstract ServiceStorage stopServiceByBeat(BeatInfo beatInfo);

    //获取所有服务信息
    public abstract Map<String,Map<String,BeatInfo>> getAllServicesInfo();

    //根据服务信息构建键名
    protected String buildBeatInfoKey(BeatInfo beatInfo){
        return beatInfo.getServiceName() + Constants.SEPARATE_NAME_ATTRIBUTE
                + beatInfo.getIp() + Constants.SEPARATE_NAME_ATTRIBUTE
                + beatInfo.getPort();
    }
}
