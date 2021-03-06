package com.beestar.jzb.goglebleweather;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.beestar.jzb.goglebleweather.Service.MyBroadCastReciver;
import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.gen.DaoMaster;
import com.beestar.jzb.goglebleweather.gen.DaoSession;

import com.beestar.jzb.goglebleweather.utils.CrashHandler;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.tsy.sdk.myokhttp.MyOkHttp;

/**
 * Created by jzb on 2017/6/28.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MyApp extends Application {
    private static MyApp mContext;
    private DaoMaster.DevOpenHelper mHelper;
    private SQLiteDatabase db;
    private DaoMaster mDaoMaster;
    private DaoSession mDaoSession;
    private MyOkHttp myOkHttp;

    public MyOkHttp getMyOkHttp() {
        return myOkHttp;
    }

    private IntentFilter mIntentFilter = null;

    private MyBroadCastReciver mMyBroadcastRecvier = null;


    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        setDataBase();

        Fresco.initialize(getContext());
        myOkHttp = new MyOkHttp();

        initFliter();
    }


    private void initFliter() {
        mIntentFilter=new IntentFilter();
        mIntentFilter.addAction(MyServiceBlueTooth.MYSENDDATA);
        mIntentFilter.addAction(MyServiceBlueTooth.BLUETOOTHCONNECTED);
        mIntentFilter.addAction(MyServiceBlueTooth.BLUETOOTHDISCONNECT);
        mIntentFilter.addAction(MyServiceBlueTooth.HAVEFINDSERVICE);
        mIntentFilter.addAction("com.example.UPDATASTEP");
        mMyBroadcastRecvier=new MyBroadCastReciver();
        registerReceiver(mMyBroadcastRecvier,mIntentFilter);
    }

    private void setDataBase() {
        // 通过DaoMaster 的内部类 DevOpenHelper，你可以得到一个便利的SQLiteOpenHelper 对象。
        // 可能你已经注意到了，你并不需要去编写「CREATE TABLE」这样的 SQL 语句，因为greenDAO 已经帮你做了。
        // 注意：默认的DaoMaster.DevOpenHelper 会在数据库升级时，删除所有的表，意味着这将导致数据的丢失。
        // 所以，在正式的项目中，你还应该做一层封装，来实现数据库的安全升级。
        mHelper = new DaoMaster.DevOpenHelper(this,"device-db", null);
        db =mHelper.getWritableDatabase();
        // 注意：该数据库连接属于DaoMaster，所以多个 Session 指的是相同的数据库连接。
        mDaoMaster = new DaoMaster(db);
        mDaoSession = mDaoMaster.newSession();
    }
    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public SQLiteDatabase getDb() {
        return db;
    }
    public static MyApp getContext() {
        return mContext;
    }
}
