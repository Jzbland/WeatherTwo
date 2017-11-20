package com.beestar.jzb.goglebleweather.ui;
/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * <p>
 * ━━━━━━感觉萌萌哒━━━━━━
 */

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.BluetoothLeService;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.bean.LoginBean;
import com.beestar.jzb.goglebleweather.bean.Login_Return;
import com.beestar.jzb.goglebleweather.bean.MyWeatherBean;
import com.beestar.jzb.goglebleweather.bean.PM;
import com.beestar.jzb.goglebleweather.bean.Weather_Bean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.ui.haveLogin.AboutUsActivity;
import com.beestar.jzb.goglebleweather.ui.haveLogin.NoDisturbActivity;
import com.beestar.jzb.goglebleweather.ui.haveLogin.SetMusicActivity;
import com.beestar.jzb.goglebleweather.ui.haveLogin.UpDateActivity;
import com.beestar.jzb.goglebleweather.ui.register.RegisterActivity_step_one;
import com.beestar.jzb.goglebleweather.ui.register.RememberPwd;
import com.beestar.jzb.goglebleweather.ui.setting.MyHomeSettingActivity;
import com.beestar.jzb.goglebleweather.utils.ChartUtils;
import com.beestar.jzb.goglebleweather.utils.Keyparameter;
import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.utils.SPUtils;
import com.beestar.jzb.goglebleweather.utils.URL;
import com.beestar.jzb.goglebleweather.view.OptionCircle;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.makeramen.roundedimageview.RoundedImageView;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.zcw.togglebutton.ToggleButton;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;

import static com.beestar.jzb.goglebleweather.ui.setting.MyHomeSettingActivity.ALBUM_PATH;

public class MainActivity extends BaseActivity {
    private SlidingMenu mSlidingMenu;
    private View login;
    public LocationClient mLocationClient = null;
    public BDAbstractLocationListener myListener = new MyLocationListener();
    private TextView locationText,locationText2;
    private View register;
    private View isLogin_layout;
    private View isNoLogin_layout;
    private Button quit;
    private View noDisturb;
    private View aboutUs;
    private View update;
    private View setMusic;
    private OptionCircle tem_circle;
    private RelativeLayout main_relative_title;
    private RelativeLayout main_relative_mid_all;
    private View swtich_button;
    private RoundedImageView image_icoin;
    private TextView userNameText;
    private LinearLayout sanjiao;
    Handler handler = new Handler();
    private ScrollView scrollView_my;
    float x1, x2, y1, y2;
    private PercentRelativeLayout setIconSliding;
    private DeviceBeanDao deviceBeanDao;
    private EditText mUsernameView, mPasswordView;
    private Message message = new Message();
    private Bitmap bitmap;
    protected String[] values = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
    };
    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 100:
                    Matrix matrix = new Matrix();
                    matrix.setScale(0.2f, 0.2f);
                    Bitmap bit = BitmapFactory.decodeFile(ALBUM_PATH + "icon", null);
                    bitmap = Bitmap.createBitmap(bit, 0, 0, bit.getWidth(),
                            bit.getHeight(), matrix, true);
                   ;
                    image_icoin.setImageBitmap(bitmap);
                    break;
            }
        }
    };

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Keyparameter.ACTTION_UPDATAUI)) {
                L.i("--------------收到UI广播--------------");
                message.what = 100;
                mhandler.sendMessage(message);
            } else if (action.equals(BluetoothLeService.MY_DATA)) {
                final String data = intent.getStringExtra(BluetoothLeService.MY_DATA);
                L.i("--------data-------"+data);
                L.i("解析温湿度信息");
                if (data == null) {
                    return;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        int len = data.length();
                        String str = data;
                        if (len > 8 && len < 10) {
                            while (10 - len > 0) {
                                str += "0";
                                len += 1;
                            }
                        }
                        if (str.length() >= 10) {
                            MyWeatherBean myWeatherBean = getWeather(str);
                            mtem.setText("温度:" + myWeatherBean.getTem() + "℃");
                            mhum.setText("湿度:" + myWeatherBean.getHum() + "％");
                            mAirpress.setText("气压:" + myWeatherBean.getAirpress() + "Hkpa");
                        }
                    }
                });
            }

        }
    };


    //063660
    private View rememberPwd;
    private OptionCircle mTem;
    private OptionCircle mtem;
    private OptionCircle mPM25;
    private OptionCircle mhum;
    private OptionCircle mAirpress;
    private TextView mUserNameText;
    /** 24℃ */
    private TextView mDataTemp,mDataTemp2;
    /** 最高27℃ */
    private TextView mTemHeigh,mTemHeigh2;
    /** 最低27℃ */
    private TextView mTemLow,mTemLow2;
    /** 多云 */
    private TextView mWeatherText,mWeatherText2;
    /** 风力3级 */
    private TextView mWeatherWindpower,mWeatherWindpower2;
    private TextView bluetoothStatusText;
    private TextView addBluetoothText;
    private ToggleButton switch_nodisturb;
    private LineChart lineChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayoutHeight(getScreenHeight(this));
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        initView();
        startService(new Intent(MainActivity.this, BluetoothLeService.class));
        mSlidingMenu = new SlidingMenu(MyApp.getContext());
        mSlidingMenu.setMode(SlidingMenu.LEFT);     //设置从左弹出/滑出SlidingMenu
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);   //设置占满屏幕
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);    //绑定到哪一个Activity对象
        mSlidingMenu.setMenu(R.layout.slidingmenulayout);                   //设置弹出的SlidingMenu的布局文件
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);       //设置SlidingMenu所占的偏移
        mSlidingMenu.setOnOpenListener(new SlidingMenu.OnOpenListener() {
            @Override
            public void onOpen() {
                if (SPUtils.contains(MyApp.getContext(), "isLogin")) {
                    if ((Boolean) SPUtils.get(MyApp.getContext(), "isLogin", false)) {
                        isLogin_layout.setVisibility(View.VISIBLE);
                        isNoLogin_layout.setVisibility(View.GONE);
                        swtich_button.setVisibility(View.VISIBLE);
                        if (!(( (String)SPUtils.get(getApplicationContext(), "name", "")) ==null)){
                            userNameText.setText(((String) SPUtils.get(getApplicationContext(), "name", "")));
                        }

                    } else {
                        isLogin_layout.setVisibility(View.GONE);
                        isNoLogin_layout.setVisibility(View.VISIBLE);
                        swtich_button.setVisibility(View.GONE);
                        userNameText.setText("未登录");
                    }
                } else {
                    isLogin_layout.setVisibility(View.GONE);
                    isNoLogin_layout.setVisibility(View.VISIBLE);
                    swtich_button.setVisibility(View.GONE);
                    userNameText.setText("未登录");
                }
            }
        });
        initSlidingMenuView();
        onClickView();
        //===========定位功能=======================================
        mLocationClient = new LocationClient(MyApp.getContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        initLocation();
        mLocationClient.start();
        //===============定位开启===================================
        Matrix matrix = new Matrix();
        matrix.setScale(0.5f, 0.5f);
        Bitmap bm = BitmapFactory.decodeFile(ALBUM_PATH + "icon", null);
        if (bm!=null){
            bitmap = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(),
                    bm.getHeight(), matrix, true);
        }
        if (bitmap != null) {
            image_icoin.setImageBitmap(bitmap);
        }

        if (SPUtils.contains(MyApp.getContext(),"switch_nodisturb")){
            if ((Boolean) SPUtils.get(MyApp.getContext(),"switch_nodisturb",false)){
                switch_nodisturb.setToggleOn();
            }else {
                switch_nodisturb.setToggleOff();
            }
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, getFilter());
        L.i("onResume------MainActivuty");
        if (deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique()==null){
            bluetoothStatusText.setText("蓝牙未连接");
        }else {
            DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
            bluetoothStatusText.setText("蓝牙已连接");
            addBluetoothText.setText(deviceBean.getName().toString());
            sendData(deviceBean.getMac(),"01");
        }

        switch_nodisturb.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                SPUtils.put(MyApp.getContext(),"switch_nodisturb",on);
                DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (deviceBean!=null){
                    if (on){
                        sendData(deviceBean.getMac(),"F2");
                    }else {
                        sendData(deviceBean.getMac(),"F3");
                    }
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        bitmap = null;
    }

    private void initView() {
        locationText = ((TextView) findViewById(R.id.addressLocation));
        locationText2 = ((TextView) findViewById(R.id.addressLocation2));
        scrollView_my = ((ScrollView) findViewById(R.id.scrollView_my));
        bluetoothStatusText = ((TextView) findViewById(R.id.blue_status_text));
        addBluetoothText = ((TextView) findViewById(R.id.addBluetooth_text));
        mtem = ((OptionCircle) findViewById(R.id.tem));
        mPM25 = ((OptionCircle) findViewById(R.id.pm_2_5));
        mhum = ((OptionCircle) findViewById(R.id.hum));
        mAirpress = ((OptionCircle) findViewById(R.id.airpress));
        scrollView_my.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        y1 = event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        x2 = event.getX();
                        y2 = event.getY();

                        break;
                    case MotionEvent.ACTION_UP:
                        if (y2 - y1 > 0
                                && (Math.abs(y2 - y1) > 25)) {
                            //向下滑動
                            L.i("------------------向下-------");
                            //sanjiao.setVisibility(View.VISIBLE);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    scrollView_my.fullScroll(ScrollView.FOCUS_UP);
                                }
                            });
                        } else if (y2 - y1 < 0
                                && (Math.abs(y2 - y1) > 25)) {
                            //向上滑动
                            L.i("--------------向上-----------");
                            //sanjiao.setVisibility(View.GONE);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    scrollView_my.fullScroll(ScrollView.FOCUS_DOWN);
                                }
                            });
                        }

                        break;
                }
                return true;
            }
        });
        mDataTemp = (TextView) findViewById(R.id.data_temp);
        mTemHeigh = (TextView) findViewById(R.id.tem_heigh);
        mTemLow = (TextView) findViewById(R.id.tem_low);
        mWeatherText = (TextView) findViewById(R.id.weather_text);
        mWeatherWindpower = (TextView) findViewById(R.id.weather_windpower);

        mDataTemp2 = (TextView) findViewById(R.id.data_temp2);
        mTemHeigh2 = (TextView) findViewById(R.id.tem_heigh2);
        mTemLow2 = (TextView) findViewById(R.id.tem_low2);
        mWeatherText2 = (TextView) findViewById(R.id.weather_text2);
        mWeatherWindpower2 = (TextView) findViewById(R.id.weather_windpower2);

        lineChart = ((LineChart) findViewById(R.id.linechart));
        ChartUtils.initChart(lineChart);
        ChartUtils.notifyDataSetChanged(lineChart,getData(),ChartUtils.weekValue);
    }



    private void onClickView() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(mUsernameView.getText()) || TextUtils.isEmpty(mPasswordView.getText())) {
                    Toast.makeText(getApplication(), "请输入完整信息", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser();
                }
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegisterActivity_step_one.class));
            }
        });
        //忘记密码
        rememberPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RememberPwd.class));
            }
        });
        //勿扰设置
        noDisturb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, NoDisturbActivity.class));
            }
        });
        //铃声设置
        setMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SetMusicActivity.class));
            }
        });
        //关于我们
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AboutUsActivity.class));
            }
        });
        //版本更新
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UpDateActivity.class));
            }
        });
        //个人设置
        setIconSliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtils.contains(MyApp.getContext(), "isLogin")) {
                    if ((Boolean) SPUtils.get(MyApp.getContext(), "isLogin", false)) {
                        startActivity(new Intent(MainActivity.this, MyHomeSettingActivity.class));
                    } else {
                        Toast.makeText(MyApp.getContext(), "您尚未登录", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MyApp.getContext(), "您尚未登录", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void initSlidingMenuView() {
        login = mSlidingMenu.findViewById(R.id.login);
        register = mSlidingMenu.findViewById(R.id.register);
        isLogin_layout = mSlidingMenu.findViewById(R.id.isLogin);
        rememberPwd = mSlidingMenu.findViewById(R.id.rememberPwd);
        isNoLogin_layout = mSlidingMenu.findViewById(R.id.isNoLogin);
        switch_nodisturb = ((ToggleButton) mSlidingMenu.findViewById(R.id.switch1));
        //quit = ((Button) mSlidingMenu.findViewById(R.id.quit));
        mUsernameView = (EditText) mSlidingMenu.findViewById(R.id.usernameView1);
        mPasswordView = (EditText) mSlidingMenu.findViewById(R.id.passwordView1);
        mUserNameText = (TextView) mSlidingMenu.findViewById(R.id.usernametext);
        noDisturb = mSlidingMenu.findViewById(R.id.nodisturb);
        aboutUs = mSlidingMenu.findViewById(R.id.aboutUs);
        update = mSlidingMenu.findViewById(R.id.update);
        setMusic = mSlidingMenu.findViewById(R.id.setMusic);
        swtich_button = findViewById(R.id.settingdis_button);
        image_icoin = ((RoundedImageView) findViewById(R.id.image_icoin));//头像
        userNameText = ((TextView) findViewById(R.id.usernametext));
        setIconSliding = ((PercentRelativeLayout) findViewById(R.id.set_icon_sliding));

    }

    //点击弹出侧滑菜单
    public void ShowSlidingMenu(View view) {
        if (SPUtils.contains(MyApp.getContext(), "isLogin")) {
            if ((Boolean) SPUtils.get(MyApp.getContext(), "isLogin", false)) {

                isLogin_layout.setVisibility(View.VISIBLE);
                isNoLogin_layout.setVisibility(View.GONE);
                swtich_button.setVisibility(View.VISIBLE);
            } else {
                isLogin_layout.setVisibility(View.GONE);
                isNoLogin_layout.setVisibility(View.VISIBLE);
                swtich_button.setVisibility(View.GONE);
            }
        } else {
            isLogin_layout.setVisibility(View.GONE);
            isNoLogin_layout.setVisibility(View.VISIBLE);
            swtich_button.setVisibility(View.GONE);
        }
        mSlidingMenu.toggle(true);
    }

    private void initLocation() {

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span = 1000;
        option.setScanSpan(span);
        //可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的

        option.setIsNeedAddress(true);
        //可选，设置是否需要地址信息，默认不需要

        option.setOpenGps(true);
        //可选，默认false,设置是否使用gps

        option.setLocationNotify(true);
        //可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果

        option.setIsNeedLocationDescribe(true);
        //可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”

        option.setIsNeedLocationPoiList(true);
        //可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到

        option.setIgnoreKillProcess(false);
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死

//        option.setIgnoreCacheException(false);
//        //可选，默认false，设置是否收集CRASH信息，默认收集

        option.setEnableSimulateGps(false);
        //可选，默认false，设置是否需要过滤GPS仿真结果，默认需要

//        option.setWifiValidTime(5*60*1000);
//        //可选，7.2版本新增能力，如果您设置了这个接口，首次启动定位时，会先判断当前WiFi是否超出有效期，超出有效期的话，会先重新扫描WiFi，然后再定位

        mLocationClient.setLocOption(option);
    }

    /**
     * 绑定设备
     */
    public void binding(View view) {
        startActivity(new Intent(MainActivity.this, AntiLostActivity.class));
    }

    //三角下拉
    public void pull_down(View view) {
       // sanjiao.setVisibility(View.GONE);
        handler.post(new Runnable() {
            @Override
            public void run() {
                scrollView_my.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });
    }

    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getAddrStr() != null) {
                L.i(bdLocation.getAddrStr() + "====地址====");
                L.i(bdLocation.getPoiList().get(0).getName() + "====地址====");
                locationText.setText(bdLocation.getAddrStr() + bdLocation.getPoiList().get(0).getName());
                locationText2.setText(bdLocation.getAddrStr() + bdLocation.getPoiList().get(0).getName());
                getWeatherInfor(bdLocation.getCity());
                //getPM25(bdLocation.getCity());
                mLocationClient.stop();
            } else {
                Toast.makeText(MyApp.getContext(), "定位失败请检查GPS以及手机网络", Toast.LENGTH_SHORT).show();
            }
        }
    }



    //设置各位置高度
    private void setLayoutHeight(int i) {
        main_relative_title = ((RelativeLayout) findViewById(R.id.main_relative_title));
        main_relative_mid_all = ((RelativeLayout) findViewById(R.id.main_relative_mid_all));
        ((LinearLayout) findViewById(R.id.bluetooth_stu)).getLayoutParams().height = (int) (i * 0.08);
        ((LinearLayout) findViewById(R.id.addBlutooth)).getLayoutParams().height = (int) (i * 0.09);
        ((LinearLayout) findViewById(R.id.qiuqiu)).getLayoutParams().height = (int) (i * 0.58);
        ((LinearLayout) findViewById(R.id.temp_and_tianqi)).getLayoutParams().height = (int) (i * 0.08);
        sanjiao = ((LinearLayout) findViewById(R.id.sanjiao));
        ((RelativeLayout) findViewById(R.id.second_screen)).getLayoutParams().height = (int) (i * 1);
        ((LinearLayout) findViewById(R.id.temp_and_tianqi2)).getLayoutParams().height = (int) (i * 0.08);
        ((LinearLayout) findViewById(R.id.k_line)).getLayoutParams().height=(int)(i*0.361);
        ((LinearLayout) findViewById(R.id.card)).getLayoutParams().height=(int)(i*0.4);

        //设置标题栏高度
        main_relative_title.getLayoutParams().height = (int) (i * 0.1);
        //设置中间部分高度
        main_relative_mid_all.getLayoutParams().height = (int) (i * 1);
        //三角下拉
        sanjiao.getLayoutParams().height = (int) (i * 0.07);
    }
    //获取PM2.5并显示
    private void getPM25(String city) {
        if (SPUtils.contains(getApplicationContext(),"phone")){
            MyApp.getContext().getMyOkHttp().get()
                    .url(URL.url + URL.url_PM)
                    .tag(this)
                    .addParam("uid", ((String) SPUtils.get(getApplicationContext(), "phone","")))
                    .addParam("city",getCityMe(city))
                    .enqueue(new GsonResponseHandler<PM>() {
                        @Override
                        public void onFailure(int statusCode, String error_msg) {
                            L.i("获取PM25---");
                        }

                        @Override
                        public void onSuccess(int statusCode, PM response) {
                            L.i("获取PM25");
                            mPM25.setText("PM2.5:"+response.getData().get(0).get_$PM25231());
                        }
                    });
        }
    }

    public static int getScreenHeight(Activity context) {
        int i = getDeviceHeight(context);
        int j = getStatusBarHeight(context) / 2;
        int k = getNavigationBarHeight(context);
        int g = i - k;
        Log.i("info", i + "------" + j + "----" + k + "" + g);
        return g;
    }

    /**获取屏幕的高*/
    public static int getDeviceHeight(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        return p.y;
    }
    /**
     * 获取顶部状态栏
     * @param context
     * @return
     */
    public static int getStatusBarHeight(Activity context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }
    /**
     * 获取底部导航栏高度
     */
    public static int getNavigationBarHeight(Activity context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }

    private void loginUser() {
        MyApp.getContext().getMyOkHttp().post()
                .url(URL.url + URL.url_login)
                .tag(this)
                .jsonParams(new Gson().toJson(new LoginBean(mUsernameView.getText().toString().trim(), mPasswordView.getText().toString().trim())))
                .enqueue(new GsonResponseHandler<Login_Return>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Toast.makeText(getApplication(), error_msg, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Login_Return response) {
                        if (response.getRtn_code() == 0) {
                            SPUtils.put(MyApp.getContext(), "isLogin", true);
                            SPUtils.put(MyApp.getContext(),"phone",mUsernameView.getText().toString().trim());
                            SPUtils.put(MyApp.getContext(),"sex",response.getAdditions().getSex());
                            SPUtils.put(MyApp.getContext(),"name",response.getAdditions().getName());
                            mUserNameText.setText(response.getAdditions().getName());
                            mSlidingMenu.toggle(true);
                            Toast.makeText(getApplication(), response.getMsg(), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getApplication(), response.getMsg(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    private void getWeatherInfor(String city) {
        MyApp.getContext().getMyOkHttp().post()
                .url(URL.url_weather)
                .tag(this)
                .addParam("city", city)
                .addParam("appkey", "5468ea49f7a75f15be7a51975ddf9087")
                .enqueue(new GsonResponseHandler<Weather_Bean>() {
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        L.i("京东天气---");
                    }

                    @Override
                    public void onSuccess(int statusCode, Weather_Bean response) {
                        Weather_Bean.ResultBeanX result = response.getResult();

                        mDataTemp.setText(response.getResult().getResult().getTemp()+"℃");
                        mTemHeigh.setText("最高"+response.getResult().getResult().getTemphigh()+"℃");
                        mTemLow.setText("最低"+response.getResult().getResult().getTemplow()+"℃");
                        mWeatherText.setText( response.getResult().getResult().getWeather());
                        mWeatherWindpower.setText("风力"+ result.getResult().getWindpower());

                        mDataTemp2.setText(response.getResult().getResult().getTemp()+"℃");
                        mTemHeigh2.setText("最高"+response.getResult().getResult().getTemphigh()+"℃");
                        mTemLow2.setText("最低"+response.getResult().getResult().getTemplow()+"℃");
                        mWeatherText2.setText( response.getResult().getResult().getWeather());
                        mWeatherWindpower2.setText("风力"+ result.getResult().getWindpower());

                        mPM25.setText("PM2.5:"+response.getResult().getResult().getAqi().getIpm2_5());
                    }
                });

    }

    //更新UI广播
    private IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keyparameter.ACTTION_UPDATAUI);
        intentFilter.addAction(BluetoothLeService.MY_DATA);
        return intentFilter;
    }

    private MyWeatherBean getWeather(String str) {
        String temp = str.substring(0, 10);
        MyWeatherBean myweather = new MyWeatherBean();
        //温度
        //温度+28 65 098 98

        if (temp.charAt(0) == '-') {
            if (temp.charAt(1) == '0') {
                myweather.setTem("-" + temp.substring(2, 3));
            } else {
                myweather.setTem("-" + temp.substring(1, 3));
            }
        } else {
            if (temp.charAt(1) == '0') {
                myweather.setTem(temp.substring(2, 3));
            } else {
                myweather.setTem(temp.substring(1, 3));
            }
        }
        //湿度
        if (temp.charAt(3) == '0') {
            myweather.setHum(temp.substring(4, 5));
        } else {
            myweather.setHum(temp.substring(3, 5));
        }
        //气压
        String s_press = str.substring(5, 8);
        if (s_press.charAt(0) == '0') {
            String s = str.substring(6, 8);
            if (s.charAt(0) == '0') {
                myweather.setAirpress(str.substring(7, 8));
            } else {
                myweather.setAirpress(s);
            }
        } else {
            myweather.setAirpress(s_press);
        }
        //电量
        String s_power = str.substring(8);
        if (s_power.charAt(0) == '0') {
            myweather.setPower(str.substring(9));
        } else {
            myweather.setPower(s_power);
        }
        return myweather;
    }
    /**
     * 截取城市名字
     */
    private String getCityMe(String city){
        String add=null ;
        if (city.contains("市")) {
            int i = city.indexOf("市");
            add=city.substring(0,i);
        }
        return add;
    }

    public void sendData(String address,String data){
        Intent intent=new Intent();
        intent.setAction(BluetoothLeService.SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);
    }

    private List<Entry> getData() {
        List<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 15));
        values.add(new Entry(1, 15));
        values.add(new Entry(2, 15));
        values.add(new Entry(3, 20));
        values.add(new Entry(4, 25));
        values.add(new Entry(5, 20));
        values.add(new Entry(6, 20));
        return values;
    }
}
