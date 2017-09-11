package com.beestar.jzb.goglebleweather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.beestar.jzb.goglebleweather.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 请输入您的用户名
     */
    private EditText mUsernameView;
    /**
     * 请输入您的密码
     */
    private EditText mPasswordView;
    /**
     * 登录
     */
    private Button mOkButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        mUsernameView = (EditText) findViewById(R.id.usernameView);
        mPasswordView = (EditText) findViewById(R.id.passwordView);
        mOkButton = (Button) findViewById(R.id.okButton);
        mOkButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.okButton:
                break;
        }
    }
}
