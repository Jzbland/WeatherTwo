package com.beestar.jzb.goglebleweather.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.BluetoothLeService;
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
                Toast.makeText(UnbindActivity.this, "正在解绑请稍候...", Toast.LENGTH_SHORT).show();
                sendData(unbinddevice.getMac(),"CC");
                if (unbinddevice == null) {
                    Toast.makeText(UnbindActivity.this, "用户不存在", Toast.LENGTH_SHORT).show();
                }else{
                    deviceBeanDao.deleteByKey(unbinddevice.getId());
                }
                startActivity(new Intent(UnbindActivity.this, AntiLostActivity.class));
                break;
            case R.id.unbind_no_button:
                finish();
                break;
        }
    }
    public void sendData(String address,String data){
        Intent intent=new Intent();
        intent.setAction(BluetoothLeService.SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);
    }
}
