package com.beestar.jzb.goglebleweather.DialogFragment;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.beestar.jzb.goglebleweather.R;

/**
 * Created by jzb on 2017/9/19.
 */

public class MyFragmentConnDialog_true extends DialogFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragmentdialogconn_true,null);
        final OnMyFragmentConnDialogTrueListener mListener= (OnMyFragmentConnDialogTrueListener) getActivity();
        view.findViewById(R.id.cancel_conn_true).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onMyFragmentConnDilogTrueCancel();
                close();
            }
        });
        view.findViewById(R.id.addinformation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mListener.onMyFragmentConnDilogTrueAddInformation();
                close();
            }
        });
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
    public interface OnMyFragmentConnDialogTrueListener{
        void onMyFragmentConnDilogTrueCancel();
        void onMyFragmentConnDilogTrueAddInformation();
    }
    public void close(){
        dismiss();
    }
}
