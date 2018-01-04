package com.beestar.jzb.goglebleweather.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.beestar.jzb.goglebleweather.R;
import com.facebook.drawee.view.SimpleDraweeView;

public class ImageActivity extends AppCompatActivity {

    private SimpleDraweeView mImageActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();
        String url = getIntent().getStringExtra("url");
        mImageActivity.setImageURI(url);
    }

    private void initView() {
        mImageActivity = (SimpleDraweeView) findViewById(R.id.image_activity);
    }
}
