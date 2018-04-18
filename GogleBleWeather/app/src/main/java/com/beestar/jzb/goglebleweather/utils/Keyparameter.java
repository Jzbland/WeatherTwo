package com.beestar.jzb.goglebleweather.utils;

/**
 * Created by jzb on 2017/6/30.
 */

public class Keyparameter {
    //写
    public static final String WRITEGATTSERVICEUUID="0000ffe5-0000-1000-8000-00805f9b34fb";
    public static final String WRITECHARACTERISTIC="0000ffe9-0000-1000-8000-00805f9b34fb";
    //读
    public static final String READATTSERVICEUUID="0000ffe0-0000-1000-8000-00805f9b34fb";
    public static final String READCHARACTERISTIC="0000ffe4-0000-1000-8000-00805f9b34fb";


    public static final String ACTTION_BLE_SEND_DATA="com.beestar.jzb.goglebleweather.acction.ble.send.data";
    public static final String ACTTION_BLE_DISCONNECT="com.beestar.jzb.goglebleweather.acction.ble.disconnect";
    public static final String BLUTOOTH_DEVICE="com.beestar.jzb.goglebleweather.device";//


    public static final String ACTTION_UPDATAUI="com.beestar.jzb.goglebleweather.updataui";


    public static final int CODE_SUCCESS=0;
    public static final int CODE_HAVE_REGISTE=10001;
    public static final int CODE_PWD_PADERNOSAME=10002;
    public static final int CODE_USER_PWD_ERROR=10003;
    public static final int CODE_USER_ERROR=10004;
    public static final int CODE_PWD_PUT_ERROR=10005;
    public static final int CODE_PWD_ERROR=10006;
    public static final int CODE_MSG_ERROR=10007;

    public static final int CODE_ADDRESS_ERROR=20001;
    public static final int CODE_WEATHERDATA_ERROR=20002;

    public static final int CODE_DATD_ERROR=30001;

    public static final int CODE_ERROR_SERVICE=90001;

    public static final String CODE_SUCCESS_STR="";
    public static final String CODE_HAVE_REGISTE_STR="手机号码已注册";
    public static final String CODE_PWD_PADERNOSAME_STR="两次密码不一致";
    public static final String CODE_USER_PWD_ERROR_STR="账号密码不正确";
    public static final String CODE_USER_ERROR_STR="不合法的用户标识";
    public static final String CODE_PWD_PUT_ERROR_STR="用户密码错误";
    public static final String CODE_PWD_ERROR_STR="不合法的用户密码";
    public static final String CODE_MSG_ERROR_STR="短信验证码已过期或无效";

    public static final String CODE_ADDRESS_ERROR_STR="不合法的用户地址信息";
    public static final String CODE_WEATHERDATA_ERROR_STR="不合法的气象数据";

    public static final String CODE_DATD_ERROR_STR="数据查询异常，请稍后重试";

    public static final String CODE_ERROR_SERVICE_STR="内部错误";


}
