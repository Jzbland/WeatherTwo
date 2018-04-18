package com.beestar.jzb.goglebleweather.DialogFragment;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.beestar.jzb.goglebleweather.R;

/**
 * Created by jzb on 2017/9/19.
 */

public class MyFragmentAddInforDilog extends DialogFragment implements View.OnClickListener {

    private View view;
    private ImageButton mCancelAddinformation;
    /**
     * 钥匙
     */
    private Button mKey;
    /**
     * 钱包
     */
    private Button mMoney;
    /**
     * 笔记本
     */
    private Button mComputer;
    /**
     * 雨伞
     */
    private Button mUmbar;
    /**
     * 背包
     */
    private Button mPack;
    /**
     * 其他
     */
    private Button mOther;
    /**
     * 开启看管
     */
    private Button mBeginWatch;
    /**
     * 输入防丢信息
     */
    private EditText mInformation;
     OnMyFragmentAddInfroListener mListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdialogaddinformation, null);
       mListener= (OnMyFragmentAddInfroListener) getActivity();
        initView(view);
        getDialog().setCancelable(false);
        getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (i == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }
        });
        return view;
    }


    private void initView(View view) {
        mCancelAddinformation = (ImageButton) view.findViewById(R.id.cancel_addinformation);
        mCancelAddinformation.setOnClickListener(this);
        mKey = (Button) view.findViewById(R.id.key);
        mKey.setOnClickListener(this);
        mMoney = (Button) view.findViewById(R.id.money);
        mMoney.setOnClickListener(this);
        mComputer = (Button) view.findViewById(R.id.computer);
        mComputer.setOnClickListener(this);
        mUmbar = (Button) view.findViewById(R.id.umbar);
        mUmbar.setOnClickListener(this);
        mPack = (Button) view.findViewById(R.id.pack);
        mPack.setOnClickListener(this);
        mOther = (Button) view.findViewById(R.id.other);
        mOther.setOnClickListener(this);
        mBeginWatch = (Button) view.findViewById(R.id.begin_watch);
        mBeginWatch.setOnClickListener(this);
        mInformation = (EditText) view.findViewById(R.id.information);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_addinformation:
                mListener.onMyFragmentAddInfroCancel();
                break;
            case R.id.key:
                mInformation.setText("钥匙");
                break;
            case R.id.money:
                mInformation.setText("钱包");
                break;
            case R.id.computer:
                mInformation.setText("笔记本");
                break;
            case R.id.umbar:
                mInformation.setText("雨伞");
                break;
            case R.id.pack:
                mInformation.setText("背包");
                break;
            case R.id.other:
                mInformation.setText("其他");
                break;
            case R.id.begin_watch:
                mListener.onMyFragmentAddInfroBeginWatch(mInformation.getText().toString().trim());
                break;
        }
    }

    public interface OnMyFragmentAddInfroListener {
        void onMyFragmentAddInfroCancel();

        void onMyFragmentAddInfroBeginWatch(String addinfor);
    }
}
