package com.our_nacos.client.loadbalance;

import com.our_nacos.client.beat.BeatInfo;

import java.util.List;

public interface MyLoadBalance {
    public String choose(List<BeatInfo> beatInfos,String rawPath);
}
