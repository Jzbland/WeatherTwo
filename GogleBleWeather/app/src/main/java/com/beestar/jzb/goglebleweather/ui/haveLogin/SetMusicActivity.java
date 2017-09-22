package com.beestar.jzb.goglebleweather.ui.haveLogin;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.ui.BaseActivity;
import com.zcw.togglebutton.ToggleButton;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_music);
        initView();
        setTggleButton();
    }

    private void setTggleButton() {
        mDataUpdataSwtich.toggle(true);
        mConnNextSwtich.toggle(true);
        mLoseSwtich.toggle(true);
    }

    private void initView() {
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
        mDataUpdataSwtich = (ToggleButton) findViewById(R.id.data_updata_swtich);
        mConnNextSwtich = (ToggleButton) findViewById(R.id.conn_next_swtich);
        mLoseSwtich = (ToggleButton) findViewById(R.id.lose_swtich);
        mTextView = (TextView) findViewById(R.id.textView);
        mListMusic = (ListView) findViewById(R.id.list_music);
        mAddMusicPerson = (Button) findViewById(R.id.add_music_person);
        mAddMusicPerson.setOnClickListener(this);
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
