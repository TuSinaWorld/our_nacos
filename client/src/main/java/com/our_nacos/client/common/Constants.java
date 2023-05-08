package com.our_nacos.client.common;

public class Constants {
    //命名分隔符常量
    public static final String SEPARATE_NAME_ATTRIBUTE = "#";
    //心跳线程名常量
    public static final String HEARTBEAT_THREAD_NAME = "com.our_nacos.beat.sender";
    //请求前缀
     public static final String REQUEST_HEAD = "http://";
     //心跳访问路径
     public static final String REST_TEMPLATE_BEAT_SEND_SUFFIX = "/beat/accept";
     //ip与端口分隔常量
     public static final String SEPARATE_IP_PORT = ":";
}
