package com.beestar.jzb.goglebleweather.DialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;
import com.beestar.jzb.goglebleweather.gen.DeviceBeanDao;

/**
 * Created by jzb on 2017/11/22.
 */

public class MyFragmentEditBlueToothDialog extends DialogFragment {
    private EditText editChangeBlueName;
    private EditText editChangeGoodName;
    private Button submit;
    private Button cancle;
    DeviceBeanDao deviceBeanDao;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_editbluetoothdialog,null);
        final OnEditBlueToothDialogListener mOnEditBlueToothDialogListener=(OnEditBlueToothDialogListener) getActivity();
        deviceBeanDao = MyApp.getContext().getDaoSession().getDeviceBeanDao();
        DeviceBean unique = deviceBeanDao.queryBuilder().where(DeviceBeanDao.Properties.IsChoose.eq(true)).build().unique();
        editChangeBlueName = (EditText)view.findViewById(R.id.change_BlueName);
        editChangeGoodName = ((EditText) view.findViewById(R.id.change_GoodName));
        editChangeBlueName.setText(unique.getName());
        editChangeGoodName.setText(unique.getSecondName());
        ((Button) view.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(editChangeGoodName.getText())){
                    mOnEditBlueToothDialogListener.onEditBlueTooThDialogNext(editChangeBlueName.getText().toString().trim(),editChangeGoodName.getText().toString().trim());
                }else {
                    mOnEditBlueToothDialogListener.onEditBlueTooThDialogNext(editChangeBlueName.getText().toString().trim(),null);
                }

                close();
            }
        });
       ((Button) view.findViewById(R.id.cancle)).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               close();
           }
       });

        return view;
    }



    public interface OnEditBlueToothDialogListener{
        void onEditBlueTooThDialogNext(String blueName,String goodName);
    }
    public void close(){
        dismiss();
    }
}
