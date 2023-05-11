package com.our_nacos.server.storage;

import com.our_nacos.server.bean.BeatInfo;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;


//@Component
//@Scope(BeanDefinition.SCOPE_SINGLETON)
public abstract class ServiceStorage {
    protected Map<String, BeatInfo> map = new HashMap<>();
}
