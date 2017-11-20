package com.beestar.jzb.goglebleweather.bean;

import java.util.List;

/**
 * Created by jzb on 2017/11/1.
 */

public class Weather_Bean {

    /**
     * code : 10000
     * charge : false
     * msg : 查询成功
     * result : {"msg":"ok","result":{"date":"2017-11-01","templow":"10","temp":"21","img":"0","week":"星期三","city":"南京","windpower":"1级","index":[{"ivalue":"较少开启","detail":"您将感到很舒适，一般不需要开启空调。","iname":"空调指数"},{"ivalue":"较适宜","detail":"天气较好，但因风力稍强，户外可选择对风力要求不高的运动，推荐您进行室内运动。","iname":"运动指数"},{"ivalue":"中等","detail":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。","iname":"紫外线指数"},{"ivalue":"较易发","detail":"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。","iname":"感冒指数"},{"ivalue":"较适宜","detail":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","iname":"洗车指数"},{"ivalue":"良","detail":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。","iname":"空气污染扩散指数"},{"ivalue":"较舒适","detail":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","iname":"穿衣指数"}],"cityid":"219","pressure":"1018","temphigh":"21","citycode":"101190101","winddirect":"北风","daily":[{"date":"2017-11-01","sunrise":"06:21","week":"星期三","sunset":"17:16","night":{"templow":"10","img":"1","winddirect":"南风","windpower":"微风","weather":"多云"},"day":{"img":"0","winddirect":"东南风","windpower":"3-4 级","weather":"晴","temphigh":"21"}},{"date":"2017-11-02","sunrise":"06:22","week":"星期四","sunset":"17:16","night":{"templow":"11","img":"1","winddirect":"北风","windpower":"3-4 级","weather":"多云"},"day":{"img":"1","winddirect":"西南风","windpower":"微风","weather":"多云","temphigh":"22"}},{"date":"2017-11-03","sunrise":"06:23","week":"星期五","sunset":"17:15","night":{"templow":"9","img":"0","winddirect":"东北风","windpower":"4-5 级","weather":"晴"},"day":{"img":"1","winddirect":"北风","windpower":"4-5 级","weather":"多云","temphigh":"20"}},{"date":"2017-11-04","sunrise":"06:24","week":"星期六","sunset":"17:14","night":{"templow":"7","img":"0","winddirect":"东风","windpower":"3-4 级","weather":"晴"},"day":{"img":"0","winddirect":"东风","windpower":"3-4 级","weather":"晴","temphigh":"17"}},{"date":"2017-11-05","sunrise":"06:25","week":"星期日","sunset":"17:13","night":{"templow":"9","img":"1","winddirect":"东风","windpower":"微风","weather":"多云"},"day":{"img":"1","winddirect":"东南风","windpower":"3-4 级","weather":"多云","temphigh":"18"}},{"date":"2017-11-06","sunrise":"07:30","week":"星期一","sunset":"19:30","night":{"templow":"10","img":"1","winddirect":"东南风","windpower":"微风","weather":"多云"},"day":{"img":"0","winddirect":"东南风","windpower":"微风","weather":"晴","temphigh":"21"}},{"date":"2017-11-07","sunrise":"07:30","week":"星期二","sunset":"19:30","night":{"templow":"11","img":"2","winddirect":"西北风","windpower":"微风","weather":"阴"},"day":{"img":"1","winddirect":"西北风","windpower":"微风","weather":"多云","temphigh":"22"}}],"weather":"晴","aqi":{"co24":"0.910","ipm2_5":"80","io3":"32","primarypollutant":"PM2.5","no2":"64","no224":"74","ico":"11","o38":"50","o324":"50","so2":"23","pm2_5":"59","so224":"15","o3":"99","pm10":"108","pm1024":"104","pm2_524":"56","co":"1.060","aqiinfo":{"measure":"极少数异常敏感人群应减少户外活动","color":"#FFFF00","level":"二级","affect":"空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响"},"quality":"良","aqi":"80","ino2":"32","ipm10":"79","io38":"25","iso2":"8","timepoint":"2017-11-01 14:00:00"},"humidity":"37","windspeed":"7.0","hourly":[{"temp":"22","img":"0","weather":"晴","time":"16:00"},{"temp":"19","img":"0","weather":"晴","time":"17:00"},{"temp":"18","img":"0","weather":"晴","time":"18:00"},{"temp":"16","img":"0","weather":"晴","time":"19:00"},{"temp":"15","img":"0","weather":"晴","time":"20:00"},{"temp":"14","img":"1","weather":"多云","time":"21:00"},{"temp":"13","img":"1","weather":"多云","time":"22:00"},{"temp":"13","img":"1","weather":"多云","time":"23:00"},{"temp":"12","img":"1","weather":"多云","time":"0:00"},{"temp":"12","img":"1","weather":"多云","time":"1:00"},{"temp":"12","img":"1","weather":"多云","time":"2:00"},{"temp":"12","img":"1","weather":"多云","time":"3:00"},{"temp":"11","img":"1","weather":"多云","time":"4:00"},{"temp":"11","img":"1","weather":"多云","time":"5:00"},{"temp":"11","img":"1","weather":"多云","time":"6:00"},{"temp":"11","img":"1","weather":"多云","time":"7:00"},{"temp":"14","img":"1","weather":"多云","time":"8:00"},{"temp":"17","img":"1","weather":"多云","time":"9:00"},{"temp":"18","img":"1","weather":"多云","time":"10:00"},{"temp":"20","img":"1","weather":"多云","time":"11:00"},{"temp":"21","img":"1","weather":"多云","time":"12:00"},{"temp":"22","img":"1","weather":"多云","time":"13:00"},{"temp":"22","img":"1","weather":"多云","time":"14:00"},{"temp":"22","img":"0","weather":"晴","time":"15:00"}],"updatetime":"2017-11-01 15:16:33"},"status":"0"}
     */

    private String code;
    private boolean charge;
    private String msg;
    private ResultBeanX result;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isCharge() {
        return charge;
    }

    public void setCharge(boolean charge) {
        this.charge = charge;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ResultBeanX getResult() {
        return result;
    }

    public void setResult(ResultBeanX result) {
        this.result = result;
    }

    public static class ResultBeanX {
        /**
         * msg : ok
         * result : {"date":"2017-11-01","templow":"10","temp":"21","img":"0","week":"星期三","city":"南京","windpower":"1级","index":[{"ivalue":"较少开启","detail":"您将感到很舒适，一般不需要开启空调。","iname":"空调指数"},{"ivalue":"较适宜","detail":"天气较好，但因风力稍强，户外可选择对风力要求不高的运动，推荐您进行室内运动。","iname":"运动指数"},{"ivalue":"中等","detail":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。","iname":"紫外线指数"},{"ivalue":"较易发","detail":"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。","iname":"感冒指数"},{"ivalue":"较适宜","detail":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","iname":"洗车指数"},{"ivalue":"良","detail":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。","iname":"空气污染扩散指数"},{"ivalue":"较舒适","detail":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","iname":"穿衣指数"}],"cityid":"219","pressure":"1018","temphigh":"21","citycode":"101190101","winddirect":"北风","daily":[{"date":"2017-11-01","sunrise":"06:21","week":"星期三","sunset":"17:16","night":{"templow":"10","img":"1","winddirect":"南风","windpower":"微风","weather":"多云"},"day":{"img":"0","winddirect":"东南风","windpower":"3-4 级","weather":"晴","temphigh":"21"}},{"date":"2017-11-02","sunrise":"06:22","week":"星期四","sunset":"17:16","night":{"templow":"11","img":"1","winddirect":"北风","windpower":"3-4 级","weather":"多云"},"day":{"img":"1","winddirect":"西南风","windpower":"微风","weather":"多云","temphigh":"22"}},{"date":"2017-11-03","sunrise":"06:23","week":"星期五","sunset":"17:15","night":{"templow":"9","img":"0","winddirect":"东北风","windpower":"4-5 级","weather":"晴"},"day":{"img":"1","winddirect":"北风","windpower":"4-5 级","weather":"多云","temphigh":"20"}},{"date":"2017-11-04","sunrise":"06:24","week":"星期六","sunset":"17:14","night":{"templow":"7","img":"0","winddirect":"东风","windpower":"3-4 级","weather":"晴"},"day":{"img":"0","winddirect":"东风","windpower":"3-4 级","weather":"晴","temphigh":"17"}},{"date":"2017-11-05","sunrise":"06:25","week":"星期日","sunset":"17:13","night":{"templow":"9","img":"1","winddirect":"东风","windpower":"微风","weather":"多云"},"day":{"img":"1","winddirect":"东南风","windpower":"3-4 级","weather":"多云","temphigh":"18"}},{"date":"2017-11-06","sunrise":"07:30","week":"星期一","sunset":"19:30","night":{"templow":"10","img":"1","winddirect":"东南风","windpower":"微风","weather":"多云"},"day":{"img":"0","winddirect":"东南风","windpower":"微风","weather":"晴","temphigh":"21"}},{"date":"2017-11-07","sunrise":"07:30","week":"星期二","sunset":"19:30","night":{"templow":"11","img":"2","winddirect":"西北风","windpower":"微风","weather":"阴"},"day":{"img":"1","winddirect":"西北风","windpower":"微风","weather":"多云","temphigh":"22"}}],"weather":"晴","aqi":{"co24":"0.910","ipm2_5":"80","io3":"32","primarypollutant":"PM2.5","no2":"64","no224":"74","ico":"11","o38":"50","o324":"50","so2":"23","pm2_5":"59","so224":"15","o3":"99","pm10":"108","pm1024":"104","pm2_524":"56","co":"1.060","aqiinfo":{"measure":"极少数异常敏感人群应减少户外活动","color":"#FFFF00","level":"二级","affect":"空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响"},"quality":"良","aqi":"80","ino2":"32","ipm10":"79","io38":"25","iso2":"8","timepoint":"2017-11-01 14:00:00"},"humidity":"37","windspeed":"7.0","hourly":[{"temp":"22","img":"0","weather":"晴","time":"16:00"},{"temp":"19","img":"0","weather":"晴","time":"17:00"},{"temp":"18","img":"0","weather":"晴","time":"18:00"},{"temp":"16","img":"0","weather":"晴","time":"19:00"},{"temp":"15","img":"0","weather":"晴","time":"20:00"},{"temp":"14","img":"1","weather":"多云","time":"21:00"},{"temp":"13","img":"1","weather":"多云","time":"22:00"},{"temp":"13","img":"1","weather":"多云","time":"23:00"},{"temp":"12","img":"1","weather":"多云","time":"0:00"},{"temp":"12","img":"1","weather":"多云","time":"1:00"},{"temp":"12","img":"1","weather":"多云","time":"2:00"},{"temp":"12","img":"1","weather":"多云","time":"3:00"},{"temp":"11","img":"1","weather":"多云","time":"4:00"},{"temp":"11","img":"1","weather":"多云","time":"5:00"},{"temp":"11","img":"1","weather":"多云","time":"6:00"},{"temp":"11","img":"1","weather":"多云","time":"7:00"},{"temp":"14","img":"1","weather":"多云","time":"8:00"},{"temp":"17","img":"1","weather":"多云","time":"9:00"},{"temp":"18","img":"1","weather":"多云","time":"10:00"},{"temp":"20","img":"1","weather":"多云","time":"11:00"},{"temp":"21","img":"1","weather":"多云","time":"12:00"},{"temp":"22","img":"1","weather":"多云","time":"13:00"},{"temp":"22","img":"1","weather":"多云","time":"14:00"},{"temp":"22","img":"0","weather":"晴","time":"15:00"}],"updatetime":"2017-11-01 15:16:33"}
         * status : 0
         */

        private String msg;
        private ResultBean result;
        private String status;

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public ResultBean getResult() {
            return result;
        }

        public void setResult(ResultBean result) {
            this.result = result;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public static class ResultBean {
            /**
             * date : 2017-11-01
             * templow : 10
             * temp : 21
             * img : 0
             * week : 星期三
             * city : 南京
             * windpower : 1级
             * index : [{"ivalue":"较少开启","detail":"您将感到很舒适，一般不需要开启空调。","iname":"空调指数"},{"ivalue":"较适宜","detail":"天气较好，但因风力稍强，户外可选择对风力要求不高的运动，推荐您进行室内运动。","iname":"运动指数"},{"ivalue":"中等","detail":"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。","iname":"紫外线指数"},{"ivalue":"较易发","detail":"昼夜温差较大，较易发生感冒，请适当增减衣服。体质较弱的朋友请注意防护。","iname":"感冒指数"},{"ivalue":"较适宜","detail":"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。","iname":"洗车指数"},{"ivalue":"良","detail":"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。","iname":"空气污染扩散指数"},{"ivalue":"较舒适","detail":"建议着薄外套、开衫牛仔衫裤等服装。年老体弱者应适当添加衣物，宜着夹克衫、薄毛衣等。","iname":"穿衣指数"}]
             * cityid : 219
             * pressure : 1018
             * temphigh : 21
             * citycode : 101190101
             * winddirect : 北风
             * daily : [{"date":"2017-11-01","sunrise":"06:21","week":"星期三","sunset":"17:16","night":{"templow":"10","img":"1","winddirect":"南风","windpower":"微风","weather":"多云"},"day":{"img":"0","winddirect":"东南风","windpower":"3-4 级","weather":"晴","temphigh":"21"}},{"date":"2017-11-02","sunrise":"06:22","week":"星期四","sunset":"17:16","night":{"templow":"11","img":"1","winddirect":"北风","windpower":"3-4 级","weather":"多云"},"day":{"img":"1","winddirect":"西南风","windpower":"微风","weather":"多云","temphigh":"22"}},{"date":"2017-11-03","sunrise":"06:23","week":"星期五","sunset":"17:15","night":{"templow":"9","img":"0","winddirect":"东北风","windpower":"4-5 级","weather":"晴"},"day":{"img":"1","winddirect":"北风","windpower":"4-5 级","weather":"多云","temphigh":"20"}},{"date":"2017-11-04","sunrise":"06:24","week":"星期六","sunset":"17:14","night":{"templow":"7","img":"0","winddirect":"东风","windpower":"3-4 级","weather":"晴"},"day":{"img":"0","winddirect":"东风","windpower":"3-4 级","weather":"晴","temphigh":"17"}},{"date":"2017-11-05","sunrise":"06:25","week":"星期日","sunset":"17:13","night":{"templow":"9","img":"1","winddirect":"东风","windpower":"微风","weather":"多云"},"day":{"img":"1","winddirect":"东南风","windpower":"3-4 级","weather":"多云","temphigh":"18"}},{"date":"2017-11-06","sunrise":"07:30","week":"星期一","sunset":"19:30","night":{"templow":"10","img":"1","winddirect":"东南风","windpower":"微风","weather":"多云"},"day":{"img":"0","winddirect":"东南风","windpower":"微风","weather":"晴","temphigh":"21"}},{"date":"2017-11-07","sunrise":"07:30","week":"星期二","sunset":"19:30","night":{"templow":"11","img":"2","winddirect":"西北风","windpower":"微风","weather":"阴"},"day":{"img":"1","winddirect":"西北风","windpower":"微风","weather":"多云","temphigh":"22"}}]
             * weather : 晴
             * aqi : {"co24":"0.910","ipm2_5":"80","io3":"32","primarypollutant":"PM2.5","no2":"64","no224":"74","ico":"11","o38":"50","o324":"50","so2":"23","pm2_5":"59","so224":"15","o3":"99","pm10":"108","pm1024":"104","pm2_524":"56","co":"1.060","aqiinfo":{"measure":"极少数异常敏感人群应减少户外活动","color":"#FFFF00","level":"二级","affect":"空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响"},"quality":"良","aqi":"80","ino2":"32","ipm10":"79","io38":"25","iso2":"8","timepoint":"2017-11-01 14:00:00"}
             * humidity : 37
             * windspeed : 7.0
             * hourly : [{"temp":"22","img":"0","weather":"晴","time":"16:00"},{"temp":"19","img":"0","weather":"晴","time":"17:00"},{"temp":"18","img":"0","weather":"晴","time":"18:00"},{"temp":"16","img":"0","weather":"晴","time":"19:00"},{"temp":"15","img":"0","weather":"晴","time":"20:00"},{"temp":"14","img":"1","weather":"多云","time":"21:00"},{"temp":"13","img":"1","weather":"多云","time":"22:00"},{"temp":"13","img":"1","weather":"多云","time":"23:00"},{"temp":"12","img":"1","weather":"多云","time":"0:00"},{"temp":"12","img":"1","weather":"多云","time":"1:00"},{"temp":"12","img":"1","weather":"多云","time":"2:00"},{"temp":"12","img":"1","weather":"多云","time":"3:00"},{"temp":"11","img":"1","weather":"多云","time":"4:00"},{"temp":"11","img":"1","weather":"多云","time":"5:00"},{"temp":"11","img":"1","weather":"多云","time":"6:00"},{"temp":"11","img":"1","weather":"多云","time":"7:00"},{"temp":"14","img":"1","weather":"多云","time":"8:00"},{"temp":"17","img":"1","weather":"多云","time":"9:00"},{"temp":"18","img":"1","weather":"多云","time":"10:00"},{"temp":"20","img":"1","weather":"多云","time":"11:00"},{"temp":"21","img":"1","weather":"多云","time":"12:00"},{"temp":"22","img":"1","weather":"多云","time":"13:00"},{"temp":"22","img":"1","weather":"多云","time":"14:00"},{"temp":"22","img":"0","weather":"晴","time":"15:00"}]
             * updatetime : 2017-11-01 15:16:33
             */

            private String date;
            private String templow;
            private String temp;
            private String img;
            private String week;
            private String city;
            private String windpower;
            private String cityid;
            private String pressure;
            private String temphigh;
            private String citycode;
            private String winddirect;
            private String weather;
            private AqiBean aqi;
            private String humidity;
            private String windspeed;
            private String updatetime;
            private List<IndexBean> index;
            private List<DailyBean> daily;
            private List<HourlyBean> hourly;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getTemplow() {
                return templow;
            }

            public void setTemplow(String templow) {
                this.templow = templow;
            }

            public String getTemp() {
                return temp;
            }

            public void setTemp(String temp) {
                this.temp = temp;
            }

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            public String getWeek() {
                return week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getWindpower() {
                return windpower;
            }

            public void setWindpower(String windpower) {
                this.windpower = windpower;
            }

            public String getCityid() {
                return cityid;
            }

            public void setCityid(String cityid) {
                this.cityid = cityid;
            }

            public String getPressure() {
                return pressure;
            }

            public void setPressure(String pressure) {
                this.pressure = pressure;
            }

            public String getTemphigh() {
                return temphigh;
            }

            public void setTemphigh(String temphigh) {
                this.temphigh = temphigh;
            }

            public String getCitycode() {
                return citycode;
            }

            public void setCitycode(String citycode) {
                this.citycode = citycode;
            }

            public String getWinddirect() {
                return winddirect;
            }

            public void setWinddirect(String winddirect) {
                this.winddirect = winddirect;
            }

            public String getWeather() {
                return weather;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public AqiBean getAqi() {
                return aqi;
            }

            public void setAqi(AqiBean aqi) {
                this.aqi = aqi;
            }

            public String getHumidity() {
                return humidity;
            }

            public void setHumidity(String humidity) {
                this.humidity = humidity;
            }

            public String getWindspeed() {
                return windspeed;
            }

            public void setWindspeed(String windspeed) {
                this.windspeed = windspeed;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }

            public List<IndexBean> getIndex() {
                return index;
            }

            public void setIndex(List<IndexBean> index) {
                this.index = index;
            }

            public List<DailyBean> getDaily() {
                return daily;
            }

            public void setDaily(List<DailyBean> daily) {
                this.daily = daily;
            }

            public List<HourlyBean> getHourly() {
                return hourly;
            }

            public void setHourly(List<HourlyBean> hourly) {
                this.hourly = hourly;
            }

            public static class AqiBean {
                /**
                 * co24 : 0.910
                 * ipm2_5 : 80
                 * io3 : 32
                 * primarypollutant : PM2.5
                 * no2 : 64
                 * no224 : 74
                 * ico : 11
                 * o38 : 50
                 * o324 : 50
                 * so2 : 23
                 * pm2_5 : 59
                 * so224 : 15
                 * o3 : 99
                 * pm10 : 108
                 * pm1024 : 104
                 * pm2_524 : 56
                 * co : 1.060
                 * aqiinfo : {"measure":"极少数异常敏感人群应减少户外活动","color":"#FFFF00","level":"二级","affect":"空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响"}
                 * quality : 良
                 * aqi : 80
                 * ino2 : 32
                 * ipm10 : 79
                 * io38 : 25
                 * iso2 : 8
                 * timepoint : 2017-11-01 14:00:00
                 */

                private String co24;
                private String ipm2_5;
                private String io3;
                private String primarypollutant;
                private String no2;
                private String no224;
                private String ico;
                private String o38;
                private String o324;
                private String so2;
                private String pm2_5;
                private String so224;
                private String o3;
                private String pm10;
                private String pm1024;
                private String pm2_524;
                private String co;
                private AqiinfoBean aqiinfo;
                private String quality;
                private String aqi;
                private String ino2;
                private String ipm10;
                private String io38;
                private String iso2;
                private String timepoint;

                public String getCo24() {
                    return co24;
                }

                public void setCo24(String co24) {
                    this.co24 = co24;
                }

                public String getIpm2_5() {
                    return ipm2_5;
                }

                public void setIpm2_5(String ipm2_5) {
                    this.ipm2_5 = ipm2_5;
                }

                public String getIo3() {
                    return io3;
                }

                public void setIo3(String io3) {
                    this.io3 = io3;
                }

                public String getPrimarypollutant() {
                    return primarypollutant;
                }

                public void setPrimarypollutant(String primarypollutant) {
                    this.primarypollutant = primarypollutant;
                }

                public String getNo2() {
                    return no2;
                }

                public void setNo2(String no2) {
                    this.no2 = no2;
                }

                public String getNo224() {
                    return no224;
                }

                public void setNo224(String no224) {
                    this.no224 = no224;
                }

                public String getIco() {
                    return ico;
                }

                public void setIco(String ico) {
                    this.ico = ico;
                }

                public String getO38() {
                    return o38;
                }

                public void setO38(String o38) {
                    this.o38 = o38;
                }

                public String getO324() {
                    return o324;
                }

                public void setO324(String o324) {
                    this.o324 = o324;
                }

                public String getSo2() {
                    return so2;
                }

                public void setSo2(String so2) {
                    this.so2 = so2;
                }

                public String getPm2_5() {
                    return pm2_5;
                }

                public void setPm2_5(String pm2_5) {
                    this.pm2_5 = pm2_5;
                }

                public String getSo224() {
                    return so224;
                }

                public void setSo224(String so224) {
                    this.so224 = so224;
                }

                public String getO3() {
                    return o3;
                }

                public void setO3(String o3) {
                    this.o3 = o3;
                }

                public String getPm10() {
                    return pm10;
                }

                public void setPm10(String pm10) {
                    this.pm10 = pm10;
                }

                public String getPm1024() {
                    return pm1024;
                }

                public void setPm1024(String pm1024) {
                    this.pm1024 = pm1024;
                }

                public String getPm2_524() {
                    return pm2_524;
                }

                public void setPm2_524(String pm2_524) {
                    this.pm2_524 = pm2_524;
                }

                public String getCo() {
                    return co;
                }

                public void setCo(String co) {
                    this.co = co;
                }

                public AqiinfoBean getAqiinfo() {
                    return aqiinfo;
                }

                public void setAqiinfo(AqiinfoBean aqiinfo) {
                    this.aqiinfo = aqiinfo;
                }

                public String getQuality() {
                    return quality;
                }

                public void setQuality(String quality) {
                    this.quality = quality;
                }

                public String getAqi() {
                    return aqi;
                }

                public void setAqi(String aqi) {
                    this.aqi = aqi;
                }

                public String getIno2() {
                    return ino2;
                }

                public void setIno2(String ino2) {
                    this.ino2 = ino2;
                }

                public String getIpm10() {
                    return ipm10;
                }

                public void setIpm10(String ipm10) {
                    this.ipm10 = ipm10;
                }

                public String getIo38() {
                    return io38;
                }

                public void setIo38(String io38) {
                    this.io38 = io38;
                }

                public String getIso2() {
                    return iso2;
                }

                public void setIso2(String iso2) {
                    this.iso2 = iso2;
                }

                public String getTimepoint() {
                    return timepoint;
                }

                public void setTimepoint(String timepoint) {
                    this.timepoint = timepoint;
                }

                public static class AqiinfoBean {
                    /**
                     * measure : 极少数异常敏感人群应减少户外活动
                     * color : #FFFF00
                     * level : 二级
                     * affect : 空气质量可接受，但某些污染物可能对极少数异常敏感人群健康有较弱影响
                     */

                    private String measure;
                    private String color;
                    private String level;
                    private String affect;

                    public String getMeasure() {
                        return measure;
                    }

                    public void setMeasure(String measure) {
                        this.measure = measure;
                    }

                    public String getColor() {
                        return color;
                    }

                    public void setColor(String color) {
                        this.color = color;
                    }

                    public String getLevel() {
                        return level;
                    }

                    public void setLevel(String level) {
                        this.level = level;
                    }

                    public String getAffect() {
                        return affect;
                    }

                    public void setAffect(String affect) {
                        this.affect = affect;
                    }
                }
            }

            public static class IndexBean {
                /**
                 * ivalue : 较少开启
                 * detail : 您将感到很舒适，一般不需要开启空调。
                 * iname : 空调指数
                 */

                private String ivalue;
                private String detail;
                private String iname;

                public String getIvalue() {
                    return ivalue;
                }

                public void setIvalue(String ivalue) {
                    this.ivalue = ivalue;
                }

                public String getDetail() {
                    return detail;
                }

                public void setDetail(String detail) {
                    this.detail = detail;
                }

                public String getIname() {
                    return iname;
                }

                public void setIname(String iname) {
                    this.iname = iname;
                }
            }

            public static class DailyBean {
                /**
                 * date : 2017-11-01
                 * sunrise : 06:21
                 * week : 星期三
                 * sunset : 17:16
                 * night : {"templow":"10","img":"1","winddirect":"南风","windpower":"微风","weather":"多云"}
                 * day : {"img":"0","winddirect":"东南风","windpower":"3-4 级","weather":"晴","temphigh":"21"}
                 */

                private String date;
                private String sunrise;
                private String week;
                private String sunset;
                private NightBean night;
                private DayBean day;

                public String getDate() {
                    return date;
                }

                public void setDate(String date) {
                    this.date = date;
                }

                public String getSunrise() {
                    return sunrise;
                }

                public void setSunrise(String sunrise) {
                    this.sunrise = sunrise;
                }

                public String getWeek() {
                    return week;
                }

                public void setWeek(String week) {
                    this.week = week;
                }

                public String getSunset() {
                    return sunset;
                }

                public void setSunset(String sunset) {
                    this.sunset = sunset;
                }

                public NightBean getNight() {
                    return night;
                }

                public void setNight(NightBean night) {
                    this.night = night;
                }

                public DayBean getDay() {
                    return day;
                }

                public void setDay(DayBean day) {
                    this.day = day;
                }

                public static class NightBean {
                    /**
                     * templow : 10
                     * img : 1
                     * winddirect : 南风
                     * windpower : 微风
                     * weather : 多云
                     */

                    private String templow;
                    private String img;
                    private String winddirect;
                    private String windpower;
                    private String weather;

                    public String getTemplow() {
                        return templow;
                    }

                    public void setTemplow(String templow) {
                        this.templow = templow;
                    }

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }

                    public String getWinddirect() {
                        return winddirect;
                    }

                    public void setWinddirect(String winddirect) {
                        this.winddirect = winddirect;
                    }

                    public String getWindpower() {
                        return windpower;
                    }

                    public void setWindpower(String windpower) {
                        this.windpower = windpower;
                    }

                    public String getWeather() {
                        return weather;
                    }

                    public void setWeather(String weather) {
                        this.weather = weather;
                    }
                }

                public static class DayBean {
                    /**
                     * img : 0
                     * winddirect : 东南风
                     * windpower : 3-4 级
                     * weather : 晴
                     * temphigh : 21
                     */

                    private String img;
                    private String winddirect;
                    private String windpower;
                    private String weather;
                    private String temphigh;

                    public String getImg() {
                        return img;
                    }

                    public void setImg(String img) {
                        this.img = img;
                    }

                    public String getWinddirect() {
                        return winddirect;
                    }

                    public void setWinddirect(String winddirect) {
                        this.winddirect = winddirect;
                    }

                    public String getWindpower() {
                        return windpower;
                    }

                    public void setWindpower(String windpower) {
                        this.windpower = windpower;
                    }

                    public String getWeather() {
                        return weather;
                    }

                    public void setWeather(String weather) {
                        this.weather = weather;
                    }

                    public String getTemphigh() {
                        return temphigh;
                    }

                    public void setTemphigh(String temphigh) {
                        this.temphigh = temphigh;
                    }
                }
            }

            public static class HourlyBean {
                /**
                 * temp : 22
                 * img : 0
                 * weather : 晴
                 * time : 16:00
                 */

                private String temp;
                private String img;
                private String weather;
                private String time;

                public String getTemp() {
                    return temp;
                }

                public void setTemp(String temp) {
                    this.temp = temp;
                }

                public String getImg() {
                    return img;
                }

                public void setImg(String img) {
                    this.img = img;
                }

                public String getWeather() {
                    return weather;
                }

                public void setWeather(String weather) {
                    this.weather = weather;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }
            }
        }
    }
}
