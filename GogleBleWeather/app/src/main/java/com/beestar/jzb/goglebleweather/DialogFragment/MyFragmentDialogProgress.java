package com.beestar.jzb.goglebleweather.DialogFragment;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.R;

/**
 * Created by jzb on 2018/1/4.
 */

public class MyFragmentDialogProgress extends DialogFragment {

    private TextView title;
    private TextView content;
    public ProgressBar prigress;

    static MyFragmentDialogProgress instance;

    public static MyFragmentDialogProgress newInstance(String title,String content){
        MyFragmentDialogProgress instance=new MyFragmentDialogProgress();
        Bundle arg=new Bundle();
        arg.putString("title",title);
        arg.putString("content",content);
        instance.setArguments(arg);
        return instance;
    }
    public static MyFragmentDialogProgress getInstance(){
        return MyFragmentDialogProgress.instance;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instance=this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_progress, container);
        title = ((TextView) view.findViewById(R.id.title_text));
        content = ((TextView) view.findViewById(R.id.content));
        title.setText(getArguments().getString("title"));
        content.setText(getArguments().getString("content"));
        prigress = ((ProgressBar) view.findViewById(R.id.progressBar2));

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
}
