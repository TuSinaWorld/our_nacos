package com.our_nacos.client.discovery;

import com.our_nacos.client.annotation.Loadbalance;
import com.our_nacos.client.beat.BeatInfo;
import com.our_nacos.client.common.MyRestTemplate;
import com.our_nacos.client.loadbalance.MyLoadBalance;
import com.our_nacos.client.reg.NacosRegAuto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.List;
import java.util.Map;

@Component
public class ServiceDiscovery {

    @Autowired
    private MyRestTemplate restTemplate;

    //根据serviceName获取服务端口
    public  Map<String,BeatInfo> findAll(){
        String ServiceName="res-food";
        String url="http://localhost:25544/get/listall";
        ResponseEntity<Map> forEntity = restTemplate.getForEntity(url, Map.class);
        Map<String, Map<String, BeatInfo>> body = forEntity.getBody();
        Map<String, BeatInfo> stringBeatInfoMap = body.get(ServiceName);
        return stringBeatInfoMap;
    }
     //找负载均衡器
    public  MyLoadBalance selectloadbalancer() {
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
                        myLoadBalance =(MyLoadBalance)declaredConstructor.newInstance();
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
        return myLoadBalance;
    }

}
