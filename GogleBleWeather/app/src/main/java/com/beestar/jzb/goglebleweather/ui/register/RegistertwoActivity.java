package com.beestar.jzb.goglebleweather.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registertwo);
        initView();

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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_regist_two:
                break;
            case R.id.checkbox_men:
                break;
            case R.id.checkbox_women:
                break;
            case R.id.submit_data:
                startActivity(new Intent(RegistertwoActivity.this,RegisterThreeActivity.class));
                break;
        }
    }
}
