package com.our_nacos.client.file.environment;

import com.our_nacos.client.exception.NoFileException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;


import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class EnvironmentSpace {
    @Value("${our-storage.local-space}")
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

    @Autowired
    private void checkFile(){
        File file = new File(space);
        if(!file.exists()){
            throw new NoFileException("设定文件夹无法找到!");
        }
    }

    public String getSpace() {
        return space;
    }

    public void setSpace(String space) {
        this.space = space;
    }

    public Long getFree_space() {
        return free_space;
    }

    public void setFree_space(Long free_space) {
        this.free_space = free_space;
    }

    public Long getTotal_space() {
        return total_space;
    }

    public void setTotal_space(Long total_space) {
        this.total_space = total_space;
    }
}
