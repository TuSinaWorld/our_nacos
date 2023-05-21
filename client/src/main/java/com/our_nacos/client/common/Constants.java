package com.our_nacos.client.common;

public class Constants {
    //命名分隔符常量
    public static final String SEPARATE_NAME_ATTRIBUTE = "#";
    //心跳线程名常量
    public static final String HEARTBEAT_THREAD_NAME = "com.our_nacos.beat.sender";
    //注册线程名常量
    public static final String REG_THREAD_NAME = "com.our_nacos.reg.sender";
    //请求前缀
    public static final String REQUEST_HEAD = "http://";
    //心跳访问路径
    public static final String REST_TEMPLATE_BEAT_SEND_SUFFIX = "/beat/accept";
    //ip与端口分隔常量
    public static final String SEPARATE_IP_PORT = ":";
    //注册实例后缀
    public static final String REST_TEMPLATE_REG_SEND_SUFFIX = "/beat/reg";
    //销毁实例后缀
    public static final String REST_TEMPLATE_REMOVE_SEND_SUFFIX = "/beat/remove";
    //获取所以服务实例
    public static final String GET_ALL_SERVICES = "/get/listall";
}
