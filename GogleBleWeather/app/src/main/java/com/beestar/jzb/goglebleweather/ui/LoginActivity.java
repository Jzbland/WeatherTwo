package com.beestar.jzb.goglebleweather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.bean.Registe_UserInfo;
import com.beestar.jzb.goglebleweather.bean.ReturnBean;
import com.beestar.jzb.goglebleweather.utils.URL;
import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

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
                if (TextUtils.isEmpty(mUsernameView.getText())|| TextUtils.isEmpty(mPasswordView.getText())){
                    Toast.makeText(getApplication(),"请输入完整信息",Toast.LENGTH_SHORT).show();
                }else {
                    loginUser();
                }
                break;
        }
    }
    private void loginUser(){
        MyApp.getContext().getMyOkHttp().post()
                                        .url(URL.url+URL.url_login)
                                        .tag(this)
                                        .jsonParams(new Gson().toJson(new Registe_UserInfo(mUsernameView.getText().toString().trim(),mPasswordView.getText().toString().trim())))
                                        .enqueue(new GsonResponseHandler<ReturnBean>() {
                                            @Override
                                            public void onFailure(int statusCode, String error_msg) {

                                            }

                                            @Override
                                            public void onSuccess(int statusCode, ReturnBean response) {
                                                if (response.getRtn_code()==0){
                                                    Toast.makeText(getApplication(),response.getMsg(),Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });

    }
}
