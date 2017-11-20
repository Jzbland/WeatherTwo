package com.beestar.jzb.goglebleweather.utils;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

/**
 * Created by jzb on 2017/11/9.
 */

public class MyMakerView extends MarkerView{
    private TextView tvContent;


    /**
     * Constructor. Sets up the MarkerView with a custom layout resource.
     *
     * @param context
     * @param layoutResource the layout resource to use for the MarkerView
     */
    public MyMakerView(Context context, int layoutResource) {
        super(context, layoutResource);
        //tvContent= ((TextView) findViewById(R.id.t));
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        tvContent.setText("" + e.getY());
    }

    @Override
    public float getX() {
        return  -(getWidth() / 2);
    }

    @Override
    public float getY() {
        return -getHeight();
    }
}
