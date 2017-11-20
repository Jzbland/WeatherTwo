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

public class MyFragmentConnDialog_False extends DialogFragment {
     @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragmentdialog_con_false, container);
        final MyFragmentConnDialog_False.OnMyFragmentConnFalseLister mLister= (MyFragmentConnDialog_False.OnMyFragmentConnFalseLister) getActivity();

         view.findViewById(R.id.cancel_conn_false).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLister.onFragmentConnFalseCancel("cancel");
                close();
            }
        });
        view.findViewById(R.id.button_conn_false_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mLister.onFragmentConnFalseNext("next");
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
    public void close(){
        dismiss();
    }
    public interface OnMyFragmentConnFalseLister{
        void onFragmentConnFalseCancel(String s);
        void onFragmentConnFalseNext(String s);
    }
}
