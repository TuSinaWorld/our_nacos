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

    //日志
    Logger logger = LoggerFactory.getLogger(this.getClass());



    //线程池
    private final ScheduledExecutorService executorService;
    //TODO:从配置文件中自定义线程数
    private int threadCount = 64;

    //构造时初始化线程池
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
        return regNewService(nacosDiscoveryProperties.getService());
    }

    @Override
    public ServiceStorage regNewService(String serverName) {
        //初次注册服务进入if语句操作
        if(!servicesMap.containsKey(serverName)){
            Map<String,BeatInfo> beatMap = new HashMap<>();
            //注册
            logger.info("新服务注册:" + serverName);
            servicesMap.put(serverName,beatMap);
        }
        if(!fileMap.containsKey(serverName)){
            addFileService(serverName);
        }
        return this;
    }

    @Override
    public ServiceStorage addServiceByBeat(BeatInfo beatInfo) {
        //为了尚未开始的权限验证等功能,必须先注册服务再接收心跳
        if(!servicesMap.containsKey(beatInfo.getServiceName())){
            throw new NoRegException(beatInfo.getServiceName());
        }
        Map<String, BeatInfo> beatInfoMap = servicesMap.get(beatInfo.getServiceName());
        //接收全新心跳时进入if判断
        if(!beatInfoMap.containsKey(buildBeatInfoKey(beatInfo))) {
            beatInfoMap.put(buildBeatInfoKey(beatInfo), beatInfo);
            //新增心跳检测线程
            logger.info("新增心跳:"+buildBeatInfoKey(beatInfo));
            executorService.schedule(new RunTask(executorService,beatInfo),5000,TimeUnit.MILLISECONDS);
        }else{
            //当里面心跳亚健康时,重置schedule和stopped属性,恢复心跳健康,并重启心跳检测线程
            if(getBeatInfo(beatInfo).isStopped()){
                beatInfoMap.replace(buildBeatInfoKey(beatInfo), beatInfo);
                logger.info("心跳复苏:"+buildBeatInfoKey(beatInfo));
                executorService.schedule(new RunTask(executorService,beatInfo),5000,TimeUnit.MILLISECONDS);

            }
            //当心跳仍然健康时,重置里面scheduled属性,维持心跳健康,此时心跳检测线程正常运行,无需操作
            else {
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
        //心跳健康停止
        stringBeatInfoMap.get(buildBeatInfoKey(beatInfo)).setStopped(true);
        return this;
    }

    @Override
    public Map<String, Map<String, BeatInfo>> getAllServicesInfo() {
        return servicesMap;
    }

    public ServiceStorage setThreadCount(int threadCount){
        this.threadCount = threadCount;
        return this;
    }

    public Integer getThreadCount() {
        return this.threadCount;
    }

    //顾名思义,根据心跳信息替换服务类别中的心跳
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

    private void setFileBeatInfo(BeatInfo beatInfo){
        addFileByBeatInfo(beatInfo);
    }

    //根据心跳信息构建键名获取服务类别中的心跳
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

    //心跳检测线程
    private class RunTask implements Runnable {
        //线程池
        private final ScheduledExecutorService executorService;
        //心跳检测倒计时
        private Integer beatTime;
        //心跳信息
        private BeatInfo beatInfo;
        //发送心跳间隔
        private Integer nextTime;

        //未带倒计时的构建方法
        public RunTask(ScheduledExecutorService executorService,BeatInfo beatInfo){
            this.beatInfo = beatInfo;
            this.executorService = executorService;
            this.beatTime = Constants.BEAT_TIME_LIMIT;
        }

        //带倒计时的构建方法
        public RunTask(ScheduledExecutorService executorService,BeatInfo beatInfo,Integer beatTime){
            this.beatInfo = beatInfo;
            this.executorService = executorService;
            this.beatTime = beatTime;
        }

        @Override
        public void run() {
            //心跳不存在属于严重错误,终止线程运行
            this.beatInfo = getBeatInfo(this.beatInfo);
            if (this.beatInfo == null) {
                logger.error("严重错误:该心跳不存在!");
                return;
            }
            //心跳停止后终止线程运行
            if(beatInfo.isStopped()){
                logger.info("心跳已停止..." + buildBeatInfoKey(beatInfo));
            }
            //心跳正常时执行健康检测
            else {
                /*
                演示注释,请演示完成后注释
                */
//                logger.info("正常检测"+beatInfo+beatTime);
                try {
                    nextTime = Constants.BEAT_NEXT_TIME;
                    //根据心跳中属性确定是否存在心跳维持,若有,则重置倒计时.
                    if (beatInfo.isScheduled()) {
                        this.beatTime = Constants.BEAT_TIME_LIMIT;
                        this.beatInfo.setScheduled(false);
                        setBeatInfo(beatInfo);
                        setFileBeatInfo(beatInfo);
                    }
                    //若无,心跳倒计时减5秒
                    else {
                        this.beatTime -= 5;
                    }
                    //若心跳倒计时归0,该心跳终止.
                    if (beatTime <= 0) {
                        stopServiceByBeat(beatInfo);
                        /*
                        演示注释,请演示完成后注释
                        */
//                        logger.info("心跳已经停止.....:" + buildBeatInfoKey(beatInfo));
                        nextTime = 0;
                    }
                }catch (Exception e){
                    logger.error("心跳健康检测线程出现错误:"+e.getMessage());
                }finally {
                    //计划重启线程
                    executorService.schedule(new RunTask(executorService,beatInfo,beatTime),nextTime,TimeUnit.MILLISECONDS);
                }
            }
        }
    }

}
