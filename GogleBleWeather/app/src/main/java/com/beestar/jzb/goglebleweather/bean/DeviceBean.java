package com.beestar.jzb.goglebleweather.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

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
    private boolean isConn;
    private boolean isChoose;
    public boolean getIsChoose() {
        return this.isChoose;
    }
    public void setIsChoose(boolean isChoose) {
        this.isChoose = isChoose;
    }
    public boolean getIsConn() {
        return this.isConn;
    }
    public void setIsConn(boolean isConn) {
        this.isConn = isConn;
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
    @Generated(hash = 148863758)
    public DeviceBean(Long id, String name, String mac, boolean isConn,
            boolean isChoose) {
        this.id = id;
        this.name = name;
        this.mac = mac;
        this.isConn = isConn;
        this.isChoose = isChoose;
    }

    public DeviceBean(String name, String mac, boolean isConn, boolean isChoose) {
        this.name = name;
        this.mac = mac;
        this.isConn = isConn;
        this.isChoose = isChoose;
    }

    @Generated(hash = 74682814)
    public DeviceBean() {
    }
   

}
