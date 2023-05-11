package com.our_nacos.server.controller;

import com.our_nacos.server.storage.ServiceStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Test2 {
    @Autowired
    public ServiceStorage storage;

}
