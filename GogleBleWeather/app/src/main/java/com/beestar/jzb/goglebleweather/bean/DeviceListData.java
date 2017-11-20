package com.beestar.jzb.goglebleweather.bean;

/**
 * Created by jzb on 2017/10/31.
 */

public class DeviceListData {
    private String name;
    private boolean flag;

    public DeviceListData() {
    }

    public DeviceListData(String name, boolean flag) {
        this.name = name;
        this.flag = flag;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
