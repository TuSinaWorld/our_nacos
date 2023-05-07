package com.our_nacos.client.beat;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientTest {

    //TODO:根据配置文件或者其它方法获取端口号
    private Integer port = 2201;

    public void sentBeat(){
        BeatInfo beatInfo = new BeatInfo();
        try {

            beatInfo.setIp(String.valueOf(InetAddress.getLocalHost().getHostAddress()));
            beatInfo.setPort(port);
            String jsonString = JSON.toJSONString(beatInfo);
            Socket socket = new Socket("127.0.0.1",25544);
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            outputStreamWriter.write(jsonString);
            outputStreamWriter.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
