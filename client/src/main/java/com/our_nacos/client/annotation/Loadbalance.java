package com.our_nacos.client.annotation;

import com.our_nacos.client.loadbalance.PollingLoadBalancer;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Loadbalance {
//    String value() default "PollingLoadBalancer";
    Class<?> configuration() default PollingLoadBalancer.class;

}
