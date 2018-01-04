package com.beestar.jzb.goglebleweather.Service;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattService;

import java.util.List;

/**
 * Created by jzb on 2017/6/29.
 */

public interface MyBlueToothInterface {
     interface OnScanGetDeviceListener{
         //监听扫描借口
        void OnSlcanGetDeviceSuccess(BluetoothDevice device, int rssi, byte[] scanRecord);
    }
    interface OnConnectionStateChangeListener{
        void OnConnectionStateChange(BluetoothGatt gatt, int status, int newState);
    }
    interface OnDiscoverServiceListener{
        void OnDiscoverService(BluetoothGatt gatt,List<BluetoothGattService> gattServices);
    }
    interface OnBleSendToPhoneDataListener{
        void OnBleSendToPhoneData(BluetoothGatt gatt,String data);
    }
    //获取已连接设备
    interface OnBlueToothConnectDeviceListener{
        void OnBluetoothConnectList(List<BluetoothDevice> bluetoothDeviceList);
    }
    interface OnScanStopListener{
        void OnBlueScanStopListener(boolean status);
    }
    //获取蓝牙服务
    interface OnBlueToothServiceDiscoverLister{
        void OnBlueToothServicelist(BluetoothGatt gatt,int status);
    }
    //获取已连接设备gatt
    interface OnBlueToothConnectGattListener{
        void OnBlueToothGattList(List<BluetoothGatt> gattList);
    }
}
