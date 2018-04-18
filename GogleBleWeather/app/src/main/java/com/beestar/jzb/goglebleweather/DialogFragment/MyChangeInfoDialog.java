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
import com.beestar.jzb.goglebleweather.utils.SPUtils;

/**
 * Created by jzb on 2018/1/18.
 */

public class MyChangeInfoDialog extends DialogFragment {

    private EditText editInfoName;
    private Button submit;
    private Button cancle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.changeinfolayout,null);
        final MyChangeInfoDialog.OnChangeInfoDialogListener mListener= ((MyChangeInfoDialog.OnChangeInfoDialogListener) getActivity());
        editInfoName = (EditText)view.findViewById(R.id.change_BlueName);


        editInfoName.setText(((String) SPUtils.get(MyApp.getContext(), "name", "")));
        ((Button) view.findViewById(R.id.submit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(editInfoName.getText())){
                    mListener.onChangeInfoDialogNext(editInfoName.getText().toString());
                }else {
                    mListener.onChangeInfoDialogNext("未设置");
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



    public interface OnChangeInfoDialogListener{
        void onChangeInfoDialogNext(String infoName);
    }
    public void close(){
        dismiss();
    }
}
