package com.beestar.jzb.goglebleweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jzb on 2017/12/14.
 * 适用于第二第三个不显示头部
 */

public class LineTemView extends View {
    private Paint paint;
    private Paint textPaint,textPaint2,paintTime,paintTimeText,paintBg;//绘制文字画笔
    private Paint circlePaint=new Paint();
    private int width;//view的宽度
    private int height;//view的高度

    private Path mPath;//数据曲线
    private Path mAssistPath;//辅助曲线
    private PathMeasure mPathMeasure;
    private float drawScale = 1f;
    private float lineWidth=-1;
    private List<Point> mPoints=new ArrayList<>();
    private int colorLine;
    private int strWidth = 10;//绘制文字所占用的宽度

    private Path brokenPath;
    private int brokenLineColor   = 0xff02bbb7;
    private Paint brokenPaint;

    private float defYAxis = 500f;
    private float defXAxis = 20f;

    private int timeCount  = 6;
    private int selectTime = 6;//选中的时间
    public LineTemView(Context context) {
        this(context,null);
        initView();
    }
    public LineTemView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
        initView();
    }
    public LineTemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        width = 2510;
        height = 230;
        brokenPath=new Path();

        paintBg = new Paint();//时间刻度画笔
        paintBg.setColor(Color.rgb(17,31,68));
        paintBg.setAlpha(300);
        paintBg.setTextSize(30);
        paintBg.setAntiAlias(true);

        textPaint = new Paint();//最左边字
        textPaint.setColor(Color.WHITE);
        textPaint.setTextSize(30);
        textPaint.setAntiAlias(true);

        textPaint2 = new Paint();
        textPaint2.setColor(Color.WHITE);
        textPaint2.setTextSize(30);
        textPaint2.setStrokeWidth(5);
        textPaint2.setAlpha(500);
        textPaint2.setAntiAlias(true);

        paintTime = new Paint();//时间刻度画笔
        paintTime.setColor(Color.rgb(17,31,68));
        paintTime.setTextSize(30);
        paintTime.setAntiAlias(true);



        paint = new Paint();
        paint.setStrokeWidth(0.5f);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);

        circlePaint.setAntiAlias(true);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.WHITE);

        brokenPaint = new Paint();
        brokenPaint.setAntiAlias(true);
        brokenPaint.setStyle(Paint.Style.STROKE);
        brokenPaint.setStrokeWidth(5);
        brokenPaint.setStrokeCap(Paint.Cap.ROUND);
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

        drawLine(canvas,mPoints);


    }

    private void drawLine(Canvas canvas,List<Point> mPointList) {
        if (mPointList == null)
            return;
        measurePath(mPoints);
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        //绘制辅助线
        //canvas.drawPath(mAssistPath,paint);

        paint.setColor(Color.rgb(156,185,255));//最终划线颜色
        Path dst = new Path();
        dst.rLineTo(0, 0);
        float distance = mPathMeasure.getLength() * drawScale;
        if (mPathMeasure.getSegment(0, distance, dst, true)) {
            //绘制线
            canvas.drawPath(dst, paint);
            float[] pos = new float[2];
            mPathMeasure.getPosTan(distance, pos, null);
            //绘制阴影
            drawShadowArea(canvas, dst, pos);
            //绘制点
            drawPoint(canvas,pos);
        }
    }
    /**
     * 绘制点
     * @param canvas
     * @param pos
     */
    private void drawPoint(Canvas canvas, final float[] pos){
        Paint redPaint = new Paint();
        redPaint.setColor(Color.WHITE);
        redPaint.setStrokeWidth(3);
        redPaint.setStyle(Paint.Style.FILL);
        for (int i=0;i<mPoints.size();i++){

            if (mPoints.get(i).x > pos[0]) {
                break;
            }

            //绘制浮动文本背景框
            if(i == selectTime - 1) {
                drawFloatTextBackground(canvas, mPoints.get(i).x, -mPoints.get(i).y - dipToPx(10f));
                canvas.drawText(String.valueOf((mPoints.get(i).y-60)/3+"℃"), mPoints.get(i).x - dipToPx(15f), -mPoints.get(i).y - dipToPx(17f), textPaint);

            }
//            canvas.drawCircle(mPoints.get(i).x, -mPoints.get(i).y, 5, redPaint);
        }
    }
    private void drawRect(Canvas canvas,List<Point> mPoints) {
//        /**
//         * 刻度值
//         */
//        canvas.drawText("0℃",10,-60,textPaint);
//        canvas.drawText("20℃",10,-120,textPaint);
//        canvas.drawText("40℃",10,-180,textPaint);
//        /**
//         * 刻度
//         */
//        canvas.drawLine(100,0,95,-0,textPaint);//-20
//        canvas.drawLine(100,-30,98,-30,textPaint);//-10
//        canvas.drawLine(100,-60,95,-60,textPaint);//0
//        canvas.drawLine(100,-90,98,-90,textPaint);//10
//        canvas.drawLine(100,-120,95,-120,textPaint);//20
//        canvas.drawLine(100,-150,98,-150,textPaint);//30
//        canvas.drawLine(100,-180,95,-180,textPaint);//40
//        canvas.drawLine(100,-210,98,-210,textPaint);//40


        /**
         * 坐标轴
         */
//        canvas.drawLine(100,0,100,-230,textPaint);//竖线
        canvas.drawLine(0,0,2400,0,textPaint);//X轴


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
        for(int i = 0; i < mPoints.size(); i++)
        {
            // dipToPx(8)乘以2为了适当增大触摸面积
            if(x > (mPoints.get(i).x - dipToPx(10) * 2) && x < (mPoints.get(i).x + dipToPx(10) * 2))
            {
                Log.i("info", "validateTouch: x======"+i);
                if(y < (dipToPx(mPoints.get(i).y) - dipToPx(10) * 2) && y < (dipToPx(mPoints.get(i).y) + dipToPx(10) * 2))
                {
                    selectTime = i + 1;
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 绘制阴影
     * @param canvas
     * @param path
     * @param pos
     */
    private void drawShadowArea(Canvas canvas, Path path, float[] pos) {
        Log.i("info", "drawShadowArea: "+pos[0]);
        path.lineTo(2400, defYAxis);
        path.lineTo(0, defYAxis);
        path.close();
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.rgb(17,31,100));
        canvas.drawPath(path, paint);
    }
    /**
     * 设置点的数据
     * @param points
     */
    public void setDatas(List<Point> points){
        this.mPoints = points;
        measurePath(points);
        invalidate();

    }
    private void measurePath(List<Point> mPointList) {
        mPath = new Path();
        mAssistPath = new Path();
        float prePreviousPointX = Float.NaN;
        float prePreviousPointY = Float.NaN;
        float previousPointX = Float.NaN;
        float previousPointY = Float.NaN;
        float currentPointX = Float.NaN;
        float currentPointY = Float.NaN;
        float nextPointX;
        float nextPointY;

        final int lineSize = mPointList.size();
        for (int valueIndex = 0; valueIndex < lineSize; ++valueIndex) {
            if (Float.isNaN(currentPointX)) {
                Point point = mPointList.get(valueIndex);
                currentPointX = point.x;
                currentPointY = point.y;
            }
            if (Float.isNaN(previousPointX)) {
                //是否是第一个点
                if (valueIndex > 0) {
                    Point point = mPointList.get(valueIndex - 1);
                    previousPointX = point.x;
                    previousPointY = point.y;
                } else {
                    //是的话就用当前点表示上一个点
                    previousPointX = currentPointX;
                    previousPointY = currentPointY;
                }
            }

            if (Float.isNaN(prePreviousPointX)) {
                //是否是前两个点
                if (valueIndex > 1) {
                    Point point = mPointList.get(valueIndex - 2);
                    prePreviousPointX = point.x;
                    prePreviousPointY = point.y;
                } else {
                    //是的话就用当前点表示上上个点
                    prePreviousPointX = previousPointX;
                    prePreviousPointY = previousPointY;
                }
            }

            // 判断是不是最后一个点了
            if (valueIndex < lineSize - 1) {
                Point point = mPointList.get(valueIndex + 1);
                nextPointX = point.x;
                nextPointY = point.y;
            } else {
                //是的话就用当前点表示下一个点
                nextPointX = currentPointX;
                nextPointY = currentPointY;
            }

            if (valueIndex == 0) {
                // 将Path移动到开始点
                mPath.moveTo(currentPointX, -currentPointY);
                mAssistPath.moveTo(currentPointX, -currentPointY);
            } else {
                // 求出控制点坐标
                final float firstDiffX = (currentPointX - prePreviousPointX);
                final float firstDiffY = (currentPointY - prePreviousPointY);
                final float secondDiffX = (nextPointX - previousPointX);
                final float secondDiffY = (nextPointY - previousPointY);
                final float firstControlPointX = previousPointX + (0.11f * firstDiffX);
                final float firstControlPointY = previousPointY + (0.11f * firstDiffY);
                final float secondControlPointX = currentPointX - (0.11f * secondDiffX);
                final float secondControlPointY = currentPointY - (0.11f * secondDiffY);
                //画出曲线
                mPath.cubicTo(firstControlPointX, -firstControlPointY, secondControlPointX, -secondControlPointY,
                        currentPointX, -currentPointY);
                //将控制点保存到辅助路径上
                mAssistPath.lineTo(firstControlPointX, -firstControlPointY);
                mAssistPath.lineTo(secondControlPointX, -secondControlPointY);
                mAssistPath.lineTo(currentPointX, -currentPointY);
            }

            // 更新值,
            prePreviousPointX = previousPointX;
            prePreviousPointY = previousPointY;
            previousPointX = currentPointX;
            previousPointY = currentPointY;
            currentPointX = nextPointX;
            currentPointY = nextPointY;
        }
        mPathMeasure = new PathMeasure(mPath, false);
    }


   public void setColorLine(int colorLine){
       this.colorLine=colorLine;
   }
    public void setPaintWidth(float width){
        this.lineWidth=width;
        invalidate();

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
//
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
//
        //最后一个点连接到第一个点
        brokenPath.lineTo(x, y);

        canvas.drawPath(brokenPath, brokenPaint);
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
