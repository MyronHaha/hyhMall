package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Msg;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_msg;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;

import java.util.List;

/**
 * Created by Jason on 2017/10/20.
 */

public class MyAdapter_msg extends BaseAdapter {
    Context context;
    List list;
   public static  int lastShowid = -1;

    public MyAdapter_msg(Context context, List list) {
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
    public View getView(final int i, View view, ViewGroup viewGroup) {

        final ViewHolder viewholder;
        ViewHolder viewHolder2 = null;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_msg, null);
            viewholder = new ViewHolder();
            viewholder.tv_title = (TextView) view.findViewById(R.id.tv_title);
            viewholder.tv_date = (TextView) view.findViewById(R.id.tv_date);
            viewholder.tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            viewholder.msg_detail = (LinearLayout) view.findViewById(R.id.msg_detail);
            viewholder.ll_msg = (LinearLayout) view.findViewById(R.id.ll_msg);
            view.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) view.getTag();
        }

//            "content": "您于2017-05-26所下订单，编号：【DD17052616590001】已通过系统确认，我方会计会联系您进行付款，确认付款后仓库会自动进行发货。",
//                    "creationTime": "2017-10-17 16:32:27",
//                    "creatorId": 1,
//                    "id": 5034,
//                    "msgState": 0,
//                    "msgStateText": "已办",
//                    "msgType": 0,
//                    "readDate": null,
//                    "receiveId": 1,
//                    "receiveName": "管理员",
//                    "sourceId": 297,
//                    "sourceParam": "",
//                    "sourceType": 2,
//                    "state": 0,
//                    "stateText": "未读",
//                    "title": "您的订单已通过系统确认，正在等待付款。"

        final Msg.Data data = (Msg.Data) list.get(i);
        if(data.isIshow()){
            viewholder.msg_detail.setVisibility(View.VISIBLE);
        }else{
            viewholder.msg_detail.setVisibility(View.GONE);
        }
        viewholder.ll_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if (viewholder.msg_detail.getVisibility() == View.VISIBLE) {
//                    viewholder.msg_detail.setVisibility(View.GONE);
//                    Log.e("pos",i+"");
//                    notifyDataSetChanged();
//                } else {
//                    Log.e("pos",i+"");
//                    viewholder.msg_detail.setVisibility(View.VISIBLE);
//                    notifyDataSetChanged();
//                }

                if (data.isIshow()) {
                    data.setIshow(false);
//                    notifyDataSetChanged();

                } else {
                    if (lastShowid != -1) {
                        Msg.Data dd = (Msg.Data) list.get(lastShowid);
                        dd.setIshow(false);
                        lastShowid = i;
                        ViewHolder v = (ViewHolder) Detail_msg.mlist.getChildAt(lastShowid).getTag();
                        v.msg_detail.setVisibility(View.GONE);
                        notifyDataSetChanged();
                    }
                    data.setIshow(true);
                    lastShowid = i;
                }

                notifyDataSetChanged();
                Detail_msg.changeHeight();


            }
        });
        viewholder.tv_title.setText(data.getTitle());
        viewholder.tv_date.setText(data.getCreationTime());
        if (data.getContent().equals("")) {
            viewholder.tv_detail.setText("暂无更多信息");
        } else {
            viewholder.tv_detail.setText(data.getContent());
        }
        return view;
    }


    static class ViewHolder {
        TextView tv_title, tv_date, tv_detail;
        LinearLayout msg_detail, ll_msg;
    }

    public void setData(List data) {
        this.list = data;
    }


}
