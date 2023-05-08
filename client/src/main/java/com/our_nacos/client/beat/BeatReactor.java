package com.our_nacos.client.beat;

import java.util.Map;

public interface BeatReactor {
    //增加新的实例
    BeatReactor addBeatInfo(BeatInfo beatInfo);
    //获取包含完整键名的BeatInfo
    BeatInfo getBeatInfoWithAllName(String serverInfo);
    //根据服务名获取BeatInfo数组
    BeatInfo[] getBeanInfo(String serverName);
    //获取BeatInfo集合
    Map<String,BeatInfo> getBeatInfos();
    //根据BeatInfo停止对应心跳线程
    BeatReactor stopByBeatInfo(BeatInfo beatInfo);
    //根据实例信息停止对应心跳线程
    BeatReactor stopByServerInfo(String serverName,String ip,int port);
    //根据实例对应键名停止对应心跳线程
    BeatReactor stopByMapKey(String key);
}
