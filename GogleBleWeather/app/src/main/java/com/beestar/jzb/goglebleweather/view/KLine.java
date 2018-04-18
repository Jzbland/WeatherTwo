package com.beestar.jzb.goglebleweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.ShapeDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jzb on 2018/3/20.
 */

public class KLine extends View {
    private int mWidth=2400;
    private Paint paintbg;
    private Paint paintBc;
    private Paint paintL,paintPoint,paintSear;
    private int paintColor=-1;
    private int lineWidth=-1;
    private List<Point> data=new ArrayList<>();
    public KLine(Context context) {
        super(context);
        init();
    }

    public KLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public KLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(2400,heightMeasureSpec);
    }

    /**
     * 初始化
     */
    private void init() {
        paintBc=new Paint();
        paintBc.setColor(Color.rgb(17,31,68));
        paintBc.setStrokeWidth(1);
        paintBc.setAlpha(80);
        paintBc.setStyle(Paint.Style.FILL);
        paintBc.setAntiAlias(true);

        //虚线画笔
        paintbg=new Paint();
        paintbg.setColor(Color.WHITE);
        paintbg.setStrokeWidth(1);
        paintbg.setAntiAlias(true);

        //描点画笔
        paintPoint=new Paint();
        paintPoint.setColor(Color.WHITE);
        paintPoint.setStrokeWidth(5);
        paintPoint.setTextSize(30);

        //折线画笔
        paintL=new Paint();
        paintL.setColor(Color.WHITE);
        paintL.setStrokeWidth(2);
        paintL.setStyle(Paint.Style.STROKE);
        paintL.setAntiAlias(true);

        //阴影画笔
        paintSear=new Paint();
        paintSear.setStyle(Paint.Style.FILL);
        paintSear.setAntiAlias(true);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (paintColor!=-1){
            //设置曲线颜色
            paintL.setColor(paintColor);
        }
        canvas.drawRect(0,getHeight()*(-1),2400,0,paintBc);
        drawLine(canvas,getHeight());
        drawPonit(canvas,data,getHeight());
    }

    /**
     * 描点
     * @param canvas
     * @param data
     */
    private void drawPonit(Canvas canvas, List<Point> data,int height) {

        if (data.size()!=0){
            /**
             * 初始化标度
             */
            float height1 = (float) height;
            int y = data.get(0).y;
            float d=height1/(2*y);
            //描点
            for (int i=0;i<data.size();i++){
                canvas.drawPoint(100*i,-(data.get(i).y*d),paintPoint);
                canvas.drawText(data.get(i).y+"",100*i,-(data.get(i).y*d)-10,paintPoint);
            }
            Path path=new Path();
            boolean isStart=false;
            for (int i=0;i<data.size();i++){
                Point point = data.get(i);
                if (!isStart){
                    isStart=true;
                    path.moveTo(point.x,point.y*d*(-1));
                }else {
                    path.lineTo(point.x,point.y*d*(-1));
                    Log.i("info", "drawPonit: =====================");
                }
            }
            canvas.drawPath(path,paintL);
            path.lineTo(data.get(data.size()-1).x,0);
            path.lineTo(0,0);
            canvas.drawPath(path,paintSear);
        }
    }

    /**
     * 划线
     * @param canvas
     */
    private void drawLine(Canvas canvas,int height) {
        canvas.translate(0,height);//初始化原点
        for (int i=0;i<24;i++){
            drawOneLine(canvas,100*i,0,100*i,height*(-1),height,paintbg);
        }
    }

    /**
     * 画虚线
     */
    private void drawOneLine(Canvas canvas,float startX, float startY, float stopX, float stopY ,int height,Paint paint){
        for (int i = 0; i<100;i++){
            if (i%2==0){
                canvas.drawLine(startX,-(height*i)/100,stopX,-(height*(i+1))/100,paint);
            }
        }
    }

    /**
     * 设置曲线画笔颜色
     * @param color
     */
    public void setColorLine(int color){
        this.paintColor=color;
        invalidate();
    }

    /**
     * 设置曲线的值
     * @param points
     */
    public void setDatas(List<Point> points){
        this.data=points;
        invalidate();
    }


    public void setShape(int i){

        LinearGradient lg1 = new LinearGradient( 100, getHeight() * (-1), 100, 0,Color.argb(255,220,20,60), Color.argb(0,220,20,60), Shader.TileMode.CLAMP);
        LinearGradient lg2 = new LinearGradient(100, getHeight() * (-1), 100, 0, Color.argb(255,55,190,240), Color.argb(0,55,190,240), Shader.TileMode.CLAMP);
        LinearGradient lg3 = new LinearGradient(100, getHeight() * (-1), 100, 0, Color.argb(255,242,159,62), Color.argb(0,242,159,62), Shader.TileMode.CLAMP);
        LinearGradient lg4 = new LinearGradient(100, getHeight() * (-1), 100, 0, Color.argb(255,153,71,255), Color.argb(0,153,71,255), Shader.TileMode.CLAMP);

        switch (i){
            case 1:
                paintSear.setShader(lg1);
                Log.i("info", "setShape: ====1111111111111");
                invalidate();
                break;
            case 2:
                paintSear.setShader(lg2);
                invalidate();
                break;
            case 3:
                paintSear.setShader(lg3);
                invalidate();
                break;
            case 4:
                paintSear.setShader(lg4);
                invalidate();
                break;
        }
    }
    public void setBcAlpha(int alpha){
        paintBc.setAlpha(alpha);
        invalidate();
    }
    public void setWidthLine(int i){
        paintL.setStrokeWidth(i);
        invalidate();
    }
}
