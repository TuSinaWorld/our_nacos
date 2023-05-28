package com.our_nacos.server.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.common.Constants;
import com.our_nacos.server.storage.GetService;
import com.our_nacos.server.storage.ServiceStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileServer {

    @Autowired
    ServiceStorage storage;

    @Autowired
    GetService getService;
    Logger logger = LoggerFactory.getLogger(this.getClass());




    @RequestMapping("/upload")
    public String uploadFile( @RequestParam String serverName,@RequestPart MultipartFile file){
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }
        logger.info("获取到的服务名"+serverName);
        try {
            String forwardUrl = uploadUrl(serverName);
            logger.info("获取的url地址:" + forwardUrl);
            URL url = new URL(forwardUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");

            // 设置请求头
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------boundary");

            // 构建请求体
            OutputStream outputStream = new BufferedOutputStream(connection.getOutputStream());
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8), true);
            String boundary = "---------------------------boundary";
            writer.append("--").append(boundary).append("\r\n");
            writer.append("Content-Disposition: form-data; name=\"file\"; filename=\"").append(file.getOriginalFilename()).append("\"\r\n");
            writer.append("Content-Type: ").append(file.getContentType()).append("\r\n\r\n");
            writer.flush();


            // 分块上传
            int chunkSize = 1024 * 1024; // 每块大小为1MB
            byte[] buffer = new byte[chunkSize];
            try (InputStream inputStream = new BufferedInputStream(file.getInputStream())) {
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            // 将文件写入请求体
            //outputStream.write(file.getBytes());
            outputStream.flush();

            writer.append("\r\n");
            writer.append("--").append(boundary).append("--\r\n");
            writer.close();

            // 发送请求并获取响应
            int responseCode = connection.getResponseCode();
            logger.info("Response Code: " + responseCode);
            logger.info("Response Message: " + connection.getResponseMessage());
            return 200+"上传成功";
        } catch (IOException e) {
            // 处理异常
            logger.error("文件上传异常", e);
            return "文件上传异常：" + e.getMessage();
        }
    }


    @RequestMapping("/download/{fileName}")
    public void proxyFile(HttpServletRequest request, HttpServletResponse response, @RequestParam String serverName , @PathVariable String fileName) {
        String originalServerUrl = downloadUrl(serverName,fileName); // 原服务器的URL

        try {
            URL url = new URL(originalServerUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            try{
                InputStream input = new BufferedInputStream(connection.getInputStream());
                OutputStream output = new BufferedOutputStream(response.getOutputStream());
                response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = input.read(buffer)) != -1) {
                    output.write(buffer, 0, bytesRead);
                }
                output.flush();
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        } catch (IOException e) {
            // 处理异常
            logger.error("代理文件异常", e);
            throw new RuntimeException("代理文件异常", e);
        }
    }

    private String downloadUrl(String serviceName,String fileName){
        if("".equals(serviceName) || serviceName.length()==0){
            logger.error("获取服务名失败");
            return "获取的服务名为空";
        }
        if("".equals(fileName) || fileName.length()==0 ){
            logger.error("获取到的文件名为空!");
            return "获取到的文件名为空";
        }
        Map<String, Map<String, BeatInfo>> fileMap = storage.getFileMap();
        Map<String, BeatInfo> beatInfoMap = fileMap.get(serviceName);
        BeatInfo beatInfo = beatInfoMap.get(fileName);
        // http:// ip :port/服务名/download/文件名
        String url= Constants.HTTP+beatInfo.getIp()+Constants.URL_SEPARATE+beatInfo.getPort()
                +Constants.HTTP_SEPARATE+ serviceName + Constants.HTTP_SEPARATE +"download"+Constants.HTTP_SEPARATE+fileName;
        logger.info("获取到的url:"+url);
        return url;
    }

    private String uploadUrl(String serviceName) throws JsonProcessingException {
        if("".equals(serviceName) || serviceName.length()==0){
            logger.error("获取服务名失败");
            return "获取的服务名为空";
        }
        BeatInfo beatInfo = getService.GetServiceName(serviceName);
        // http:// ip :port/服务名/download/文件名
        String url= Constants.HTTP+beatInfo.getIp()+Constants.URL_SEPARATE+beatInfo.getPort()
                +Constants.HTTP_SEPARATE+ serviceName + Constants.HTTP_SEPARATE +"upload";
        logger.info("获取到的url:"+url);
        return url;
    }
}
