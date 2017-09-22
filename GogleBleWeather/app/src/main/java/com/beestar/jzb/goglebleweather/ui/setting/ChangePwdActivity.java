package com.beestar.jzb.goglebleweather.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;

public class ChangePwdActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * 请输入旧密码
     */
    private EditText mOldPwd;
    /**
     * 请输入密码
     */
    private EditText mNewPwd;
    /**
     * 请确认密码
     */
    private EditText mNewPwdNext;
    /**
     * 保存
     */
    private Button mSubmitPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pwd);
        initView();

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mOldPwd = (EditText) findViewById(R.id.old_pwd);
        mNewPwd = (EditText) findViewById(R.id.new_pwd);
        mNewPwdNext = (EditText) findViewById(R.id.new_pwd_next);
        mSubmitPwd = (Button) findViewById(R.id.submit_pwd);
        mSubmitPwd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                break;
            case R.id.submit_pwd:
                break;
        }
    }
}
