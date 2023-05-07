package com.our_nacos.client.beat;

import java.util.Map;

public interface BeatReactor {
    //增加新的实例
    BeatReactor addBeatInfo(BeatInfo beatInfo);
    //获取包含完整键名的BeatInfo
    BeatInfo getBeatInfo0WithAllName(String serverInfo);
    //根据服务名获取BeatInfo数组
    BeatInfo[] getBeanInfo0(String serverName);
    //获取BeatInfo集合
    Map<String,BeatInfo> getBeatInfos();
    BeatReactor clearBeatInfo();
}
