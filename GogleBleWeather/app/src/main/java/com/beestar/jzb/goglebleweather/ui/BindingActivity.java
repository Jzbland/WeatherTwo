package com.beestar.jzb.goglebleweather.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.DialogFragment.MyFragementDialog;
import com.beestar.jzb.goglebleweather.DialogFragment.MyFragmentAddInforDilog;
import com.beestar.jzb.goglebleweather.DialogFragment.MyFragmentConnDialog_False;
import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.BluetoothLeService;
import com.beestar.jzb.goglebleweather.Service.MyBlueToothInterface;
import com.beestar.jzb.goglebleweather.Service.MyBluetoothScan;
import com.beestar.jzb.goglebleweather.adapter.MyDeviceListAdapter;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.utils.Keyparameter;
import com.beestar.jzb.goglebleweather.utils.L;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BindingActivity extends BaseActivity implements MyFragementDialog.OnMyFragmentDialogLister,MyFragmentConnDialog_False.OnMyFragmentConnFalseLister
,MyFragmentAddInforDilog.OnMyFragmentAddInfroListener
{

    private static final int REQUEST_ENABLE_BT = 1;
    private static final long SCAN_PERIOD = 10000;
    private static boolean mScanning=true;
    BluetoothManager bluetoothManager;
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    Handler mHandler=new Handler();
    private RecyclerView dev_list;
    private List<DeviceBean> list=new ArrayList<>();
    private MyDeviceListAdapter myDeviceListAdapter;
    private MyBluetoothScan myBluetoothScan= new MyBluetoothScan();
    private ServiceConnection connection;
    private BluetoothLeService mService;

    //写数据
    private BluetoothGattCharacteristic writecharacteristic;
    private BluetoothGattService writenotyGattService;;
    //读数据
    private BluetoothGattCharacteristic readCharacteristic;
    private BluetoothGattService readMnotyGattService;
    private BluetoothGattCharacteristic mNotifyCharacteristic;

    private String m_szImei;
    private String mac;
    private DeviceBeanDao deviceBeanDao;
    private MyFragementDialog myFragementDialog;
    private MyFragmentAddInforDilog myFragmentAddInforDilog;
    private  MyFragmentConnDialog_False connDialog_false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        initTelphoneMga();
        bindLocal();
        initView();
        initblebool();
        boolBluetooth();
        OnSlcanDevice();
        OnConnectAndService();
        slcan_btn();
    }

    private void initTelphoneMga() {
        BluetoothAdapter mBlueth= BluetoothAdapter.getDefaultAdapter();
         m_szImei= mBlueth.getAddress();
        L.i(m_szImei.toString().length()+"----------dizhi------"+m_szImei);

        while (m_szImei.length() != 32){
            m_szImei += "0";
        }
        m_szImei="f1"+m_szImei+"1f";
    }

    private void sendDataToBle(String address,String data){
        if (writecharacteristic!=null&&readCharacteristic!=null){
            final int charaProp = writecharacteristic.getProperties();
            //发送读数据的通知
            mService.setCharacteristicNotification(address,readCharacteristic, true);
            //如果该char可写
            if ((charaProp | BluetoothGattCharacteristic.PROPERTY_READ) > 0) {
                // If there is an active notification on a characteristic, clear
                // it first so it doesn't update the data field on the user interface.
                if (mNotifyCharacteristic != null) {
                    mService.setCharacteristicNotification(address, mNotifyCharacteristic, false);
                    mNotifyCharacteristic = null;
                }
                mService.readCharacteristic(address,writecharacteristic);
                //发送数据
                byte[] value = new byte[20];
                value[0] = (byte) 0x00;
                writecharacteristic.setValue(data.getBytes());
                mService.writeCharacteristic(address,writecharacteristic);

                if ((charaProp | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                    mNotifyCharacteristic = writecharacteristic;
                    mService.setCharacteristicNotification(address,writecharacteristic, true);
                }
                getBleData();
          }
        }
    }
    /**
     * 获取Ble发送的数据
     */
    private void getBleData(){
        mService.setmOnBleSendToPhoneDataListener(new MyBlueToothInterface.OnBleSendToPhoneDataListener() {
            @Override
            public void OnBleSendToPhoneData(BluetoothGatt gatt,String data) {
                L.i(gatt.getDevice().getName()+"=======================================获得的数据是"+data);
                if (data.contains("ff")||data.contains("FF")){
                    sendData(gatt.getDevice().getAddress(),m_szImei);
                }else if (data.contains("f2")||data.contains("F2")){
                    sendData(gatt.getDevice().getAddress(),m_szImei);
                }else if(data.contains("CD")||data.contains("cd")){
                    showDialog();
                }else if (data.contains("bb")||data.contains("BB")){
                    mService.disconnect(mac);
                }else if (data.contains("ef")||data.contains("EF")){
                    //绑定成功
                    sendData(gatt.getDevice().getAddress(),"01");
                    DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.Mac.eq(gatt.getDevice().getAddress())).build().unique();
                    DeviceBean deviceBean2 = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                    if (deviceBean2==null){

                    }else {
                        deviceBean2.setIsChoose(false);
                        deviceBeanDao.update(deviceBean2);
                    }
                    if (deviceBean==null){
                        L.i("没有符合条件的对象");
                        deviceBeanDao.insert(new DeviceBean(gatt.getDevice().getName(),null,gatt.getDevice().getAddress(),true,true));
                    }else {
                        L.i("you符合条件的对象"+deviceBean.getName());
                        deviceBean.setIsChoose(true);
                        deviceBeanDao.update(deviceBean);
                    }
                    myFragementDialog.close();
                    showDialog3();

                }else if (data.length()>8){
                    L.i("----------数据转换---------"+new String(hexStringToBytes(data)));
                    //L.i("-------测试转换2b020306080100010909-----"+new String(hexStringToBytes("2b020306080100010909")));
                    Intent intent = new Intent(BluetoothLeService.MY_DATA);
                    intent.putExtra(BluetoothLeService.MY_DATA,new String(hexStringToBytes(data)));
                    sendBroadcast(intent);
                }
            }
        });
    }

    /**
     * 连接并获取服务列表等
     */
    private void OnConnectAndService() {
        myDeviceListAdapter.setOnItemClickListener(new MyDeviceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Log.i("info",list.get(position).getName()+"---点击的item---");
                mac = list.get(position).getMac();
                mService.connect(list.get(position).getMac());
                mService.setOnDiscoverServiceListener(new MyBlueToothInterface.OnDiscoverServiceListener() {
                    @Override
                    public void OnDiscoverService(BluetoothGatt gatt,List<BluetoothGattService> gattServices) {
                        L.i(gattServices.size()+"-------Main获取服务列表----------");
                        for (BluetoothGattService service:gattServices){
                            if (service.getUuid().toString().equals(Keyparameter.WRITEGATTSERVICEUUID)){
                                //写需要的服务数据
                                writenotyGattService=service;
                                writecharacteristic=writenotyGattService.getCharacteristic(UUID.fromString(Keyparameter.WRITECHARACTERISTIC));
                            }
                            if (service.getUuid().toString().equals(Keyparameter.READATTSERVICEUUID)){
                                //读需要的服务数据
                                readMnotyGattService=service;
                                readCharacteristic=readMnotyGattService.getCharacteristic(UUID.fromString(Keyparameter.READCHARACTERISTIC));
                            }
                        }
                        if (readCharacteristic!=null){
                            mService.setCharacteristicNotification(gatt.getDevice().getAddress(),readCharacteristic, true);

                        }

                        getBleData();
                        binding();
                    }
                });
                mService.setOnConnectionStateChange(new MyBlueToothInterface.OnConnectionStateChangeListener() {
                    @Override  //状态信息发生改变
                    public void OnConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                        if (newState == BluetoothProfile.STATE_CONNECTED){
                                L.i("连接成功");
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindLocal();
    }

    public void slcan_btn() {//点击开始扫描
        list.clear();
        myDeviceListAdapter.clear();
        myBluetoothScan.scanLeDevice(true);
    }
    public void Discon_btn(View view) {//断开连接
        L.i("点击断开");
        sendData(mac,"CC");
    }
    //绑定
    public void binding() {
        //sendDataToBle(mac,m_szImei);
        sendData(mac,"FF");
        showDialog2();
    }
    //获取温湿度PM2.5
    public void getTemAndHumi(){
        L.i("---------获取温湿度");
        sendData(mac,"01");
    }

    //设备扫描
    private void OnSlcanDevice(){
        myBluetoothScan.setOnScanGetDeviceListener(new MyBlueToothInterface.OnScanGetDeviceListener() {
            @Override
            public void OnSlcanGetDeviceSuccess(BluetoothDevice device, int rssi, byte[] scanRecord) {
                Log.i("info","------main--------设备名称-----------"+device.getName());
                myDeviceListAdapter.addItem(new DeviceBean(device.getName().toString(),device.getAddress().toString(),false,false));
                list.add(new DeviceBean(device.getName().toString(),device.getAddress().toString(),false,false));
            }
        });
    }
    private void initView() {
        dev_list = ((RecyclerView) findViewById(R.id.dev_list));
        myDeviceListAdapter = new MyDeviceListAdapter(getApplication(),new ArrayList<DeviceBean>());
        dev_list.setLayoutManager(new LinearLayoutManager(this));
        dev_list.setAdapter(myDeviceListAdapter);
        dev_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
    }

    /**
     * 判断是否支持蓝牙
     */
    private void boolBluetooth() {
        if (mBluetoothAdapter == null) {
            Toast.makeText(this,"对不起，您的设备不支持蓝牙,即将退出", Toast.LENGTH_SHORT).show();
            finish();
        } else if(!mBluetoothAdapter.isEnabled()) {//蓝牙未开启
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断是不是启动蓝牙的结果
        if (requestCode == REQUEST_ENABLE_BT) {
            if (resultCode == Activity.RESULT_OK) {
                //成功
                Toast.makeText(this, "蓝牙开启成功...", Toast.LENGTH_SHORT).show();

            } else {
                //失败
                Toast.makeText(this, "蓝牙开启失败...", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 是否支持Ble设备
     */
    private void initblebool() {
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this,"您的手机不支持Ble设备", Toast.LENGTH_SHORT).show();
            finish();
        }
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "您的手机不支持Ble蓝牙", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
    /**
     *  绑定蓝牙服务
     */
    public void bindLocal() {
        Intent bind = new Intent(this, BluetoothLeService.class);
        connection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                mService = ((BluetoothLeService.LocalBinder) iBinder).getService();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
             
            }
        };
        bindService(bind,connection, Context.BIND_AUTO_CREATE);

    }
    /**
     * 解绑蓝牙服务
     */
    public void unbindLocal(){
        if (connection != null)
            unbindService(connection);
    }
    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * Convert hex string to byte[]
     *
     * @param hexString the hex string
     * @return byte[]
     */
    public byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    /**
     * Convert char to byte
     *
     * @param c char
     * @return byte
     */
    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);

    }
    public void sendData(String address,String data){
        Intent intent=new Intent();
        intent.setAction(BluetoothLeService.SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);

    }
    //重新开始弹出框
    public void showDialog() {
         connDialog_false=new MyFragmentConnDialog_False();
        connDialog_false.show(getFragmentManager(),"conn_false");
    }
    //绑定弹出框
    public void showDialog2(){
        myFragementDialog = new MyFragementDialog();
        myFragementDialog.show(getFragmentManager(),"bindingDialog");
    }
    //改名字弹出框
    public void showDialog3(){
        myFragmentAddInforDilog = new MyFragmentAddInforDilog();
        myFragmentAddInforDilog.show(getFragmentManager(),"addinfor");
    }
    @Override
    public void onFragmentDialogCancelLister(String s) {
        L.i("我点了取消1");
        mService.disconnect(mac);
    }

    @Override
    public void onFragmentConnFalseCancel(String s) {
        L.i("我点了取消2");
        mService.disconnect(mac);
    }

    @Override
    public void onFragmentConnFalseNext(String s) {
        L.i("重新开始");
        sendData(mac,m_szImei);
    }

    //添加信息取消
    @Override
    public void onMyFragmentAddInfroCancel() {
        finish();
    }
    //添加信息开始看管
    @Override
    public void onMyFragmentAddInfroBeginWatch(String addinfor) {
        DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
        if (!(deviceBean==null)){
            sendData(deviceBean.getMac(),"F1");
            deviceBean.setSecondName(addinfor);
            deviceBeanDao.update(deviceBean);
        }
        finish();
    }

    public void back(View view) {
        finish();
    }
}
