package com.our_nacos.client.annotation;


import com.our_nacos.client.EnableDiscoveryImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
//TODO:请在@Import类中写入需要自动配置类
@Import(EnableDiscoveryImportSelector.class)
public @interface OurEnableDiscoveryClient {
    boolean autoregister() default true;
}
