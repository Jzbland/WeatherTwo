package com.beestar.jzb.goglebleweather.view;

/**
 * Created by jzb on 2017/12/3.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.github.lzyzsd.randomcolor.RandomColor;

import java.util.Random;

/**
 * Created by AItsuki on 2016/1/12.
 */
public class BallView extends View {
    private Paint paint;
    private final Random mRandom;
    private String text="";
    private String text2="";
    private int colorText;
    private Bitmap mBackground;

    class Ball {
        int radius; // 半径
        float cx;   // 圆心
        float cy;   // 圆心
        float vx; // X轴速度
        float vy; // Y轴速度
        Paint paint;

        // 移动
        void move() {
            //向角度的方向移动，偏移圆心
            cx += vx;
            cy += vy;
        }

        int left() {
            return (int) (cx - radius);
        }

        int right() {
            return (int) (cx +radius);
        }

        int bottom() {
            return (int) (cy + radius);
        }

        int top() {
            return (int) (cy - radius);
        }
    }

    private int mCount = 1;   // 小球个数
    private int maxRadius;  // 小球最大半径
    private int minRadius; // 小球最小半径
    private int minSpeed = 100; // 小球最小移动速度
    private int maxSpeed = 150; // 小球最大移动速度

    private int mWidth = 200;
    private int mHeight = 200;


    public Ball[] mBalls;   // 用来保存所有小球的数组
    public void setaddBackground(Bitmap bm){
        this.mBackground=bm;
    }
    public BallView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // 初始化所有球(设置颜色和画笔, 初始化移动的角度)
        mRandom = new Random();
        RandomColor randomColor = new RandomColor(); // 随机生成好看的颜色，github开源库。
        mBalls = new Ball[mCount];

        for(int i=0; i< mCount; i++) {
            mBalls[i] = new Ball();
            // 设置画笔
            paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.FILL);
//            paint.setAlpha(100);
            paint.setStrokeWidth(1);


            // 设置速度
            float speedX = (mRandom.nextInt(maxSpeed -minSpeed +1)+5)/10f;
            float speedY = (mRandom.nextInt(maxSpeed -minSpeed +1)+5)/10f;
            mBalls[i].paint = paint;
            mBalls[i].vx = mRandom.nextBoolean() ? speedX : -speedX;
            mBalls[i].vy = mRandom.nextBoolean() ? speedY : -speedY;
        }
        // 圆心和半径测量的时候才设置
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = resolveSize(mWidth, widthMeasureSpec);
        mHeight = resolveSize(mHeight, heightMeasureSpec);
        setMeasuredDimension(mWidth, mHeight);
        maxRadius = mWidth/8;
        minRadius = maxRadius;

        // 初始化圆的半径和圆心
        for (int i=0; i<mBalls.length; i++) {
            mBalls[i].radius = mRandom.nextInt(maxRadius+1 - minRadius) +minRadius;
//            mBalls[i].mass = (int) (Math.PI * mBalls[i].radius * mBalls[i].radius);
            // 初始化圆心的位置， x最小为 radius， 最大为mwidth- radius
            mBalls[i].cx = mRandom.nextInt(mWidth - mBalls[i].radius) + mBalls[i].radius;
            mBalls[i].cy = mRandom.nextInt(mHeight - mBalls[i].radius) + mBalls[i].radius;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long startTime = System.currentTimeMillis();
        int center = getWidth() / 2;
        // 先画出所有圆
        for (int i = 0; i < mCount; i++) {
            Ball ball = mBalls[i];
//            canvas.drawCircle(ball.cx, ball.cy, ball.radius, ball.paint);
            Paint paint1=new Paint(Paint.ANTI_ALIAS_FLAG);
            paint1.setTextSize(mWidth/20-20);
            paint1.setColor(Color.WHITE);

            Paint paint2=new Paint(Paint.ANTI_ALIAS_FLAG);
            paint2.setTextSize(mWidth/13-20);
            paint2.setColor(Color.WHITE);
            canvas.drawBitmap(x1tox2(mBackground),ball.cx-maxRadius,ball.cy-maxRadius,paint);
            canvas.drawText(text, ball.cx-maxRadius/4 ,
                    ball.cy-mWidth/20 ,paint1);// 设置文字竖直方向居中
            canvas.drawText(text2, ball.cx-maxRadius/2 ,
                    ball.cy +mWidth/20,paint2);

        }

        // 球碰撞边界
        for (int i = 0; i < mCount; i++) {
            Ball ball = mBalls[i];
            collisionDetectingAndChangeSpeed(ball); // 碰撞边界的计算
            ball.move(); // 移动
        }

        long stopTime = System.currentTimeMillis();
        long runTime = stopTime - startTime;
        // 16毫秒执行一次
        postInvalidateDelayed(Math.abs(runTime -16));

    }



    // 判断球是否碰撞碰撞边界
    public void collisionDetectingAndChangeSpeed(Ball ball) {
        int left = getLeft();
        int top = getTop();
        int right = getRight();
        int bottom = getBottom();

        float speedX = ball.vx;
        float speedY = ball.vy;

        // 碰撞左右，X的速度取反。 speed的判断是防止重复检测碰撞，然后黏在墙上了=。=
        if(ball.left() <= left && speedX < 0) {
            ball.vx = -ball.vx;
        } else if(ball.top() <= top && speedY < 0) {
            ball.vy = -ball.vy;
        } else if(ball.right() >= right && speedX >0) {
            ball.vx = -ball.vx;
        } else if(ball.bottom() >= bottom && speedY >0) {
            ball.vy = -ball.vy;
        }
    }
    public void setText(String s) {
        this.text2 = s;
    }
    public void setTextName(String s){
        this.text=s;
    }
    public  void setColorText(int s){
        this.colorText=s;
    }
    public Bitmap x1tox2(Bitmap bm){
        // 获得图片的宽高
        int width = bm.getWidth();
        int height = bm.getHeight();
        // 设置想要的大小
        int newWidth = mWidth/4+20;
        int newHeight = mWidth/4+20;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // 得到新的图片
        Bitmap newbm = Bitmap.createBitmap(bm, 0, 0, width, height, matrix,
                true);
        return newbm;
    }
}
