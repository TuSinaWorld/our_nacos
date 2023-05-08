package com.our_nacos.client.beat.beat_send;

import com.alibaba.fastjson.JSON;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

//使用Socket发包方法
public class SocketBeatSend extends BeatSend{
    @Override
    public void send() {
        Socket socket = null;
        OutputStreamWriter outputStreamWriter = null;
        try {
            String jsonString = JSON.toJSONString(beatInfo);
            socket = new Socket(serverIp,serverPort);
            outputStreamWriter =
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            outputStreamWriter.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException("发送心跳时出现错误:",e);
        }finally {
            try {
                outputStreamWriter.close();
                socket.close();
            } catch (IOException e) {
                throw new RuntimeException("未知的错误:关闭连接错误:",e);
            }

        }
    }
}
