package com.our_nacos.client.file.environment;

import org.springframework.beans.factory.annotation.Value;


import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class EnvironmentSpace {
    @Value("${ourstorage.localspace}")
    private String space;

    private Long free_space;

    private Long total_space;

    public Map<String ,Long> getMemory(){

        String path = space.substring(0,space.indexOf(":")+1);
        String pan=space.substring(0,space.indexOf(":"));
        File file=new File(path);
        Map<String,Long> map= new ConcurrentHashMap();

        //获取信息
        free_space=file.getFreeSpace();
        total_space=file.getTotalSpace();


        map.put("total",total_space);
        map.put("free",free_space);
        return map;
    }

}
