package com.beestar.jzb.goglebleweather.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.bean.Registe_UserInfo;
import com.beestar.jzb.goglebleweather.bean.ReturnBean;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.ui.MainActivity;
import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.utils.URL;
import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

public class RememberPwd extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * 请输入您的手机号码
     */
    private EditText mRemenberTel;
    /**
     * 请输入新密码
     */
    private EditText mRememberNewPwd;
    /**
     * 再次输入新密码
     */
    private EditText mRememberConfimPwd;
    /**
     * 验证码
     */
    private EditText mRememberSmsCode;
    /**
     * 获取验证码
     */
    private Button mGetSmsCodeRemember;
    /**
     * 重置密码
     */
    private Button mRememberButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remember_pwd);
        initView();

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mRemenberTel = (EditText) findViewById(R.id.remenber_tel);
        mRememberNewPwd = (EditText) findViewById(R.id.remember_new_pwd);
        mRememberConfimPwd = (EditText) findViewById(R.id.remember_confim_pwd);
        mRememberSmsCode = (EditText) findViewById(R.id.remember_sms_code);
        mGetSmsCodeRemember = (Button) findViewById(R.id.get_sms_code_remember);
        mGetSmsCodeRemember.setOnClickListener(this);
        mRememberButton = (Button) findViewById(R.id.remember_button);
        mRememberButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.get_sms_code_remember:
                getSms();
                break;
            case R.id.remember_button:
                if (TextUtils.isEmpty(mRemenberTel.getText())||TextUtils.isEmpty(mRememberSmsCode.getText())
                        ||TextUtils.isEmpty(mRememberNewPwd.getText())||TextUtils.isEmpty(mRememberConfimPwd.getText())){
                    Toast.makeText(getApplicationContext(),"请填写完整信息",Toast.LENGTH_SHORT).show();
                }else {
                   L.i(new Gson().toJson(new Registe_UserInfo(mRemenberTel.getText().toString().trim(),
                           mRememberConfimPwd.getText().toString().trim(),
                           mRememberNewPwd.getText().toString().trim(),
                           mRememberSmsCode.getText().toString().trim()

                   ))+"--json");
                    MyApp.getContext().getMyOkHttp().post()
                            .url(URL.url+URL.url_forgot)
                            .jsonParams(new Gson().toJson(new Registe_UserInfo(mRemenberTel.getText().toString().trim(),
                                    mRememberConfimPwd.getText().toString().trim(),
                                    mRememberNewPwd.getText().toString().trim(),
                                     mRememberSmsCode.getText().toString().trim()

                            )))
                            .tag(this)
                            .enqueue(new RawResponseHandler() {
                                @Override
                                public void onSuccess(int statusCode, String response) {
                                    ReturnBean returnBean = new Gson().fromJson(response, ReturnBean.class);
                                    if (returnBean.getRtn_code()==0){
                                        startActivity(new Intent(RememberPwd.this,MainActivity.class));
                                        Toast.makeText(RememberPwd.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(RememberPwd.this,returnBean.getMsg(),Toast.LENGTH_SHORT).show();
                                        L.i(returnBean.getMsg()+"---"+returnBean.getRtn_code());
                                    }
                                }
                                @Override
                                public void onFailure(int statusCode, String error_msg) {

                                }
                            });
                }


                break;
        }
    }
    /**
     * 获取短信验证码
     */
    private void getSms(){
        L.i(mRemenberTel.getText().toString().trim()+"----tel---");
        MyApp.getContext().getMyOkHttp().get()
                .url(URL.url+URL.url_sms)
                .addParam("phone",mRemenberTel.getText().toString().trim())
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        ReturnBean returnBean = new Gson().fromJson(response, ReturnBean.class);
                        if (returnBean.getRtn_code()==0){
                            L.i(returnBean.getMsg().toString());
                        }else {
                            L.i(returnBean.getMsg().toString());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {

                    }
                });
    }
}
