package com.beestar.jzb.goglebleweather.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.bean.ChangePwd;
import com.beestar.jzb.goglebleweather.bean.ReturnBean;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.utils.SPUtils;
import com.beestar.jzb.goglebleweather.utils.URL;
import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.response.GsonResponseHandler;

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
                finish();
                break;
            case R.id.submit_pwd:
                MyApp.getContext().getMyOkHttp().post()
                        .url(URL.url+URL.url_change)
                        .jsonParams(new Gson().toJson(new ChangePwd((String)SPUtils.get(MyApp.getContext(),"iphone",""),mOldPwd.getText().toString().trim(),
                                mNewPwd.getText().toString().trim(),mSubmitPwd.getText().toString().trim()) ))
                        .tag(this)
                        .enqueue(new GsonResponseHandler<ReturnBean>() {
                            @Override
                            public void onFailure(int statusCode, String error_msg) {
                                Toast.makeText(getApplicationContext(),error_msg,Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(int statusCode, ReturnBean response) {
                                if (response.getRtn_code()==0){
                                    Toast.makeText(getApplicationContext(),response.getMsg(),Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                finish();
                break;
        }
    }
}
