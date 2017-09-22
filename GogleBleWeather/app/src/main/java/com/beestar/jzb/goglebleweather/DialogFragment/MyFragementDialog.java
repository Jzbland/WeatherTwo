package com.beestar.jzb.goglebleweather.DialogFragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beestar.jzb.goglebleweather.R;

/**
 * Created by jzb on 2017/9/15.
 */

public class MyFragementDialog extends DialogFragment{


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdialog_binding, container);
        view.findViewById(R.id.imageButton_cancle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnMyFragmentDialogLister mLister=(OnMyFragmentDialogLister) getActivity();
                mLister.onFragmentDialogCancelLister("Cancel");
                close();
            }
        });
        return view;
    }

    public interface OnMyFragmentDialogLister
    {
        void onFragmentDialogCancelLister(String s);
    }
    public void close(){
        dismiss();
    }
}
