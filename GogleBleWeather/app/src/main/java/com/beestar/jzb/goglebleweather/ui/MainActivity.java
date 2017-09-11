package com.beestar.jzb.goglebleweather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.BluetoothLeService;
import com.beestar.jzb.goglebleweather.ui.haveLogin.AboutUsActivity;
import com.beestar.jzb.goglebleweather.ui.haveLogin.NoDisturbActivity;
import com.beestar.jzb.goglebleweather.ui.haveLogin.SetMusicActivity;
import com.beestar.jzb.goglebleweather.ui.haveLogin.UpDateActivity;
import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.utils.SPUtils;
import com.beestar.jzb.goglebleweather.view.OptionCircle;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
public class MainActivity extends BaseActivity {
    private SlidingMenu mSlidingMenu;
    private View login;
    public LocationClient mLocationClient = null;
    public BDAbstractLocationListener myListener = new MyLocationListener();
    private TextView locationText;
    private View register;
    private View isLogin_layout;
    private View isNoLogin_layout;
    private Button quit;
    private View noDisturb;
    private View aboutUs;
    private View update;
    private View setMusic;
    private OptionCircle tem_circle;
//    public static final int circle0_r = 33;
//
//    private static final int SLEEPING_PERIOD = 100; // 刷新UI间隔时间
//    private static final int UPDATE_ALL_CIRCLE = 99;
//    int circleCenter_r;
//    int circle1_r;
//    boolean circle0Clicked = false;
//    boolean circle1Clicked = false;
//
//    OptionCircle centerCircle;
//    OptionCircle circle0;
//    OptionCircle circle1;
//    OptionCircle circle2;
//
//    CircleHandler handler = new CircleHandler(this);
//
//    /**
//     * Handler : 用于更新UI
//     */
//    static class CircleHandler extends Handler {
//        MainActivity activity;
//        boolean zoomDir = true;
//        boolean circle2Shaking = false;
//        int r = circle0_r;
//        int moveDir = 0;  // 浮动方向
//        int circle1_x = 0;// 偏移量的值
//        int circle1_y = 0;
//        int circle2_x = 0;
//        int circle2ShakeTime = 0;
//        int circle2Offsets[] = {10, 15, -6, 12, 0};// 抖动偏移量坐标
//
//        CircleHandler(MainActivity a) {
//            activity = a;
//        }
//
//        @Override
//        public void handleMessage(Message msg) {
//            switch (msg.what) {
//                case UPDATE_ALL_CIRCLE: {
//                    if (zoomDir) {// 用简单的办法实现半径变化
//                        r++;
//                        if (r >= 44) zoomDir = false;
//                    } else {
//                        r--;
//                        if (r <= circle0_r) zoomDir = true;
//                    }
//                    activity.circle0.invalidate();
//                    activity.circle0.setRadius(r);
//                    calOffsetX();// 计算圆心偏移量
//                    activity.circle1.invalidate();
//                    activity.circle1.setCenterOffset(circle1_x, circle1_y);
//
//                    if (circle2Shaking) {
//                        if (circle2ShakeTime < circle2Offsets.length - 1) {
//                            circle2ShakeTime++;
//                        } else {
//                            circle2Shaking = false;
//                            circle2ShakeTime = 0;
//                        }
//                        activity.circle2.invalidate();
//                        activity.circle2.setCenterOffset(circle2Offsets[circle2ShakeTime], 0);
//                    }
//                }
//            }
//        }
//        // 计算circle1圆心偏移量；共有4个浮动方向
//        private void calOffsetX() {
//            if (moveDir == 0) {
//                circle1_x--;
//                circle1_y++;
//                if (circle1_x <= -6) moveDir = 1;
//            }
//            if (moveDir == 1) {
//                circle1_x++;
//                circle1_y++;
//                if (circle1_x >= 0) moveDir = 2;
//            }
//            if (moveDir == 2) {
//                circle1_x++;
//                circle1_y--;
//                if (circle1_x >= 6) moveDir = 3;
//            }
//            if (moveDir == 3) {
//                circle1_x--;
//                circle1_y--;
//                if (circle1_x <= 0) moveDir = 0;
//            }
//        }
//    }
//
//    class UpdateCircles implements Runnable {
//
//        @Override
//        public void run() {
//            while (true) {// 配合Handler，循环刷新UI
//                Message message = new Message();
//                message.what = UPDATE_ALL_CIRCLE;
//                handler.sendEmptyMessage(message.what);
//                try {
//                    Thread.sleep(SLEEPING_PERIOD); // 暂停
//                } catch (InterruptedException e) {
//                    Thread.currentThread().interrupt();
//                }
//            }
//        }
//    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createOptionCircle();
        startService(new Intent(MainActivity.this, BluetoothLeService.class));
        mSlidingMenu = new SlidingMenu(MyApp.getContext());
        mSlidingMenu.setMode(SlidingMenu.LEFT);     //设置从左弹出/滑出SlidingMenu
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);   //设置占满屏幕
        mSlidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);    //绑定到哪一个Activity对象
        mSlidingMenu.setMenu(R.layout.slidingmenulayout);                   //设置弹出的SlidingMenu的布局文件
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);       //设置SlidingMenu所占的偏移
        initSlidingMenuView();
        onClickView();
        initView();
        //===========定位功能=======================================
        mLocationClient = new LocationClient(MyApp.getContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        //注册监听函数
        initLocation();
        mLocationClient.start();
        //===============定位开启===================================
    }

    private void createOptionCircle() {
        tem_circle = ((OptionCircle) findViewById(R.id.tem));

    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i("onResume------MainActivuty");
    }

    private void initView() {
        locationText = ((TextView) findViewById(R.id.addressLocation));
    }

    private void onClickView() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SPUtils.put(MyApp.getContext(),"isLogin",true);
                mSlidingMenu.toggle(true);

            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
            }
        });
        //退出登录
        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SPUtils.clear(MyApp.getContext());
                mSlidingMenu.toggle(true);
            }
        });
        //勿扰设置
        noDisturb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,NoDisturbActivity.class));
            }
        });
        //铃声设置
        setMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,SetMusicActivity.class));
            }
        });
        //关于我们
        aboutUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,AboutUsActivity.class));
            }
        });
        //版本更新
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,UpDateActivity.class));
            }
        });
    }

    private void initSlidingMenuView() {
        login = mSlidingMenu.findViewById(R.id.login);
        register = mSlidingMenu.findViewById(R.id.register);
        isLogin_layout = mSlidingMenu.findViewById(R.id.isLogin);
        isNoLogin_layout = mSlidingMenu.findViewById(R.id.isNoLogin);
        quit = ((Button) mSlidingMenu.findViewById(R.id.quit));

        noDisturb = mSlidingMenu.findViewById(R.id.nodisturb);
        aboutUs = mSlidingMenu.findViewById(R.id.aboutUs);
        update = mSlidingMenu.findViewById(R.id.update);
        setMusic = mSlidingMenu.findViewById(R.id.setMusic);
    }

    public void ShowSlidingMenu(View view) {
        if (SPUtils.contains(MyApp.getContext(),"isLogin")){
            if ((Boolean) SPUtils.get(MyApp.getContext(),"isLogin",false)){

                isLogin_layout.setVisibility(View.VISIBLE);
                isNoLogin_layout.setVisibility(View.GONE);
                quit.setVisibility(View.VISIBLE);
            }else {
                isLogin_layout.setVisibility(View.GONE);
                isNoLogin_layout.setVisibility(View.VISIBLE);
                quit.setVisibility(View.GONE);
            }
        }else {
            isLogin_layout.setVisibility(View.GONE);
            isNoLogin_layout.setVisibility(View.VISIBLE);
            quit.setVisibility(View.GONE);
        }
        mSlidingMenu.toggle(true);
    }

    private void initLocation(){

        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
        //可选，默认高精度，设置定位模式，高精度，低功耗，仅设备

        option.setCoorType("bd09ll");
        //可选，默认gcj02，设置返回的定位结果坐标系

        int span=1000;
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

    public void binding(View view) {
        startActivity(new Intent(MainActivity.this,BindingActivity.class));
    }

    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getAddrStr()!=null){
                L.i(bdLocation.getAddrStr()+"====地址====");
                L.i(bdLocation.getPoiList().get(0).getName()+"====地址====");
                locationText.setText(bdLocation.getAddrStr()+bdLocation.getPoiList().get(0).getName());
                mLocationClient.stop();
            }else {
                Toast.makeText(MyApp.getContext(),"定位失败请检查GPS以及手机网络",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
