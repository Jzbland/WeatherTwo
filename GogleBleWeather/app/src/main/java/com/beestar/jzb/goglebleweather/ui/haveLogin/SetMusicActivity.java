package com.beestar.jzb.goglebleweather.ui.haveLogin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.Service.MyServiceBlueTooth;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.beestar.jzb.goglebleweather.utils.SPUtils;
import com.zcw.togglebutton.ToggleButton;
@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class SetMusicActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private ToggleButton mDataUpdataSwtich;
    private ToggleButton mConnNextSwtich;
    private ToggleButton mLoseSwtich;
    /**
     * 反向查找铃声（连续按3下硬件按钮，可反向查找手机）
     */
    private TextView mTextView;
    private ListView mListMusic;
    /**
     * 添加铃声
     */
    private Button mAddMusicPerson;
    private DeviceBeanDao deviceBeanDao;

    private RadioGroup rg;
    private RelativeLayout setMusic_new_1,setMusic_new_2,setMusic_new_3;
    private ImageView setMusic_img1,setMusic_img2,setMusic_img3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_music);
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        initView();
        setTggleButton();
    }

    private void setTggleButton() {
        if (SPUtils.contains(MyApp.getContext(),"mDataUpdataSwtich")){
            
        }
        mDataUpdataSwtich.toggle(false);
        mConnNextSwtich.toggle(false);
        mLoseSwtich.toggle(false);
    }

    private void initView() {
        final DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mDataUpdataSwtich = (ToggleButton) findViewById(R.id.data_updata_swtich);
        mConnNextSwtich = (ToggleButton) findViewById(R.id.conn_next_swtich);
        mLoseSwtich = (ToggleButton) findViewById(R.id.lose_swtich);
        mTextView = (TextView) findViewById(R.id.textView);
        mListMusic = (ListView) findViewById(R.id.list_music);
        mAddMusicPerson = (Button) findViewById(R.id.add_music_person);
        mAddMusicPerson.setOnClickListener(this);

        rg = ((RadioGroup) findViewById(R.id.rp));
        setMusic_new_1 = ((RelativeLayout) findViewById(R.id.setMusis1_new));
        setMusic_new_2 = ((RelativeLayout) findViewById(R.id.setMusis2_new));
        setMusic_new_3 = ((RelativeLayout) findViewById(R.id.setMusis3_new));

        setMusic_img1 = ((ImageView) findViewById(R.id.setMusis1_new_img));
        setMusic_img2 = ((ImageView) findViewById(R.id.setMusis2_new_img));
        setMusic_img3 = ((ImageView) findViewById(R.id.setMusis3_new_img));

        setMusic_new_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMusic_img1.setVisibility(View.VISIBLE);
                setMusic_img2.setVisibility(View.GONE);
                setMusic_img3.setVisibility(View.GONE);
                if (deviceBean!=null){
                    sendData(deviceBean.getMac(),"F9");
                }
            }
        });
        setMusic_new_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMusic_img1.setVisibility(View.GONE);
                setMusic_img2.setVisibility(View.VISIBLE);
                setMusic_img3.setVisibility(View.GONE);
                if (deviceBean!=null){
                    sendData(deviceBean.getMac(),"FA");
                }
            }
        });
        setMusic_new_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMusic_img1.setVisibility(View.GONE);
                setMusic_img2.setVisibility(View.GONE);
                setMusic_img3.setVisibility(View.VISIBLE);
                if (deviceBean!=null){
                    sendData(deviceBean.getMac(),"FB");
                }
            }
        });

        mDataUpdataSwtich.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
//                SPUtils.put(MyApp.getContext(),"mDataUpdataSwtich",on);
//                DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
//                if (deviceBean!=null){
//                    if (on){
//                        sendData(deviceBean.getMac(),"F9");
//                    }
//                }
            }
        });
        mConnNextSwtich.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
//                SPUtils.put(MyApp.getContext(),"mConnNextSwtich",on);
//                DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
//                if (deviceBean!=null){
//                    if (on){
//                        sendData(deviceBean.getMac(),"FA");
//                    }
//                }
            }
        });
        mLoseSwtich.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
//                SPUtils.put(MyApp.getContext(),"mLoseSwtich",on);
//                DeviceBean deviceBean = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
//                if (deviceBean!=null){
//                    if (on){
//                        sendData(deviceBean.getMac(),"FB");
//                    }
//                }

            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void sendData(String address, String data){
        Intent intent=new Intent();
        intent.setAction(MyServiceBlueTooth.SEND_DATA);
        intent.putExtra("address",address);
        intent.putExtra("data",data);
        sendBroadcast(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.add_music_person:
                break;
        }
    }
}
