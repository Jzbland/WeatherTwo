package com.dialog.suota.bluetooth;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.util.Log;

import com.dialog.suota.DeviceActivity;
import com.dialog.suota.async.DeviceConnectTask;
import com.dialog.suota.data.Statics;

import java.math.BigInteger;

/**
 * Created by wouter on 9-10-14.
 */
public class Callback extends BluetoothGattCallback {
    public static String TAG = "Callback";
    DeviceConnectTask task;

    public Callback(DeviceConnectTask task) {
        this.task = task;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status,
                                        int newState) {
        Log.i(TAG, "le onConnectionStateChange [" + newState + "]");
        if (newState == BluetoothProfile.STATE_CONNECTED) {
            Log.i(TAG, "le device connected");
            gatt.discoverServices();

            /*Log.i(TAG, "onServicesDiscovered");
			BluetoothGattSingleton.setGatt(gatt);
            Intent intent = new Intent();
            intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
            intent.putExtra("step", 0);
            task.context.sendBroadcast(intent);*/

        } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
            Log.i(TAG, "le device disconnected");
        }
        Intent intent = new Intent();
        intent.setAction(Statics.CONNECTION_STATE_UPDATE);
        intent.putExtra("state", newState);
        task.context.sendBroadcast(intent);
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        Log.i(TAG, "onServicesDiscovered");
        BluetoothGattSingleton.setGatt(gatt);
        Intent intent = new Intent();
        intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
        intent.putExtra("step", 0);
        task.context.sendBroadcast(intent);
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        boolean sendUpdate = true;
        int index = -1;
        int step = -1;

        if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_MANUFACTURER_NAME_STRING)) {
            index = 0;
        } else if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_MODEL_NUMBER_STRING)) {
            index = 1;
        } else if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_FIRMWARE_REVISION_STRING)) {
            index = 2;
            String s = new String(characteristic.getValue());
            Log.d(TAG, "onCharacteristicRead: "+s);
        } else if (characteristic.getUuid().equals(Statics.ORG_BLUETOOTH_CHARACTERISTIC_SOFTWARE_REVISION_STRING)) {
            index = 3;
        }
        // SPOTA
        else if (characteristic.getUuid().equals(Statics.SPOTA_MEM_INFO_UUID)) {
//			int memInfoValue = characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT32, 0);
//			Log.d("mem info", memInfoValue + "");
//			DeviceActivity.getInstance().logMemInfoValue(memInfoValue);
            step = 5;
            Log.i(TAG, "onCharacteristicRead: 开始低5步2");
        } else {
            sendUpdate = false;
        }

        if (sendUpdate) {
            Log.d(TAG, "onCharacteristicRead: " + index);
            Intent intent = new Intent();
            intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
            if (index >= 0) {
                intent.putExtra("characteristic", index);
                intent.putExtra("value", new String(characteristic.getValue()));
            } else {
                intent.putExtra("step", step);
                intent.putExtra("value", characteristic.getIntValue(BluetoothGattCharacteristic.FORMAT_UINT32, 0));
            }
            task.context.sendBroadcast(intent);
        }

        super.onCharacteristicRead(gatt, characteristic, status);
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        Log.d(TAG, "onCharacteristicWrite: " + characteristic.getUuid().toString());

        if (status == BluetoothGatt.GATT_SUCCESS) {
            Log.i(TAG, "write succeeded");
            int step = -1;
            // Step 3 callback: write SPOTA_GPIO_MAP_UUID value
            if (characteristic.getUuid().equals(Statics.SPOTA_GPIO_MAP_UUID)) {
                step = 4;
                Log.i(TAG, "onCharacteristicWrite:开始第四步");
            }
            // Step 4 callback: set the patch length, default 240
            else if (characteristic.getUuid().equals(Statics.SPOTA_PATCH_LEN_UUID)) {
                step = DeviceActivity.getInstance().bluetoothManager.type == SuotaManager.TYPE ? 5 : 7;
                Log.i(TAG, "onCharacteristicWrite: 开始低5步3");
            } else if (characteristic.getUuid().equals(Statics.SPOTA_MEM_DEV_UUID)) {
            }
            else if (characteristic.getUuid().equals(Statics.SPOTA_PATCH_DATA_UUID)
                    //&& DeviceActivity.getInstance().bluetoothManager.type == SuotaManager.TYPE
                    && DeviceActivity.getInstance().bluetoothManager.chunkCounter != -1
                    ) {
                //step = DeviceActivity.getInstance().bluetoothManager.type == SuotaManager.TYPE ? 5 : 7;
                /*DeviceActivity.getInstance().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DeviceActivity.getInstance().bluetoothManager.sendBlock();
                    }
                });*/
                Log.i(TAG, "onCharacteristicWrite: 发送下一个block 在chunk里面");
                Log.d(TAG, "Next block in chunk " + DeviceActivity.getInstance().bluetoothManager.chunkCounter);
                DeviceActivity.getInstance().bluetoothManager.sendBlock();
            }

            if (step > 0) {
                Intent intent = new Intent();
                intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
                intent.putExtra("step", step);
                task.context.sendBroadcast(intent);
            }
        } else {
            Log.e(TAG, "write failed: " + status);
        }
        super.onCharacteristicWrite(gatt, characteristic, status);
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);
        Log.d(TAG, "onDescriptorWrite");
        if (descriptor.getCharacteristic().getUuid().equals(Statics.SPOTA_SERV_STATUS_UUID)) {
            int step = 2;
            Log.i(TAG, "onDescriptorWrite: 开始第二步");
            Intent intent = new Intent();
            intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
            intent.putExtra("step", step);
            task.context.sendBroadcast(intent);
        }
        task.publishProgess(gatt);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);
        int value = new BigInteger(characteristic.getValue()).intValue();
        String stringValue = String.format("%#10x", value);
        Log.d("changed", stringValue);

        int step = -1;
        int error = -1;
        int memDevValue = -1;
        // Set memtype callback
        if (stringValue.trim().equals("0x10")) {
            step = 3;
            Log.i(TAG, "onCharacteristicChanged: 开始第三步");
        }
        // Successfully sent a block, send the next one
        else if (stringValue.trim().equals("0x2")) {
            step = DeviceActivity.getInstance().bluetoothManager.type == SuotaManager.TYPE ? 5 : 8;
            Log.i(TAG, "onCharacteristicChanged: 开始低5步1");
        } else if (stringValue.trim().equals("0x3") || stringValue.trim().equals("0x1")) {
            memDevValue = value;
        } else {
            error = Integer.parseInt(stringValue.trim().replace("0x", ""));
        }
        if (step >= 0 || error >= 0 || memDevValue >= 0) {
            Intent intent = new Intent();
            intent.setAction(Statics.BLUETOOTH_GATT_UPDATE);
            intent.putExtra("step", step);
            intent.putExtra("error", error);
            intent.putExtra("memDevValue", memDevValue);
            task.context.sendBroadcast(intent);
        }
    }
}