package com.beestar.jzb.goglebleweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jzb on 2018/1/30.
 */

public class YLineView extends View{
    private Paint textPaint;

    private int width;//view的宽度
    private int height;//view的高度

    public YLineView(Context context) {
        this(context,null);
        initView();
    }
    public YLineView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        initView();
    }
    public YLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        width = 100;


        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(32);
        textPaint.setStrokeWidth(1);
        textPaint.setAlpha(1000);
        textPaint.setAntiAlias(true);


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
//        canvas.drawRect(0,-100,100,0,textPaint);

        canvas.drawLine(100,0,100,-height*400/400,textPaint);

        canvas.drawText("空气",10,-height*90/400,textPaint);
        canvas.drawText("风力",10,-height*30/400,textPaint);

        /**
         * 刻度值
         */
        canvas.drawText("0℃",10,-height*240/400,textPaint);
        canvas.drawText("20℃",10,-height*300/400,textPaint);
        canvas.drawText("40℃",10,-height*360/400,textPaint);

        /**
         * 刻度
         */
        canvas.drawLine(100,-height*180/400,95,-height*180/400,textPaint);//-20
        canvas.drawLine(100,-height*210/400,98,-height*210/400,textPaint);//-10
        canvas.drawLine(100,-height*240/400,95,-height*240/400,textPaint);//0
        canvas.drawLine(100,-height*270/400,98,-height*270/400,textPaint);//10
        canvas.drawLine(100,-height*300/400,95,-height*300/400,textPaint);//20
        canvas.drawLine(100,-height*330/400,98,-height*330/400,textPaint);//30
        canvas.drawLine(100,-height*360/400,95,-height*360/400,textPaint);//40
        canvas.drawLine(100,-height*390/400,98,-height*390/400,textPaint);//40
    }
    /**
     * dip 转换成px
     *
     * @param dip
     * @return
     */
    private int dipToPx(float dip)
    {
        float density = getContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f * (dip >= 0 ? 1 : -1));
    }

}
