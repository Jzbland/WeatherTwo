package com.beestar.jzb.goglebleweather.Service;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jzb on 2017/6/29.
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class MyBluetoothScan {
    private static final long SCAN_PERIOD = 1000;
    Handler mHandler=new Handler();
    BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    MyBlueToothInterface.OnScanGetDeviceListener mOnScanGetDeviceListener;
    MyBlueToothInterface.OnScanStopListener mOnScanStopListener;
    private List<String> list=new ArrayList<>();


    @SuppressWarnings("deprecation")
    public void scanLeDevice(final boolean enable) {
        if (enable) {
            mHandler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void run() {
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD); //10秒后停止搜索
            mBluetoothAdapter.startLeScan(mLeScanCallback); //开始搜索
        } else {
            mBluetoothAdapter.stopLeScan(mLeScanCallback);//停止搜索
            mOnScanStopListener.OnBlueScanStopListener(false);
        }
    }
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, final int rssi, final byte[] scanRecord) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (device.getName()!=null&&device.getName().length()!=0){
                        if (device.getAddress()!=null){
                            Log.i("info","--------------设备名称-----------"+device.getName()+device.getAddress()+device.getUuids());
                            mOnScanGetDeviceListener.OnSlcanGetDeviceSuccess( device,rssi,scanRecord);
                        }
                    }
                }
            }).run();
        }
    };
    private String checkDecive(String address) {
        if (list.contains(address)){
            return null;
        }else {
            list.add(address);
            return address;
        }

    }

    public void setOnScanGetDeviceListener(MyBlueToothInterface.OnScanGetDeviceListener l){
        mOnScanGetDeviceListener=l;
    }
    public void setScanStopListener(MyBlueToothInterface.OnScanStopListener l){
        mOnScanStopListener=l;
    }
}
