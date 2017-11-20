package com.beestar.jzb.goglebleweather.bean;

/**
 * Created by jzb on 2017/9/29.
 */

public class Registe_UserInfo {

    /**
     * phone : 13909119988
     * name : 光头强
     * pwd : 123456
     * confirm : 123456
     * sex : male
     * code : 1234
     */

    private String phone;
    private String name;
    private String pwd;
    private String confirm;
    private String sex;
    private String newpwd;
    //忘记密码
    public Registe_UserInfo(String phone, String confirm, String newpwd, String code) {
        this.phone = phone;
        this.confirm = confirm;
        this.newpwd = newpwd;
        this.code = code;
    }

    //注册--json
    public Registe_UserInfo(String phone, String name, String pwd, String confirm, String sex, String code) {
        this.phone = phone;
        this.name = name;
        this.pwd = pwd;
        this.confirm = confirm;
        this.sex = sex;
        this.code = code;
    }
    public String getNewpwd() {
        return newpwd;
    }

    public void setNewpwd(String newpwd) {
        this.newpwd = newpwd;
    }

    private String code;
    public Registe_UserInfo(String phone, String pwd) {
        this.phone = phone;
        this.pwd = pwd;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getConfirm() {
        return confirm;
    }

    public void setConfirm(String confirm) {
        this.confirm = confirm;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
