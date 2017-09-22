package com.beestar.jzb.goglebleweather.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.haveLogin.SetMusicActivity;
import com.beestar.jzb.goglebleweather.ui.setting.UnbindActivity;
import com.zhy.android.percent.support.PercentRelativeLayout;

public class AntiLostActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private ImageButton mAdd;
    private ImageView mNotdisturbmeImage;
    /**
     * 定时勿扰
     */
    private TextView mNotdisturbmeText;
    private LinearLayout mNotdisturbme;
    private ImageView mRingImage;
    /**
     * 响铃
     */
    private TextView mRingText;
    private LinearLayout mRing;
    private ImageView mUnbindImage;
    /**
     * 解绑
     */
    private TextView mUnbindText;
    private LinearLayout mUnbind;
    private ImageView mBlueEditImage;
    /**
     * 蓝牙编辑
     */
    private TextView mBlueEditText;
    private LinearLayout mBlueEdit;
    /**
     * 蓝牙已连接
     */
    private TextView mBlueStatus;
    /**
     * 电量65%
     */
    private TextView mBatteryStatus;
    /**
     * 道具库
     */
    private TextView mDeviceName;
    /**
     * 钥匙
     */
    private TextView mGoodsName;
    /**
     * 看管中
     */
    private TextView mGoodsStatus;
    private PercentRelativeLayout mUnlostInfor;
    /**
     * 呼叫
     */
    private Button mCallButton;
    private ListView mDeviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anti_lost);
        initView();

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mAdd = (ImageButton) findViewById(R.id.add);
        mNotdisturbmeImage = (ImageView) findViewById(R.id.notdisturbme_image);
        mNotdisturbmeText = (TextView) findViewById(R.id.notdisturbme_text);
        mNotdisturbme = (LinearLayout) findViewById(R.id.notdisturbme);
        mNotdisturbme.setOnClickListener(this);
        mRingImage = (ImageView) findViewById(R.id.ring_image);
        mRingText = (TextView) findViewById(R.id.ring_text);
        mRing = (LinearLayout) findViewById(R.id.ring_);
        mRing.setOnClickListener(this);
        mUnbindImage = (ImageView) findViewById(R.id.unbind_image);
        mUnbindText = (TextView) findViewById(R.id.unbind_text);
        mUnbind = (LinearLayout) findViewById(R.id.unbind_);
        mUnbind.setOnClickListener(this);
        mBlueEditImage = (ImageView) findViewById(R.id.blue_edit_image);
        mBlueEditText = (TextView) findViewById(R.id.blue_edit_text);
        mBlueEdit = (LinearLayout) findViewById(R.id.blue_edit_);
        mBlueEdit.setOnClickListener(this);
        mBlueStatus = (TextView) findViewById(R.id.blue_status);
        mBatteryStatus = (TextView) findViewById(R.id.battery_status);
        mDeviceName = (TextView) findViewById(R.id.device_Name);
        mGoodsName = (TextView) findViewById(R.id.goods_Name);
        mGoodsStatus = (TextView) findViewById(R.id.goods_status);
        mUnlostInfor = (PercentRelativeLayout) findViewById(R.id.unlost_infor);
        mCallButton = (Button) findViewById(R.id.call_button);
        mCallButton.setOnClickListener(this);
        mDeviceList = (ListView) findViewById(R.id.device_list);
        mAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                break;
            case R.id.notdisturbme:
                break;
            case R.id.ring_:
                startActivity(new Intent(AntiLostActivity.this,SetMusicActivity.class));
                break;
            case R.id.unbind_:
                startActivity(new Intent(AntiLostActivity.this,UnbindActivity.class));
                break;
            case R.id.blue_edit_:
                break;
            case R.id.call_button:
                break;
            case R.id.add:
                startActivity(new Intent(AntiLostActivity.this,BindingActivity.class));
                break;
        }
    }
}
