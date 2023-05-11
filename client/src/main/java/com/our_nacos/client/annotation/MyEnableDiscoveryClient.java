package com.our_nacos.client.annotation;

import com.our_nacos.client.autoconfig.RegAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author: 乐哥
 * @Date: 2023/5/10
 * @Time: 10:52
 * @Description:  是否启用服务注册的注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(RegAutoConfig.class)
public @interface MyEnableDiscoveryClient {

    boolean autoRegister() default true;
}
