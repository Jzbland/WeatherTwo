package com.beestar.jzb.goglebleweather.ui;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.MyApp;
import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.utils.SQLdm;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
public class ImageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView mImageActivity,mImageActivity2;
    private ImageView mBack;
    private TextView title_img;
    private TextView description_img;
    private TextView poetry_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        initView();

        Picasso.with(this).load(getPic(0)).into(mImageActivity);
        Picasso.with(this).load(getPic(1)).into(mImageActivity2);

        SQLdm s = new SQLdm();
        SQLiteDatabase db =s.openDatabase(MyApp.getContext());
        String sql="select * from weather_calendar where doo=?";
        String data=getDate()+"";
       try {
           Cursor c = db.rawQuery(sql, new String[]{data});
           String title=null;
           while (c.moveToNext()){
               title = c.getString(c.getColumnIndex("title"));
               String description = c.getString(c.getColumnIndex("description"));
               String poetry = c.getString(c.getColumnIndex("poetry"));
               Log.i("info", "onCreate: "+title+"__"+description+"__"+poetry);

               title_img.setText(title);
               description_img.setText(description);
               poetry_img.setText(poetry);

               if (title!=null){
                   break;
               }
           }
       }catch (Exception e){

       }
    }

    private void initView() {
        mImageActivity = (ImageView) findViewById(R.id.image_activity);
        mImageActivity2 = (ImageView) findViewById(R.id.image_activity2);
        title_img = ((TextView) findViewById(R.id.title_img));
        description_img = ((TextView) findViewById(R.id.description_img));
        poetry_img = ((TextView) findViewById(R.id.poetry_img));
        mBack = (ImageView) findViewById(R.id.back);
        mBack.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }
    private String getPic(int i){
        String url = "http://123.207.173.111/PWS/images/calendar/";
        if (i == 0){
            return url+getDate()+"L.jpg";
        }
        else{
            return url+getDate()+"R.jpg";
        }

    }
    private String getDate(){
        String[] weeks = {"星期日","星期一","星期二","星期三","星期四","星期五","星期六"};
        Calendar c = Calendar.getInstance();
//        Date date = c.getTime();
        String m = "", d = "";
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        if (month <= 9){
            m = "0" + month;
        }else{
            m = month + "";
        }
        int day = c.get(Calendar.DAY_OF_MONTH);
        if (day <= 9){
            d = "0" + day;
        }else{
            d = day + "";
        }
        int week_index = c.get(Calendar.DAY_OF_WEEK) - 1;
        if(week_index<0){
            week_index = 0;
        }
//        return weeks[week_index];
//        date.setText(month+"月"+day+"日" + "  " + weeks[week_index]);
        return year+"-"+m+"-"+d;
    }
}
