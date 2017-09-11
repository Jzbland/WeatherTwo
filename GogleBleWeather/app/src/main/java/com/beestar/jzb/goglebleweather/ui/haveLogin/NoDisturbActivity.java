package com.beestar.jzb.goglebleweather.ui.haveLogin;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.utils.L;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.LinkagePicker;
import cn.qqtheme.framework.util.DateUtils;

/**
 * 勿扰设置
 */
public class NoDisturbActivity extends BaseActivity implements View.OnClickListener{
    private ImageView mBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_disturb);
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

    public void setData(View view) {
        LinkagePicker.DataProvider provider = new LinkagePicker.DataProvider() {
            @Override
            public boolean isOnlyTwo() {
                return false;
            }

            @Override
            public List<String> provideFirstData() {
                ArrayList<String> firstList = new ArrayList<>();
                firstList.add("上午");
                firstList.add("下午");
                return firstList;
            }

            @Override
            public List<String> provideSecondData(int firstIndex) {
                ArrayList<String> secondList = new ArrayList<>();
                for (int i = 1; i <= 12; i++) {
                    String str = DateUtils.fillZero(i);

                    secondList.add(str);
                }
                return secondList;
            }

            @Override
            public List<String> provideThirdData(int firstIndex, int secondIndex) {
                ArrayList<String> thredList=new ArrayList<>();
                for (int i = 1; i <= 59; i++) {
                    String str = DateUtils.fillZero(i);
                    thredList.add(str);
                }
                return thredList;
            }
        };
        LinkagePicker picker = new LinkagePicker(this, provider);
        picker.setCycleDisable(true);
        picker.setSelectedItem("上午","1","01");
        picker.setLabel("", "点","分");
        picker.setSelectedIndex(0,0,0);
        picker.setColumnWeight(0.2,0.3,0.2);

        picker.setOnWheelListener(new LinkagePicker.OnWheelListener() {
            @Override
            public void onFirstWheeled(int i, String s) {
                L.i(i+"---------------first-----------"+s);
            }

            @Override
            public void onSecondWheeled(int i, String s) {
                L.i(i+"---------------second-----------"+s);
            }

            @Override
            public void onThirdWheeled(int i, String s) {
                L.i(i+"---------------Third-----------"+s);
            }
        });
        picker.setOnLinkageListener(new LinkagePicker.OnLinkageListener() {
            @Override
            public void onPicked(String s, String s1, String s2) {
                Toast.makeText(MyApp.getContext(),s+"--"+s1+"--"+s2,Toast.LENGTH_SHORT).show();
            }
        });
        picker.show();
    }
}
