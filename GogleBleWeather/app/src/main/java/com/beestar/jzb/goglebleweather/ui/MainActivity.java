package com.beestar.jzb.goglebleweather.ui;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.ui.haveLogin.AboutUsActivity;
import com.beestar.jzb.goglebleweather.ui.haveLogin.NoDisturbActivity;
import com.beestar.jzb.goglebleweather.ui.haveLogin.SetMusicActivity;
import com.beestar.jzb.goglebleweather.ui.haveLogin.UpDateActivity;
import com.beestar.jzb.goglebleweather.ui.register.RegisterActivity_step_one;
import com.beestar.jzb.goglebleweather.ui.setting.MyHomeSettingActivity;
import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.utils.SPUtils;
import com.beestar.jzb.goglebleweather.view.OptionCircle;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.zhy.android.percent.support.PercentRelativeLayout;

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
    private RelativeLayout main_relative_title;
    private RelativeLayout main_relative_mid_all;
    private View swtich_button;
    private ImageView image_icoin;
    private TextView userNameText;
    private LinearLayout sanjiao;
    Handler handler=new Handler();
    private ScrollView scrollView_my;
    float x1,x2,y1,y2;
    private PercentRelativeLayout setIconSliding;
    private DeviceBeanDao deviceBeanDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setLayoutHeight(getScreenHeight(this));
//        createOptionCircle();
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        initView();
        startService(new Intent(MainActivity.this, BluetoothLeService.class));
        mSlidingMenu = new SlidingMenu(MyApp.getContext());
        mSlidingMenu.setMode(SlidingMenu.LEFT);     //设置从左弹出/滑出SlidingMenu
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);   //设置占满屏幕
        mSlidingMenu.attachToActivity(this,SlidingMenu.SLIDING_CONTENT);    //绑定到哪一个Activity对象
        mSlidingMenu.setMenu(R.layout.slidingmenulayout);                   //设置弹出的SlidingMenu的布局文件
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);       //设置SlidingMenu所占的偏移
        initSlidingMenuView();
        onClickView();

        //===========定位功能=======================================
        mLocationClient = new LocationClient(MyApp.getContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );
        //注册监听函数
        initLocation();
        mLocationClient.start();
        //===============定位开启===================================
    }

//    private void createOptionCircle() {
//        tem_circle = ((OptionCircle) findViewById(R.id.tem));
//
//    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i("onResume------MainActivuty");
    }

    private void initView() {
        locationText = ((TextView) findViewById(R.id.addressLocation));
        scrollView_my = ((ScrollView) findViewById(R.id.scrollView_my));

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
                                && (Math.abs(y2 - y1 ) > 25)) {
                            //向下滑動
                            L.i("------------------向下-------");
                            sanjiao.setVisibility(View.VISIBLE);

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    scrollView_my.fullScroll(ScrollView.FOCUS_UP);
                                }
                            });
                        } else if (y2 - y1  < 0
                                && (Math.abs(y2 - y1 ) > 25)) {
                            //向上滑动
                            L.i("--------------向上-----------");
                            sanjiao.setVisibility(View.GONE);

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
                startActivity(new Intent(MainActivity.this,RegisterActivity_step_one.class));
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
        //个人设置
        setIconSliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (SPUtils.contains(MyApp.getContext(),"isLogin")){
                    if ((Boolean) SPUtils.get(MyApp.getContext(),"isLogin",false)){
                        startActivity(new Intent(MainActivity.this,MyHomeSettingActivity.class));
                    }else {
                        Toast.makeText(MyApp.getContext(),"您尚未登录",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(MyApp.getContext(),"您尚未登录",Toast.LENGTH_SHORT).show();
                }

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
        swtich_button = findViewById(R.id.settingdis_button);
        image_icoin = ((ImageView) findViewById(R.id.image_icoin));//头像
        userNameText = ((TextView) findViewById(R.id.usernametext));
        setIconSliding = ((PercentRelativeLayout) findViewById(R.id.set_icon_sliding));
    }

    public void ShowSlidingMenu(View view) {
        if (SPUtils.contains(MyApp.getContext(),"isLogin")){
            if ((Boolean) SPUtils.get(MyApp.getContext(),"isLogin",false)){

                isLogin_layout.setVisibility(View.VISIBLE);
                isNoLogin_layout.setVisibility(View.GONE);
                swtich_button.setVisibility(View.VISIBLE);
//                quit.setVisibility(View.VISIBLE);
            }else {
                isLogin_layout.setVisibility(View.GONE);
                isNoLogin_layout.setVisibility(View.VISIBLE);
//                quit.setVisibility(View.GONE);
                swtich_button.setVisibility(View.GONE);
            }
        }else {
            isLogin_layout.setVisibility(View.GONE);
            isNoLogin_layout.setVisibility(View.VISIBLE);
//            quit.setVisibility(View.GONE);
            swtich_button.setVisibility(View.GONE);
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
        startActivity(new Intent(MainActivity.this,AntiLostActivity.class));
    }
    //三角下拉
    public void pull_down(View view) {
        sanjiao.setVisibility(View.GONE);

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
    //设置各位置高度
    private void setLayoutHeight(int i) {
        main_relative_title = ((RelativeLayout) findViewById(R.id.main_relative_title));
        main_relative_mid_all = ((RelativeLayout) findViewById(R.id.main_relative_mid_all));
        ((LinearLayout) findViewById(R.id.bluetooth_stu)).getLayoutParams().height=(int)(i*0.08);
        ((LinearLayout) findViewById(R.id.addBlutooth)).getLayoutParams().height=(int)(i*0.09);
        ((LinearLayout) findViewById(R.id.qiuqiu)).getLayoutParams().height=(int)(i*0.58);
        ((LinearLayout) findViewById(R.id.temp_and_tianqi)).getLayoutParams().height=(int)(i*0.08);
        sanjiao = ((LinearLayout) findViewById(R.id.sanjiao));
        ((RelativeLayout) findViewById(R.id.second_screen)).getLayoutParams().height=(int)(i*0.761);

        //设置标题栏高度
        main_relative_title.getLayoutParams().height=(int)(i*0.1);
       //设置中间部分高度
        main_relative_mid_all.getLayoutParams().height=(int)(i*1);
        //三角下拉
        sanjiao.getLayoutParams().height=(int)(i*0.07);
    }

    public static int getScreenHeight(Activity context){
        int i = getDeviceHeight(context);
        int j = getStatusBarHeight(context)/2;
        int k = getNavigationBarHeight(context);
        int g= i-k;
        Log.i("info",i+"------"+j+"----"+k+""+g);
        return g;
    }
    /**获取屏幕的高*/
    public static int getDeviceHeight(Activity context){
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
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }
    /**
     * 获取底部导航栏高度
     */
    public static int getNavigationBarHeight(Activity context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }
}
