package com.our_nacos.client.exception;

public class NullBeatInfoException extends RuntimeException{
    public NullBeatInfoException() {
        super("查找不到任何BeatInfo,请检查代码!");
    }
}
