package com.our_nacos.client.annotation;


import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
//TODO:请在@Import类中写入需要自动配置类
//@Import()
public @interface OurEnableDiscoveryClient {
    boolean autoRegister() default true;
}
