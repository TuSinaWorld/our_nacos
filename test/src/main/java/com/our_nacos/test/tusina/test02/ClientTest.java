package com.our_nacos.test.tusina.test02;

import com.alibaba.fastjson.JSON;
import com.our_nacos.client.beat.BeatInfo;
import com.our_nacos.client.beat.BeatReactor;
import com.our_nacos.client.beat.BeatReactorImpl;

import java.io.*;
import java.net.Socket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ClientTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        BeatReactor beatReactor = new BeatReactorImpl();
        beatReactor.addBeatInfo(new BeatInfo()
                .setIp("123.456.789").setPort(25522)
                .setServiceName("aServer"));
        Thread.sleep(20000);
    }
}
