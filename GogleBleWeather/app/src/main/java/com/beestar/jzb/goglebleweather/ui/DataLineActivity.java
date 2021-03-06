package com.beestar.jzb.goglebleweather.ui;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.view.KLine;
import com.beestar.jzb.goglebleweather.view.KLineView;
import com.beestar.jzb.goglebleweather.view.KLineView2;
import com.beestar.jzb.goglebleweather.view.KLineView_AirPress;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class DataLineActivity extends BaseActivity implements View.OnClickListener {


    private ImageView mImageView3;
    private TextView mAddressLocation2;
    /**
     * 温度
     */
    private RadioButton mRadioTemp;
    /**
     * 湿度
     */
    private RadioButton mRadioHum;
    /**
     * 大气压
     */
    private RadioButton mRadioPress;
    /**
     * PM2.5
     */
    private RadioButton mRadioPm;
    private KLine mKLine1;
    private KLine mKLine2;
    private KLine mKLine3;
    private KLine mKLine4;
    private RadioGroup rp;
    private List<Point> temdata;
    private List<Point> humdata;
    private List<Point> airPressdata;
    private List<Point> pmdata=new ArrayList<>();
    private int count=0;
    public LocationClient mLocationClient = null;


    public BDAbstractLocationListener myListener = new MyLocationListener();
    private class MyLocationListener extends BDAbstractLocationListener {
        @Override
        public void onReceiveLocation(BDLocation bdLocation) {
            if (bdLocation.getAddrStr() != null) {
                L.i(bdLocation.getAddrStr() + "====地址====");
                L.i(bdLocation.getPoiList().get(0).getName() + "====地址====");
                mAddressLocation2.setText(bdLocation.getAddrStr() + bdLocation.getPoiList().get(0).getName());
                mLocationClient.stop();
            } else {
                Toast.makeText(MyApp.getContext(), "定位失败请检查GPS以及手机网络", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 100:
                    mKLine1.setShape(1);
                    mKLine2.setShape(2);
                    mKLine3.setShape(3);
                    mKLine4.setShape(4);
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_line);
        String pm25 = getIntent().getStringExtra("PM25");
        Log.i("dataline", "onCreate: pM25:"+pm25);
        if (pm25.equals("-1")){

        }else {
            int i = Integer.parseInt(pm25);
            mPMData(i);
        }
        requestLocation();
        //requestLocation2();
        initView();
        DeviceBeanDao deviceBeanDao=MyApp.getContext().getDaoSession().getDeviceBeanDao();
        DeviceBean unique = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
        if (unique!=null){
            sendData(unique.getMac(),"FD");
            temdata=new ArrayList<>();
            humdata=new ArrayList<>();
            airPressdata=new ArrayList<>();
            count=0;
        }

        mKLine1.setColorLine(Color.rgb(236,103,153));
        mKLine2.setColorLine(Color.rgb(55,190,240));
        mKLine3.setColorLine(Color.rgb(242,159,62));
        mKLine4.setColorLine(Color.rgb(153,71,255));

        mKLine1.setBcAlpha(150);
        mKLine2.setBcAlpha(80);
        mKLine3.setBcAlpha(80);
        mKLine4.setBcAlpha(80);

        mKLine1.setWidthLine(5);
        mKLine2.setWidthLine(2);
        mKLine3.setWidthLine(2);
        mKLine4.setWidthLine(2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    Message msg=new Message();
                    msg.what=100;
                    handler.sendMessage(msg);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
        /**
         * 定位
         */
        mLocationClient = new LocationClient(MyApp.getContext());
        //声明LocationClient类
        mLocationClient.registerLocationListener(myListener);
        //注册监听函数
        initLocation();
        mLocationClient.start();
    }
    private void requestLocation2() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.READ_PHONE_STATE)) {
//            Toast.makeText(this, "获取您的设备信息", Toast.LENGTH_LONG).show();
            L.i("-----------111-----------------------------------------------");
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
//                    Toast.makeText(getApplicationContext(), "用户授予获取设备信息的权限", Toast.LENGTH_LONG).show();
                    L.i("--------------------------11--------------------------------");
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
//                    Toast.makeText(getApplicationContext(), "用户拒绝了获取设备信息的权限", Toast.LENGTH_LONG).show();
                    L.i("-------------------1---------------------------------------");
                }
            }, new String[]{Manifest.permission.READ_PHONE_STATE});
        }
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
    private void initView() {
        mImageView3 = (ImageView) findViewById(R.id.imageView3);
        mImageView3.setOnClickListener(this);
        mAddressLocation2 = (TextView) findViewById(R.id.addressLocation2);
        mRadioTemp = (RadioButton) findViewById(R.id.radio_temp);
        mRadioTemp.setOnClickListener(this);
        mRadioHum = (RadioButton) findViewById(R.id.radio_hum);
        mRadioHum.setOnClickListener(this);
        mRadioPress = (RadioButton) findViewById(R.id.radio_press);
        mRadioPress.setOnClickListener(this);
        mRadioPm = (RadioButton) findViewById(R.id.radio_pm);
        mRadioPm.setOnClickListener(this);
        mKLine1 = (KLine) findViewById(R.id.k_line1);
        mKLine2 = (KLine) findViewById(R.id.k_line2);
        mKLine3 = (KLine) findViewById(R.id.k_line3);
        mKLine4 = (KLine) findViewById(R.id.k_line4);



//            mKLine1.setAlpha();
//            mKLine2.setAlpha();
//            mKLine3.setAlpha();
//            mKLine4.setAlpha();




        rp = ((RadioGroup) findViewById(R.id.rp));
        rp.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                switch (i) {
                    case R.id.radio_temp:
                        mKLine1.setBcAlpha(150);
                        mKLine2.setBcAlpha(80);
                        mKLine3.setBcAlpha(80);
                        mKLine4.setBcAlpha(80);

                        mKLine1.setWidthLine(5);
                        mKLine2.setWidthLine(2);
                        mKLine3.setWidthLine(2);
                        mKLine4.setWidthLine(2);
                        break;
                    case R.id.radio_hum:
                        mKLine1.setBcAlpha(80);
                        mKLine2.setBcAlpha(150);
                        mKLine3.setBcAlpha(80);
                        mKLine4.setBcAlpha(80);

                        mKLine1.setWidthLine(2);
                        mKLine2.setWidthLine(5);
                        mKLine3.setWidthLine(2);
                        mKLine4.setWidthLine(2);
                        break;
                    case R.id.radio_press:
                        mKLine1.setBcAlpha(80);
                        mKLine2.setBcAlpha(80);
                        mKLine3.setBcAlpha(150);
                        mKLine4.setBcAlpha(80);

                        mKLine1.setWidthLine(2);
                        mKLine2.setWidthLine(2);
                        mKLine3.setWidthLine(5);
                        mKLine4.setWidthLine(2);
                        break;
                    case R.id.radio_pm:
                        mKLine1.setBcAlpha(80);
                        mKLine2.setBcAlpha(80);
                        mKLine3.setBcAlpha(80);
                        mKLine4.setBcAlpha(150);

                        mKLine1.setWidthLine(2);
                        mKLine2.setWidthLine(2);
                        mKLine3.setWidthLine(2);
                        mKLine4.setWidthLine(5);
                        break;
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver,getFilter());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MyServiceBlueTooth.LINEDATA)){
                //Q +00 00000R
                String linedata=intent.getStringExtra("linedata");
                if (linedata!=null){

                    if (linedata.substring(1,2).equals("+")){
                        temdata.add(new Point(count*100,Integer.parseInt(linedata.substring(2,4))));
                    }else {
                        temdata.add(new Point(count*100,Integer.parseInt(linedata.substring(1,4))));
                    }
                    Log.i("info", "onReceive:湿度和气压"+linedata.substring(4,6)+"__"+linedata.substring(6,9));
                    humdata.add(new Point(count*100,Integer.parseInt(linedata.substring(4,6))));
                    airPressdata.add(new Point(count*100,Integer.parseInt(linedata.substring(6,9))));
                    count++;
                    if (count==24){
                        mKLine1.setDatas(temdata);
                        mKLine2.setDatas(humdata);
                        mKLine3.setDatas(airPressdata);
                        mKLine4.setDatas(pmdata);
                    }
                }
            }
        }
    };
    //更新数据广播
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyServiceBlueTooth.LINEDATA);
        return intentFilter;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imageView3:
                finish();
                break;
            case R.id.radio_temp:
                break;
            case R.id.radio_hum:
                break;
            case R.id.radio_press:
                break;
            case R.id.radio_pm:
                break;
        }
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
     * 获取当前时间 时
     * @return
     */
    public int getHour(){
        SimpleDateFormat sDateFormat = new SimpleDateFormat("HH");
        String date = sDateFormat.format(new java.util.Date());
        int hour = Integer.parseInt(date);
        return hour;
    }
    public List<String> titleData(){
        List<String> data=new ArrayList<>();
        int date = getHour();
        if (date!=0){
            for (int i=date+1;i<24;i++){
                Log.i("info", "onCreate:for1 "+i);
                data.add(i+"");
            }
            for (int j=0;j<date+1;j++){
                Log.i("info", "onCreate:for2 "+j);
                data.add(j+"");
            }
        }
        return data;
    }

    public void mPMData(int mPm){

        for (int i=0;i<=24;i++){
            pmdata.add(new Point(i*100,mPm));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void sendData(String address, String data){
        Intent intent=new Intent();
        intent.setAction(MyServiceBlueTooth.SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);
    }
}
