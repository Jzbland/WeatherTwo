package com.beestar.jzb.goglebleweather.bean;

/**
 * Created by jzb on 2017/10/30.
 */

public class MyWeatherBean {
    private String tem;
    private String hum;
    private String airpress;
    private String power;

    public MyWeatherBean() {
    }

    public MyWeatherBean(String tem, String hum, String airpress, String power) {
        this.tem = tem;
        this.hum = hum;
        this.airpress = airpress;
        this.power = power;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getAirpress() {
        return airpress;
    }

    public void setAirpress(String airpress) {
        this.airpress = airpress;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }
}
