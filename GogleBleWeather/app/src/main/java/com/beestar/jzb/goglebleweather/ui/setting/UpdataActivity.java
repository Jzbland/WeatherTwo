package com.beestar.jzb.goglebleweather.ui.setting;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.utils.MyFile;
import com.github.dfqin.grantor.PermissionListener;
import com.github.dfqin.grantor.PermissionsUtil;

public class UpdataActivity extends AppCompatActivity implements View.OnClickListener {
    private static String filesDir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/CSdowned";
    static UpdataActivity instance;
    BroadcastReceiver bluetoothGattReceiver;
    private ServiceConnection connection;
    private MyServiceBlueTooth mService;
    private DeviceBeanDao deviceBeanDao;
    DeviceBean unique;
    /**
     * step1
     */
    private Button mBtn1;
    /**
     * step2
     */
    private Button mBtn2;
    /**
     * step3
     */
    private Button mBtn3;
    /**
     * step4
     */
    private Button mBtn4;
    /**
     * step5
     */
    private Button mBtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updata);
        initView();

        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        unique = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
        instance = this;
        requestLocation();
        MyFile.createFileDirectories();
    }
    private void requestLocation() {
        if (PermissionsUtil.hasPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
//            Toast.makeText(this, "获取您的内存卡读权限", Toast.LENGTH_LONG).show();
        } else {
            PermissionsUtil.requestPermission(this, new PermissionListener() {
                @Override
                public void permissionGranted(@NonNull String[] permissions) {
                   Toast.makeText(getApplicationContext(), "用户授予获取内存卡读权限", Toast.LENGTH_LONG).show();
                }

                @Override
                public void permissionDenied(@NonNull String[] permissions) {
//                    Toast.makeText(getApplicationContext(), "用户拒绝了获取内存卡读权限", Toast.LENGTH_LONG).show();
                }
            }, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }


    private void initView() {
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn3.setOnClickListener(this);
        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn4.setOnClickListener(this);
        mBtn5 = (Button) findViewById(R.id.btn5);
        mBtn5.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                Intent intent=new Intent();
                intent.setAction(MyServiceBlueTooth.CESHI);
                intent.putExtra("address",unique.getMac());

                sendBroadcast(intent);
                break;
            case R.id.btn2:
                break;
            case R.id.btn3:
                break;
            case R.id.btn4:
                break;
            case R.id.btn5:
                Intent it=new Intent(MyServiceBlueTooth.GETBANBEN);
                sendBroadcast(it);
                break;
            case R.id.test:
                Intent intent1=new Intent(MyServiceBlueTooth.FILENAME);
                intent1.putExtra("filename","ble_app_ota_580.img");
                sendBroadcast(intent1);
                break;
        }
    }

}
