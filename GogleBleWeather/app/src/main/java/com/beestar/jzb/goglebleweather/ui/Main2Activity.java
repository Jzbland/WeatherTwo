package com.beestar.jzb.goglebleweather.ui;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.beestar.jzb.goglebleweather.R;

public class Main2Activity extends BaseActivity{
    int i,j,k,screenHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

         i=getDeviceHeight(this);
        j=getNavigationBarHeight(this);
        k=getStatusBarHeight(this);
        int screenHeight=i-j-k;
        Log.i("info",i+"------"+j+"----"+k);
        setMyLayoutHeight(screenHeight);
    }
    private void setMyLayoutHeight(int g) {

        ((ImageView) findViewById(R.id.imageView_bac1)).getLayoutParams().height=((int)(g*1)+48);
        ((ImageView) findViewById(R.id.imageView_bac2)).getLayoutParams().height=((int)(g*1)+48);

       ((RelativeLayout) findViewById(R.id.title_main)).getLayoutParams().height=(int)(g*0.1) ;
        ((RelativeLayout) findViewById(R.id.main_contont_all)).getLayoutParams().height=((int)(g*1)+48);


    }

    /**获取屏幕的高*/
    public static int getDeviceHeight(Activity context){
        Display display = context.getWindowManager().getDefaultDisplay();
        Point p = new Point();
        display.getSize(p);
        return p.y;
    }

    /**
     * 获取顶部状态栏
     * @param context
     * @return
     */
    private int getStatusBarHeight(Activity context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("status_bar_height", "dimen","android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Status height:" + height);
        return height;
    }
    /**
     * 获取底部导航栏高度
     */
    private int getNavigationBarHeight(Activity context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        Log.v("dbw", "Navi height:" + height);
        return height;
    }
}
