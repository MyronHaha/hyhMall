package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myron.heyihui.R;

import java.util.List;

/**
 * Created by Jason on 2017/10/20.
 */

public class MyAdapter_zc extends BaseAdapter{
    Context context ;
    List list;
    public MyAdapter_zc(Context context, List list){
        this.context = context;
        this.list = list;
        Log.e("size",String.valueOf(list.size()));
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewholder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_zc,null);
            viewholder = new ViewHolder();
            view.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) view.getTag();
        }
        viewholder.tv_gs  = (TextView) view.findViewById(R.id.tv_gs);//公司
        viewholder.tv_date = (TextView) view.findViewById(R.id.tv_date);//riqi
        viewholder.tv_dh  = (TextView) view.findViewById(R.id.tv_dh);//单号
        viewholder.tv_price = (TextView) view.findViewById(R.id.tv_price);//价格
        viewholder.tv_state  = (TextView) view.findViewById(R.id.tv_state);
        return view;
    }

    static class  ViewHolder{
        TextView tv_gs,tv_dh,tv_date,tv_price,tv_state;
    }
}
