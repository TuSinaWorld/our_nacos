package com.our_nacos.server.config;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;

/**
 * @Author: 乐哥
 * @Date: 2023/5/27
 * @Time: 18:49
 * @Description:
 */
@Configuration
public class FileConfig {
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //单个文件大小2G
        factory.setMaxFileSize(DataSize.ofGigabytes(2L));
        //设置总上传数据大小2GB
        factory.setMaxRequestSize(DataSize.ofGigabytes(2L));

        return factory.createMultipartConfig();
    }
}
