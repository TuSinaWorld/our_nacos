package com.our_nacos.server.exception;

public class NullBeatInfoException extends RuntimeException{
    public NullBeatInfoException() {
        super("根据服务名查找不到任何BeatInfo,请检查代码!");
    }
}
