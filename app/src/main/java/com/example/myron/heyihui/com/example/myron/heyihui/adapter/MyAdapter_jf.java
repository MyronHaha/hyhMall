package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_Jf;

import java.util.List;

/**
 * Created by Jason on 2017/10/20.
 */

public class MyAdapter_jf extends BaseAdapter {
    Context context;
    List list;
    public static int total_jf = 0;

    public MyAdapter_jf(Context context, List list) {
        this.context = context;
        this.list = list;
        Log.e("size", String.valueOf(list.size()));
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

        final ViewHolder viewholder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_jf, null);
            viewholder = new ViewHolder();
            viewholder.tv_from = (TextView) view.findViewById(R.id.tv_from);
            viewholder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            viewholder.tv_jf = (TextView) view.findViewById(R.id.tv_jf);
            viewholder.iv_changeTag = (ImageView) view.findViewById(R.id.iv_changetag);
            view.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) view.getTag();
        }
        if(viewholder!= null) {
//        //  模拟
//        Log.e("i",i+"");
//            int index = (int) list.get(i);
            Order_Jf.Data data = (Order_Jf.Data) list.get(i);
            double goal = data.getCredit();
            viewholder.tv_jf.setText(String.format("%.2f",goal));
            viewholder.tv_from.setText(data.getName());
            viewholder.tv_date.setText(data.getCreationTime());
            if (goal>0) {
                viewholder.iv_changeTag.setBackgroundResource(R.mipmap.icon_jt_h);
                viewholder.tv_jf.setTextColor(Color.parseColor("#ff5d5d"));
            } else {
                viewholder.iv_changeTag.setBackgroundResource(R.mipmap.icon_jt_l);
                viewholder.tv_jf.setTextColor(Color.parseColor("#01be36"));
            }
        }
        return view;
    }



    static class ViewHolder {
        TextView tv_from, tv_date, tv_jf;
        ImageView iv_changeTag;
    }
   public void setData(List data){
        this.list = data;
   }
}
