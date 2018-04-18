package com.beestar.jzb.goglebleweather.bean;

/**
 * Created by jzb on 2017/11/6.
 */

public class ChangePwd {

    /**
     * uid : 13909119988
     * pwd : 123456
     * newpwd : 654321
     * confirm : 654321
     */


    private String uid;
    private String pwd;
    private String newpwd;
    private String confirm;
    /**
     * name : 熊大
     * sex : 男
     */

    private String name;
    private String sex;

    public ChangePwd(String uid, String name) {
        this.uid = uid;
        this.name = name;
    }

    public ChangePwd(String uid, String pwd, String newpwd, String confirm) {
        this.uid = uid;
        this.pwd = pwd;
        this.newpwd = newpwd;
        this.confirm = confirm;
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

    public String getNewpwd() {
        return newpwd;
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
