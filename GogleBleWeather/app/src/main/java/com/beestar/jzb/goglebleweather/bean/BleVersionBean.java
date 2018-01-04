package com.beestar.jzb.goglebleweather.bean;

/**
 * Created by jzb on 2017/12/8.
 */

public class BleVersionBean {

    /**
     * version : 0.0.6
     * downloadUrl : /PWS/downloads/ble_app_ota_580_v_0.0.6.img
     * rtn_code : 0
     * msg : SUCCESS
     */

    private String version;
    private String downloadUrl;
    private int rtn_code;
    private String msg;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

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
