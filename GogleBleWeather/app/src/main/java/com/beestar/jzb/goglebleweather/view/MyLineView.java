package com.beestar.jzb.goglebleweather.view;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by JZB on 2018/2/2.
 */

public class MyLineView extends View {
    private String TAG="jzb";
    private Paint paint ,linePaint;

    private int width;//view的宽度
    private int height;//view的高度
    private List<Point> linedata=new ArrayList<>();

    private Path brokenPath;
    private int brokenLineColor   = 0xff02bbb7;
    private Paint brokenPaint;
    private int timeCount=6;


    public MyLineView(Context context) {
        this(context,null);
        initView();
    }
    public MyLineView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        initView();
    }
    public MyLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        width = 2300;

        paint= new Paint();
        paint.setColor(Color.rgb(10,10,120));
        paint.setTextSize(32);
        paint.setStrokeWidth(1);
        paint.setAlpha(1000);
        paint.setAntiAlias(true);

        linePaint= new Paint();
        linePaint.setColor(Color.WHITE);
        linePaint.setTextSize(32);
        linePaint.setStrokeWidth(1);
        linePaint.setAlpha(1000);
        linePaint.setAntiAlias(true);

        brokenPaint = new Paint();
        brokenPaint.setAntiAlias(true);
        brokenPaint.setStyle(Paint.Style.STROKE);
        brokenPaint.setStrokeWidth(5);
        brokenPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);// 得到模式
        height = MeasureSpec.getSize(heightMeasureSpec);// 得到尺寸
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawKLine(canvas);
    }
    private void drawKLine(Canvas canvas) {
        canvas.translate(0,height);
        drawRect(canvas);
        drawPoint(canvas,linedata);

    }

    private void drawPoint(Canvas canvas,List<Point> dataPoint) {
        Paint redPaint = new Paint();
        Path path=new Path();
        brokenPath=new Path();
        redPaint.setColor(Color.WHITE);
        redPaint.setStrokeWidth(3);
        redPaint.setStyle(Paint.Style.FILL);

        Paint linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);
//        linePaint.setStrokeJoin(Paint.Join.ROUND);
        CornerPathEffect cornerPathEffect = new CornerPathEffect(200);
        linePaint.setPathEffect(cornerPathEffect);
        linePaint.setColor(Color.parseColor("#f8f8ff"));

        for (int i=0;i<dataPoint.size();i++){
//            canvas.drawCircle(dataPoint.get(i).x, -((height*(dataPoint.get(i).y+20))/70), 5, redPaint);
            if (i==0){
                path.moveTo(dataPoint.get(i).x, -((height*(dataPoint.get(i).y+20))/70));
            }else {
                path.lineTo(dataPoint.get(i).x, -((height*(dataPoint.get(i).y+20))/70));
            }
            if (i==timeCount-1){
                drawFloatTextBackground(canvas, dataPoint.get(i).x, -((height*(dataPoint.get(i).y+20))/70) - dipToPx(10f));
                canvas.drawText(String.valueOf(dataPoint.get(i).y+"℃"), dataPoint.get(i).x - dipToPx(15f), -((height*(dataPoint.get(i).y+20))/70) - dipToPx(17f), paint);
            }

        }
        canvas.drawPath(path,linePaint);
        if (dataPoint.size()!=0){
            redPaint.setPathEffect(cornerPathEffect);

            redPaint.setAlpha(100);
            path.lineTo(2800,100);
            path.lineTo(-10,100);
            path.lineTo(0,-((height*(dataPoint.get(0).y+20))/70));

            canvas.drawPath(path,redPaint);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        this.getParent().requestDisallowInterceptTouchEvent(true);//一旦底层View收到touch的action后调用这个方法那么父层View就不会再调用onInterceptTouchEvent了，也无法截获以后的action

        switch(event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                onActionUpEvent(event);
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
            case MotionEvent.ACTION_CANCEL:
                this.getParent().requestDisallowInterceptTouchEvent(false);
                break;
        }
        return true;
    }

    private void onActionUpEvent(MotionEvent event) {
        boolean isValidTouch = validateTouch(event.getX(), event.getY());

        if(isValidTouch)
        {
            invalidate();
        }
    }

    private boolean validateTouch(float x, float y) {
        //曲线触摸区域
        for(int i = 0; i < linedata.size(); i++)
        {
            // dipToPx(8)乘以2为了适当增大触摸面积
            if(x > (linedata.get(i).x - dipToPx(10) * 2) && x < (linedata.get(i).x + dipToPx(10) * 2))
            {
                Log.i("info", "validateTouch: x======"+i);
                if(y < (((height*(linedata.get(i).y+20))/70) - dipToPx(10) * 2) && y < (((height*(linedata.get(i).y+20))/70) + dipToPx(10) * 2))
                {
                    timeCount = i + 1;
                    return true;
                }
            }
        }
        return false;
    }


    //绘制显示浮动文字的背景
    private void drawFloatTextBackground(Canvas canvas, int x, int y)
    {
        brokenPath.reset();
        brokenPaint.setColor(brokenLineColor);
        brokenPaint.setStyle(Paint.Style.FILL);

        //P1
        Point point = new Point(x, y);
        brokenPath.moveTo(point.x, point.y);

        //P2
        point.x = point.x + dipToPx(5);
        point.y = point.y - dipToPx(5);
        brokenPath.lineTo(point.x, point.y);

        //P3
        point.x = point.x + dipToPx(12);
        brokenPath.lineTo(point.x, point.y);

        //P4
        point.y = point.y - dipToPx(17);
        brokenPath.lineTo(point.x, point.y);

        //P5
        point.x = point.x - dipToPx(34);
        brokenPath.lineTo(point.x, point.y);

        //P6
        point.y = point.y + dipToPx(17);
        brokenPath.lineTo(point.x, point.y);

        //P7
        point.x = point.x + dipToPx(12);
        brokenPath.lineTo(point.x, point.y);

        //最后一个点连接到第一个点
        brokenPath.lineTo(x, y);

        canvas.drawPath(brokenPath, brokenPaint);
    }

    private void drawRect(Canvas canvas) {
//        canvas.drawRect(0,-height,2400,0,paint);
//        canvas.drawLine(0,-(int)(height/7),2400,-(int)(height/7),linePaint);
//        canvas.drawLine(0,-(int)(2*height/7),2400,-(int)(2*height/7),linePaint);
//        canvas.drawLine(0,-(int)(3*height/7),2400,-(int)(3*height/7),linePaint);
//        canvas.drawLine(0,-(int)(4*height/7),2400,-(int)(4*height/7),linePaint);
//        canvas.drawLine(0,-(int)(5*height/7),2400,-(int)(5*height/7),linePaint);
//        canvas.drawLine(0,-(int)(6*height/7),2400,-(int)(6*height/7),linePaint);
    }
    public void setData(List<Point> data){
        this.linedata=data;
        invalidate();
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
