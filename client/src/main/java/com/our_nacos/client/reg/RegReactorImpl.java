package com.our_nacos.client.reg;

import com.our_nacos.client.adapter.BeatInfoAdapterNacosDiscoveryProperties;
import com.our_nacos.client.beat.BeatReactor;
import com.our_nacos.client.common.Constants;
import com.our_nacos.client.reg.reg_send.RegSend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class RegReactorImpl implements RegReactor {
    //注册信息发送抽象类
    //TODO:更多实现类
    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    RegSend regSend;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    @Autowired
    BeatReactor beatReactor;

    //日志信息
    Logger logger = LoggerFactory.getLogger(this.getClass());

    //线程池
    private final ScheduledExecutorService executorService;
    //TODO:从配置文件中自定义线程数
    private int threadCount = 4;

    @Override
    public RegReactor setThreadCount(int threadCount) {
        this.threadCount = threadCount;
        return this;
    }

    //初始化时创建线程池API
    public RegReactorImpl() {
        this.executorService = new ScheduledThreadPoolExecutor(threadCount, r -> {
            Thread thread = new Thread(r);
            //必须为精灵线程,否则服务无法正常结束.
            thread.setDaemon(true);
            thread.setName(Constants.REG_THREAD_NAME);
            return thread;
        });
    }

    @Override
    public RegReactor addReg(NacosDiscoveryProperties nacosDiscoveryProperties) {
        executorService.schedule(new RegTask(nacosDiscoveryProperties,regSend,true,beatReactor),1000, TimeUnit.MILLISECONDS);
        return this;
    }

    @Override
    public RegReactor deleteReg(NacosDiscoveryProperties nacosDiscoveryProperties) {
        executorService.schedule(new RegTask(nacosDiscoveryProperties,regSend,false,beatReactor),1000, TimeUnit.MILLISECONDS);
        return this;
    }

    private class RegTask implements Runnable{
        //心跳线程内部心跳信息
        private NacosDiscoveryProperties nacosDiscoveryProperties;
        //心跳线程内部发包方法
        private final RegSend regSend;
        //flag为true代表注册,flag为false代表销毁
        private final boolean flag;
        //获取心跳线程发生类
        BeatReactor beatReactor;

        //构建方法
        public RegTask(NacosDiscoveryProperties nacosDiscoveryProperties,RegSend regSend,boolean flag,BeatReactor beatReactor){
            if(nacosDiscoveryProperties == null){
                throw new NullPointerException("传入beatInfo为空!");
            }else if(regSend == null){
                throw new NullPointerException("传入beatSend为空!");
            }
            this.nacosDiscoveryProperties = nacosDiscoveryProperties;
            regSend.setNacosDiscoveryProperties(nacosDiscoveryProperties);
            this.regSend = regSend;
            this.flag = flag;
            this.beatReactor = beatReactor;
        }
        @Override
        public void run() {
            //TODO:暂时只根据ip和端口发送
            try {
                if(flag) {
                    //发送注册信息
                    regSend.sendRegInfo();
                }else {
                    //发送销毁信息
                    regSend.revocationRegInfo();
                }
                //执行心跳增加流程
                beatReactor.addBeatInfo(new BeatInfoAdapterNacosDiscoveryProperties(nacosDiscoveryProperties));
            }catch(Exception e){
                //注册时异常将抛出异常
                throw new RuntimeException("注册实例时发生未知的错误:",e);
            }
        }
    }
}
