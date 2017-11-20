package com.beestar.jzb.goglebleweather.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by jzb on 2017/11/1.
 */

public class PM {

    /**
     * data : [{"city":"南京","PM2.5":"58","AQI":"79","quality":"良","PM10":"85","CO":"0.8","NO2":"56","O3":"22","SO2":"11","time":"2017-10-26 07:32:21"}]
     * rtn_code : 0
     * msg : SUCCESS
     */

    private int rtn_code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * city : 南京
         * PM2.5 : 58
         * AQI : 79
         * quality : 良
         * PM10 : 85
         * CO : 0.8
         * NO2 : 56
         * O3 : 22
         * SO2 : 11
         * time : 2017-10-26 07:32:21
         */

        private String city;
        @SerializedName("PM2.5")
        private String _$PM25231; // FIXME check this code
        private String AQI;
        private String quality;
        private String PM10;
        private String CO;
        private String NO2;
        private String O3;
        private String SO2;
        private String time;

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String get_$PM25231() {
            return _$PM25231;
        }

        public void set_$PM25231(String _$PM25231) {
            this._$PM25231 = _$PM25231;
        }

        public String getAQI() {
            return AQI;
        }

        public void setAQI(String AQI) {
            this.AQI = AQI;
        }

        public String getQuality() {
            return quality;
        }

        public void setQuality(String quality) {
            this.quality = quality;
        }

        public String getPM10() {
            return PM10;
        }

        public void setPM10(String PM10) {
            this.PM10 = PM10;
        }

        public String getCO() {
            return CO;
        }

        public void setCO(String CO) {
            this.CO = CO;
        }

        public String getNO2() {
            return NO2;
        }

        public void setNO2(String NO2) {
            this.NO2 = NO2;
        }

        public String getO3() {
            return O3;
        }

        public void setO3(String O3) {
            this.O3 = O3;
        }

        public String getSO2() {
            return SO2;
        }

        public void setSO2(String SO2) {
            this.SO2 = SO2;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
