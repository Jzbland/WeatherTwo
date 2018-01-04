package com.beestar.jzb.goglebleweather.ui.haveLogin;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.utils.L;
import com.beestar.jzb.goglebleweather.utils.SPUtils;
import com.zcw.togglebutton.ToggleButton;

import java.util.ArrayList;
import java.util.List;

import cn.qqtheme.framework.picker.LinkagePicker;
import cn.qqtheme.framework.util.DateUtils;

/**
 * 勿扰设置
 */
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
                time_picker1();
                break;
            case R.id.end_time:
                time_picker2();
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
                ArrayList<String> thredList = new ArrayList<>();
                for (int i = 1; i <= 59; i++) {
                    String str = DateUtils.fillZero(i);
                    thredList.add(str);
                }
                return thredList;
            }
        };
        LinkagePicker picker = new LinkagePicker(this, provider);
        picker.setCycleDisable(true);
        picker.setSelectedItem("上午", "1", "01");
        picker.setLabel("", "点", "分");
        picker.setSelectedIndex(0, 0, 0);
        picker.setColumnWeight(0.2, 0.3, 0.2);

        picker.setOnWheelListener(new LinkagePicker.OnWheelListener() {
            @Override
            public void onFirstWheeled(int i, String s) {
                L.i(i + "---------------first-----------" + s);
            }

            @Override
            public void onSecondWheeled(int i, String s) {
                L.i(i + "---------------second-----------" + s);
            }

            @Override
            public void onThirdWheeled(int i, String s) {
                L.i(i + "---------------Third-----------" + s);
            }
        });
        picker.setOnLinkageListener(new LinkagePicker.OnLinkageListener() {
            @Override
            public void onPicked(String s, String s1, String s2) {
                Toast.makeText(MyApp.getContext(), s + "--" + s1 + "--" + s2, Toast.LENGTH_SHORT).show();
            }
        });
        picker.show();
    }

    public void time_picker1() {
        new TimePickerDialog(NoDisturbActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mBeginTimeText.setText(hourOfDay+":"+minute);
            }
            //0,0指的是时间，true表示是否为24小时，true为24小时制
        }, 0, 0, true).show();
    }
    public void time_picker2() {
        new TimePickerDialog(NoDisturbActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                mEndTimeText.setText(hourOfDay+":"+minute);
            }
            //0,0指的是时间，true表示是否为24小时，true为24小时制
        }, 0, 0, true).show();
    }
    public void sendData(String address,String data){
        Intent intent=new Intent();
        intent.setAction(MyServiceBlueTooth.SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);

    }
}
