package com.beestar.jzb.goglebleweather.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.bean.ReturnBean;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.utils.CountDownButtonHelper;
import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.utils.URL;
import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;

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
    /**
     * 《用户协议》
     */
    private TextView mShowWeb1;
    /**
     * 《版权说明》
     */
    private TextView mShowWeb2;

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
        mShowWeb1 = (TextView) findViewById(R.id.showWeb1);
        mShowWeb1.setOnClickListener(this);
        mShowWeb2 = (TextView) findViewById(R.id.showWeb2);
        mShowWeb2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                RegisterActivity_step_one.this.finish();
                break;
            case R.id.get_sms_code:
                CountDownButtonHelper helper = new CountDownButtonHelper(mGetSmsCode,
                        "发送验证码", 60, 1);
                helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

                    @Override
                    public void finish() {
                        Toast.makeText(RegisterActivity_step_one.this, "倒计时结束",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                helper.start();
                getSms();
                break;
            case R.id.register_button:
                if (!TextUtils.isEmpty(mTelRegister.getText())
                        && !TextUtils.isEmpty(mPwd.getText())
                        && !TextUtils.isEmpty(mPwdNext.getText())
                        && !TextUtils.isEmpty(mSmsCode.getText())
                        ) {
                    if (mPwd.getText().toString().trim().equals(mPwdNext.getText().toString().trim())) {
                        Intent intent = new Intent(RegisterActivity_step_one.this, RegistertwoActivity.class);
                        intent.putExtra("phone", mTelRegister.getText().toString().trim());
                        intent.putExtra("pwd", mPwd.getText().toString().trim());
                        intent.putExtra("confirm", mPwdNext.getText().toString().trim());
                        intent.putExtra("code", mSmsCode.getText().toString().trim());
                        startActivity(intent);
                    } else {
                        Toast.makeText(MyApp.getContext().getApplicationContext(), "两次密码不一致", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(MyApp.getContext().getApplicationContext(), "请输入完整信息", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.showWeb1:
                    Intent it=new Intent(RegisterActivity_step_one.this,WebActivity.class);
                    it.putExtra("page",1);
                    startActivity(it);
                break;
            case R.id.showWeb2:
                    Intent it2=new Intent(RegisterActivity_step_one.this,WebActivity.class);
                    it2.putExtra("page",2);
                    startActivity(it2);
                break;
        }
    }

    /**
     * 获取短信验证码
     */
    private void getSms() {
        L.i(mTelRegister.getText().toString().trim() + "----tel---");
        MyApp.getContext().getMyOkHttp().get()
                .url(URL.url + URL.url_sms)
                .addParam("phone", mTelRegister.getText().toString().trim())
                .tag(this)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        ReturnBean returnBean = new Gson().fromJson(response, ReturnBean.class);
                        if (returnBean.getRtn_code() == 0) {
                            L.i(returnBean.getMsg().toString());
                            Toast.makeText(MyApp.getContext().getApplicationContext(), "短信发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            L.i(returnBean.getMsg().toString());
                            Toast.makeText(MyApp.getContext().getApplicationContext(), "短信发送失败，请检查您的手机号...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Toast.makeText(MyApp.getContext().getApplicationContext(), "短信发送失败，请重试", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
