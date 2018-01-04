package com.example.boxing;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class NiaojianActivity extends Activity
{
	ArrayList<Double> yList;
	LineGraphicView tu;
	
	private ImageButton backButton;
	private TextView titleTextView;
	private ImageButton rightButton;
	
	private ImageButton backButton2;
	private TextView titleTextView2;
	private ImageButton rightButton2;
	
	private LinearLayout layout1,layout2;
	
	
	int num=0;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.niaojian);
		
		backButton=(ImageButton)findViewById(R.id.backbut_niaojian);
		titleTextView=(TextView)findViewById(R.id.titleview_niaojian);
		rightButton=(ImageButton)findViewById(R.id.right_niaojian);
		
		backButton2=(ImageButton)findViewById(R.id.backbut2_niaojian);
		titleTextView2=(TextView)findViewById(R.id.titleview2_niaojian);
		rightButton2=(ImageButton)findViewById(R.id.right2_niaojian);
		
		titleTextView.setText("Äò¼ì»¯Ñéµ¥");
		rightButton.setBackgroundResource(R.drawable.icon_bar_huayandan);
		rightButton.setVisibility(View.VISIBLE);
		
		initView();
		
		
		
		layout1=(LinearLayout)findViewById(R.id.layout1);
		layout2=(LinearLayout)findViewById(R.id.layout2);
		layout2.setVisibility(View.GONE);
		layout1.setVisibility(View.VISIBLE);
		
		backButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				NiaojianActivity.this.finish();
				
			}
		});
		backButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				NiaojianActivity.this.finish();
				
			}
		});
		rightButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
					
					layout1.setVisibility(View.GONE);
					layout2.setVisibility(View.VISIBLE);
				
			}
		});
		rightButton2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				layout2.setVisibility(View.GONE);
				layout1.setVisibility(View.VISIBLE);
					
				
			}
		});
		
	}
	public void initView(){
		tu = (LineGraphicView) findViewById(R.id.line_graphic);

		yList = new ArrayList<Double>();
		yList.add((double) 2.103);
		yList.add(4.05);
		yList.add(6.60);
		yList.add(3.08);
		yList.add(4.32);
		yList.add(2.0);
		yList.add(5.0);

		ArrayList<String> xRawDatas = new ArrayList<String>();
		xRawDatas.add("05-19");
		xRawDatas.add("05-20");
		xRawDatas.add("05-21");
		xRawDatas.add("05-22");
		xRawDatas.add("05-23");
		xRawDatas.add("05-24");
		xRawDatas.add("05-25");
		xRawDatas.add("05-26");
		tu.setData(yList, xRawDatas, 8, 2);
	}
	

}
