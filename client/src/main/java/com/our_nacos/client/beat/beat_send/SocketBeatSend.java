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
            //将心跳对象转为字符串
            String jsonString = JSON.toJSONString(beatInfo);
            //根据ip和端口新建Socket
            socket = new Socket(serverIp,serverPort);
            //获取输出流
            outputStreamWriter =
                    new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            //写入数据
            outputStreamWriter.write(jsonString);
        } catch (IOException e) {
            throw new RuntimeException("发送心跳时出现错误:",e);
        }finally {
            //确保关闭连接
            try {
                if (outputStreamWriter != null) {
                    outputStreamWriter.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                throw new RuntimeException("未知的错误:关闭连接错误:",e);
            }

        }
    }
}
