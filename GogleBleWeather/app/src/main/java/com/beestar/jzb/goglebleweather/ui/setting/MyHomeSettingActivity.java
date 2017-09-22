package com.beestar.jzb.goglebleweather.ui.setting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;

public class MyHomeSettingActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    private ImageView mTurnPic1;
    private RelativeLayout mSetIcon;
    private ImageView mTurnPic2;
    /**
     * 用户名
     */
    private TextView mSetName;
    private RelativeLayout mSettingName;
    private ImageView mTurnPic3;
    /**
     * 男
     */
    private TextView mSetSex;
    private RelativeLayout mSettingSex;
    private ImageView mTurnPic4;
    /**
     * 123456789
     */
    private TextView mSetNumber;
    private RelativeLayout mSettingNumber;
    private RelativeLayout mChangePwd;
    private RelativeLayout mUnbindSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_home_setting);
        initView();

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mTurnPic1 = (ImageView) findViewById(R.id.turn_pic_1);
        mSetIcon = (RelativeLayout) findViewById(R.id.setIcon);
        mSetIcon.setOnClickListener(this);
        mTurnPic2 = (ImageView) findViewById(R.id.turn_pic_2);
        mSetName = (TextView) findViewById(R.id.set_Name);
        mSettingName = (RelativeLayout) findViewById(R.id.setting_Name);
        mSettingName.setOnClickListener(this);
        mTurnPic3 = (ImageView) findViewById(R.id.turn_pic_3);
        mSetSex = (TextView) findViewById(R.id.set_Sex);
        mSettingSex = (RelativeLayout) findViewById(R.id.setting_Sex);
        mSettingSex.setOnClickListener(this);
        mTurnPic4 = (ImageView) findViewById(R.id.turn_pic_4);
        mSetNumber = (TextView) findViewById(R.id.set_Number);
        mSettingNumber = (RelativeLayout) findViewById(R.id.setting_Number);
        mSettingNumber.setOnClickListener(this);
        mChangePwd = (RelativeLayout) findViewById(R.id.change_pwd);
        mUnbindSet = (RelativeLayout) findViewById(R.id.unbind_set);
        mUnbindSet.setOnClickListener(this);
        mChangePwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.setIcon:
                break;
            case R.id.setting_Name:
                break;
            case R.id.setting_Sex:
                break;
            case R.id.setting_Number:
                break;
            case R.id.unbind_set:
                startActivity(new Intent(MyHomeSettingActivity.this,UnbindActivity.class));
                break;
            case R.id.change_pwd:
                startActivity(new Intent(MyHomeSettingActivity.this,ChangePwdActivity.class));
                break;
        }
    }
}
