package com.beestar.jzb.goglebleweather.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.ui.AntiLostActivity;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;

public class UnbindActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * 解除绑定
     */
    private Button mUnbindButton;
    /**
     * 取消
     */
    private Button mUnbindNoButton;
    private DeviceBeanDao deviceBeanDao;
    private DeviceBean unbinddevice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbind);
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        unbinddevice = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
        initView();

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mUnbindButton = (Button) findViewById(R.id.unbind_button);
        mUnbindButton.setOnClickListener(this);
        mUnbindNoButton = (Button) findViewById(R.id.unbind_no_button);
        mUnbindNoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.unbind_button:
                Toast.makeText(MyApp.getContext(), "正在解绑请稍候...", Toast.LENGTH_LONG).show();
                sendData(unbinddevice.getMac(),"CC");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent it=new Intent(MyServiceBlueTooth.DISCONNECTED);
                        it.putExtra("address",unbinddevice.getMac());
                        sendBroadcast(it);
                        Log.d("jzb", "unbind run: 发送断开连接通知");
                    }
                },3000);
                if (unbinddevice == null) {
                    Toast.makeText(UnbindActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                }else{
                    deviceBeanDao.deleteByKey(unbinddevice.getId());
                }
                try {
                    Thread.sleep(1000);
                    startActivity(new Intent(UnbindActivity.this, AntiLostActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.unbind_no_button:
                finish();
                break;
        }
    }
    public void sendData(String address,String data){
        Intent intent=new Intent();
        intent.setAction(MyServiceBlueTooth.SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);
    }
}
