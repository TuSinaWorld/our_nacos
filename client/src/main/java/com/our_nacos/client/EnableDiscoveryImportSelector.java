package com.our_nacos.client;

import com.our_nacos.client.annotation.OurEnableDiscoveryClient;
import com.our_nacos.client.reg.Instance;
import com.our_nacos.client.reg.NacosRegAuto;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotationMetadata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @Author: 乐哥
 * @Date: 2023/5/10
 * @Time: 19:04
 * @Description:  判断注解属性中的方法
 */

@Order(Ordered.LOWEST_PRECEDENCE - 100)
public class EnableDiscoveryImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata metadata) {
        String[] imports = new String[]{""};
        String mainClassName = System.getProperty("sun.java.command");
        try {
            Class<?> clazz = Class.forName(mainClassName);
            Map<String, Object> attributes = AnnotationUtils.getAnnotationAttributes(
                    clazz.getAnnotation(OurEnableDiscoveryClient.class)
            );
            boolean autoRegister = (boolean) attributes.get("autoregister");
            if (autoRegister) {
                System.out.println("进来了....");
                imports = new String[] {NacosRegAuto.class.getName(), Instance.class.getName()};
            } else {
                System.out.println("未启用服务发现功能.....");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("未启用服务发现功能.....");
        }
        return imports;
    }

}
