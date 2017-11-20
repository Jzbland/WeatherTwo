package com.beestar.jzb.goglebleweather.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.ui.MainActivity;

public class RegisterThreeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBackRegistTwo;
    /**
     * 立即登录
     */
    private Button mTurnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_three);
        initView();

    }

    private void initView() {
        mBackRegistTwo = (ImageView) findViewById(R.id.back_regist_two);
        mBackRegistTwo.setOnClickListener(this);
        mTurnLogin = (Button) findViewById(R.id.turn_login);
        mTurnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_regist_two:
                finish();
                break;
            case R.id.turn_login:
                Intent intent = new Intent(RegisterThreeActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
    }
}
