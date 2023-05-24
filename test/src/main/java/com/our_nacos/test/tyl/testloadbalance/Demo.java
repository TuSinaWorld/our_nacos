package com.our_nacos.test.tyl.testloadbalance;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.our_nacos.client.loadRestTemplate.MineRestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class Demo {

    @Autowired
    private MineRestTemplate mineRestTemplate;

    @RequestMapping("demo")
    public String get() throws JsonProcessingException, URISyntaxException {
//        URI uri=new URI("http://res-food/test");
        String uri="http://res-food/test";
        String serviceName = mineRestTemplate.getForObject(uri, String.class);
        return serviceName;
    }

    @RequestMapping("/test")
    public Map<Object,Object>   test(){
        String s ="E:\\BaiduNetdiskDownload";
        String path = s.substring(0,s.indexOf(":")+1);
        String pan=s.substring(0,s.indexOf(":"));
        File file=new File(path);
        Map<Object,Object> map= new ConcurrentHashMap();
//        Runtime runtime = Runtime.getRuntime();
//        Long totalMemory = runtime.totalMemory();
//        Long freeMemory=runtime.freeMemory();
//        int Processors = runtime.availableProcessors();
        double oneGB = 1024 * 1024 * 1024;
        long freeMemory = file.getFreeSpace();
        long totalMemory = file.getTotalSpace();
        long usableMemory = file.getUsableSpace();

        double freeMemoryGB=freeMemory/oneGB;
        double totalMemoryGB=totalMemory/oneGB;
        double useableMemoryGB=usableMemory/oneGB;

        map.put(pan+"盘总空间",totalMemoryGB+"GB");
        map.put(pan+"盘剩余空间",freeMemoryGB+"GB");
        map.put(pan+"盘已用空间",useableMemoryGB+"GB");
//        map.put("几个核",Processors);
        return map;
    }
}
