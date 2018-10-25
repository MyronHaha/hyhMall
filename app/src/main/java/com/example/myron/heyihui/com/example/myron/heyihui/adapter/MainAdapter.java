package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.myron.heyihui.R;

import java.util.List;

/**
 * Created by Jason on 2017/10/20.
 */

public class MainAdapter extends BaseAdapter{
    Context context ;
    List list;
    public MainAdapter(Context context, List list){
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
            view = LayoutInflater.from(context).inflate(R.layout.item_main,null);
            viewholder = new ViewHolder();
            view.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) view.getTag();
        }
        return view;
    }

    static class  ViewHolder{

    }
}
