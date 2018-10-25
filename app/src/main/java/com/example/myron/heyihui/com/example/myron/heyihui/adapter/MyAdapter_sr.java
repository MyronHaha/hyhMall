package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
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
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_sr;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_msg;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_sr;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;

import java.util.List;

/**
 * Created by Jason on 2017/10/20.
 *   "accountId": 45,
 "amount": 90000,
 "creationTime": "2017-01-11 00:00:00",
 "creatorId": 24,
 "direction": 1,
 "id": 641,
 "name": "广州威齐贸易有限公司",
 "realTime": "2017-01-11 00:00:00",
 "remark": "0106日长沙银行收广州威齐90000元",
 "s": 0,
 "sourceId": 462,
 "state": 2,
 "type": 0,
 "updateId": 24,
 "updateTime": "2017-01-11 00:00:00",
 "userId": 1,
 "userName": ""
 */

public class MyAdapter_sr extends BaseAdapter{
    Context context ;
    List list;
    public MyAdapter_sr(Context context, List list){
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
        final ViewHolder viewholder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_sr,null);
            viewholder = new ViewHolder();
            view.setTag(viewholder);
            viewholder.tv_gs  = (TextView) view.findViewById(R.id.tv_gs);
            viewholder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            viewholder.tv_more  = (TextView) view.findViewById(R.id.tv_more);
            viewholder.tv_sr = (TextView) view.findViewById(R.id.tv_price);
            viewholder.bt_more = (Button) view.findViewById(R.id.bt_more);
            viewholder.ll_more = (LinearLayout) view.findViewById(R.id.ll_more);
        }else{
            viewholder = (ViewHolder) view.getTag();
        }

        viewholder.bt_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("click","more");
                if(viewholder.ll_more.getVisibility() == View.VISIBLE){
                    Log.e("VISIBLE","more");
                    viewholder.ll_more.setVisibility(View.GONE);
                    viewholder.bt_more.setBackgroundResource(R.mipmap.main_jt_xia);
                    notifyDataSetChanged();
                    common.setListViewHeightBasedOnChildren(Detail_sr.mlist,Detail_sr.adapter);
                }else {
                    viewholder.bt_more.setBackgroundResource(R.mipmap.main_jt_shang);
                    viewholder.ll_more.setVisibility(View.VISIBLE);
                    notifyDataSetChanged();
                    common.setListViewHeightBasedOnChildren(Detail_sr.mlist,Detail_sr.adapter);
                }
            }
        });
        Order_sr.Data data = (Order_sr.Data) list.get(i);
        viewholder.tv_gs.setText(data.getName());
        viewholder.tv_date.setText(data.getRealTime());
        viewholder.tv_sr.setText(String.format("%.2f",data.getAmount())+"元");
        viewholder.tv_more.setText(data.getRemark());
        return view;
    }

    static class  ViewHolder{
        TextView tv_gs,tv_more,tv_date,tv_sr;
        Button bt_more;
        LinearLayout ll_more;

    }
    public void setData(List data){
        this.list = data;
    }
}
