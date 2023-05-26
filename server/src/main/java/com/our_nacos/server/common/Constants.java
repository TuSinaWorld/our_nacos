package com.our_nacos.server.common;

public class Constants {
    //命名分隔符常量
    public static final String SEPARATE_NAME_ATTRIBUTE = "#";
    //心跳线程池名字
    public static final String BEAT_THREAD_NAME = "beatThread";
    //心跳健康检测时间上限
    public static final Integer BEAT_TIME_LIMIT = 15;
    //心跳检间隔(毫秒)
    public static final Integer BEAT_NEXT_TIME = 5000;

    //路径分隔符
    public static final String URL_SEPARATE = ":";

    // http:// ip :port/ 服务名/ down
    public static  final String  HTTP = "http://";

    //http路径分隔符
    public static  final String  HTTP_SEPARATE = "/";
}
