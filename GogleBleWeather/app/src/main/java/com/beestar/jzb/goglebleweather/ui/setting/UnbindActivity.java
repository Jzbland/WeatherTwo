package com.beestar.jzb.goglebleweather.ui.setting;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;

public class UnbindActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mBack;
    /**
     * 解除绑定
     */
    private Button mUnbindButton;
    /**
     * 取消
     */
    private Button mUnbindNoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_unbind);
        initView();

    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mUnbindButton = (Button) findViewById(R.id.unbind_button);
        mUnbindButton.setOnClickListener(this);
        mUnbindNoButton = (Button) findViewById(R.id.unbind_no_button);
        mUnbindNoButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.unbind_button:
                break;
            case R.id.unbind_no_button:
                break;
        }
    }
}
