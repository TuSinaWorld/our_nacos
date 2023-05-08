package com.our_nacos.client.beat;

import com.our_nacos.client.beat.beat_send.BeatSend;
import com.our_nacos.client.beat.beat_send.RestTemplateSend;
import com.our_nacos.client.common.Constants;
import com.our_nacos.client.exception.NullBeatInfoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BeatReactorImpl implements BeatReactor {

    //TODO:新增发送方式
    BeatSend beatSend = new RestTemplateSend();

    Logger logger = LoggerFactory.getLogger(this.getClass());

    //存放详细beat信息的map
    //格式:服务名+分割常量+ip+分隔常量+端口
    Map<String,BeatInfo> beatDetailedInfo = new HashMap<>();

    private final ScheduledExecutorService executorService;
    private int threadCount = 16;

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    //初始化时创建线程池API
    public BeatReactorImpl() {
        //设置计划~~
        this.executorService = new ScheduledThreadPoolExecutor(threadCount, r -> {
            Thread thread = new Thread(r);
            //必须为精灵线程,否则服务无法正常结束.
            thread.setDaemon(true);
            thread.setName(Constants.HEARTBEAT_THREAD_NAME);
            return thread;
        });
    }

    @Override
    public BeatReactor addBeatInfo(BeatInfo beatInfo) {
        if(beatInfo == null){
            throw new NullPointerException("传入beatInfo为空!");
        }
        beatDetailedInfo.put(buildKey(beatInfo),beatInfo);
        executorService.schedule(new BeatTask(beatInfo,beatSend),beatInfo.getPeriod(), TimeUnit.MILLISECONDS);
        return this;
    }

    @Override
    public BeatInfo getBeatInfo0WithAllName(String serverInfo) {
        BeatInfo beatInfo = beatDetailedInfo.get(serverInfo);
        if(beatInfo == null){
            throw new NullBeatInfoException();
        }
        return beatDetailedInfo.get(serverInfo);
    }

    @Override
    public BeatInfo[] getBeanInfo0(String serverName) {
        List<BeatInfo> list = new ArrayList<>();
        for (BeatInfo value : beatDetailedInfo.values()) {
            if(value.getServiceName().equals(serverName)){
                list.add(value);
            }
        }
        if(list.isEmpty()){
            throw new NullBeatInfoException();
        }
        //根据list返回数组
        return list.toArray(new BeatInfo[0]);
    }

    @Override
    public Map<String, BeatInfo> getBeatInfos() {
        return beatDetailedInfo;
    }

    @Override
    public BeatReactor clearBeatInfo() {
        beatDetailedInfo.clear();
        return this;
    }

    //根据BeatInfo获取完整键
    private String buildKey(BeatInfo beatInfo){
        return beatInfo.getServiceName() + Constants.SEPARATE_NAME_ATTRIBUTE
                + beatInfo.getIp() + Constants.SEPARATE_NAME_ATTRIBUTE
                + beatInfo.getPort();
    }

    private class BeatTask implements Runnable{

        private final BeatInfo beatInfo;
        private final BeatSend beatSend;

        public BeatTask(BeatInfo beatInfo, BeatSend beatSend){
            if(beatInfo == null){
                throw new NullPointerException("传入beatInfo为空!");
            }else if(beatSend == null){
                throw new NullPointerException("传入beatSend为空!");
            }
            this.beatInfo = beatInfo;
            this.beatSend = beatSend.setBeatInfo(beatInfo);
        }
        @Override
        public void run() {
            try {
                beatSend.send();
            }catch(Exception e){
                logger.error("未知的错误:",e);
            }finally {
                executorService.schedule(new BeatTask(beatInfo,beatSend),beatInfo.getPeriod(), TimeUnit.MILLISECONDS);
            }
        }
    }

}
