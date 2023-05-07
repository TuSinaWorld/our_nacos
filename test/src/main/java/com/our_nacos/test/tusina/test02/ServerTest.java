package com.our_nacos.test.tusina.test02;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTest {
    public static void main(String[] args) {
        try {
            Socket clientSocket;
            try (ServerSocket serverSocket = new ServerSocket(25544)) {
                clientSocket = serverSocket.accept();
            }
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            String s = bufferedReader.readLine();
            BeatInfo beatInfo = JSON.parseObject(s, BeatInfo.class);
            System.out.println(beatInfo);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
