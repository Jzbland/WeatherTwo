package com.beestar.jzb.goglebleweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jzb on 2017/12/14.
 *
 */

public class LineTimeView extends View {
    private Paint paint;
    private Paint textPaint,textPaint2,paintTime;//绘制文字画笔
    private Paint circlePaint=new Paint();
    private int width;//view的宽度
    private int height;//view的高度
    private float lineWidth=-1;
    private List<String> timeData=new ArrayList<>();
    private int colorLine;
    private int strWidth = 10;//绘制文字所占用的宽度
    public LineTimeView(Context context) {
        this(context,null);
        initView();
    }
    public LineTimeView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        initView();
    }
    public LineTimeView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        width = 2300;
//        height = 150;

        textPaint = new Paint();
        textPaint.setColor(Color.RED);
        textPaint.setTextSize(28);
        textPaint.setAntiAlias(true);

        textPaint2 = new Paint();
        textPaint2.setColor(Color.WHITE);
        textPaint2.setTextSize(32);
        textPaint2.setStrokeWidth(1);
        textPaint2.setAlpha(1000);
        textPaint2.setAntiAlias(true);





        paintTime = new Paint();//时间刻度画笔
        paintTime.setColor(Color.rgb(17,31,80));
        paintTime.setTextSize(30);
        paintTime.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);// 得到模式
        height = MeasureSpec.getSize(heightMeasureSpec);// 得到尺寸

        switch (heightMode){
            case MeasureSpec.AT_MOST:
                setMeasuredDimension(width,height);
                break;

            case MeasureSpec.EXACTLY:
                setMeasuredDimension(width,height);
                break;

            case MeasureSpec.UNSPECIFIED:
                setMeasuredDimension(width,height);
                break;
        }

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawKLine(canvas);
    }
    private void drawKLine(Canvas canvas) {
        canvas.translate(0,height);
        drawRect(canvas);

    }
    private void drawRect(Canvas canvas) {
//        canvas.drawLine(0,-height,2400,-height,textPaint2);
//        canvas.drawLine(0,0,100,-150,textPaint2);


        /**
         * 空气质量
         */
        canvas.drawRect(0,-height*2/3,2400,-height/3,textPaint);//空气
        for(int i=0;i<=24;i++){
            //空气分割线
            canvas.drawLine(100*i,-height/3,100*i,-height*2/3,textPaint2);

        }
        for(int i=1;i<=24;i++){
            //空气质量值
            canvas.drawText("50",100*i+10,-height/2+10,textPaint2);
        }
        /**
         * Y时间刻度值
         */
        canvas.drawRect(0,-height,2400,-height*2/3,paintTime);
        for (int i=0;i<timeData.size();i++){
            canvas.drawText(timeData.get(i),100*i-20,-height*5/6+10,textPaint2);
        }
        /**
         * 风力
         */
        canvas.drawRect(0,-height/3,2400,0,paintTime);
    }
    public void setTimeData(List<String> data){
        this.timeData=data;
        invalidate();
    }


}
