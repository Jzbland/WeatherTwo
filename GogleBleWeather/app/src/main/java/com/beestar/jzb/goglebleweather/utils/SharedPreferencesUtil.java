package com.beestar.jzb.goglebleweather.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.beestar.jzb.goglebleweather.MyApp;

/**
 * Created by Administrator on 2016/2/1.
 */
public class SharedPreferencesUtil {

    public static final String TEMP_HOUR = "temp_hour";
    public static final String TEMP_MINUTE = "temp_minute";
    public static final String LOGIN_NAME = "login_name";
    public static final String LOGIN_PWD = "login_pwd";
    public static final String SERVER = "server";
    public static final String PORT = "port";
    public static final String ADDRESS = "address";
    public static final String LOSES = "loses";

    public static final String FIRST = "first";

    public static final String DOUBLE = "double";

    public static final String TIME="time";

    private SharedPreferences mPrefs;
    private static SharedPreferencesUtil sInstance;

    public static SharedPreferencesUtil getInstance() {
        if (sInstance == null) {
            sInstance = new SharedPreferencesUtil(MyApp.getContext());
        }
        return sInstance;
    }

    private SharedPreferencesUtil(Context context) {
        mPrefs = context.getSharedPreferences("setting", Context.MODE_PRIVATE);
    }

    public int getTempHour() {
        return mPrefs.getInt(TEMP_HOUR, -1);
    }

    public int getTempMinute() {
        return mPrefs.getInt(TEMP_MINUTE, -1);
    }

    public boolean getLoses() {
        return mPrefs.getBoolean(LOSES, false);
    }

    public void setLoese(boolean b){
        mPrefs.edit().putBoolean(LOSES, b).commit();
    }

    public boolean getDouble() {
        return mPrefs.getBoolean(DOUBLE, true);
    }

    public void setDouble(boolean b){
        mPrefs.edit().putBoolean(DOUBLE, b).commit();
    }

    public void setTempHour(int hour){
        mPrefs.edit().putInt(TEMP_HOUR, hour).commit();
    }

    public void setTempMinute(int minute){
        mPrefs.edit().putInt(TEMP_MINUTE, minute).commit();
    }

    public int getFIRST() {
        return mPrefs.getInt(FIRST, 0);
    }

    public void setFIRST(int i){
        mPrefs.edit().putInt(FIRST, i).commit();
    }

    public void setLoginName(String name) {
        mPrefs.edit().putString(LOGIN_NAME, name).commit();
    }

    public String getLoginName(){
        return mPrefs.getString(LOGIN_NAME, "");
    }

    public void setLoginPwd(String pwd) {
        mPrefs.edit().putString(LOGIN_PWD, pwd).commit();
    }

    public String getLoginPwd(){
        return mPrefs.getString(LOGIN_PWD, "");
    }

    public void setServer(String server) {
        mPrefs.edit().putString(SERVER, server).commit();
    }

    public String getServer(){
        return mPrefs.getString(SERVER, "");
    }

    public void setPort(String port) {
        mPrefs.edit().putString(PORT, port).commit();
    }

    public String getPort() {
        return mPrefs.getString(PORT, "");
    }

    public void setAddress(String address) {
        mPrefs.edit().putString(ADDRESS, address).commit();
    }

    public String getAddress() {
        return mPrefs.getString(ADDRESS, "");
    }
}
