package com.beestar.jzb.goglebleweather.DialogFragment;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.beestar.jzb.goglebleweather.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyFragmentHaveBind extends DialogFragment {


    private View view;
    private ImageButton mCancelConnHavebind;
    private ImageView mIcon;

    public MyFragmentHaveBind() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_fragment_have_bind, container, false);
        final OnMyFragmentHaveBindListener listener=(OnMyFragmentHaveBindListener) getActivity();
        initView(view);
        mCancelConnHavebind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMyFragmentHaveBindCancle();
            }
        });
        return view;
    }

    private void initView(View view) {
        mCancelConnHavebind = (ImageButton) view.findViewById(R.id.cancel_conn_havebind);
        mIcon = (ImageView) view.findViewById(R.id.icon);
    }
    public void close(){
        dismiss();
    }
    public interface OnMyFragmentHaveBindListener{
        void onMyFragmentHaveBindCancle();
    }
}
