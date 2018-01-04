package com.beestar.jzb.goglebleweather.Service;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.beestar.jzb.goglebleweather.DialogFragment.MyFragmentDialogProgress;
import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.utils.Keyparameter;
import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.utils.MyFile;
import com.beestar.jzb.goglebleweather.utils.RestartAPPTool;
import com.beestar.jzb.goglebleweather.utils.SampleGattAttributes;
import com.beestar.jzb.goglebleweather.utils.Statics;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.beestar.jzb.goglebleweather.Service.Constants.ACTION_GATT_DISCONNECTING;

/**
 * Created by jzb on 2017/11/21.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MyServiceBlueTooth extends Service {
    private final static String TAG = MyServiceBlueTooth.class.getSimpleName();
    public static final int END_SIGNAL = 0xfe000000;
    public static final int REBOOT_SIGNAL = 0xfd000000;

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private List<BluetoothGatt> connectBlueGatt=new ArrayList<>();
    private int mConnectionState = STATE_DISCONNECTED;

    private  String m_szImei=null;
    private static final int MAX_CONNECT_NUM = 16;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public static final int MEMORY_TYPE_EXTERNAL_I2C = 0x12;
    public static final int MEMORY_TYPE_EXTERNAL_SPI = 0x13;
    /**
     * 静态广播
     */
    public final static String MYSENDDATA=
            "com.weather.MYSENDDATA";
    public final static String BLUETOOTHCONNECTED=
            "com.weather.BLUETOOTHCONNECTED";
    public final static String BLUETOOTHDISCONNECT=
            "com.weather.BLUETOOTHDISCONNECT";
    public final static String HAVEFINDSERVICE=
            "com.weather.HAVEFINDSERVICE";

    public static final String UPDATUI_BINGSUCCESS           ="com.weather.UPDATUI_BINGSUCCESS";
    public static final String UPDATUI_BINGFAILD           ="com.weather.UPDATUI_BINGFAILD";
    public static final String BING_NUMBER                 ="com.weather.BING_NUMBER";


    public static final String BING_SUCCESS                   ="com.weather.BING_SUCCESS";
    public static final String BING_FAILD                     ="com.weather.BING_FAILD";
    public static final String HAVEBING_WITHOTHERS            ="com.weather.HAVEBING_WITHOTHERS";
    public static final String DISCONNECTED                   ="com.weather.DISCONNECTED";//断开连接
    public static final String DISCONNECTED_SENDBINDDATA      ="com.weather.DISCONNECTED_SENDBINDDATA";//发送绑定吗
    public final static String UPDATTEMP                      ="com.example.bluetooth.le.UPDATTEMP";//更新温度等数据
    public final static String UPDATSTEP                      ="com.example.UPDATASTEP";
    public final static String PROGRESSTEP                    ="com.example.PROGRESSTEP";
    public final static String LINEDATA                       ="com.example.LINEDATA";

    public final static String CESHI                          ="CESHI";
    public final static String FILENAME                       ="GETFILENAM";
    public final static String GETBANBEN                      ="GETBANBEN";
    public final static String HAVEGETVISION                  ="HAVEGETVISION";
    public final static String HAVEERROR                      ="HAVEERROR";

    public final static String ACTION_GATT_CONNECTED           = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";//已连接
    public final static String ACTION_GATT_DISCONNECTED        = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";//已断开
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE           = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA                      = "com.example.bluetooth.le.EXTRA_DATA";
    public final static String SEND_DATA                       ="com.example.bluetooth.le.SEND_DATA";
    public final static String UPDATA_NAME                     ="com.example.bluetooth.le.UPDATA_NAME";
    public final static String UPDATA_NAMESUCCESS              ="com.example.bluetooth.le.UPDATA_NAME_SUCCESS";
    public final static String MY_DATA                         ="com.example.bluetooth.le.MY_DATA";
    public final static UUID UUID_HEART_RATE_MEASUREMENT       = UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
    public final static String RECCONECT                       ="com.weather.RECCONECT";
    public final static String PROGRESSBAR                     ="com.weather.PROGRESSBAR";

    private boolean bool;
    private boolean lastBlock=false;
    private boolean preparedForLastBlock = false;
    boolean lastBlockSent = false;
    boolean endSignalSent = false;
    boolean rebootsignalSent = false;
    boolean finished = false;
    boolean hasError = false;
    int blockCounter = 0;
    int chunkCounter = -1;
    private String fileName;
    private MyFile myFile = null;
    private int myStep=-1;
    private String updataAddress=null;
    MyBlueToothInterface.OnConnectionStateChangeListener mOnConnectionStateChange;
    MyBlueToothInterface.OnDiscoverServiceListener mOnDiscoverServiceListener;
    MyBlueToothInterface.OnBleSendToPhoneDataListener mOnBleSendToPhoneDataListener;
    MyBlueToothInterface.OnBlueToothConnectDeviceListener mOnBlueToothConnectDeviceListener;
    MyBlueToothInterface.OnBlueToothServiceDiscoverLister mOnBlueToothServiceDiscoverLister;
    MyBlueToothInterface.OnBlueToothConnectGattListener mOnBlueToothConnectGattListener;


    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            String intentAction;
            L.i("------当前状态码---"+status+"---"+newState+"--"+ BluetoothProfile.STATE_DISCONNECTED+"--"+BluetoothProfile.STATE_CONNECTED+"--"
                    +BluetoothProfile.STATE_DISCONNECTING+"--"+BluetoothProfile.STATE_CONNECTING);
            String tmpAddress=gatt.getDevice().getAddress();
            L.i("-----------------连接设备地址-----"+tmpAddress);
            if (status!=0){
                gatt.close();
                gatt.disconnect();
                gatt.getDevice().connectGatt(MyApp.getContext(),false,mGattCallback);
            }
            if (newState == BluetoothProfile.STATE_DISCONNECTED) {//0
                L.d("Ble连接失败");
                intentAction = BLUETOOTHDISCONNECT;
                L.i("连接失败+Service"+connectBlueGatt.size());
                Log.i(TAG, "Disconnected from GATT server."+connectBlueGatt.size());

                for (BluetoothGatt blg:connectBlueGatt){
                    if (blg==gatt){
                        connectBlueGatt.remove(blg);
                    }
                }
                gatt.close();
                DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.Mac.eq(gatt.getDevice().getAddress())).build().unique();
                if (!(deviceBean==null)){
                    deviceBean.setIsConn(false);
                    deviceBeanDao.update(deviceBean);
                    Log.d(TAG, "更新数据库信息 ");
                    connect(gatt.getDevice().getAddress());
                    broadcastUpdate2(intentAction, gatt);
                }else {
                    Log.d(TAG, "onConnectionStateChange: delete not null");
                }
            }
            else if (newState == BluetoothProfile.STATE_CONNECTED) {//2
                L.d("Ble已连接");
                L.i("连接成功+Service"+connectBlueGatt.size());
                intentAction = BLUETOOTHCONNECTED;

                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        gatt.discoverServices());
                if (!gatt.discoverServices()){
                    Intent it=new Intent(MyApp.getContext(),MyServiceBlueTooth.class);
                    it.putExtra("address",gatt.getDevice().getAddress());
                    startService(it);
                }
                broadcastUpdate2(intentAction, gatt);
                //sendData(gatt.getDevice().getAddress(),"FF");
            } else if (newState == BluetoothProfile.STATE_DISCONNECTING) {//3
                L.d("Ble正在断开连接");
                intentAction = ACTION_GATT_DISCONNECTING;
                Log.i(TAG, "Disconnecting from GATT server.");
                broadcastUpdate2(intentAction, gatt);
            }
            else if (newState == BluetoothProfile.STATE_CONNECTING) {//1
                L.d("Ble正在连接");
            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                if (mOnBlueToothServiceDiscoverLister != null) {
                    mOnBlueToothServiceDiscoverLister.OnBlueToothServicelist(gatt, status);
                }
                Log.i(TAG, "onServicesDiscovered: 发现服务");
                displayGattServices2(gatt,getSupportedGattServices(gatt.getDevice().getAddress()));
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            boolean sendUpdate = true;
            int index = -1;
            int step = -1;

            if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_MANUFACTURER_NAME_STRING)) {
                index = 0;
                Log.d(TAG, "onCharacteristicRead:0 "+new String(characteristic.getValue()));
            } else if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_MODEL_NUMBER_STRING)) {
                index = 1;
                Log.d(TAG, "onCharacteristicRead: 1"+new String(characteristic.getValue()));
            } else if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_FIRMWARE_REVISION_STRING)) {
                index = 2;
                Log.d(TAG, "onCharacteristicRead: 2"+new String(characteristic.getValue()));
                Intent it=new Intent(HAVEGETVISION);
                it.putExtra("vison",new String(characteristic.getValue()));
                sendBroadcast(it);
            } else if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_SOFTWARE_REVISION_STRING)) {
                index = 3;
                Log.d(TAG, "onCharacteristicRead:3 "+new String(characteristic.getValue()));
            }
            // SPOTA
            else if (characteristic.getUuid().equals(Statics.SPOTA_MEM_INFO_UUID)) {
                step = 5;
                Log.i("jzb","------------------------5-----------------------");
            } else {
                sendUpdate = false;
            }

            if (sendUpdate) {
                Log.d(TAG, "onCharacteristicRead: " + index);
                Intent intent = new Intent();
                intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
                if (index >= 0) {
                    Log.i("Callback",index+"---index------"+new String(characteristic.getValue()));
                    intent.putExtra("characteristic", index);
                    intent.putExtra("value", new String(characteristic.getValue()));
                } else {
                    intent.putExtra("step", step);
                    intent.putExtra("value", characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT32, 0));
                }
                MyApp.getContext().sendBroadcast(intent);
            }
        }

        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                Log.i(TAG, "write succeeded");
                int step = -1;
                // Step 3 callback: write SPOTA_GPIO_MAP_UUID value
                if (characteristic.getUuid().equals(Statics.SPOTA_GPIO_MAP_UUID)) {
                    step = 4;
                    L.i("可以开始第四步");

                }
                // Step 4 callback: set the patch length, default 240
                else if (characteristic.getUuid().equals(Statics.SPOTA_PATCH_LEN_UUID)) {
                    step =  5 ;

                } else if (characteristic.getUuid().equals(Statics.SPOTA_MEM_DEV_UUID)) {

                }
                else if (characteristic.getUuid().equals(Statics.SPOTA_PATCH_DATA_UUID)&&chunkCounter!=-1) {
                    //发送下一个block step=5
                    sendBlock();
                }
                if (step>0){
                    broadcastUpdate(UPDATSTEP,step,-1,-1);
                }

            } else {
                Log.e(TAG, "write failed: " + status);
            }
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        @Override
        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            if (descriptor.getCharacteristic().getUuid().equals(Statics.SPOTA_SERV_STATUS_UUID)) {
                int step = 2;
                L.i("onDescriptorWrite--可以跳转第二步");
               broadcastUpdate(UPDATSTEP,step,-1,-1);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE,gatt, characteristic);
            int value = new BigInteger(characteristic.getValue()).intValue();
            String stringValue = String.format("%#10x", value);
            Log.d("changed", stringValue);
            Log.i("changed","============stringvalue=========="+stringValue);
            int step=-1;
            int error = -1;
            int memDevValue=-1;
            // Set memtype callback
            if (stringValue.trim().equals("0x10")) {
                step = 3;
                L.i("可以开始第三步");

            }
            // Successfully sent a block, send the next one
            else if (stringValue.trim().equals("0x2")) {
                step = 5;
                L.i("可以开始第5步2");


            } else if (stringValue.trim().equals("0x3") || stringValue.trim().equals("0x1")) {
                memDevValue=value;

            } else {
                error = Integer.parseInt(stringValue.trim().replace("0x", ""));
                Log.i(TAG, "onCharacteristicChanged: 发生错误------错误状态码"+error);
            }
            if (step >= 0 || error >= 0 || memDevValue >= 0) {
                broadcastUpdate(UPDATSTEP,step,error,memDevValue);
            }
        }
    };
    private DeviceBeanDao deviceBeanDao;
    private MyFragmentDialogProgress myFragmentDialogProgress;

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
        sendBroadcast(intent);
    }

    private void broadcastUpdate(final String action,int i,int j,int k) {
        final Intent intent = new Intent(action);
        intent.putExtra("step",i);
        intent.putExtra("error",j);
        intent.putExtra("memDevValue",k);
        sendBroadcast(intent);
    }
    private void broadcastUpdate(final String action,BluetoothGatt gatt,
                                 final BluetoothGattCharacteristic characteristic) {
        final Intent intent = new Intent(action);
        // This is special handling for the Heart Rate Measurement profile.  Data parsing is
        // carried out as per profile specifications:
        // http://developer.bluetooth.org/gatt/characteristics/Pages/CharacteristicViewer.aspx?u=org.bluetooth.characteristic.heart_rate_measurement.xml
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            int flag = characteristic.getProperties();
            int format = -1;
            if ((flag & 0x01) != 0) {
                format = BluetoothGattCharacteristic.FORMAT_UINT16;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = BluetoothGattCharacteristic.FORMAT_UINT8;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            final int heartRate = characteristic.getIntValue(format, 1);
            Log.d(TAG, String.format("Received heart rate: %d", heartRate));
            intent.putExtra(EXTRA_DATA, String.valueOf(heartRate));
        } else {
            // For all other profiles, writes the data formatted in HEX.
            final byte[] data = characteristic.getValue();
            if (data != null && data.length > 0) {
                final StringBuilder stringBuilder = new StringBuilder(data.length);
                for(byte byteChar : data)
                    stringBuilder.append(String.format("%02X ", byteChar));
                Log.i("info","------------------------------------------"+bytesToHexString(data));
                intent.putExtra(EXTRA_DATA, bytesToHexString(data) + "\n" + stringBuilder.toString());
                Intent intent1=new Intent();
                intent1.setAction(MYSENDDATA);
                intent1.putExtra("data",bytesToHexString(data));
                intent1.putExtra("device",gatt.getDevice());
                sendBroadcast(intent1);
                if ( bytesToHexString(data).contains("f9")|| bytesToHexString(data).contains("F9")){
                    broadcastUpdate(MyServiceBlueTooth.UPDATA_NAMESUCCESS);
                }else if( bytesToHexString(data).contains("f4")|| bytesToHexString(data).contains("F4")){

                }else if (bytesToHexString(data).contains("FF")|| bytesToHexString(data).contains("ff")){
                    sendData(gatt.getDevice().getAddress(),m_szImei);
                }
            }
        }
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        public MyServiceBlueTooth getService() {
            return MyServiceBlueTooth.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        // After using a given device, you should make sure that BluetoothGatt.close() is called
        // such that resources are cleaned up properly.  In this particular example, close() is
        // invoked when the UI is disconnected from the Service.
        //close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new MyServiceBlueTooth.LocalBinder();

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        if ( intent.getStringExtra("address")!=null){
            connect(intent.getStringExtra("address"));
        }
        L.i("----------蓝牙服务开启-------");
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        initialize();
        registerReceiver(mReceiver,getFilter());

    }

    /**
     * Initializes a reference to the local Bluetooth adapter.
     *
     * @return Return true if the initialization is successful.
     */
    public boolean initialize() {
        // For API level 18 and above, get a reference to BluetoothAdapter through
        // BluetoothManager.
        initTelphoneMga();
        if (mBluetoothManager == null) {
            mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }

        mBluetoothAdapter = mBluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
            return false;
        }

        return true;
    }

    /**
     * Connects to the GATT server hosted on the Bluetooth LE device.
     *
     * @param address The device address of the destination device.
     *
     * @return Return true if the connection is initiated successfully. The connection result
     *         is reported asynchronously through the
     *         {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     *         callback.
     */
    public boolean connect(final String address) {
        L.i("---------------------lianjie----------");
        if (mBluetoothAdapter == null || address == null) {
            Log.w("my", "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }

        // Previously connected device.  Try to reconnect.
        if (!connectBlueGatt.isEmpty()){
            Log.d("my", "Trying to use an existing mBluetoothGatt for connection.");
            for (BluetoothGatt blg:connectBlueGatt){
                if (blg.getDevice().getAddress().equals(address)){
                    if (blg.connect()){
                        return true;
                    }else {
                        return false;
                    }
                }
            }
        }
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w("my", "Device not found.  Unable to connect.");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.

        BluetoothGatt mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        Log.d("my", "Trying to create a new connection.");
        connectBlueGatt.add(mBluetoothGatt);
        return true;
    }
    private void broadcastUpdate(final String action, final String address) {
        final Intent intent = new Intent(action);
        intent.putExtra("address", address);
        sendBroadcast(intent);
    }
    private void broadcastUpdate2(final String action, final BluetoothGatt gatt) {
        final Intent intent = new Intent(action);
        intent.putExtra("device",gatt.getDevice());
        sendBroadcast(intent);
    }
    private void broadcastUpdateProgress(float progress) {
        final Intent intent = new Intent(MyServiceBlueTooth.PROGRESSBAR);
        Log.i(TAG, "广播: 更新progress发生改变"+progress);
        intent.putExtra("progress",progress);
        sendBroadcast(intent);
    }

    /**
     * 获取已连接的蓝牙设备列表
     * @return
     */
    public List<BluetoothDevice> getConnectDevices() {
        if (mBluetoothManager == null) return null;
        return mBluetoothManager.getConnectedDevices(BluetoothProfile.GATT);
    }
    public List<BluetoothDevice> getConnectDevice(){
        if (mBluetoothManager == null) return null;
        return mBluetoothManager.getConnectedDevices(BluetoothProfile.GATT);
    }
    public void getConnectedDevice(){
        mOnBlueToothConnectDeviceListener.OnBluetoothConnectList(getConnectDevice());
    }
    public void getGattList(){
        mOnBlueToothConnectGattListener.OnBlueToothGattList(connectBlueGatt);
    }
    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect(String address) {
        BluetoothGatt blg_temp=null;
        for (BluetoothGatt blg:connectBlueGatt){
            if (blg.getDevice().getAddress().equals(address)){
                blg_temp=blg;
                connectBlueGatt.remove(blg);
            }
        }
        DeviceBeanDao deviceBeanDao1 = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        List<DeviceBean> deviceBeanList = deviceBeanDao1.queryBuilder().where(DeviceBeanDao.Properties.Mac.eq(address)).build().list();
        for (DeviceBean deviceBean : deviceBeanList) {
            this.deviceBeanDao.delete(deviceBean);
            Log.d(TAG, "writeValue:delete devicedao----------------");
        }
        if (mBluetoothAdapter == null || blg_temp == null) {
            Log.e(TAG, "BluetoothAdapter not initialized.2");
            return;
        }

       blg_temp.disconnect();
    }
    /**
     * 写入数据
     * @param address 设备地址
     * @param serviceUUID
     * @param characteristicUUID
     * @param value
     */
    public void writeValue(final String address, final String serviceUUID, final String characteristicUUID,
                           byte[] value) {
        String s = bytesToHexString(value);
        BluetoothGatt blg_temp=null;
        for (BluetoothGatt blg:connectBlueGatt){
            if (blg.getDevice().getAddress().equals(address)){
                blg_temp=blg;
            }
        }
        if (blg_temp==null)
        {
            disconnect(address);
            return;
        }
        L.i("====写入gatt服务 size============="+blg_temp.getServices().size());
        for (BluetoothGattService bluetoothGattService : getSupportedGattServices(address)) {
            String gattServiceUUID = Long.toHexString(
                    bluetoothGattService.getUuid().getMostSignificantBits())
                    .substring(0, 4);

            for (BluetoothGattCharacteristic bluetoothGattCharacteristic : bluetoothGattService
                    .getCharacteristics()) {
                String characterUUID = Long.toHexString(
                        bluetoothGattCharacteristic.getUuid()
                                .getMostSignificantBits()).substring(0, 4);

                if (gattServiceUUID.equals(serviceUUID)
                        && characteristicUUID.equals(characterUUID)) {
                    L.i("BluetoothleService    gattServiceUUID"+gattServiceUUID.toString());
                    byte[] bs = new byte[20];
                    bs[0] = (byte) 0x00;
                    bluetoothGattCharacteristic.setValue(bs[0],
                            BluetoothGattCharacteristic.FORMAT_UINT8, 0);
                    bluetoothGattCharacteristic.setValue(value);
//                    bluetoothGattCharacteristic.setWriteType(BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE);
                    bool=blg_temp.writeCharacteristic(bluetoothGattCharacteristic);
                    Log.i("bool","bool："+bool);
//                    if (!bool){
//                        if (s.equals("FF")||s.equals("ff")){
//                            new Handler().postDelayed(new Runnable() {
//                                @Override
//                                public void run() {
//                                    writeValue(address,serviceUUID,characteristicUUID,hexStringToBytes("FF"));
//                                }
//                            },2000);
//                            Log.i(TAG, "writeValue: 发送value"+bytesToHexString(value));
//                            writeValue(address,serviceUUID,characteristicUUID,hexStringToBytes("FF"));
//                        }
//                    }
                }
            }
        }
    }
    /**
     * Enables or disables notification on a give characteristic.
     *
     * @param characteristic Characteristic to act on.
     * @param enabled If true, enable notification.  False otherwise.
     */
    public void setCharacteristicNotification(String address,BluetoothGattCharacteristic characteristic,
                                              boolean enabled) {
        BluetoothGatt blg_temp=null;
        for (BluetoothGatt blg:connectBlueGatt){
            if (blg.getDevice().getAddress().equals(address)){
                blg_temp=blg;
            }
        }
        if (mBluetoothAdapter == null || blg_temp== null) {
            Log.w(TAG, "BluetoothAdapter not initialized5");
            return;
        }
        blg_temp.setCharacteristicNotification(characteristic, enabled);
        // This is specific to Heart Rate Measurement.
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            blg_temp.writeDescriptor(descriptor);
        }
    }
    private void displayGattServices(BluetoothGatt gatt,List<BluetoothGattService> gattServices) {
        L.i("这里可以解析服务------------displayGattServices-------"+gattServices.size());
        if (gattServices == null)
            return;
        mOnDiscoverServiceListener.OnDiscoverService(gatt,gattServices);//接口回调返回服务列表
    }

    /**
     *解析服务并发送读通知
     */
    private void displayGattServices2(BluetoothGatt gatt,List<BluetoothGattService> gattServices) {
        Log.i(TAG, "displayGattServices2: 发现服务并解析");
        Intent intent=new Intent();
        intent.setAction(HAVEFINDSERVICE);
        intent.putExtra("device",gatt.getDevice());
        if (gattServices == null){
            Log.i(TAG, "displayGattServices2: 发现服务为空");
            disconnect(gatt.getDevice().getAddress());
            connect(gatt.getDevice().getAddress());
        }
        BluetoothGattService writenotyGattService=null,readMnotyGattService=null;
        BluetoothGattCharacteristic writecharacteristic=null,readCharacteristic = null;
        for (BluetoothGattService service:gattServices){
            List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
            for (BluetoothGattCharacteristic ch:characteristics){
                if (ch.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_FIRMWARE_REVISION_STRING)){
                    gatt.readCharacteristic(ch);
                }
            }
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
            if (readCharacteristic!=null){
                L.i("------已发送读通知---"+readCharacteristic.getUuid().toString());
                setCharacteristicNotification(gatt.getDevice().getAddress(),readCharacteristic,true);
                sendBroadcast(intent);
                writeValue(gatt.getDevice().getAddress(),"ffe5", "ffe9", hexStringToBytes("FF"));
            }
        }
    }
    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices(String address) {
        BluetoothGatt blg_temp=null;
        for (BluetoothGatt blg:connectBlueGatt){
            if (blg.getDevice().getAddress().equals(address)){
                blg_temp=blg;
            }
        }
        if (blg_temp == null)
            return null;
        return blg_temp.getServices();

    }
    private IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SEND_DATA);
        intentFilter.addAction(UPDATA_NAME);
        intentFilter.addAction(RECCONECT);
        intentFilter.addAction(DISCONNECTED);
        intentFilter.addAction(DISCONNECTED_SENDBINDDATA);

        intentFilter.addAction(CESHI);
        intentFilter.addAction(FILENAME);
        intentFilter.addAction(PROGRESSTEP);
        intentFilter.addAction(GETBANBEN);

        intentFilter.addAction(BING_NUMBER);
        return intentFilter;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
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
     * @param c char
     * @return byte
     */
    private byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);

    }

    public void setOnConnectionStateChange(MyBlueToothInterface.OnConnectionStateChangeListener l){
        mOnConnectionStateChange=l;
    }
    public void setOnDiscoverServiceListener(MyBlueToothInterface.OnDiscoverServiceListener l){
        mOnDiscoverServiceListener=l;
    }
    public void setmOnBleSendToPhoneDataListener(MyBlueToothInterface.OnBleSendToPhoneDataListener l){
        mOnBleSendToPhoneDataListener=l;
    }
    public void setmOnBlueToothConnectDeviceListener(MyBlueToothInterface.OnBlueToothConnectDeviceListener l){
        mOnBlueToothConnectDeviceListener=l;
    }
    public void setmOnBlueToothServiceDiscoverLister(MyBlueToothInterface.OnBlueToothServiceDiscoverLister l){
        mOnBlueToothServiceDiscoverLister=l;
    }
    public void setmOnBlueToothConnectGattListener(MyBlueToothInterface.OnBlueToothConnectGattListener l){
        mOnBlueToothConnectGattListener=l;
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
    public void sendData(String address,String data){
        Intent intent=new Intent();
        intent.setAction(SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);

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
    BroadcastReceiver mReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context,final Intent intent) {
            if (intent.getAction().equals(SEND_DATA)){
                Log.i("info","-----------------发送数据------------"+intent.getStringExtra("data")+intent.getStringExtra("address"));

                writeValue(intent.getStringExtra("address"),"ffe5", "ffe9", hexStringToBytes(intent.getStringExtra("data").toString()));
                if ((hexStringToBytes(intent.getStringExtra("data").toString())).equals("CC")){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            disconnect(intent.getStringExtra("address"));

                        }
                    },1000);
                }
            }else if (intent.getAction().equals(UPDATA_NAME)){
                try {
                    byte[] datas = (intent.getStringExtra("data")).getBytes("UTF-8");
                    writeValue(intent.getStringExtra("address"),"ffe5", "ffe9",datas);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }else if (intent.getAction().equals(RECCONECT)){
                String address = intent.getStringExtra("address");
                connect(address);
            }else if (intent.getAction().equals(DISCONNECTED)){
                String address=intent.getStringExtra("address");
                if (address!=null){
                    disconnect(address);
                }

            }else if (intent.getAction().equals(DISCONNECTED_SENDBINDDATA)){
                String address=intent.getStringExtra("address");
                sendData(address,m_szImei);
            }else if (intent.getAction().equals(CESHI)){
                updataAddress=intent.getStringExtra("address");
                int step=1;
                broadcastUpdate(UPDATSTEP,step,-1,-1);
            }else if (intent.getAction().equals(FILENAME)){
                String address=intent.getStringExtra("filename");
                fileName=address;
                updataAddress=null;
                myStep=-1;
                myFile=null;
                try {
                    myFile = MyFile.getByFileName(fileName);
                    if (myFile!=null)
                        L.i("service","==========myFile不为空=======");
                    myFile.setType();
                    myFile.setFileBlockSize(240);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }else if (intent.getAction().equals(PROGRESSTEP)){
                //更新步骤
                progressStep(intent);
            }else if (intent.getAction().equals(GETBANBEN)){
                DeviceBean unique = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                for (BluetoothGatt blg_tmp:connectBlueGatt){
                    if (blg_tmp.getDevice().getAddress().equals(unique.getMac())){
                        for (BluetoothGattService blg_service:blg_tmp.getServices()){
                            List<BluetoothGattCharacteristic> characteristics = blg_service.getCharacteristics();
                            for (BluetoothGattCharacteristic blg_ch:characteristics){
                                if (blg_ch.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_FIRMWARE_REVISION_STRING)){
                                    blg_tmp.readCharacteristic(blg_ch);
                                }
                            }
                        }
                    }
                }

            }else if (intent.getAction().equals(BING_NUMBER)){
                BluetoothDevice device = (BluetoothDevice) intent.getParcelableExtra("device");
                sendData(device.getAddress(),m_szImei);
            }
        }
    };

    private void progressStep(Intent intent) {
        int newStep=intent.getIntExtra("step",-1);
        int newError=intent.getIntExtra("error",-1);
        int newMemDevValue=intent.getIntExtra("memDevValue",-1);
        if (newError >= 0) {
            Log.i(TAG, "progressStep: 发生了错误");
        }
        else if(newMemDevValue >= 0) {
            String stringValue = String.format("%#10x", newMemDevValue);
            Log.d(TAG, "processMemDevValue() step: " + myStep + ", value: " + stringValue);
            switch (myStep) {
                case 2:
                    if(newMemDevValue == 0x1) {
                        broadcastUpdate(UPDATSTEP,3,-1,-1);
                    }
                    else {
                        Log.i(TAG, "progressStep: 发生了错误");
                    }
                    break;
            }
        }
        // If a step is set, change the global step to this value
        if (newStep >= 0) {
            myStep=newStep;
        }

        switch (myStep) {

            case 1:
                enableNotifications();
//                myFragmentDialogProgress = MyFragmentDialogProgress.getInstance();
                break;

            case 2:
                setSpotaMemDev();
                break;
            // Set mem_type for SPOTA_GPIO_MAP_UUID
            case 3:
                setSpotaGpioMap();
                break;
            // Set SPOTA_PATCH_LEN_UUID
            case 4:
                setPatchLength();
                break;
            // Send a block containing blocks of 20 bytes until the patch length (default 240) has been reached
            // Wait for response and repeat this action
            case 5:
                step_5();
        }

    }

    /**********************************************************************/
    //测试OTA 第一步
    public void enableNotifications() {
        BluetoothGatt blg_temp = null;
        for (BluetoothGatt blg : connectBlueGatt) {
            if (blg.getDevice().getAddress().equals(updataAddress)) {
                blg_temp = blg;
            }
        }
        // Get the service status UUID from the gatt and enable notifications
        List<BluetoothGattService> services = blg_temp.getServices();
        for (BluetoothGattService service : services) {

            List<BluetoothGattCharacteristic> characteristics = service.getCharacteristics();
            for (BluetoothGattCharacteristic characteristic : characteristics) {

                if (characteristic.getUuid().equals(Statics.SPOTA_SERV_STATUS_UUID)) {

                    blg_temp.setCharacteristicNotification(characteristic, true);
                    BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                            Statics.SPOTA_DESCRIPTOR_UUID);
                    descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    blg_temp.writeDescriptor(descriptor);
                }
            }
        }

    }
    //第二步
    public void setSpotaMemDev() {
        BluetoothGatt blg_temp = null;
        for (BluetoothGatt blg : connectBlueGatt) {
            if (blg.getDevice().getAddress().equals(updataAddress)) {
                blg_temp = blg;
            }
        }
        BluetoothGattCharacteristic characteristic = blg_temp.getService(Statics.SPOTA_SERVICE_UUID)
                .getCharacteristic(Statics.SPOTA_MEM_DEV_UUID);
        int menType=318767104;
        characteristic.setValue(menType, BluetoothGattCharacteristic.FORMAT_UINT32, 0);
        blg_temp.writeCharacteristic(characteristic);

    }
    //第三步
    public void setSpotaGpioMap() {
        BluetoothGatt blg_temp = null;
        for (BluetoothGatt blg : connectBlueGatt) {
            if (blg.getDevice().getAddress().equals(updataAddress)) {
                blg_temp = blg;
            }
        }
            int memInfoData = 84280064;//
            Log.d(TAG, "setSpotaGpioMap: " + String.format("%#10x", memInfoData));

            Log.i("jzb","----------------发送参数menInfoData------------------"+memInfoData);
            BluetoothGattCharacteristic characteristic = blg_temp.getService(Statics.SPOTA_SERVICE_UUID)
                    .getCharacteristic(Statics.SPOTA_GPIO_MAP_UUID);
            characteristic.setValue(memInfoData, BluetoothGattCharacteristic.FORMAT_UINT32, 0);
            blg_temp.writeCharacteristic(characteristic);
    }
    //第四步
    public void setPatchLength() {
        BluetoothGatt blg_temp = null;
        for (BluetoothGatt blg : connectBlueGatt) {
            if (blg.getDevice().getAddress().equals(updataAddress)) {
                blg_temp = blg;
            }
        }
        int blocksize = 240;

        if (lastBlock) {
            blocksize = myFile.getNumberOfBytes() % 240;
            preparedForLastBlock = true;
        }
        Log.d(TAG, "setPatchLength: " + blocksize + " - " + String.format("%#4x", blocksize));

        BluetoothGattCharacteristic characteristic = blg_temp.getService(Statics.SPOTA_SERVICE_UUID)
                .getCharacteristic(Statics.SPOTA_PATCH_LEN_UUID);
        Log.i("info","发送参数blocksize==============="+blocksize);
        characteristic.setValue(blocksize, BluetoothGattCharacteristic.FORMAT_UINT16, 0);
        blg_temp.writeCharacteristic(characteristic);
    }
    //第五步
    public float sendBlock() {
        BluetoothGatt blg_temp = null;
        for (BluetoothGatt blg : connectBlueGatt) {
            if (blg.getDevice().getAddress().equals(updataAddress)) {
                blg_temp = blg;
            }
        }
        //float progress = 0;
        final float progress = ((float) (blockCounter + 1) / (float) myFile.getNumberOfBlocks()) * 100;

        broadcastUpdateProgress(progress);
        if (!lastBlockSent) {
            Log.d(TAG, "Sending block " + (blockCounter + 1) + " of " + myFile.getNumberOfBlocks());
            byte[][] block = myFile.getBlock(blockCounter);

            //for (int i = 0; i < block.length; i++) {
            int i = ++chunkCounter;
            boolean lastChunk = false;
            if (chunkCounter == block.length - 1) {
                chunkCounter = -1;
                lastChunk = true;
            }
            byte[] chunk = block[i];
            int chunkNumber = (blockCounter * myFile.getChunksPerBlockCount()) + i + 1;
            final String message = "Sending chunk " + chunkNumber + " of " + myFile.getTotalChunkCount() + " (with " + chunk.length + " bytes)";

            String systemLogMessage = "Sending block " + (blockCounter + 1) + ", chunk " + (i + 1) + ", blocksize: " + block.length + ", chunksize " + chunk.length;
            Log.d(TAG, systemLogMessage);
            BluetoothGattCharacteristic characteristic = blg_temp.getService(Statics.SPOTA_SERVICE_UUID)
                    .getCharacteristic(Statics.SPOTA_PATCH_DATA_UUID);
            characteristic.setValue(chunk);
            characteristic.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE);
            boolean r = blg_temp.writeCharacteristic(characteristic);
            Log.d(TAG, "writeCharacteristic: " + r);
            //}

            if (lastChunk) {

                // SUOTA
                if (!lastBlock) {
                    blockCounter++;
                } else {
                    lastBlockSent = true;
                }
                if (blockCounter + 1 == myFile.getNumberOfBlocks()) {
                    lastBlock = true;
                }
            }
        }
        return progress;
    }
    public void step_5(){
        if (!lastBlock) {
            sendBlock();
        } else {
            if (!preparedForLastBlock) {
                setPatchLength();
            } else if (!lastBlockSent) {
                sendBlock();
            } else if (!endSignalSent) {
                sendEndSignal();
            } else {
                onSuccess();
            }
        }
    }
    public void sendEndSignal() {
        BluetoothGatt blg_temp = null;
        for (BluetoothGatt blg : connectBlueGatt) {
            if (blg.getDevice().getAddress().equals(updataAddress)) {
                blg_temp = blg;
            }
        }
        Log.d(TAG, "sendEndSignal");
        Log.d(TAG, "sendEndSignal: 发送结束吗");
        BluetoothGattCharacteristic characteristic = blg_temp.getService(Statics.SPOTA_SERVICE_UUID)
                .getCharacteristic(Statics.SPOTA_MEM_DEV_UUID);
        characteristic.setValue(END_SIGNAL, BluetoothGattCharacteristic.FORMAT_UINT32, 0);
        blg_temp.writeCharacteristic(characteristic);
        endSignalSent = true;
    }
    protected void onSuccess() {
        BluetoothGatt blg_temp = null;
        for (BluetoothGatt blg : connectBlueGatt) {
            if (blg.getDevice().getAddress().equals(updataAddress)) {
                blg_temp = blg;
            }
        }
        finished = true;
        Log.d(TAG, "onSuccess: 更新成功");

        sendRebootSignal(blg_temp);
    }
    public void sendRebootSignal(BluetoothGatt gatt) {
        Log.d(TAG, "sendRebootSignal");
        if (!(gatt.getService(Statics.SPOTA_SERVICE_UUID)==null)){
            BluetoothGattCharacteristic characteristic = gatt.getService(Statics.SPOTA_SERVICE_UUID)
                    .getCharacteristic(Statics.SPOTA_MEM_DEV_UUID);
            characteristic.setValue(REBOOT_SIGNAL, BluetoothGattCharacteristic.FORMAT_UINT32, 0);
            boolean b = gatt.writeCharacteristic(characteristic);
            Log.i(TAG, "sendRebootSignal: 发送RebootSignal"+b);
            rebootsignalSent = true;
            disconnect(gatt.getDevice().getAddress());
            DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.Mac.eq(gatt.getDevice().getAddress())).build().unique();
            if (deviceBean!=null){
                deviceBeanDao.delete(deviceBean);
            }
            RestartAPPTool.restartAPP(MyApp.getContext().getApplicationContext());
        }

    }
    /**********************************************************************/
}
