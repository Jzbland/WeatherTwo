package com.beestar.jzb.goglebleweather.ui.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.R;

public class WebActivity extends AppCompatActivity implements View.OnClickListener {

    private WebView mWebView;
    private ImageView mBack;
    private TextView mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        int page = getIntent().getIntExtra("page", -1);
        initView();
        if (page != -1) {
            if (page == 1) {
                mWebView.loadUrl("file:///android_asset/protocol.html");
                mTitle.setText("用户协议");
            } else if(page==2){
                mWebView.loadUrl("file:///android_asset/copyright.html");
                mTitle.setText("版权说明");
            }else if (page==3){
                mWebView.loadUrl("file:///android_asset/aboutAs.html");
                mTitle.setText("关于我们");
            }
        }

    }

    private void initView() {
        mWebView = (WebView) findViewById(R.id.webView);
        mBack = (ImageView) findViewById(R.id.back);
        mTitle = (TextView) findViewById(R.id.title);
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
