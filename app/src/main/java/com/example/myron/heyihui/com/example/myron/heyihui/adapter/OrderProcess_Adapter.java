package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_doc;

import java.util.List;

import javax.security.auth.login.LoginException;

/**
 * Created by Jason on 2017/10/20.
 */

public class OrderProcess_Adapter extends BaseAdapter {
    Context context;
    List list;

    public OrderProcess_Adapter(Context context, List list) {
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
    public View getView(int position, View view, ViewGroup viewGroup) {

        final ViewHolder viewholder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.logicst_item, null);
            viewholder = new ViewHolder();
            viewholder.top_line = view.findViewById(R.id.line_top);
            viewholder.tv_action = (TextView) view.findViewById(R.id.tv_action);
            viewholder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            viewholder.point = (ImageView) view.findViewById(R.id.point);
            viewholder.top_gone = view.findViewById(R.id.line_gone);
            view.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) view.getTag();
        }
        if (viewholder != null) {
            Log.e("当前pos",position+"");
            if(position==0){
                viewholder.top_line.setVisibility(View.GONE);
                viewholder.top_gone.setVisibility(View.VISIBLE);
                viewholder.point.setImageResource(R.drawable.point);
            }else{
                viewholder.point.setImageResource(R.drawable.point_gr);
            }

            Order_doc.Data data = (Order_doc.Data) list.get(position);
            viewholder.tv_action.setText(data.getContent());
            viewholder.tv_date.setText(data.getCreationTime());
        }
        return view;
    }

 public void setData(List data){
        this.list = data;
 }
    static class ViewHolder {
        TextView tv_action, tv_date;
        ImageView point;
        View top_line,top_gone;
    }

}
