package com.beestar.jzb.goglebleweather.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;

import java.util.List;

/**
 * Created by jzb on 2017/10/31.
 */

public class MyDeviceListViewAdapter extends BaseAdapter {


    private Context context;
    private List<DeviceBean> datas;
    private ViewHolder holder;
    private DeviceBean devicebean;
    private onItemconnListener mOnItemconnListener;
    public interface onItemconnListener {
        void onReconnClick(int i);
    }
    public void setOnItemDeleteClickListener(onItemconnListener mOnItemconnListener) {
        this.mOnItemconnListener = mOnItemconnListener;
    }
    public MyDeviceListViewAdapter(Context context, List<DeviceBean> datas) {
        this.context = context;
        this.datas = datas;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Override
    public Object getItem(int i) {
        return datas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.layout_item_listdevice, null);
            holder.layout_linear=(RelativeLayout) view.findViewById(R.id.layout_linear);
            holder.radioBtn = (RadioButton) view
                    .findViewById(R.id.radioButton);
            holder.radioBtn.setClickable(false);
            holder.textView = (TextView) view
                    .findViewById(R.id.textView);
            holder.nametext=(TextView)view.findViewById(R.id.name_textview);
            holder.isconntext=(TextView)view.findViewById(R.id.isconn_text) ;

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        devicebean = (DeviceBean) getItem(i);
        holder.radioBtn.setChecked(devicebean.getIsChoose());
        holder.textView.setText(devicebean.getName());
        holder.nametext.setText(devicebean.getSecondName());
        if (devicebean.getIsConn()){
            holder.isconntext.setText("已连接");
        }else {
            holder.isconntext.setText("已断开");
        }
//        holder.layout_linear.setBackgroundColor(Color.rgb(239,239,239));
        return view;
    }
    class ViewHolder {
        RelativeLayout layout_linear;
        RadioButton radioBtn;
        TextView textView,nametext,isconntext;
        Button reconn;
    }
    public void adddata(List<DeviceBean> dd){
        datas.clear();
        datas.addAll(dd);
        notifyDataSetChanged();
    }
    public void cleardata(){
        datas.clear();
        notifyDataSetChanged();
    }
    // 选中当前选项时，让其他选项不被选中
    public void select(int position) {
        if (!datas.get(position).getIsChoose()) {
            datas.get(position).setIsChoose(true);
            for (int i = 0; i < datas.size(); i++) {
                if (i != position) {
                    datas.get(i).setIsChoose(false);

                }
            }
        }
        notifyDataSetChanged();
    }
}
