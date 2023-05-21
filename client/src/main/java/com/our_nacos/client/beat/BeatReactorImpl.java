package com.our_nacos.client.beat;

import com.our_nacos.client.beat.beat_send.BeatSend;
import com.our_nacos.client.common.Constants;
import com.our_nacos.client.exception.NullBeatInfoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BeatReactorImpl implements BeatReactor {

    //TODO:新增发送方式
    //默认采用RestTemplate发包
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    BeatSend beatSend;
    //日志信息
    Logger logger = LoggerFactory.getLogger(this.getClass());

    //存放详细beat信息的map
    //格式:服务名+分割常量+ip+分隔常量+端口
    volatile Map<String,BeatInfo> beatDetailedInfo = new HashMap<>();

    //线程池
    private final ScheduledExecutorService executorService;
    //TODO:从配置文件中自定义线程数
    private int threadCount = 16;

    @Override
    public BeatReactor setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        return this;
    }

    //初始化时创建线程池API
    public BeatReactorImpl() {
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
        String key = buildKey(beatInfo);
        if(beatDetailedInfo.containsKey(key)){
            beatDetailedInfo.replace(key,beatInfo);
        }else {
            beatDetailedInfo.put(key, beatInfo);
        }
        //新增心跳线程,1s后执行
        executorService.schedule(new BeatTask(beatInfo,beatSend),1000, TimeUnit.MILLISECONDS);
        return this;
    }


    @Override
    public BeatInfo getBeatInfoWithAllName(String serverInfo) {
        BeatInfo beatInfo = beatDetailedInfo.get(serverInfo);
        if(beatInfo == null){
            throw new NullBeatInfoException();
        }
        return beatDetailedInfo.get(serverInfo);
    }

    @Override
    public BeatInfo[] getBeanInfo(String serverName) {
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
    public BeatReactor stopByBeatInfo(BeatInfo beatInfo) {
        return stopByMapKey(buildKey(beatInfo));
    }

    @Override
    public BeatReactor stopByServerInfo(String serverName, String ip, int port) {
        return stopByMapKey(buildKey(serverName,ip,port));
    }

    @Override
    public BeatReactor stopByMapKey(String key) {
        BeatInfo beatInfo = beatDetailedInfo.get(key);
        if(beatInfo == null){
            throw new NullBeatInfoException();
        }
        beatInfo.setStopped(true);
        beatDetailedInfo.replace(key,beatInfo);
        return this;
    }

    //根据BeatInfo获取完整键名
    private String buildKey(BeatInfo beatInfo){
        return beatInfo.getServiceName() + Constants.SEPARATE_NAME_ATTRIBUTE
                + beatInfo.getIp() + Constants.SEPARATE_NAME_ATTRIBUTE
                + beatInfo.getPort();
    }

    //根据服务信息获取完整键名
    private String buildKey(String serverName, String ip, int port){
        return serverName + Constants.SEPARATE_NAME_ATTRIBUTE
                + ip + Constants.SEPARATE_NAME_ATTRIBUTE
                + port;
    }

    //心跳线程
    private class BeatTask implements Runnable{
        //心跳线程内部心跳信息
        private BeatInfo beatInfo;
        //心跳线程内部发包方法
        private final BeatSend beatSend;

        //构建方法
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
            //若心跳停止,则终止线程
            if(beatInfo.isStopped()){
                logger.info("检测到服务中断...");
                return;
            }
            try {
                //发包
                beatSend.send();
                //重新获取最新心跳信息
                this.beatInfo = beatDetailedInfo.get(buildKey(beatInfo));
            }catch(Exception e){
                //发包时异常不影响线程运行,只做记录
                logger.error("未知的错误:",e);
            }finally {
                //根据心跳时间循环线程
                //logger.info("根据心跳时间循环发送心跳,实现心跳维持功能");
                executorService.schedule(new BeatTask(beatInfo, beatSend), beatInfo.getPeriod(), TimeUnit.MILLISECONDS);
            }
        }
    }
}
