package com.beestar.jzb.goglebleweather.ui;

import android.Manifest;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.DialogFragment.MyFragementDialog;
import com.beestar.jzb.goglebleweather.DialogFragment.MyFragmentAddInforDilog;
import com.beestar.jzb.goglebleweather.DialogFragment.MyFragmentConnDialog_False;
import com.beestar.jzb.goglebleweather.DialogFragment.MyFragmentHaveBind;
import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.MyBluetoothScan;
import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.adapter.MyDeviceListAdapter;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.utils.L;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class BindingActivity extends BaseActivity implements MyFragementDialog.OnMyFragmentDialogLister,MyFragmentConnDialog_False.OnMyFragmentConnFalseLister
,MyFragmentAddInforDilog.OnMyFragmentAddInfroListener,MyFragmentHaveBind.OnMyFragmentHaveBindListener
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
    private MyServiceBlueTooth mService;
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
    private TextView scanstastu;

    private List<String > listAddress=new ArrayList<>();

    BroadcastReceiver mReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(MyServiceBlueTooth.BING_SUCCESS)){
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("device");
                if(myFragementDialog!=null &&  myFragementDialog.getDialog()!=null
                        && myFragementDialog.getDialog().isShowing()) {
                    Log.i("binding", "onReceive: BING_SUCCESS");
                    myFragementDialog.close();
                } else {

                }
                if (myFragmentAddInforDilog!=null&& myFragmentAddInforDilog.getDialog()!=null&&myFragmentAddInforDilog.getDialog().isShowing()){

                }else {
                    showAddInforDialog();
                }

            }else if (intent.getAction().equals(MyServiceBlueTooth.BING_FAILD)){
                //绑定失败
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("device");
                showDialog();
                sendData(device.getAddress(),m_szImei);
            }else if (intent.getAction().equals(MyServiceBlueTooth.HAVEBING_WITHOTHERS)){
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("device");
                if(myFragementDialog!=null &&  myFragementDialog.getDialog()!=null
                        && myFragementDialog.getDialog().isShowing()) {
                    Log.i("binding", "onReceive: BING_SUCCESS");
                    myFragementDialog.close();
                } else {

                }
                if (myFragmentHaveBind!=null&& myFragmentHaveBind.getDialog()!=null&&myFragmentHaveBind.getDialog().isShowing()){

                }else {
                    showHaveBindDialog();
                }


            }
        }
    };
    private MyFragmentHaveBind myFragmentHaveBind;

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binding);
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        initTelphoneMga();
        registerReceiver(mReceiver,getFilter());
        initView();
        requestLocation();

        requestLocation3();
        initblebool();
        boolBluetooth();
        myScanDevice(true);
        onConnectAndService();
        slcan_btn();
    }
    private BluetoothAdapter.LeScanCallback mLeScanCallback =
            new BluetoothAdapter.LeScanCallback() {
                @Override
                public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                    if (checkDecive(device.getAddress())!=null){
                        if (device.getName()!=null){
                            Log.i("info","---设备-"+device.getName()+"--"+device.getAddress());
                            myDeviceListAdapter.addItem(new DeviceBean(device.getName().toString(),device.getAddress().toString(),false,false));
                            list.add(new DeviceBean(device.getName().toString(),device.getAddress().toString(),false,false));
                        }else {

                        }
                    }
                }
            };
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void myScanDevice(final boolean enable) {

        if (enable) {
            new Handler().postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);

            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(MyServiceBlueTooth.BING_SUCCESS);
        intentFilter.addAction(MyServiceBlueTooth.BING_FAILD);
        intentFilter.addAction(MyServiceBlueTooth.HAVEBING_WITHOTHERS);
        return intentFilter;
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


    /**
     * 连接并获取服务列表等
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void onConnectAndService() {
        myDeviceListAdapter.setOnItemClickListener(new MyDeviceListAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, int position) {
                myScanDevice(false);
                Log.i("info",list.get(position).getName()+"---点击的item---");
                mac = list.get(position).getMac();
                L.i("------mac----"+mac);
                Intent intent=new Intent();
                intent.setAction(MyServiceBlueTooth.RECCONECT);
                intent.putExtra("address",mac);
                sendBroadcast(intent);
                showDialog2(list.get(position).getName());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        myScanDevice(false);
    }

    public void slcan_btn() {//点击开始扫描
        list.clear();
        myDeviceListAdapter.clear();
        myScanDevice(true);
        scanstastu.setText("正在搜索...");
    }
    public void Discon_btn(View view) {//断开连接
        L.i("点击断开");
        sendData(mac,"CC");
    }
    private String checkDecive(String addressflag) {
        if (listAddress.contains(addressflag)){
            return null;
        }else {
            listAddress.add(addressflag);
            return addressflag;
        }

    }
    private void initView() {
        dev_list = ((RecyclerView) findViewById(R.id.dev_list));
        myDeviceListAdapter = new MyDeviceListAdapter(getApplication(),new ArrayList<DeviceBean>());
        dev_list.setLayoutManager(new LinearLayoutManager(this));
        dev_list.setAdapter(myDeviceListAdapter);
        dev_list.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        scanstastu = ((TextView) findViewById(R.id.scan_stastu));
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
        intent.setAction(MyServiceBlueTooth.SEND_DATA);
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
    public void showDialog2(String s){
        myFragementDialog = MyFragementDialog.newInstance(s);
        myFragementDialog.show(getFragmentManager(),"bindingDialog");
    }
    //改名字弹出框
    public void showAddInforDialog(){
        myFragmentAddInforDilog = new MyFragmentAddInforDilog();
        myFragmentAddInforDilog.show(getFragmentManager(),"addinfor");
    }
    //已经被绑定
    public void showHaveBindDialog(){
        myFragmentHaveBind = new MyFragmentHaveBind();
        myFragmentHaveBind.show(getSupportFragmentManager(),"havebind");
    }
    @Override
    public void onFragmentDialogCancelLister(String s) {
        L.i("我点了取消1");
        Intent in=new Intent();
        in.setAction(MyServiceBlueTooth.DISCONNECTED);
        in.putExtra("address",mac);
        sendBroadcast(in);
    }

    @Override
    public void onFragmentConnFalseCancel(String s) {
        L.i("我点了取消2");
        Intent in=new Intent();
        in.setAction(MyServiceBlueTooth.DISCONNECTED);
        in.putExtra("address",mac);
        sendBroadcast(in);
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
    public void rescan(View view) {
        listAddress.clear();
        slcan_btn();
    }

    private void requestLocation() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
            }
            }, new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
        }
    }
    private void requestLocation3() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                }
                @Override
                public void permissionDenied(@NonNull String[] permissions) {
                }
            }, new String[]{Manifest.permission.ACCESS_FINE_LOCATION});
        }
    }

    @Override
    public void onMyFragmentHaveBindCancle() {
        myFragmentHaveBind.close();
    }
}
