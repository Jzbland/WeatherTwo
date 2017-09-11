package com.beestar.jzb.goglebleweather.ui.haveLogin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;

public class SetMusicActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_music);
        initView();
    }
    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
}
