package com.our_nacos.client.autoconfig;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @Author: 乐哥
 * @Date: 2023/5/25
 * @Time: 20:18
 * @Description:
 */

@Configuration
public class FileUploadConfiuration {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件大小200mb
        factory.setMaxFileSize(DataSize.ofMegabytes(100L));
        //设置总上传数据大小1GB
        factory.setMaxRequestSize(DataSize.ofGigabytes(1L));

        return factory.createMultipartConfig();
    }
}