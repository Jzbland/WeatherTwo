package com.beestar.jzb.goglebleweather.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

/**
 * Created by jzb on 2017/6/28.
 */
@Entity
public class DeviceBean implements Serializable {
    @Id
    private Long id;
    private String name;
    private String mac;
    private boolean flg;
    public boolean getFlg() {
        return this.flg;
    }
    public void setFlg(boolean flg) {
        this.flg = flg;
    }
    public String getMac() {
        return this.mac;
    }
    public void setMac(String mac) {
        this.mac = mac;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1096922476)
    public DeviceBean(Long id, String name, String mac, boolean flg) {
        this.id = id;
        this.name = name;
        this.mac = mac;
        this.flg = flg;
    }

    public DeviceBean(String name, String mac, boolean flg) {
        this.name = name;
        this.mac = mac;
        this.flg = flg;
    }

    @Generated(hash = 74682814)
    public DeviceBean() {
    }
}
