package com.beestar.jzb.mylineview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.beestar.jzb.mylineview.utils.ChartUtils;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.List;


public class Main4Activity extends AppCompatActivity {

    private LineChart mLinechart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        initView();
    }
    private void initView() {
        mLinechart = (LineChart) findViewById(R.id.linechart_4);
        ChartUtils.initChart(mLinechart);
        ChartUtils.notifyDataSetChanged(mLinechart,getData(),ChartUtils.weekValue);
    }
    private List<Entry> getData() {
        List<Entry> values = new ArrayList<>();
        values.add(new Entry(0, 15));
        values.add(new Entry(1, 15));
        values.add(new Entry(2, 15));
        values.add(new Entry(3, 20));
        values.add(new Entry(4, 25));
        values.add(new Entry(5, 20));
        values.add(new Entry(6, 20));
        return values;
    }
}
