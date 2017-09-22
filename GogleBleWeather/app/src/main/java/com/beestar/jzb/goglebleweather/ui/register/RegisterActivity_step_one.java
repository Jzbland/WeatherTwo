package com.beestar.jzb.goglebleweather.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;

public class RegisterActivity_step_one extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * 请输入您的手机号码
     */
    private EditText mTelRegister;
    /**
     * 请输入密码
     */
    private EditText mPwd;
    /**
     * 再次输入密码
     */
    private EditText mPwdNext;
    /**
     * 验证码
     */
    private EditText mSmsCode;
    /**
     * 获取验证码
     */
    private Button mGetSmsCode;
    /**
     * 注册
     */
    private Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initView();
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mTelRegister = (EditText) findViewById(R.id.tel_register);
        mPwd = (EditText) findViewById(R.id.pwd);
        mPwdNext = (EditText) findViewById(R.id.pwd_next);
        mSmsCode = (EditText) findViewById(R.id.sms_code);
        mGetSmsCode = (Button) findViewById(R.id.get_sms_code);
        mGetSmsCode.setOnClickListener(this);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                RegisterActivity_step_one.this.finish();
                break;
            case R.id.get_sms_code:
                break;
            case R.id.register_button:
                startActivity(new Intent(RegisterActivity_step_one.this,RegistertwoActivity.class));
                break;
        }
    }
}
