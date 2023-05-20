package com.our_nacos.server.exception;

public class NoRegException extends RuntimeException {
    public NoRegException(){
        super("非法的心跳:指定的服务尚未注册!");
    }
    public NoRegException(String s){
        super("非法的心跳:指定的服务:" + s + "尚未注册!");
    }
}
