package com.our_nacos.web.feign;

import com.our_nacos.web.bean.BeatInfo;
import com.our_nacos.web.bean.ResponseBean;
import com.our_nacos.web.common.Constants;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;


@FeignClient(name = "ourNacosServer", url =
        Constants.REQUEST_HEAD + "${our-nacos.server-info.serverIp}" +
                Constants.SEPARATE_IP_PORT + "${our-nacos.server-info.serverPort}")
public interface OurNacosServer {
    @RequestMapping("/get/listall")
    Map<String, Map<String, BeatInfo>> getAllServices();

    @RequestMapping("/get/list")
    Map<String, BeatInfo> getServicesByServiceName(@RequestParam("serviceName") String serviceName);

    @RequestMapping("/beat/regByName")
    ResponseBean regByName(@RequestParam("serviceName") String serviceName);
}
