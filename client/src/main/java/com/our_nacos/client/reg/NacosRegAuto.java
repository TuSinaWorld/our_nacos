package com.our_nacos.client.reg;

import com.our_nacos.client.annotation.Loadbalance;
import com.our_nacos.client.listener.Mylistener;
import com.our_nacos.client.loadbalance.MyLoadBalance;
import com.our_nacos.client.loadbalance.PollingLoadBalancer;
import com.our_nacos.client.loadbalance.RandomLoadBalancer;
import com.our_nacos.client.loadbalance.WeightedLoadBalancer;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;

/**
 * @Author: 乐哥
 * @Date: 2023/5/10
 * @Time: 10:36
 * @Description:   服务注册自动加载类
 */

public class NacosRegAuto extends Mylistener {

    @Autowired
    RegProxy regProxy;
    @Override
    public void register(NacosDiscoveryProperties nacosDiscoveryProperties,Integer port) {
        if (!nacosDiscoveryProperties.isEnabled()) {
            System.out.println("注册未开启.......");
            return;
        }
        if (nacosDiscoveryProperties.getPort() < 0) {
            nacosDiscoveryProperties.setPort(port);
        }
        if (nacosDiscoveryProperties.getService()==null) {
            System.out.println("没有服务注册在nacos客户端上......");
            return;
        }

        try {
            // 开始注册服务
            regProxy.register(nacosDiscoveryProperties);
            System.out.println("服务注册成功......");
        }
        catch (Exception e) {
            System.out.println("服务注册失败......");
            e.printStackTrace();
        }
    }

    @Override
    protected void selectloadbalancer() {
        MyLoadBalance myLoadBalance=null;
        ClassLoader classLoader = NacosRegAuto.class.getClassLoader();
        String path = System.getProperty("sun.java.command");
        path=path.replace("." , "/");
        System.out.println(path);
        URL resource = classLoader.getResource(path+".class");
        System.out.println(resource.toString());
        File file = new File(resource.getFile());


        String absolutePath = file.getAbsolutePath();
        String className = absolutePath.substring(absolutePath.indexOf("com"), absolutePath.indexOf(".class"));
        className=className.replace("\\" , ".");
        Class<?> clazz = null;
        try {
            clazz=classLoader.loadClass(className);
            if(clazz.isAnnotationPresent(Loadbalance.class)){
                Loadbalance annotation = clazz.getAnnotation(Loadbalance.class);
                String value = annotation.value();
                Class<?> aClass = classLoader.loadClass("com.our_nacos.client.loadbalance." + value);
                Constructor<?> declaredConstructor = null;
                try {
                    declaredConstructor = aClass.getDeclaredConstructor();
                    try {
                        myLoadBalance  =(MyLoadBalance)declaredConstructor.newInstance();
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }

            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
