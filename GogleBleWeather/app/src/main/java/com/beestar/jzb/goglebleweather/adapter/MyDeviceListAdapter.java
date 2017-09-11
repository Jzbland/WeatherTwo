package com.beestar.jzb.goglebleweather.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beestar.jzb.goglebleweather.R;
import com.beestar.jzb.goglebleweather.bean.DeviceBean;

import java.util.List;

/**
 * Created by jzb on 2017/6/21.
 */

public class MyDeviceListAdapter extends RecyclerView.Adapter<MyDeviceListAdapter.MyViewHolder> implements View.OnClickListener {
    private Context context;
    private List<DeviceBean> datas;
    private OnItemClickListener mOnItemClickListener = null;
    public MyDeviceListAdapter(Context context, List<DeviceBean> datas){
        this.context=context;
        this.datas=datas;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(datas.get(position).getName());
        holder.itemView.setTag(position);
    }
    @Override
    public int getItemCount() {
        return datas.size();
    }
    public void addItem(DeviceBean add){
        datas.add(datas.size(),add);
        notifyItemInserted(datas.size());
    }

    public void removeItem(int position){
        datas.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onClick(View view) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(view,(int)view.getTag());
        }
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        public MyViewHolder(View view) {
            super(view);
            tv = (TextView) view.findViewById(R.id.id_num);
        }
    }
    public void clear(){
        datas.clear();
        notifyDataSetChanged();
    }
    public interface OnItemClickListener{
        void onItemClick(View view , int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }
}
