package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Msg;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/11/16.
 */

public class SingleItemAdapter extends RecyclerView.Adapter<SingleItemAdapter.MyViewHolder>  {

    private Context mContext;
    private List<Msg.Data> data = new ArrayList<>();//数据


    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view,int position);
    }

    private static OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    //适配器初始化
    public SingleItemAdapter(Context context, List<Msg.Data> datas) {
        mContext = context;
        this.data = datas;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据item类别加载不同ViewHolder
        Log.e("onCreateViewHolder","");
        View view = LayoutInflater.from(mContext
        ).inflate(R.layout.item_msg, parent,
                false);//这个布局就是一个imageview用来显示图片
        MyViewHolder holder = new MyViewHolder(view);
//
//        //给布局设置点击和长点击监听
//        view.setOnClickListener(this);
//        view.setOnLongClickListener(this);

        return holder;


    }

    //bindData
    @Override
    public void onBindViewHolder(MyViewHolder viewholder, int i) {
         Msg.Data datas =  data.get(i);
        Log.e("onBindViewHolder",i+"");
        viewholder.tv_title.setText(datas.getTitle());
        viewholder.tv_date.setText(datas.getCreationTime());
        if (datas.getContent().equals("")) {
            viewholder.tv_detail.setText("暂无更多信息");
        } else {
            viewholder.tv_detail.setText(datas.getContent());
        }

    }

    @Override
    public int getItemCount() {
        return data.size();//获取数据的个数
    }


    //自定义ViewHolder，用于加载图片
   public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView tv_title, tv_date, tv_detail;
        public LinearLayout msg_detail;
        LinearLayout ll_msg;

        public MyViewHolder(View view) {
            super(view);
            tv_title = (TextView) view.findViewById(R.id.tv_title);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_detail = (TextView) view.findViewById(R.id.tv_detail);
            msg_detail = (LinearLayout) view.findViewById(R.id.msg_detail);
            ll_msg = (LinearLayout) view.findViewById(R.id.ll_msg);
            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            if(mOnItemClickListener!=null){
                mOnItemClickListener.onItemClick(view,getPosition());
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }
}
