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

public class Adapter_state extends BaseAdapter{
    Context context ;
    String[] list;
    public Adapter_state(Context context, String[] list){
        this.context = context;
        this.list = list;
        Log.e("size",String.valueOf(list.length));
    }
    @Override
    public int getCount() {
        return list.length;
    }

    @Override
    public Object getItem(int i) {
        return list[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewholder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.simple_item,null);
            viewholder = new ViewHolder();
            view.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) view.getTag();
        }
        viewholder.tv_state  = (TextView) view.findViewById(R.id.tv_state);
        viewholder.tv_state.setText(list[i]);
        return view;
    }

    static class  ViewHolder{
        TextView tv_state;
    }
}
