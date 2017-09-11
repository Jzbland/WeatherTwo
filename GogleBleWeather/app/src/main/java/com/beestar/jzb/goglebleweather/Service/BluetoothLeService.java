/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.beestar.jzb.goglebleweather.Service;

import android.annotation.SuppressLint;
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
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.utils.SampleGattAttributes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static com.beestar.jzb.goglebleweather.Service.Constants.ACTION_GATT_DISCONNECTING;

/**
 * Service for managing connection and data communication with a GATT server hosted on a
 * given Bluetooth LE device.
 */
@SuppressLint("NewApi")
public class BluetoothLeService extends Service {
    private final static String TAG = BluetoothLeService.class.getSimpleName();

    private BluetoothManager mBluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private String mBluetoothDeviceAddress;
    private BluetoothGatt mBluetoothGatt;
    private int mConnectionState = STATE_DISCONNECTED;
    private Map<String, BluetoothGatt> mBluetoothGattMap;
    private List<String> mConnectedAddressList;
    private static final int MAX_CONNECT_NUM = 16;

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTING = 1;
    private static final int STATE_CONNECTED = 2;

    public final static String ACTION_GATT_CONNECTED           = "com.example.bluetooth.le.ACTION_GATT_CONNECTED";//已连接
    public final static String ACTION_GATT_DISCONNECTED        = "com.example.bluetooth.le.ACTION_GATT_DISCONNECTED";//已断开
    public final static String ACTION_GATT_SERVICES_DISCOVERED = "com.example.bluetooth.le.ACTION_GATT_SERVICES_DISCOVERED";
    public final static String ACTION_DATA_AVAILABLE           = "com.example.bluetooth.le.ACTION_DATA_AVAILABLE";
    public final static String EXTRA_DATA                      = "com.example.bluetooth.le.EXTRA_DATA";
    public final static String SEND_DATA                       ="com.example.bluetooth.le.SEND_DATA";

    public final static UUID UUID_HEART_RATE_MEASUREMENT       = UUID.fromString(SampleGattAttributes.HEART_RATE_MEASUREMENT);
    private boolean bool;
    MyBlueToothInterface.OnConnectionStateChangeListener mOnConnectionStateChange;
    MyBlueToothInterface.OnDiscoverServiceListener mOnDiscoverServiceListener;
    MyBlueToothInterface.OnBleSendToPhoneDataListener mOnBleSendToPhoneDataListener;
    MyBlueToothInterface.OnBlueToothConnectDeviceListener mOnBlueToothConnectDeviceListener;
    MyBlueToothInterface.OnBlueToothServiceDiscoverLister mOnBlueToothServiceDiscoverLister;

    // Implements callback methods for GATT events that the app cares about.  For example,
    // connection change and services discovered.
    private final BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            mOnConnectionStateChange.OnConnectionStateChange(gatt,status,newState);
            String intentAction;
            String tmpAddress=gatt.getDevice().getAddress();
            L.i("-----------------连接设备地址-----"+tmpAddress);
            if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                intentAction = ACTION_GATT_DISCONNECTED;
                //连接失败
                Log.i(TAG, "Disconnected from GATT server.");
                broadcastUpdate(intentAction, tmpAddress);
                close(tmpAddress);
            }
             else if (newState == BluetoothProfile.STATE_CONNECTED) {
                //连接成功
                mConnectedAddressList.add(tmpAddress);
                intentAction = ACTION_GATT_CONNECTED;
                broadcastUpdate(intentAction, tmpAddress);
                Log.i(TAG, "Connected to GATT server.");
                // Attempts to discover services after successful connection.
                Log.i(TAG, "Attempting to start service discovery:" +
                        mBluetoothGattMap.get(tmpAddress).discoverServices());
            } else if (newState == BluetoothProfile.STATE_DISCONNECTING) {
                //断开连接中
                mConnectedAddressList.remove(tmpAddress);
                intentAction = ACTION_GATT_DISCONNECTING;
                Log.i(TAG, "Disconnecting from GATT server.");
                broadcastUpdate(intentAction, tmpAddress);
            }
//            if (newState == BluetoothProfile.STATE_CONNECTED) {
//                intentAction = ACTION_GATT_CONNECTED;
//                mConnectionState = STATE_CONNECTED;
//                broadcastUpdate(intentAction);
//                Log.i(TAG, "Connected to GATT server.");
//                // Attempts to discover services after successful connection.
//                Log.i(TAG, "Attempting to start service discovery:" +
//                        mBluetoothGattMap.get(tmpAddress).discoverServices());
//                L.i("已连接===================================");
//            } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
//                intentAction = ACTION_GATT_DISCONNECTED;
//                mConnectionState = STATE_DISCONNECTED;
//                Log.i(TAG, "Disconnected from GATT server.");
//                broadcastUpdate(intentAction);
//                L.i("已断开===================================");
//                if (mBluetoothGattMap.get(tmpAddress) == null) {
//                    return;
//                }
//                mBluetoothGattMap.get(tmpAddress).close();
//                mBluetoothGattMap.remove(tmpAddress);
//            }
        }

        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                broadcastUpdate(ACTION_GATT_SERVICES_DISCOVERED);
                if (mOnBlueToothServiceDiscoverLister != null) {
                    mOnBlueToothServiceDiscoverLister.OnBlueToothServicelist(gatt, status);
                }
                displayGattServices(gatt,getSupportedGattServices(gatt.getDevice().getAddress()));
            } else {
                Log.w(TAG, "onServicesDiscovered received: " + status);
            }
        }

        @Override
        public void onCharacteristicRead(BluetoothGatt gatt,
                                         BluetoothGattCharacteristic characteristic,
                                         int status) {
            if (status == BluetoothGatt.GATT_SUCCESS) {
                L.i("发送数据回去-----------------------");
                broadcastUpdate(ACTION_DATA_AVAILABLE, gatt,characteristic);
            }
        }

        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt,
                                            BluetoothGattCharacteristic characteristic) {
            broadcastUpdate(ACTION_DATA_AVAILABLE,gatt, characteristic);
        }
    };

    private void broadcastUpdate(final String action) {
        final Intent intent = new Intent(action);
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
                mOnBleSendToPhoneDataListener.OnBleSendToPhoneData(gatt,bytesToHexString(data));
            }
        }
        sendBroadcast(intent);
    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
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
        close();
        return super.onUnbind(intent);
    }

    private final IBinder mBinder = new LocalBinder();

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        L.i("----------蓝牙服务开启-------");
        return START_REDELIVER_INTENT;
    }

    @Override
    public void onCreate() {
        super.onCreate();
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

        if (getConnectDevices().size() > MAX_CONNECT_NUM) return false;
        if (mConnectedAddressList == null) {
            mConnectedAddressList = new ArrayList<>();
        }
        if (mConnectedAddressList.contains(address)) {
            Log.d(TAG, "This is device already connected.");
            return true;
        }
        if (mBluetoothAdapter == null || address == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        }
        //Previously connected device.  Try to reconnect.
        if (mBluetoothGattMap == null) {
            mBluetoothGattMap = new HashMap<>();
        }
        if (mBluetoothGattMap.get(address) != null && mConnectedAddressList.contains(address)) {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (mBluetoothGattMap.get(address).connect()) {
                return true;
            } else {
                return false;
            }
        }
        final BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if (device == null) {
            Log.w(TAG, "Device not found. Unable to connect.");
            return false;
        }
        //We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        BluetoothGatt bluetoothGatt = device.connectGatt(this, false, mGattCallback);
        if (bluetoothGatt != null) {
            mBluetoothGattMap.put(address, bluetoothGatt);
            Log.d(TAG, "Trying to create a new connection.");
            if (!mConnectedAddressList.contains(address)){
                mConnectedAddressList.add(address);
            }
            return true;
        }
        return false;
    }
    private void broadcastUpdate(final String action, final String address) {
        final Intent intent = new Intent(action);
        intent.putExtra("address", address);
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
    /**
     * Disconnects an existing connection or cancel a pending connection. The disconnection result
     * is reported asynchronously through the
     * {@code BluetoothGattCallback#onConnectionStateChange(android.bluetooth.BluetoothGatt, int, int)}
     * callback.
     */
    public void disconnect(String address) {
        if (mBluetoothAdapter == null || mBluetoothGattMap.get(address) == null) {
            Log.e(TAG, "BluetoothAdapter not initialized.2");
            return;
        }
        mBluetoothGattMap.get(address).disconnect();
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     * <p>
     * Close this Bluetooth GATT client.
     *
     * @param address You will close Gatt client's address.
     */
    public void close(String address) {
        mConnectedAddressList.remove(address);
        if (mBluetoothGattMap.get(address) != null) {
            mBluetoothGattMap.get(address).close();
            mBluetoothGattMap.remove(address);
        }
    }

    /**
     * After using a given BLE device, the app must call this method to ensure resources are
     * released properly.
     */
    public void close() {
        if (mConnectedAddressList == null) return;
        for (String address :
                mConnectedAddressList) {
            if (mBluetoothGattMap.get(address) != null) {
                mBluetoothGattMap.get(address).close();
            }
        }
        mBluetoothGattMap.clear();
        mConnectedAddressList.clear();
    }

    /**
     * Request a read on a given {@code BluetoothGattCharacteristic}. The read result is reported
     * asynchronously through the {@code BluetoothGattCallback#onCharacteristicRead(android.bluetooth.BluetoothGatt, android.bluetooth.BluetoothGattCharacteristic, int)}
     * callback.
     *
     * @param characteristic The characteristic to read from.
     */
    public void readCharacteristic(String address,BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGattMap.get(address) == null) {
            Log.w(TAG, "BluetoothAdapter not initialized3.");
            return;
        }
        mBluetoothGattMap.get(address).readCharacteristic(characteristic);
    }
    //写入指定的characteristic
    public void writeCharacteristic(String address,BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized4");
            return;
        }
        mBluetoothGattMap.get(address).writeCharacteristic(characteristic);
    }

    /**
     * 写入数据
     * @param address 设备地址
     * @param serviceUUID
     * @param characteristicUUID
     * @param value
     */
    public void writeValue(String address,String serviceUUID, String characteristicUUID,
                           byte[] value) {
        if (!mBluetoothGattMap.containsKey(address))
        {
            disconnect(address);
            return;
        }
        L.i("====size============="+mBluetoothGattMap.containsKey(address));
        L.i("====size============="+mBluetoothGattMap.values().contains(address));
        L.i("====size============="+mBluetoothGattMap.get(address).getServices().size()+mBluetoothGattMap.values().contains(address));
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
                    bool=mBluetoothGattMap.get(address).writeCharacteristic(bluetoothGattCharacteristic);
                    Log.i("bool","bool："+bool);
                }
            }
        }
        if (bytesToHexString(value).equals("CC")||bytesToHexString(value).equals("cc")){
            try {
                Thread.sleep(3000);
                disconnect(address);
            } catch (InterruptedException e) {
                e.printStackTrace();
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
        if (mBluetoothAdapter==null){
            L.i("adapter---------------------空");
        }
        if (mBluetoothAdapter == null || mBluetoothGattMap.get(address) == null) {
            Log.w(TAG, "BluetoothAdapter not initialized5");
            return;
        }
        mBluetoothGattMap.get(address).setCharacteristicNotification(characteristic, enabled);

        // This is specific to Heart Rate Measurement.
        if (UUID_HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(
                    UUID.fromString(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG));
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            mBluetoothGattMap.get(address).writeDescriptor(descriptor);
        }
    }
    private void displayGattServices(BluetoothGatt gatt,List<BluetoothGattService> gattServices) {
        L.i("这里可以解析服务------------displayGattServices-------"+gattServices.size());
        if (gattServices == null)
            return;
        mOnDiscoverServiceListener.OnDiscoverService(gatt,gattServices);//接口回调返回服务列表
    }
    /**
     * Retrieves a list of supported GATT services on the connected device. This should be
     * invoked only after {@code BluetoothGatt#discoverServices()} completes successfully.
     *
     * @return A {@code List} of supported services.
     */
    public List<BluetoothGattService> getSupportedGattServices(String address) {
        if (mBluetoothGattMap.get(address) == null) return null;
        return mBluetoothGattMap.get(address).getServices();

    }
    private IntentFilter getFilter() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(SEND_DATA);
        return intentFilter;
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    BroadcastReceiver mReceiver =new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(SEND_DATA)){
                Log.i("info","-----------------发送数据------------"+intent.getStringExtra("data")+intent.getStringExtra("address"));
                Toast.makeText(getApplicationContext(),"发送成功",Toast.LENGTH_LONG).show();
                writeValue(intent.getStringExtra("address"),"ffe5", "ffe9", hexStringToBytes(intent.getStringExtra("data")));
            }
        }
    };
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
}
