package com.mm.web;

import java.io.Serializable;

import static com.mm.util.I18n.msg;

public class R implements Serializable {

    private int code;

    private String msg;

    //@ApiModelProperty
    private Object data;

    public static R ok = new R(0, "ok", null);

    private R() {
    }


    private R(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //成功操作 操作码默认为0 无参
    public static R ok() {
        return new R(0, "ok", null);
    }

    /**
     * Return ok status with data
     */
    public static R ok(Object data) {
        return new R(0, "ok", data);
    }

    /**
     * Return ok status with i18n message,but no data return.
     *
     * @param code
     * @param args
     * @return
     */
    public static R ok(int code, Object... args) {
        return r(code, args);
    }

    /**
     * Return ok status with i18n messages and data.
     *
     * @param data
     * @param code
     * @param args
     * @return
     */
    public static R ok(Object data, int code, Object... args) {
        return r(data, code, args);
    }

    private static R r(Object data, int code, Object... args) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg(code, args));
        r.setData(data);
        return r;
    }

    private static R r(int code, Object... args) {
        R r = new R();
        r.setCode(code);
        r.setMsg(msg(code, args));
        return r;
    }

    public static R fail() {
        return r(9999);
    }

    public static R fail(int code) {
        return r(code);
    }

    public static R fail(int code, Object... args) {
        return r(code, args);
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}