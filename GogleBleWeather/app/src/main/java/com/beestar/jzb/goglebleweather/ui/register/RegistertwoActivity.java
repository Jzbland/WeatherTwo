package com.beestar.jzb.goglebleweather.ui.register;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.bean.Registe_UserInfo;
import com.beestar.jzb.goglebleweather.bean.ReturnBean;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.utils.Keyparameter;
import com.beestar.jzb.goglebleweather.utils.URL;
import com.google.gson.Gson;
import com.tsy.sdk.myokhttp.response.RawResponseHandler;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class RegistertwoActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackRegistTwo;
    /**
     * 请输入您的姓名
     */
    private EditText mRegisterName;
    /**
     * 男
     */
    private CheckBox mCheckboxMen;
    /**
     * 女
     */
    private CheckBox mCheckboxWomen;
    /**
     * 提交
     */
    private Button mSubmitData;
    private Intent getintent;
    private String  sex="0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registertwo);
        initView();
        getintent = getIntent();

    }

    private void initView() {
        mBackRegistTwo = (ImageView) findViewById(R.id.back_regist_two);
        mBackRegistTwo.setOnClickListener(this);
        mRegisterName = (EditText) findViewById(R.id.register_Name);
        mCheckboxMen = (CheckBox) findViewById(R.id.checkbox_men);
        mCheckboxMen.setOnClickListener(this);
        mCheckboxWomen = (CheckBox) findViewById(R.id.checkbox_women);
        mCheckboxWomen.setOnClickListener(this);
        mSubmitData = (Button) findViewById(R.id.submit_data);
        mSubmitData.setOnClickListener(this);
        mCheckboxMen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckboxMen.isChecked()){
                    mCheckboxWomen.setChecked(false);
                    sex="0";
                }
            }
        });
        mCheckboxWomen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCheckboxWomen.isChecked()){
                    mCheckboxMen.setChecked(false);
                    sex="1";
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_regist_two:
                finish();
                break;
            case R.id.checkbox_men:

                break;
            case R.id.checkbox_women:

                break;
            case R.id.submit_data:
                if (!TextUtils.isEmpty(mRegisterName.getText())){
                    sendRegister(new Gson().toJson(new Registe_UserInfo(getintent.getStringExtra("phone"),
                                                                        mRegisterName.getText().toString().trim(),
                                                                        getintent.getStringExtra("pwd"),
                                                                        getintent.getStringExtra("confirm"),
                                                                        sex,
                                                                        getintent.getStringExtra("code")
                            )));
                    Log.i("info", "注册数据 "+getintent.getStringExtra("phone")+""+
                            mRegisterName.getText().toString().trim()+""+
                            getintent.getStringExtra("pwd")+""+
                            getintent.getStringExtra("confirm")+""+
                            sex+""+
                            getintent.getStringExtra("code"));
                }
                break;
        }
    }

    private void sendRegister(String s){
        MyApp.getContext().getMyOkHttp().post()
                .url(URL.url+URL.url_register)
                .tag(this)
                .jsonParams(s)
                .enqueue(new RawResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, String response) {
                        ReturnBean returnBean = new Gson().fromJson(response, ReturnBean.class);
                        if (returnBean.getRtn_code()==0){
                            startActivity(new Intent(RegistertwoActivity.this,RegisterThreeActivity.class));
                            Toast.makeText(RegistertwoActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                        }else if(returnBean.getRtn_code()== Keyparameter.CODE_HAVE_REGISTE) {
                            Toast.makeText(RegistertwoActivity.this,Keyparameter.CODE_HAVE_REGISTE_STR,Toast.LENGTH_SHORT).show();
                        }else if(returnBean.getRtn_code()== Keyparameter.CODE_PWD_PADERNOSAME) {
                            Toast.makeText(RegistertwoActivity.this,Keyparameter.CODE_PWD_PADERNOSAME_STR,Toast.LENGTH_SHORT).show();
                        }else if(returnBean.getRtn_code()== Keyparameter.CODE_USER_ERROR) {
                            Toast.makeText(RegistertwoActivity.this,Keyparameter.CODE_USER_ERROR_STR,Toast.LENGTH_SHORT).show();
                        }else if(returnBean.getRtn_code()== Keyparameter.CODE_MSG_ERROR) {
                            Toast.makeText(RegistertwoActivity.this,Keyparameter.CODE_MSG_ERROR_STR,Toast.LENGTH_SHORT).show();
                        }else if(returnBean.getRtn_code()== Keyparameter.CODE_HAVE_REGISTE) {
                            Toast.makeText(RegistertwoActivity.this,Keyparameter.CODE_HAVE_REGISTE_STR,Toast.LENGTH_SHORT).show();
                        }else if(returnBean.getRtn_code()== Keyparameter.CODE_ERROR_SERVICE) {
                            Toast.makeText(RegistertwoActivity.this,Keyparameter.CODE_ERROR_SERVICE_STR,Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(RegistertwoActivity.this,"数据请求发生错误，请稍后重试...",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        Log.i("jzb", "onFailure:注册接口 ");
                    }
                });
    }
}
