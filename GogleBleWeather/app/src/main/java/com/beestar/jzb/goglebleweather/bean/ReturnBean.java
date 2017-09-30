package com.beestar.jzb.goglebleweather.bean;

/**
 * Created by jzb on 2017/9/29.
 */

public class ReturnBean {

    /**
     * rtn_code : 0
     * msg : 操作成功
     */

    private int rtn_code;
    private String msg;

    public int getRtn_code() {
        return rtn_code;
    }

    public void setRtn_code(int rtn_code) {
        this.rtn_code = rtn_code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
