package com.beestar.jzb.goglebleweather.ui.haveLogin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.utils.SPUtils;
import com.zcw.togglebutton.ToggleButton;

import java.util.Calendar;

/**
 * 勿扰设置
 */
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class NoDisturbActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    /**
     * 上午 0:00
     */
    private TextView mBeginTimeText;
    private LinearLayout mBeginTime;
    /**
     * 上午 0:00
     */
    private TextView mEndTimeText;
    private LinearLayout mEndTime;
    private ToggleButton switch_nodisturb;

    private DeviceBeanDao deviceBeanDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_disturb);
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        initView();
        mBeginTimeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("textchange", "afterTextChanged: startTime"+mBeginTimeText.getText());
                mBeginTime.setBackgroundColor(Color.rgb(255,255,255));
            }
        });
        mEndTimeText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.i("textchange", "afterTextChanged: startTime"+mEndTimeText.getText());
                mEndTime.setBackgroundColor(Color.rgb(255,255,255));
            }
        });
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mBeginTimeText = (TextView) findViewById(R.id.begin_time_text);
        mBeginTime = (LinearLayout) findViewById(R.id.begin_time);
        mEndTimeText = (TextView) findViewById(R.id.end_time_text);
        mEndTime = (LinearLayout) findViewById(R.id.end_time);
        switch_nodisturb = ((ToggleButton) findViewById(R.id.switch_nodisturb));
        mBeginTime.setOnClickListener(this);
        mEndTime.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SPUtils.contains(MyApp.getContext(),"switch_nodisturb")){
            if ((Boolean) SPUtils.get(MyApp.getContext(),"switch_nodisturb",false)){
                switch_nodisturb.setToggleOn();
            }else {
                switch_nodisturb.setToggleOff();
            }
        }
        if (SPUtils.contains(MyApp.getContext(),"StarTimeHour")&&SPUtils.contains(MyApp.getContext(),"StarTimeMinute")){
            mBeginTimeText.setText((Integer) SPUtils.get(MyApp.getContext(),"StarTimeHour",-1)+":"+(Integer) SPUtils.get(MyApp.getContext(),"StarTimeMinute",-1));
        }
        if (SPUtils.contains(MyApp.getContext(),"EndTimeHour")&&SPUtils.contains(MyApp.getContext(),"EndTimeMinute")){
            mEndTimeText.setText((Integer) SPUtils.get(MyApp.getContext(),"EndTimeHour",-1)+":"+(Integer) SPUtils.get(MyApp.getContext(),"EndTimeMinute",-1));
        }
        switch_nodisturb.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                SPUtils.put(MyApp.getContext(),"switch_nodisturb",on);
                DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
                if (deviceBean!=null){
                    if (on){
                        sendData(deviceBean.getMac(),"F2");
                    }else {
                        sendData(deviceBean.getMac(),"F3");
                    }
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.begin_time:
                mBeginTime.setBackgroundColor(Color.rgb(226,227,228));
                time_picker1();
                break;
            case R.id.end_time:
                mEndTime.setBackgroundColor(Color.rgb(226,227,228));
                time_picker2();
                break;
        }
    }
    public void time_picker1() {

        cn.qqtheme.framework.picker.TimePicker picker = new cn.qqtheme.framework.picker.TimePicker(this, cn.qqtheme.framework.picker.TimePicker.HOUR_24);
        picker.setCycleDisable(false);
        picker.setRangeStart(0, 0);//00:00
        picker.setRangeEnd(23, 59);//23:59
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        picker.setSelectedItem(currentHour, currentMinute);
        picker.setTopLineVisible(false);
        picker.setOnTimePickListener(new cn.qqtheme.framework.picker.TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                mBeginTimeText.setText(hour+":"+minute);
                SPUtils.put(MyApp.getContext(),"StarTimeHour",Integer.parseInt(hour));
                SPUtils.put(MyApp.getContext(),"StarTimeMinute",Integer.parseInt(minute));
            }
        });
        picker.show();
    }
    public void time_picker2() {
        cn.qqtheme.framework.picker.TimePicker picker = new cn.qqtheme.framework.picker.TimePicker(this, cn.qqtheme.framework.picker.TimePicker.HOUR_24);
        picker.setCycleDisable(false);
        picker.setRangeStart(0, 0);//00:00
        picker.setRangeEnd(23, 59);//23:59
        int currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int currentMinute = Calendar.getInstance().get(Calendar.MINUTE);
        picker.setSelectedItem(currentHour, currentMinute);
        picker.setTopLineVisible(false);
        picker.setOnTimePickListener(new cn.qqtheme.framework.picker.TimePicker.OnTimePickListener() {
            @Override
            public void onTimePicked(String hour, String minute) {
                mEndTimeText.setText(hour+":"+minute);
                SPUtils.put(MyApp.getContext(),"EndTimeHour",Integer.parseInt(hour));
                SPUtils.put(MyApp.getContext(),"EndTimeMinute",Integer.parseInt(minute));
            }
        });
        picker.show();
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void sendData(String address, String data){
        Intent intent=new Intent();
        intent.setAction(MyServiceBlueTooth.SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);

    }
}
