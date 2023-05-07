package com.our_nacos.test.tusina.test02;

import com.alibaba.fastjson.JSON;

import java.io.*;
import java.net.Socket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class ClientTest {

    public static void main(String[] args) throws IOException {
        BeatInfoTest beatInfoTest = new BeatInfoTest();
        beatInfoTest.setIp(String.valueOf(InetAddress.getLocalHost().getHostAddress()));
        beatInfoTest.setPort(2201);
        String jsonString = JSON.toJSONString(beatInfoTest);
        Socket socket = new Socket("127.0.0.1",25544);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
        outputStreamWriter.write(jsonString);
        outputStreamWriter.close();
        socket.close();
    }
}
