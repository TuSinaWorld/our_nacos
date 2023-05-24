package com.our_nacos.client.environment;

import org.springframework.beans.factory.annotation.Value;


import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class Environment {
    @Value("${ourstorage.localspace}")
    private String space;

    private BigDecimal free_space;

    private BigDecimal total_space;

    public Map<Object,Object> getMemory(){

        String path = space.substring(0,space.indexOf(":")+1);
        String pan=space.substring(0,space.indexOf(":"));
        File file=new File(path);
        Map<Object,Object> map= new ConcurrentHashMap();

        //获取信息
        BigDecimal oneGB=new BigDecimal(1024 * 1024 * 1024);
        BigDecimal freeMemory=new BigDecimal(file.getFreeSpace());
        BigDecimal totalMemory=new BigDecimal(file.getTotalSpace());


        free_space=freeMemory.divide(oneGB,2, RoundingMode.HALF_UP);
        total_space=totalMemory.divide(oneGB,2,RoundingMode.HALF_UP);


        map.put(pan+"盘总空间",total_space+"GB");
        map.put(pan+"盘剩余空间",free_space+"GB");
        return  map;
    }

}
