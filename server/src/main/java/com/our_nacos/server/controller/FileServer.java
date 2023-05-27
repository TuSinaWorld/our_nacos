package com.our_nacos.server.controller;

import com.our_nacos.server.bean.BeatInfo;
import com.our_nacos.server.common.Constants;
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
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileServer {

    @Autowired
    ServiceStorage storage;
    Logger logger = LoggerFactory.getLogger(this.getClass());




    @RequestMapping("/upload/{fileName}")
    public String uploadFile(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName,@RequestParam String serverName,@RequestPart MultipartFile file){
        if(file.isEmpty()){
            logger.error("获取到的文件为空");
            return "获取文件为空";
        }
        RestTemplate restTemplate = new RestTemplate();
        try{
            File uploadfile = new File(file.getOriginalFilename());//获取文件名称
            // 构建文件转发请求
            String forwardUrl = uploadUrl(serverName, fileName);
            logger.info("上传的文件地址:"+forwardUrl);
            MultiValueMap<String, Object> requestBody = new LinkedMultiValueMap<>();
            requestBody.add("file", new FileSystemResource(uploadfile));

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

            // 发送文件转发请求给原服务器
            ResponseEntity<String> responseEntity = restTemplate.exchange(forwardUrl, HttpMethod.POST, requestEntity, String.class);


        if (responseEntity.getStatusCode() == HttpStatus.OK) {
            return "File uploaded successfully.";
        } else {
            return "Failed to upload file.";
        }
    } catch (Exception e) {
        // 处理异常
        logger.error("文件上传异常", e);
        return "文件上传异常";
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
        String url= Constants.HTTP+beatInfo.getServerIp()+Constants.URL_SEPARATE+beatInfo.getPort()
                +Constants.HTTP_SEPARATE+ serviceName + Constants.HTTP_SEPARATE +"download"+Constants.HTTP_SEPARATE+fileName;
        logger.info("获取到的url:"+url);
        return url;
    }

    private String uploadUrl(String serviceName,String fileName){
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
        String url= Constants.HTTP+beatInfo.getServerIp()+Constants.URL_SEPARATE+beatInfo.getPort()
                +Constants.HTTP_SEPARATE+ serviceName + Constants.HTTP_SEPARATE +"upload"+Constants.HTTP_SEPARATE+fileName;
        logger.info("获取到的url:"+url);
        return url;
    }
}
