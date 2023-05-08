package com.our_nacos.test.tusina.test02;

import com.our_nacos.client.beat.BeatInfo;
import com.our_nacos.client.beat.BeatReactor;
import com.our_nacos.client.beat.BeatReactorImpl;


public class ClientTest {
    public static void main(String[] args) throws InterruptedException {
        BeatReactor beatReactor = new BeatReactorImpl();
        BeatInfo beatInfo = new BeatInfo()
                .setIp("123.456.789").setPort(25522)
                .setServiceName("aServer");
        beatReactor.addBeatInfo(beatInfo);
        Thread.sleep(20000);
        beatReactor.stopByBeatInfo(beatInfo);
        Thread.sleep(20000);
    }
}
