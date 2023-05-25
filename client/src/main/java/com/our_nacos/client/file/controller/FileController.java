package com.our_nacos.client.file.controller;

import com.our_nacos.client.exception.NoFileException;
import com.our_nacos.client.file.environment.EnvironmentSpace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

@RequestMapping("/${my.application.name:}")
public class FileController {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private EnvironmentSpace environmentSpace;


    @RequestMapping("/upload")
    public String downloadFile(@RequestPart MultipartFile file)  {
        String filename= file.getOriginalFilename();//文件名
        System.out.println(filename);
        try {
            logger.info("文件路径:"+environmentSpace.getSpace()+File.separator+filename);
            //transferto(),用于把图片上传到指定磁盘
            file.transferTo(new File(environmentSpace.getSpace()+File.separator+filename));
            environmentSpace.addFile(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "接收成功";
    }
    @RequestMapping("/download/{fileName}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName) {
        File file = new File(environmentSpace.getSpace(), fileName);
        if (!file.isFile()) {
            throw new NoFileException(fileName);
        }

        try (InputStream input = new BufferedInputStream(Files.newInputStream(file.toPath()));
             OutputStream output = response.getOutputStream()) {

            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            output.flush();
        } catch (FileNotFoundException e) {
            // 文件未找到异常处理
            logger.error("文件未找到", e);
            response.setStatus(HttpStatus.NOT_FOUND.value());
        } catch (IOException e) {
            // 使用日志记录器记录异常信息
            logger.error("文件下载异常", e);
            throw new RuntimeException("文件下载异常", e);
        }
    }
}
