package com.beestar.jzb.goglebleweather.bean;

/**
 * Created by jzb on 2017/10/30.
 */

public class Login_Return {

    /**
     * additions : {"name":"碧星","sex":"UNKNOWN"}
     * rtn_code : 0
     * msg : SUCCESS
     */

    private AdditionsBean additions;
    private int rtn_code;
    private String msg;

    public AdditionsBean getAdditions() {
        return additions;
    }

    public void setAdditions(AdditionsBean additions) {
        this.additions = additions;
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

    public static class AdditionsBean {
        /**
         * name : 碧星
         * sex : UNKNOWN
         */

        private String name;
        private String sex;

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
}
