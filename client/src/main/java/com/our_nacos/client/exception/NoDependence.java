package com.our_nacos.client.exception;

public class NoDependence extends RuntimeException{
    public NoDependence(String s){
        super("未导入必须依赖:" + s);
    }
}
