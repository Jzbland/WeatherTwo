package com.beestar.jzb.goglebleweather.ui;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.DialogFragment.MyFragmentEditBlueToothDialog;
import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;

import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.adapter.MyDeviceListViewAdapter;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.bean.MyWeatherBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.ui.haveLogin.NoDisturbActivity;
import com.beestar.jzb.goglebleweather.ui.haveLogin.SetMusicActivity;
import com.beestar.jzb.goglebleweather.ui.setting.UnbindActivity;
import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.utils.SPUtils;
import com.beestar.jzb.goglebleweather.view.MyListView;
import com.zhy.android.percent.support.PercentRelativeLayout;

import java.util.ArrayList;
import java.util.List;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class AntiLostActivity extends BaseActivity implements View.OnClickListener ,View.OnTouchListener,MyFragmentEditBlueToothDialog.OnEditBlueToothDialogListener{

    private ImageView mBack;
    private ImageButton mAdd;
    private ImageView mNotdisturbmeImage;
    /**
     * 定时勿扰
     */
    private TextView mNotdisturbmeText;
    private LinearLayout mNotdisturbme;
    private ImageView mRingImage;
    /**
     * 响铃
     */
    private TextView mRingText;
    private LinearLayout mRing;
    private ImageView mUnbindImage;
    /**
     * 解绑
     */
    private TextView mUnbindText;
    private LinearLayout mUnbind;
    private ImageView mBlueEditImage;
    /**
     * 蓝牙编辑
     */
    private TextView mBlueEditText;
    private LinearLayout mBlueEdit;
    /**
     * 蓝牙已连接
     */
    private TextView mBlueStatus;
    /**
     * 电量65%
     */
    private TextView mBatteryStatus;
    /**
     * 道具库
     */
    private TextView mDeviceName;
    /**
     * 钥匙
     */
    private TextView mGoodsName;
    /**
     * 看管中
     */
    private TextView mGoodsStatus;
    private PercentRelativeLayout mUnlostInfor;
    /**
     * 呼叫
     */
    private Button mCallButton;
    private MyListView mDeviceList;
    private MyDeviceListViewAdapter myDeviceListViewAdapter;
    private DeviceBeanDao deviceBeanDao;
    boolean flagcall=false;
    List<DeviceBean> list;
    /**
     * 修改名字弹出框
     */
    private MyFragmentEditBlueToothDialog myFragmentEditBlueToothDialog;
    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==200){
                L.i("------antilost--修改成功-");
            }else if (msg.what==100){
                L.i("------antilost--更新电量-");
            }
        }
    };
    BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
        @Override
        public void onReceive(Context context, Intent intent) {
            Message message=new Message();
            if (intent.getAction().equals(MyServiceBlueTooth.UPDATTEMP)) {

                final String data = intent.getStringExtra("data");
                L.i("Antilost数据--"+data);
                if (data == null) {
                    return;
                }
                message.what=100;
                mHandler.sendMessage(message);
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
                            mBatteryStatus.setText("电量："+myWeatherBean.getPower().toString()+"%");
                        }
                    }
                });
            }else if (intent.getAction().equals(MyServiceBlueTooth.UPDATA_NAMESUCCESS)){
                message.what=200;
                mHandler.sendMessage(message);
            }else if (intent.getAction().equals(MyServiceBlueTooth.UPDATUI_BINGFAILD)){
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("device");
                DeviceBean unique = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (unique!=null){
                    if (device.getAddress().equals(unique.getMac())){
                        mBlueStatus.setText("蓝牙未连接");
                        mGoodsStatus.setText("已断开");
                        List<DeviceBean> list = deviceBeanDao.queryBuilder().list();
                        myDeviceListViewAdapter.cleardata();
                        myDeviceListViewAdapter.adddata(list);
                    }
                }


            }else if (intent.getAction().equals(MyServiceBlueTooth.UPDATUI_BINGSUCCESS)){
                Log.d("info", "onReceive: 连接成功更新界面2");
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("device");
                DeviceBean unique = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (unique!=null){
                    if (device.getAddress().equals(unique.getMac())){
                        mBlueStatus.setText("蓝牙已连接");
                        mGoodsStatus.setText("已连接");
                        unique.setSecondName("钱包");
                        deviceBeanDao.update(unique);
                        List<DeviceBean> list = deviceBeanDao.queryBuilder().list();
                        myDeviceListViewAdapter.cleardata();
                        myDeviceListViewAdapter.adddata(list);
                    }
                }else {
                    Log.d("info", "onReceive: 连接成功更新界面2   kong");
                }
            }
        }
    };
    private TextView eemptyView;
    private ImageView antilost_call_img;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_lost);
        registerReceiver(mReceiver,getFilter());

        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        initView();
        setAndCheckDevice();
        mDeviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                myDeviceListViewAdapter.select(i);
                DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.Name.eq(list.get(i).getName())).build().unique();
                DeviceBean deviceBean2 = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (deviceBean2==null){

                }else {
                    deviceBean2.setIsChoose(false);
                    deviceBeanDao.update(deviceBean2);
                }
                if (deviceBean==null){

                }else {
                    deviceBean.setIsChoose(true);
                    deviceBeanDao.update(deviceBean);
                }
            }
        });
    }

    private void setAndCheckDevice() {

        myDeviceListViewAdapter = new MyDeviceListViewAdapter(getApplicationContext(),new ArrayList<DeviceBean>());
        mDeviceList.setAdapter(myDeviceListViewAdapter);
//        myDeviceListViewAdapter.setOnItemDeleteClickListener(new MyDeviceListViewAdapter.onItemconnListener() {
//            @Override
//            public void onReconnClick(int i) {
//                startActivity(new Intent(AntiLostActivity.this,BindingActivity.class));
//            }
//        });


    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mAdd = (ImageButton) findViewById(R.id.add);
        mNotdisturbmeImage = (ImageView) findViewById(R.id.notdisturbme_image);
        mNotdisturbmeText = (TextView) findViewById(R.id.notdisturbme_text);
        mNotdisturbme = (LinearLayout) findViewById(R.id.notdisturbme);
        mNotdisturbme.setOnClickListener(this);
        mNotdisturbme.setOnTouchListener(this);
        mRingImage = (ImageView) findViewById(R.id.ring_image);
        mRingText = (TextView) findViewById(R.id.ring_text);
        mRing = (LinearLayout) findViewById(R.id.ring_);
        mRing.setOnClickListener(this);
        mRing.setOnTouchListener(this);
        mUnbindImage = (ImageView) findViewById(R.id.unbind_image);
        mUnbindText = (TextView) findViewById(R.id.unbind_text);
        mUnbind = (LinearLayout) findViewById(R.id.unbind_);
        mUnbind.setOnClickListener(this);
        mUnbind.setOnTouchListener(this);
        mBlueEditImage = (ImageView) findViewById(R.id.blue_edit_image);
        mBlueEditText = (TextView) findViewById(R.id.blue_edit_text);
        mBlueEdit = (LinearLayout) findViewById(R.id.blue_edit_);
        mBlueEdit.setOnClickListener(this);
        mBlueEdit.setOnTouchListener(this);
        mBlueStatus = (TextView) findViewById(R.id.blue_status);//连接状态
        mBatteryStatus = (TextView) findViewById(R.id.battery_status);//电池电量
        mDeviceName = (TextView) findViewById(R.id.device_Name);//device
        mGoodsName = (TextView) findViewById(R.id.goods_Name);// 钥匙
        mGoodsStatus = (TextView) findViewById(R.id.goods_status);//看管中
        mUnlostInfor = (PercentRelativeLayout) findViewById(R.id.unlost_infor);
        mCallButton = (Button) findViewById(R.id.call_button);
        mCallButton.setOnClickListener(this);
        mDeviceList = (MyListView) findViewById(R.id.device_list);
        mAdd.setOnClickListener(this);
        eemptyView = ((TextView) findViewById(R.id.emptyView));
        mDeviceList.setEmptyView(eemptyView);
        antilost_call_img = ((ImageView) findViewById(R.id.antilost_call_img));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.notdisturbme:
                startActivity(new Intent(AntiLostActivity.this, NoDisturbActivity.class));
                break;
            case R.id.ring_:
                startActivity(new Intent(AntiLostActivity.this,SetMusicActivity.class));
                break;
            case R.id.unbind_:
                DeviceBean deviceBean_unbind = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (deviceBean_unbind!=null){
                    startActivity(new Intent(AntiLostActivity.this,UnbindActivity.class));
                }else {
                    Toast.makeText(MyApp.getContext(),"当前无设备连接",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.blue_edit_:
                DeviceBean deviceBean2 = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (deviceBean2!=null){
                    showDialogChangName();
                }

                break;
            case R.id.call_button:
                DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (deviceBean==null){

                }else {
                    if (!flagcall){
                        sendData(deviceBean.getMac(),"AA");
                        mCallButton.setText("取消");
                        antilost_call_img.setVisibility(View.VISIBLE);
                        flagcall=true ;
                    }else {
                        sendData(deviceBean.getMac(),"AB");
                        mCallButton.setText("呼叫");
                        antilost_call_img.setVisibility(View.GONE);
                        flagcall=false ;
                    }

                }
                break;
            case R.id.add:
                startActivity(new Intent(AntiLostActivity.this,BindingActivity.class));
                break;
        }
    }
//13851729204
    /**
     * 按下 抬起 按钮UI变化
     * @param view
     * @param motionEvent
     * @return
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId()){
            case R.id.notdisturbme:
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    mNotdisturbmeImage.setImageResource(R.mipmap.unlost_uncareme);
                    mNotdisturbmeText.setTextColor(getResources().getColor(R.color.bindtext));
                    mNotdisturbme.setBackgroundColor(Color.WHITE);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){

                    mNotdisturbmeImage.setImageResource(R.mipmap.unlost_uncaremepress);
                    mNotdisturbmeText.setTextColor(Color.WHITE);
                    mNotdisturbme.setBackgroundColor(getResources().getColor(R.color.textcolor));
                }
                break;
            case R.id.ring_:
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    mRingImage.setImageResource(R.mipmap.unlost_music);
                    mRingText.setTextColor(getResources().getColor(R.color.bindtext));
                    mRing.setBackgroundColor(Color.WHITE);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    mRingImage.setImageResource(R.mipmap.unlost_musicpress);
                    mRingText.setTextColor(Color.WHITE);
                    mRing.setBackgroundColor(getResources().getColor(R.color.textcolor));
                }
                break;
            case R.id.unbind_:
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    mUnbindImage.setImageResource(R.mipmap.unlost_cc);
                    mUnbindText.setTextColor(getResources().getColor(R.color.bindtext));
                    mUnbind.setBackgroundColor(Color.WHITE);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    mUnbindImage.setImageResource(R.mipmap.unlost_ccpress);
                    mUnbindText.setTextColor(Color.WHITE);
                    mUnbind.setBackgroundColor(getResources().getColor(R.color.textcolor));

                }
                break;
            case R.id.blue_edit_:
                if(motionEvent.getAction() == MotionEvent.ACTION_UP){
                    mBlueEditImage.setImageResource(R.mipmap.unlost_edit);
                    mBlueEditText.setTextColor(getResources().getColor(R.color.bindtext));
                    mBlueEdit.setBackgroundColor(Color.WHITE);
                }
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    mBlueEditImage.setImageResource(R.mipmap.unlost_editpress);
                    mBlueEditText.setTextColor(Color.WHITE);
                    mBlueEdit.setBackgroundColor(getResources().getColor(R.color.textcolor));
                }
                break;
        }
        return false;

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void sendData(String address, String data){
        Intent intent=new Intent();
        intent.setAction(MyServiceBlueTooth.SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void updataName(String address, String name){
        Intent intent=new Intent();
        intent.setAction(MyServiceBlueTooth.UPDATA_NAME);
//        intent.setAction(MyServiceBlueTooth.UPDATA_NAMESUCCESS);
        intent.putExtra("address",address);
        intent.putExtra("data",name);
        sendBroadcast(intent);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onResume() {
        super.onResume();
        L.i("onResumeAntiLost");
        list = deviceBeanDao.queryBuilder().list();
        DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
        if (deviceBean!=null){
            if (deviceBean.getIsConn()){
                mGoodsName.setText(deviceBean.getSecondName());
                mGoodsStatus.setText("看管中");
                sendData(deviceBean.getMac(),"01");
            }else {
                mGoodsName.setText(deviceBean.getSecondName());
                mGoodsStatus.setText("已断开");
            }

        }else {
            mGoodsName.setText("");
            mGoodsStatus.setText("");
        }
        L.i("------数据长度----"+list.size());
        if (list.size()>0){
            mBlueStatus.setText("蓝牙已连接");
        }else {
            mBlueStatus.setText("蓝牙未连接");
        }
        myDeviceListViewAdapter.cleardata();
        myDeviceListViewAdapter.adddata(list);
        if (SPUtils.contains(MyApp.getContext(),"name")){
            mDeviceName.setText((String)SPUtils.get(MyApp.getContext(),"name","")+"的道具库");
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyServiceBlueTooth.MY_DATA);
        intentFilter.addAction(MyServiceBlueTooth.UPDATA_NAMESUCCESS);

        intentFilter.addAction(MyServiceBlueTooth.UPDATUI_BINGFAILD);
        intentFilter.addAction(MyServiceBlueTooth.UPDATUI_BINGSUCCESS);
        intentFilter.addAction(MyServiceBlueTooth.UPDATTEMP);
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
    public void showDialogChangName(){
        myFragmentEditBlueToothDialog=new MyFragmentEditBlueToothDialog();
        myFragmentEditBlueToothDialog.show(getFragmentManager(),"changeName");
    }

    /**
     * 修改BlueName
     * @param blueName
     * @param goodName
     */
    @Override
    public void onEditBlueTooThDialogNext(String blueName, String goodName) {
        DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
        if (!(deviceBean.getName().equals(blueName))){
            sendData(deviceBean.getMac(),"F4");
            updataName(deviceBean.getMac(),blueName);
        }
        if (goodName!=null){
            deviceBean.setSecondName(goodName);
            deviceBean.setName(blueName);
            deviceBeanDao.update(deviceBean);
            list=deviceBeanDao.queryBuilder().list();
            myDeviceListViewAdapter.cleardata();
            myDeviceListViewAdapter.adddata(list);
        }
    }
}
