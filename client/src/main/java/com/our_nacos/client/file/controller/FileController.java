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

import javax.servlet.ServletOutputStream;
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
    public String uploadFile(@RequestPart MultipartFile file) {
        if (file.isEmpty()) {
            return "上传失败，请选择文件";
        }

        String filename = file.getOriginalFilename();
        // 对文件名进行安全性验证和清理
        logger.info("获取到的文件"+filename);
        try (InputStream input = new BufferedInputStream(file.getInputStream());
             OutputStream output = new BufferedOutputStream(Files.newOutputStream(new File(environmentSpace.getSpace() + File.separator + filename).toPath()))) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = input.read(buffer)) != -1) {
                output.write(buffer, 0, bytesRead);
            }

            environmentSpace.addFile(filename);
            return "接收成功";
        } catch (IOException e) {
            logger.error("文件上传失败", e);
            return "上传失败：" + e.getMessage();
        }
    }
    @RequestMapping("/download/{fileName}")
    public void download(HttpServletRequest request, HttpServletResponse response, @PathVariable String fileName) {
        logger.info("开始下载:"+fileName);
        File file = new File(environmentSpace.getSpace(), fileName);
        if (!file.isFile()) {
            throw new NoFileException(fileName);
        }
        try (InputStream input = new BufferedInputStream(Files.newInputStream(file.toPath()));
             OutputStream output = new BufferedOutputStream(response.getOutputStream())) {
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
