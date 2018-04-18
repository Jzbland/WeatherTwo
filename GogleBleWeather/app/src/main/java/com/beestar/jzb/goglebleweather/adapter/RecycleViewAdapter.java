package com.beestar.jzb.goglebleweather.adapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.bean.Weather_Bean;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jzb on 2018/3/22.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHodler> implements View.OnClickListener {


    private Context context;
    private List<Weather_Bean.ResultBeanX.ResultBean.HourlyBean> data;
    private List<Boolean> isClick=new ArrayList<>();
    OnItemOnClickListener mOnItemOnClickListener;

    public RecycleViewAdapter(Context context, List<Weather_Bean.ResultBeanX.ResultBean.HourlyBean> databean,List<Boolean> isClick) {
        this.context = context;
        this.data = databean;
        this.isClick=isClick;
    }

    @Override
    public MyViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.layout_item, parent, false);
        MyViewHodler holder = new MyViewHodler(view);
        view.setOnClickListener(this);
        return holder;
    }


    @Override
    public void onBindViewHolder(final MyViewHodler holder, int position) {
        holder.weather.setText(data.get(position).getWeather());
        holder.weather_tem.setText(data.get(position).getTemp());
        holder.weather_tim.setText(data.get(position).getTime());
        try {
            holder.weather_img.setImageDrawable(context.getResources().getDrawable(context.getResources().getIdentifier("weather" + data.get(position).getImg(), "mipmap", context.getPackageName())));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (isClick.size()!=0){
            if (isClick.get(position)){
                holder.linearLayout.setBackground(context.getDrawable(R.drawable.gradient));
            } else {
                holder.linearLayout.setBackgroundColor(Color.argb(0,255,180,180));
            }
        }

        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemOnClickListener!=null){
            mOnItemOnClickListener.onItemClick(v,(int)v.getTag());
        }
    }

    class MyViewHodler extends RecyclerView.ViewHolder {

        private final LinearLayout linearLayout;
        private ImageView weather_img;
        private TextView weather;

        private TextView weather_tem;
        private TextView weather_tim;

        public MyViewHodler(View itemView) {
            super(itemView);
            weather = ((TextView) itemView.findViewById(R.id.weather));
            weather_img = ((ImageView) itemView.findViewById(R.id.weather_img));
            weather_tem = ((TextView) itemView.findViewById(R.id.weather_tem));
            weather_tim = ((TextView) itemView.findViewById(R.id.weather_tim));
            linearLayout = ((LinearLayout) itemView.findViewById(R.id.linea_bg));
        }
    }
    public void clear(){
        data.clear();
        notifyDataSetChanged();
    }
    public void addData(List<Weather_Bean.ResultBeanX.ResultBean.HourlyBean> dd){

        data.addAll(dd);
        isClick=new ArrayList<>();
        for (int i=0;i<dd.size();i++){
            isClick.add(false);
        }
        notifyDataSetChanged();
    }
    public interface OnItemOnClickListener{
        void onItemClick(View view,int postion);
    }
    public void setmOnItemOnClickListener(OnItemOnClickListener listener){
        mOnItemOnClickListener=listener;
    }
    public void addIsClick(List<Boolean> bb){
        isClick.clear();
        isClick.addAll(bb);
        notifyDataSetChanged();
    }
}
