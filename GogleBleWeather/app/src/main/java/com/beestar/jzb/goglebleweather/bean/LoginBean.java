package com.beestar.jzb.goglebleweather.bean;

/**
 * Created by jzb on 2017/10/9.
 */

public class LoginBean {

    /**
     * uid : 13909119988
     * pwd : 123456
     */

    private String uid;
    private String pwd;

    public LoginBean(String uid, String pwd) {
        this.uid = uid;
        this.pwd = pwd;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
