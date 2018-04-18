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

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.adapter.RecycleViewAdapter;
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
import com.beestar.jzb.goglebleweather.ui.register.WebActivity;
import com.beestar.jzb.goglebleweather.ui.setting.MyHomeSettingActivity;
import com.beestar.jzb.goglebleweather.utils.Keyparameter;
import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.utils.SPUtils;
import com.beestar.jzb.goglebleweather.utils.URL;
import com.beestar.jzb.goglebleweather.view.BallView;
import com.beestar.jzb.goglebleweather.view.LineTemView;
import com.beestar.jzb.goglebleweather.view.LineTimeView;
import com.beestar.jzb.goglebleweather.view.MyLineView;
import com.beestar.jzb.goglebleweather.view.OptionCircle;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;
import com.zcw.togglebutton.ToggleButton;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;


@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MainActivity extends BaseActivity {
    private List<Boolean> isClicks=new ArrayList<>();
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
    private static final int IMAGE = 1;
    protected String[] values = new String[]{
            "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
    };

    public final static String ALBUM_PATH
            = Environment.getExternalStorageDirectory() + "/weather_icon/";

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1000:
                    try {
                        main_bg1.setImageBitmap(decodeSampledBitmapFromFilePath(ALBUM_PATH+"bg",100,200));
                        main_bg2.setImageDrawable(getResources().getDrawable(R.mipmap.homebgcover));

                        main_bg1.setImageAlpha(1000);
                        main_bg2.setImageAlpha(450);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;
            }
        }
    };

    BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Keyparameter.ACTTION_UPDATAUI)) {
                L.i("--------------收到UI广播--------------");
                try {
//                    message.what = 100;
//                    mhandler.sendMessage(message);
                }catch (Exception e){

                }

            } else if (action.equals(MyServiceBlueTooth.UPDATTEMP)) {
                final String data = intent.getStringExtra("data");
                L.i("---Main-----data-------"+data);
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
                            mtem.setText( myWeatherBean.getTem() + "℃");
                            mhum.setText( myWeatherBean.getHum() + "％");
                            mAirpress.setText( myWeatherBean.getAirpress() + "Kpa");
                        }
                    }
                });
            }else if (action.equals(MyServiceBlueTooth.BING_SUCCESS)){
                BluetoothDevice device=(BluetoothDevice) intent.getParcelableExtra("device");
                bluetoothStatusText.setText("蓝牙已连接");
                if (device!=null){
                    if (device.getName()!=null){
                        addBluetoothText.setText(device.getName().toString());
                    }
                }
            }else if (action.equals(MyServiceBlueTooth.UPDATUI_BINGFAILD)){
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("device");
                DeviceBean unique = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (unique!=null){
                    if (unique.getMac().equals(device.getAddress())){
                        addBluetoothText.setText(unique.getName()+"已断开连接");
                    }
                }
            }else if (action.equals(MyServiceBlueTooth.UPDATUI_BINGSUCCESS)){
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("device");
                DeviceBean unique = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (unique!=null){
                    if (unique.getMac().equals(device.getAddress())){
                        addBluetoothText.setText(unique.getName()+"");
                    }
                }
            }
        }
    };
    //063660
    private View rememberPwd;
    private OptionCircle mTem;
    private BallView mtem;
    private BallView mPM25;
    private BallView mhum;
    private BallView mAirpress;
    private TextView mUserNameText;
    private String s_Pm=null;

    private TextView bluetoothStatusText;
    private TextView addBluetoothText;
    private Switch switch_nodisturb;

    private TextView date;
    private ImageView card_1  ,card_2;
    private static String filesDir = Environment.getExternalStorageDirectory() + "//AMyWeather";
    private ImageView main_bg1,main_bg2;
    private Bitmap mBitmap;
    Dialog mCameraDialog=null;
    private HorizontalScrollView horizontalScrollView;
    private RecyclerView recycle_kline;

    private LinearLayoutManager linearLayoutManager;
    private Map<String,Drawable> dataIcon=new HashMap<>();
    private RecycleViewAdapter recycleViewAdapter;
    private TextView tem_low_hight;
    private TextView new_Weather_text;
    private ImageView new_Weather_img;
    private TextView air_mass;
    private TextView weather_windpower;

    private TextView tem_low_hight2;
    private TextView new_Weather_text2;
    private ImageView new_Weather_img2;
    private TextView air_mass2;
    private TextView weather_windpower2;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestLocation();
        checkWritePermission();
        setLayoutHeight(getScreenHeight(this));
        registerReceiver(mReceiver, getFilter());
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        initView();
        startService(new Intent(MainActivity.this, MyServiceBlueTooth.class));
        mSlidingMenu = new SlidingMenu(MyApp.getContext());
        mSlidingMenu.addIgnoredView(recycle_kline);
        mSlidingMenu.setMode(SlidingMenu.LEFT);     //设置从左弹出/滑出SlidingMenu
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);   //设置占满屏幕
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);    //绑定到哪一个Activity对象
        mSlidingMenu.setMenu(R.layout.slidingmenulayout);                   //设置弹出的SlidingMenu的布局文件
        mSlidingMenu.setBehindOffsetRes(R.dimen.sliding_menu_offset);       //设置SlidingMenu所占的偏移
        mSlidingMenu.setOnOpenListener(new SlidingMenu.OnOpenListener() {//滑动打开监听
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
        matrix.setScale(0.1f, 0.1f);
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
                switch_nodisturb.setChecked(true);
                switch_nodisturb.setTrackDrawable(getResources().getDrawable(R.drawable.on_turn));

            }else {
                switch_nodisturb.setChecked(false);
                switch_nodisturb.setTrackDrawable(getResources().getDrawable(R.drawable.off_turn));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onResume() {
        super.onResume();

        L.i("onResume------MainActivuty");
        if (deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique()==null){
            bluetoothStatusText.setText("蓝牙未连接");
        }else {
            DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
            if (deviceBean.getIsConn()){
                bluetoothStatusText.setText("蓝牙已连接");
                addBluetoothText.setText(deviceBean.getName().toString());
                sendData(deviceBean.getMac(),"01");
            }else {
                bluetoothStatusText.setText("蓝牙未连接");
                addBluetoothText.setText(deviceBean.getName().toString()+"已断开连接");
                Intent intent=new Intent();
                intent.setAction(MyServiceBlueTooth.RECCONECT);
                intent.putExtra("address",deviceBean.getMac());
                sendBroadcast(intent);
            }

        }

        switch_nodisturb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean on) {
                SPUtils.put(MyApp.getContext(),"switch_nodisturb",on);
                DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (deviceBean!=null){
                    if (on){
                        sendData(deviceBean.getMac(),"F2");
                        switch_nodisturb.setTrackDrawable(getResources().getDrawable(R.drawable.on_turn));
                    }else {
                        sendData(deviceBean.getMac(),"F3");
                        switch_nodisturb.setTrackDrawable(getResources().getDrawable(R.drawable.off_turn));
                    }
                }
            }
        });


        Bitmap bit = decodeSampledBitmapFromFilePath(ALBUM_PATH + "icon", 100, 100);
        if (bit!=null){
            image_icoin.setImageBitmap(bit);
        }
        try {
            if (read()!=null){
                main_bg1.setImageBitmap(decodeSampledBitmapFromFilePath(ALBUM_PATH+"bg",100,200));
                main_bg2.setImageDrawable(getResources().getDrawable(R.mipmap.homebgcover));

                main_bg1.setImageAlpha(1000);
                main_bg2.setImageAlpha(450);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
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

        
        main_bg1 = ((ImageView) findViewById(R.id.imageView_bac1));
        main_bg2 = ((ImageView) findViewById(R.id.imageView_bac2));
        mtem = ((BallView) findViewById(R.id.tem));
        mPM25 = ((BallView) findViewById(R.id.pm_2_5));
        mhum = ((BallView) findViewById(R.id.hum));
        mAirpress = ((BallView) findViewById(R.id.airpress));

        mtem.setTextName("温度：");
        mPM25.setTextName("PM2.5:");
        mhum.setTextName("湿度：");
        mAirpress.setTextName("大气压：");

        mtem.setText("--℃");
        mPM25.setText("--μg/m³");
        mhum.setText("--%");
        mAirpress.setText("-- Kpa");

        tem_low_hight = ((TextView) findViewById(R.id.tem_heigh_low));
        new_Weather_text = ((TextView) findViewById(R.id.new_WeatherText));
        new_Weather_img = ((ImageView) findViewById(R.id.weather_img_new));
        air_mass = ((TextView) findViewById(R.id.air_mass));
        weather_windpower = ((TextView) findViewById(R.id.weather_windpower));

        tem_low_hight2 = ((TextView) findViewById(R.id.tem_heigh_low2));
        new_Weather_text2 = ((TextView) findViewById(R.id.new_WeatherText2));
        new_Weather_img2 = ((ImageView) findViewById(R.id.weather_img_new2));
        air_mass2 = ((TextView) findViewById(R.id.air_mass2));
        weather_windpower2 = ((TextView) findViewById(R.id.weather_windpower2));

        mtem.setaddBackground(((BitmapDrawable) this.getResources().getDrawable(R.drawable.hometemp)).getBitmap());
        mPM25.setaddBackground(((BitmapDrawable) this.getResources().getDrawable(R.drawable.homepm25)).getBitmap());
        mhum.setaddBackground(((BitmapDrawable) this.getResources().getDrawable(R.drawable.homehumi)).getBitmap());
        mAirpress.setaddBackground(((BitmapDrawable) this.getResources().getDrawable(R.drawable.homepressure)).getBitmap());

        mtem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("info", "onClick: 点击事件");
                DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (deviceBean!=null){
                    Intent it=new Intent(MainActivity.this,DataLineActivity.class);
                    if (s_Pm!=null){
                        it.putExtra("PM25",s_Pm);
                    }else {
                        it.putExtra("PM25","-1");
                    }
                    startActivity(it);
                }
            }
        });

        date = (TextView)findViewById(R.id.date);
        card_1 = ((ImageView) findViewById(R.id.card_1));
        card_2 = ((ImageView) findViewById(R.id.card_2));
        Picasso.with(MainActivity.this).load(getPic(1)).into(card_1);
        Picasso.with(MainActivity.this).load(getPic(0)).into(card_2);

        card_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(MainActivity.this,ImageActivity.class);

                startActivity(it);
            }
        });
        card_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent(MainActivity.this,ImageActivity.class);

                startActivity(it);
            }
        });
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
                                && (Math.abs(y2 - y1) > 20)) {
                            scrollView_my.fullScroll(ScrollView.FOCUS_UP);
                        } else if (y2 - y1 < 0
                                && (Math.abs(y2 - y1) > 20)) {

                            scrollView_my.fullScroll(ScrollView.FOCUS_DOWN);

                        }

                        break;
                }
                return true;
            }
        });

//        horizontalScrollView = ((HorizontalScrollView) findViewById(R.id.horizontalScrollView));

        recycle_kline = ((RecyclerView) findViewById(R.id.recycle_kline));

        recycle_kline.setHasFixedSize(true);//设置固定大小
        recycle_kline.setItemAnimator(new DefaultItemAnimator());//设置默认动画
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.HORIZONTAL);//设置滚动方向，横向滚动
        recycle_kline.setLayoutManager(linearLayoutManager);

        recycleViewAdapter=new RecycleViewAdapter(MyApp.getContext(),new ArrayList<Weather_Bean.ResultBeanX.ResultBean.HourlyBean>(),new ArrayList<Boolean>());
        recycle_kline.setAdapter(recycleViewAdapter);
        recycleViewAdapter.setmOnItemOnClickListener(new RecycleViewAdapter.OnItemOnClickListener() {
            @Override
            public void onItemClick(View view, int postion) {
                recycleViewAdapter.addIsClick(getDatClick(postion));
            }
        });

        //更换主题背景
        ((ImageView) findViewById(R.id.imageView2)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                makeMyDialog();
            }
        });
    }

    private void makeMyDialog(){
        mCameraDialog = new Dialog(this, R.style.my_dialog);
        LinearLayout root = (LinearLayout) LayoutInflater.from(this).inflate(
                R.layout.layout_camera_control, null);
        root.findViewById(R.id.btn_open_camera).setOnClickListener(btnlistener);
        root.findViewById(R.id.btn_choose_img).setOnClickListener(btnlistener);
        root.findViewById(R.id.btn_cancel).setOnClickListener(btnlistener);
        mCameraDialog.setContentView(root);
        Window dialogWindow = mCameraDialog.getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = -20; // 新位置Y坐标
        lp.width = (int) getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();
        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
        mCameraDialog.show();
    }
    private View.OnClickListener btnlistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_open_camera: // 恢复默认默认
                    deleteFile(ALBUM_PATH+"bg");
                    main_bg1.setImageDrawable(getResources().getDrawable(R.drawable.homedefaultbg));
                    main_bg2.setImageDrawable(getResources().getDrawable(R.mipmap.homebgcover));



                    if (mCameraDialog != null) {
                        mCameraDialog.dismiss();
                    }
                    break;
                // 打开相册
                case R.id.btn_choose_img:
                    checkPermission();
                    if (mCameraDialog != null) {
                        mCameraDialog.dismiss();
                    }
                    break;
                // 取消
                case R.id.btn_cancel:
                    if (mCameraDialog != null) {
                        mCameraDialog.dismiss();
                    }
                    break;
            }
        }
    };
    public void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }else {
            //选择相册
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, IMAGE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        //获取图片路径
        if (requestCode == IMAGE && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
            String[] filePathColumns = {MediaStore.Images.Media.DATA};
            Cursor c = getContentResolver().query(selectedImage, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String imagePath = c.getString(columnIndex);
            showImage(imagePath);
            c.close();
        }
    }
    //加载图片
    private void showImage(String imaePath){

        Bitmap bm = BitmapFactory.decodeFile(imaePath);
        mBitmap=reviewPicRotate(bm,imaePath);
        new Thread(saveFileRunnable).start();
    }
    private Runnable saveFileRunnable = new Runnable(){
        @Override
        public void run() {
            try {
                saveFile(mBitmap, "bg");
                Log.i("jzb", "run: 保存chenggong"+mBitmap.getByteCount());
                Message msg=new Message();
                msg.what=1000;
                mhandler.sendMessage(msg);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

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
                Intent intent = new Intent(MainActivity.this, WebActivity.class);
                intent.putExtra("page",3);
                startActivity(intent);
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
        switch_nodisturb = ((Switch) mSlidingMenu.findViewById(R.id.switch1));
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
        if (SPUtils.contains(MyApp.getContext(), "isLogin")) {
            if ((Boolean) SPUtils.get(MyApp.getContext(), "isLogin", false)) {
                DeviceBean unique = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsConn.eq(true)).build().unique();
                if (unique!=null){
                    startActivity(new Intent(MainActivity.this, AntiLostActivity.class));
                }else {
                    startActivity(new Intent(MainActivity.this,BindingActivity.class));
                }
            }else {
                Toast.makeText(MyApp.getContext(),"你尚未登录",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(MyApp.getContext(),"你尚未登录",Toast.LENGTH_SHORT).show();
        }
    }

    //三角下拉
    public void pull_down(View view) {
        // sanjiao.setVisibility(View.GONE);

        scrollView_my.fullScroll(ScrollView.FOCUS_DOWN);
    }
    //三角上啦
    public void pull_up(View view) {
        scrollView_my.fullScroll(ScrollView.FOCUS_UP);
    }


    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getAddrStr() != null) {
                L.i(bdLocation.getAddrStr() + "====地址====");
                L.i(bdLocation.getPoiList().get(0).getName() + "====地址====");
                locationText.setText(bdLocation.getAddrStr());
                locationText2.setText(bdLocation.getAddrStr());
                getWeatherInfor(bdLocation.getCity());
                //getPM25(bdLocation.getCity());
                mLocationClient.stop();
            } else {
//                Toast.makeText(MyApp.getContext(), "定位失败请检查GPS以及手机网络", Toast.LENGTH_SHORT).show();
            }
        }
    }
    //设置各位置高度
    private void setLayoutHeight(int i) {
        main_relative_title = ((RelativeLayout) findViewById(R.id.main_relative_title));
        main_relative_mid_all = ((RelativeLayout) findViewById(R.id.main_relative_mid_all));
        ((LinearLayout) findViewById(R.id.bluetooth_stu)).getLayoutParams().height = (int) (i * 0.06);
        ((LinearLayout) findViewById(R.id.addBlutooth)).getLayoutParams().height = (int) (i * 0.09);
        ((LinearLayout) findViewById(R.id.qiuqiu)).getLayoutParams().height = (int) (i * 0.6);
        ((RelativeLayout) findViewById(R.id.screen_1_rele_1)).getLayoutParams().height=(int)(i*0.17);
        ((LinearLayout) findViewById(R.id.temp_and_tianqi)).getLayoutParams().height = (int) (i * 0.13);
        sanjiao = ((LinearLayout) findViewById(R.id.sanjiao));
        ((RelativeLayout) findViewById(R.id.second_screen)).getLayoutParams().height = (int) (i * 1);
        ((RelativeLayout) findViewById(R.id.screen_2_rela_2)).getLayoutParams().height=(int)(i*0.2);
        ((LinearLayout) findViewById(R.id.temp_and_tianqi2)).getLayoutParams().height = (int) (i * 0.1);

        ((RelativeLayout) findViewById(R.id.rela_2)).getLayoutParams().height=(int)(i*0.35);
        ((LinearLayout) findViewById(R.id.k_line)).getLayoutParams().height=(int)(i*0.35);

        ((LinearLayout) findViewById(R.id.card)).getLayoutParams().height=(int)(i*0.35);

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
                            mPM25.setText(""+response.getData().get(0).get_$PM25231());
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
                            Toast.makeText(getApplication(), "登录成功", Toast.LENGTH_SHORT).show();
                        } else if (response.getRtn_code()==Keyparameter.CODE_ERROR_SERVICE){
                            Toast.makeText(getApplication(), Keyparameter.CODE_ERROR_SERVICE_STR, Toast.LENGTH_SHORT).show();
                        }else if (response.getRtn_code()==Keyparameter.CODE_PWD_PUT_ERROR){
                            Toast.makeText(getApplication(), Keyparameter.CODE_PWD_ERROR_STR, Toast.LENGTH_SHORT).show();
                        }else if (response.getRtn_code()==Keyparameter.CODE_USER_PWD_ERROR){
                            Toast.makeText(getApplication(), Keyparameter.CODE_USER_PWD_ERROR, Toast.LENGTH_SHORT).show();
                        }else if (response.getRtn_code()==Keyparameter.CODE_USER_ERROR){
                            Toast.makeText(getApplication(), Keyparameter.CODE_USER_ERROR_STR, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(getApplication(), "数据请求出现错误,请稍后重试", Toast.LENGTH_SHORT).show();
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
                        tem_low_hight.setText(result.getResult().getTemplow()+"℃"+"/"+result.getResult().getTemphigh()+"℃");
                        new_Weather_text.setText(result.getResult().getWeather());
                        weather_windpower.setText(result.getResult().getWinddirect()+result.getResult().getWindpower());
                        air_mass.setText(result.getResult().getAqi().getQuality());


                        tem_low_hight2.setText(result.getResult().getTemplow()+"℃"+"/"+result.getResult().getTemphigh()+"℃");
                        new_Weather_text2.setText(result.getResult().getWeather());
                        weather_windpower2.setText(result.getResult().getWinddirect()+result.getResult().getWindpower());
                        String img = "weather"+result.getResult().getImg();
                        new_Weather_img.setImageResource(getResources().getIdentifier(img,"mipmap",getPackageName()));
                        new_Weather_img2.setImageResource(getResources().getIdentifier(img,"mipmap",getPackageName()));


                        int i = Integer.parseInt(result.getResult().getAqi().getPm2_5());
                        if (i<=50){
                           air_mass.setText("优");
                           air_mass.setBackgroundResource(getResources().getIdentifier("you","mipmap",getPackageName()));
                            air_mass2.setText("优");
                            air_mass2.setBackgroundResource(getResources().getIdentifier("you","mipmap",getPackageName()));

                        }else if (i>50&&i<100){
                            air_mass.setText("良");
                            air_mass.setBackgroundResource(getResources().getIdentifier("liang","mipmap",getPackageName()));
                            air_mass2.setText("良");
                            air_mass2.setBackgroundResource(getResources().getIdentifier("liang","mipmap",getPackageName()));

                        }else if (i>100&&i<150){
                            air_mass.setText("轻度污染");
                            air_mass.setBackgroundResource(getResources().getIdentifier("qingdu","mipmap",getPackageName()));
                            air_mass2.setText("轻度污染");
                            air_mass2.setBackgroundResource(getResources().getIdentifier("qingdu","mipmap",getPackageName()));

                        }else if (i>150&&i<200){
                            air_mass.setText("中度污染");
                            air_mass.setBackgroundResource(getResources().getIdentifier("zhongdu","mipmap",getPackageName()));
                            air_mass2.setText("中度污染");
                            air_mass2.setBackgroundResource(getResources().getIdentifier("zhongdu","mipmap",getPackageName()));

                        } else if (i>200&&i<300){
                            air_mass.setText("重度污染");
                            air_mass.setBackgroundResource(getResources().getIdentifier("zhongdubad","mipmap",getPackageName()));
                            air_mass2.setText("重度污染");
                            air_mass2.setBackgroundResource(getResources().getIdentifier("zhongdubad","mipmap",getPackageName()));

                        } else if (i>300&&i<500){
                            air_mass.setText("严重污染");
                            air_mass.setBackgroundResource(getResources().getIdentifier("yanzhong","mipmap",getPackageName()));
                            air_mass2.setText("严重污染");
                            air_mass2.setBackgroundResource(getResources().getIdentifier("yanzhong","mipmap",getPackageName()));

                        }

                        mPM25.setText(""+response.getResult().getResult().getAqi().getIpm2_5()+"μg/m³");
                        s_Pm=""+response.getResult().getResult().getAqi().getPm2_5();

                       //获取到天气数据
                        recycleViewAdapter.addData(response.getResult().getResult().getHourly());

                    }
                });
    }

    //更新UI广播
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Keyparameter.ACTTION_UPDATAUI);
        intentFilter.addAction(MyServiceBlueTooth.BING_SUCCESS);
        intentFilter.addAction(MyServiceBlueTooth.UPDATTEMP);

        intentFilter.addAction(MyServiceBlueTooth.UPDATUI_BINGSUCCESS);
        intentFilter.addAction(MyServiceBlueTooth.UPDATUI_BINGFAILD);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void sendData(String address, String data){
        Intent intent=new Intent();
        intent.setAction(MyServiceBlueTooth.SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);
    }

    private List<Point> getData() {
        List<Point> pointList = new ArrayList<>();
        for (int i=1;i<=24;i++){
            pointList.add(new Point(100*i,(int)(new Random().nextInt(10)+30)*4));
        }
        return pointList;
    }
    private String getPic(int i){
        String url = "http://123.207.173.111/PWS/images/calendar/";
        if (i == 0){
            return url+getDate()+"L.jpg";
        }
        else{
            return url+getDate()+"R.jpg";
        }

    }
    private String getDate(){
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar c = Calendar.getInstance();
//        Date date = c.getTime();
        String m = "", d = "";
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        if (month <= 9){
            m = "0" + month;
        }else{
            m = month + "";
        }
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (day <= 9){
            d = "0" + day;
        }else{
            d = day + "";
        }
        int week_index = c.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
//        return weeks[week_index];
        date.setText(month+"月"+day+"日" + "  " + weeks[week_index]);
        return year+"-"+m+"-"+d;
    }


    private void requestLocation() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
//            Toast.makeText(this, "获取您的位置信息", Toast.LENGTH_LONG).show();
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
//                    Toast.makeText(getApplicationContext(), "用户授予获取位置信息的权限", Toast.LENGTH_LONG).show();
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
//                    Toast.makeText(getApplicationContext(), "用户拒绝了获取位置信息的权限", Toast.LENGTH_LONG).show();
                }
            }, new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 101) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE) && grantResults[0]
                    == PackageManager.PERMISSION_GRANTED) {
                //用户同意
                checkWritePermission();
            } else {
                //用户不同意
                Toast.makeText(getApplicationContext(),"你未授予读写权限，某些功能将无法实现",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void checkWritePermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int checkSelfPermission = checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            Log.i("main", "checkWritePermission: 读写权限申请"+checkSelfPermission+"--"+PackageManager.PERMISSION_DENIED);
            if (checkSelfPermission == PackageManager.PERMISSION_DENIED) {
                Log.i("main", "checkWritePermission: 读写权限申请");
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            }
        }
        File appDir = new File(filesDir);
        if (!appDir.exists()) {
            boolean isSuccess = appDir.mkdirs();
            Log.d("isSuccess:" ,"----------0------------------"+isSuccess);
        }
    }
    /**
     * 保存文件
     * @param bm
     * @param fileName
     * @throws IOException
     */
    public void saveFile(Bitmap bm, String fileName) throws IOException {
        File dirFile = new File(ALBUM_PATH);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        File myCaptureFile = new File(ALBUM_PATH + fileName);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
        bm.compress(Bitmap.CompressFormat.JPEG, 10, bos);
        Uri uri = Uri.fromFile(myCaptureFile);
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        bos.flush();
        bos.close();
    }

    /**
     * 读图片
     * @return
     * @throws FileNotFoundException
     */
    private Bitmap read() throws FileNotFoundException {
        Bitmap bm=null;
        Matrix matrix = new Matrix();
        matrix.setScale(0.1f, 0.1f);
        Bitmap bitmap = BitmapFactory.decodeFile(ALBUM_PATH+"bg",null);
        if (bitmap!=null){

            bm = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        if (bm==null){
            Log.i("jzb", "read: 返回为null");
        }
        return bm;
    }

    /**
     * 删除文件
     * @param filePath
     * @return
     */
    public boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }
    /**
     * 获取图片文件的信息，是否旋转了90度，如果是则反转
     * @param bitmap 需要旋转的图片
     * @param path   图片的路径
     */
    public static Bitmap reviewPicRotate(Bitmap bitmap,String path){
        int degree = getPicRotate(path);
        if(degree!=0){
            Matrix m = new Matrix();
            int width = bitmap.getWidth();
            int height = bitmap.getHeight();
            m.setRotate(degree); // 旋转angle度
            m.setScale(0.1f, 0.2f);
            bitmap = Bitmap.createBitmap(bitmap,0 , 0, width, height,m, true);// 从新生成图片
        }
        return bitmap;
    }

    /**
     * 读取图片文件旋转的角度
     * @param path 图片绝对路径
     * @return 图片旋转的角度
     */
    public static int getPicRotate(String path) {
        int degree  = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 图片改小
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // 源图片的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // 计算出实际宽高和目标宽高的比率
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            // 选择宽和高中最小的比率作为inSampleSize的值，这样可以保证最终图片的宽和高
            // 一定都会大于等于目标的宽和高。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromFilePath(String imagePath,
                                                         int reqWidth, int reqHeight) {
        // 第一次解析将inJustDecodeBounds设置为true，来获取图片大小
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(imagePath, options);
        // 调用上面定义的方法计算inSampleSize值
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        // 使用获取到的inSampleSize值再次解析图片
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(imagePath,options);
    }
    public List<Boolean> getDatClick(int postion){
        isClicks.clear();
        for (int i=0;i<24;i++){
            isClicks.add(false);
        }
        isClicks.set(postion,true);
        return isClicks;
    }
}
