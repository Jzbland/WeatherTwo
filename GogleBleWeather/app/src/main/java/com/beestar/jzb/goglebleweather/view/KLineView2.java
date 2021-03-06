package com.beestar.jzb.goglebleweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jzb on 2017/12/14.
 * 适用于第二第三个不显示头部
 */

public class KLineView2 extends View {
    private Paint paint;
    private Paint textPaint,textPaint2;//绘制文字画笔
    private Paint circlePaint=new Paint();
    private int width;//view的宽度
    private int height;//view的高度
    private float lineWidth=-1;
    private List<Point> mPoints=new ArrayList<>();
    private int colorLine;
    private int strWidth = 10;//绘制文字所占用的宽度
    public KLineView2(Context context) {
        this(context,null);
        initView();
    }
    public KLineView2(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        initView();
    }
    public KLineView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        width = 2440;
        height = 200;
        paint = new Paint();
        textPaint = new Paint();
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(28);
        textPaint.setAntiAlias(true);

        textPaint2 = new Paint();
        textPaint2.setColor(Color.WHITE);
        textPaint2.setTextSize(32);
        textPaint2.setStrokeWidth(1);
        textPaint2.setAlpha(100);
        textPaint2.setAntiAlias(true);

        paint.setStrokeWidth(0.5f);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(width,height);

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawKLine(canvas);
    }
    private void drawKLine(Canvas canvas) {
        canvas.translate(0,height);
        drawRect(canvas,mPoints);
        drawPoints(mPoints,canvas);
    }
    private void drawRect(Canvas canvas,List<Point> mPoints) {
        for(int i=0;i<=24;i++){//绘制竖线
            canvas.drawLine(100*i,0,100*i,-200,textPaint2);
        }
        if (mPoints.size()!=0){
            for (int i=0;i<mPoints.size();i++){
                canvas.drawText(mPoints.get(i).y+"",mPoints.get(i).x+8,-(mPoints.get(i).y+90),textPaint);
                canvas.drawCircle(mPoints.get(i).x,-(mPoints.get(i).y+strWidth+50),3,circlePaint);
            }
        }


    }
    public void drawPoints(List<Point> points,Canvas canvas) {
        Path path = new Path();
        boolean isStartPoint = false;
        paint.setColor(colorLine);
        if (lineWidth != -1) {
            paint.setStrokeWidth(lineWidth);
        } else {
            paint.setStrokeWidth(4);
        }

        if (points != null) {
            for (int i = 0; i < points.size(); i++) {
                Point point = points.get(i);
                if (!isStartPoint) {
                    isStartPoint = true;
                    path.moveTo(point.x, -point.y - strWidth - 50);
                } else {
//                    if (i%2 == 0){
//                        path.quadTo(point.x-50, -point.y - strWidth - 100, point.x, -point.y - strWidth - 50);
//                    }else {
//                        path.quadTo(point.x-50, -point.y - strWidth, point.x, -point.y - strWidth - 50);
//                    }
                }
            }
            if (points != null) {
                for (Point point : points) {
                    if (!isStartPoint) {
                        isStartPoint = true;
                        path.moveTo(point.x, -point.y - strWidth - 50);
                    } else {
                        path.lineTo(point.x, -point.y - strWidth - 50);
                    }
                }
                canvas.drawPath(path, paint);
            }
        }
    }


    /**
     * 设置点的数据
     * @param points
     */
    public void setDatas(List<Point> points){
        this.mPoints = points;
        invalidate();
    }
   public void setColorLine(int colorLine){
       this.colorLine=colorLine;
   }
    public void setPaintWidth(float width){
        this.lineWidth=width;
        invalidate();

    }
}
