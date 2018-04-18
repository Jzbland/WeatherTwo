package com.beestar.jzb.goglebleweather.bean;

/**
 * Created by jzb on 2018/1/18.
 */

public class ChangeSex {
    private String uid;

    private String sex;

    public ChangeSex(String uid, String sex) {
        this.uid = uid;
        this.sex = sex;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
