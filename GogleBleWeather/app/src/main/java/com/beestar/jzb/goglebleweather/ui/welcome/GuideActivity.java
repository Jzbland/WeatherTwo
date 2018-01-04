package com.beestar.jzb.goglebleweather.ui.welcome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.MainActivity;
import com.beestar.jzb.goglebleweather.utils.SharedPreferencesUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class GuideActivity extends AppCompatActivity {
    private int imgs[]={R.drawable.start1,R.drawable.start2,R.drawable.start3,R.drawable.start4,};
    private ViewPager viewPager;
    private ArrayList<ImageView> imgList = new ArrayList<>();
    private MyAdapter2 adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferencesUtil sp = SharedPreferencesUtil.getInstance();
        int count = sp.getFIRST();
        if (count == 0) {
            setContentView(R.layout.activity_guide);
            sp.setFIRST(++count);
            initView();
            initData();
            initAdapter();
            viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    if (position==imgList.size()-1){
                        finish();
                        startActivity(new Intent(GuideActivity.this, MainActivity.class));
                    }
                }
                @Override
                public void onPageSelected(int position) {

                }
                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }else {
            startActivity(new Intent(GuideActivity.this, MainActivity.class));
            finish();
        }
    }
    private void initAdapter() {
        adapter = new MyAdapter2();
        viewPager.setAdapter(adapter);
    }
    private void initData() {
        for (int i = 0; i <imgs.length; i++) {
            ImageView iv  =new ImageView(this);
            Picasso.with(this).
                    load(imgs[i]).
                    config(Bitmap.Config.RGB_565).
                    into(iv);
            imgList.add(iv);
            Log.i("tmd", "initData: "+imgList.size());
        }
    }
    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.pager);

    }
    class MyAdapter2 extends PagerAdapter {

        @Override
        public int getCount() {
            return imgList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imgList.get(position));
            return imgList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
