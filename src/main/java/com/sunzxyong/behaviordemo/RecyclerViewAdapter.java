package com.sunzxyong.behaviordemo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by 晓勇 on 2015/8/25 0025.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<String> datas;
    public RecyclerViewAdapter(List<String> datas){
        this.datas = datas;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.mTextViewContent.setText(datas.get(position));
        holder.mTextViewDate.setText(getNowDate());
    }


    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewContent;
        public TextView mTextViewDate;
        public MyViewHolder(View itemView) {
            super(itemView);
            mTextViewContent = (TextView) itemView.findViewById(R.id.tv_content);
            mTextViewDate = (TextView) itemView.findViewById(R.id.tv_date);
        }
    }
    private String getNowDate(){
         SimpleDateFormat format = new SimpleDateFormat("EEEE", Locale.CHINA);
         return format.format(new Date(System.currentTimeMillis()));
    }

}
