package com.mm.biz.exception;

import com.mm.util.I18n;

public class BizException extends RuntimeException {

    private int code;
    private String msg;

    public BizException(){
        super();
    }

    public BizException(int code){
        this.code = code;
        this.msg = I18n.msg(code,null);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
