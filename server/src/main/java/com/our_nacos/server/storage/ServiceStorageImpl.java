package com.our_nacos.server.storage;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.bean.NacosDiscoveryProperties;
import com.our_nacos.server.common.Constants;
import com.our_nacos.server.exception.NoRegException;
import com.our_nacos.server.exception.NullBeatInfoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//TODO:自动配置类
@Component
@Scope(BeanDefinition.SCOPE_SINGLETON)
public class ServiceStorageImpl extends ServiceStorage {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //线程池
    private final ScheduledExecutorService executorService;
    //TODO:从配置文件中自定义线程数
    private int threadCount = 16;

    public ServiceStorageImpl(){
        super();
        this.executorService = new ScheduledThreadPoolExecutor(threadCount, r -> {
            Thread thread = new Thread(r);
            //必须为精灵线程,否则服务无法正常结束.
            thread.setDaemon(true);
            thread.setName(Constants.BEAT_THREAD_NAME);
            return thread;
        });
    }
    @Override
    public ServiceStorage regNewService(NacosDiscoveryProperties nacosDiscoveryProperties) {
        if(!servicesMap.containsKey(nacosDiscoveryProperties.getService())){
            Map<String,BeatInfo> beatMap = new HashMap<>();
            servicesMap.put(nacosDiscoveryProperties.getService(),beatMap);
        }
        return this;
    }

    @Override
    public ServiceStorage addServiceByBeat(BeatInfo beatInfo) {
        if(!servicesMap.containsKey(beatInfo.getServiceName())){
            throw new NoRegException(beatInfo.getServiceName());
        }
        Map<String, BeatInfo> beatInfoMap = servicesMap.get(beatInfo.getServiceName());
        if(!beatInfoMap.containsKey(buildBeatInfoKey(beatInfo))) {
            beatInfoMap.put(buildBeatInfoKey(beatInfo), beatInfo);
            //增加指定线程接收心跳维持功能
            executorService.schedule(new RunTask(executorService,beatInfo),5000,TimeUnit.MILLISECONDS);
        }else{
            //当心跳仍然健康时,重置里面scheduled属性,维持心跳健康
            //当里面心跳亚健康时,重置schedule和stopped属性,恢复心跳健康
            if(getBeatInfo(beatInfo).isStopped()){
                beatInfoMap.replace(buildBeatInfoKey(beatInfo), beatInfo);
                executorService.schedule(new RunTask(executorService,beatInfo),5000,TimeUnit.MILLISECONDS);
            }else {
                beatInfoMap.replace(buildBeatInfoKey(beatInfo), beatInfo);
            }
        }
        return this;
    }

    @Override
    public ServiceStorage stopServiceByBeat(BeatInfo beatInfo) {
        if(!servicesMap.containsKey(beatInfo.getServiceName())){
            throw new NoRegException(beatInfo.getServiceName());
        }
        Map<String, BeatInfo> stringBeatInfoMap = servicesMap.get(beatInfo.getServiceName());
        if(!stringBeatInfoMap.containsKey(buildBeatInfoKey(beatInfo))) {
            throw new NullBeatInfoException();
        }
        stringBeatInfoMap.get(buildBeatInfoKey(beatInfo)).setStopped(true);
        return this;
    }

    @Override
    public Map<String, Map<String, BeatInfo>> getAllServicesInfo() {
        return servicesMap;
    }

    @Override
    public Map<String, BeatInfo> getServicesByServiceName(String serviceName) {
        return servicesMap.getOrDefault(serviceName, null);
    }

    public ServiceStorage setThreadCount(int threadCount){
        this.threadCount = threadCount;
        return this;
    }

    public Integer getThreadCount() {
        return this.threadCount;
    }

    private void setBeatInfo(BeatInfo beatInfo){
        if(!servicesMap.containsKey(beatInfo.getServiceName())){
            return;
        }
        Map<String, BeatInfo> stringBeatInfoMap = servicesMap.get(beatInfo.getServiceName());
        if(!stringBeatInfoMap.containsKey(buildBeatInfoKey(beatInfo))) {
            return;
        }
        stringBeatInfoMap.replace(buildBeatInfoKey(beatInfo),beatInfo);
    }

    private BeatInfo getBeatInfo(BeatInfo beatInfo){
        if(!servicesMap.containsKey(beatInfo.getServiceName())){
            return null;
        }
        Map<String, BeatInfo> stringBeatInfoMap = servicesMap.get(beatInfo.getServiceName());
        if(!stringBeatInfoMap.containsKey(buildBeatInfoKey(beatInfo))) {
            return null;
        }
        return stringBeatInfoMap.get(buildBeatInfoKey(beatInfo));
    }

    private class RunTask implements Runnable {

        private final ScheduledExecutorService executorService;

        private Integer beatTime;

        private BeatInfo beatInfo;

        public RunTask(ScheduledExecutorService executorService,BeatInfo beatInfo){
            this.beatInfo = beatInfo;
            this.executorService = executorService;
            this.beatTime = Constants.BEAT_TIME_LIMIT;
        }

        @Override
        public void run() {
            this.beatInfo = getBeatInfo(this.beatInfo);
            if (this.beatInfo == null) {
                logger.error("严重错误:该心跳不存在!");
                return;
            }
            if(beatInfo.isStopped()){
                logger.info("心跳已停止,无需检测...");
            }else {
                try {
                    if (beatInfo.isScheduled()) {
                        this.beatTime = Constants.BEAT_TIME_LIMIT;
                        this.beatInfo.setScheduled(false);
                        setBeatInfo(beatInfo);
                    } else {
                        this.beatTime -= 5;
                    }
                    if (beatTime <= 0) {
                        stopServiceByBeat(beatInfo);
                        logger.info("心跳已经停止.....:" + beatInfo.toString());
                    }
                }catch (Exception e){
                    logger.error("心跳健康检测线程出现错误:"+e.getMessage());
                }finally {
                    //计划重启线程
                    executorService.schedule(new RunTask(executorService,beatInfo),5000,TimeUnit.MILLISECONDS);
                }
            }
        }
    }

}
