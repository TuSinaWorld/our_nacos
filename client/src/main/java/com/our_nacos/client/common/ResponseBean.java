package com.our_nacos.client.common;

public class ResponseBean {
    private Integer code;
    private String msg;
    private Object obj = null;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public ResponseBean(Integer code, String msg, Object obj) {
        this.code = code;
        this.msg = msg;
        this.obj = obj;
    }

    public ResponseBean(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ResponseBean() {
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", obj=" + obj +
                '}';
    }
}
