package com.our_nacos.client.exception;

import java.io.File;

public class NoFileException extends RuntimeException {
    public NoFileException(){
        super("错误!查无此文件!");
    }

    public NoFileException(String file){
        super("错误!查无此文件:"+file);
    }

    public NoFileException(File file){
        super("错误!查无此文件:"+file);
    }
}
