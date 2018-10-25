package com.example.myron.heyihui.com.example.myron.heyihui.adapter.MallHome;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.HomeProduct;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Product;


import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class MallProductAdapter extends RecyclerView.Adapter<MallProductAdapter.MyViewHolder> {

    private Context mContext;
    private List< HomeProduct.Data> datas = new ArrayList<>();//数据

    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    private static OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    //适配器初始化
    public MallProductAdapter(Context context, List<HomeProduct.Data> data) {
        mContext = context;
        this.datas = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据item类别加载不同ViewHolder
        View view = LayoutInflater.from(mContext
        ).inflate(R.layout.item_product, parent,
                false);
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
        HomeProduct.Data data =  datas.get(i);
//
//        Glide.with(mContext)
//                .load("http://hyh.hyhscm.com/"+data.getImg())
//                .placeholder(R.mipmap.nopictures_bg)
//                .crossFade()
//                .into(viewholder.iv);

    }

    @Override
    public int getItemCount() {
        return datas.size();//获取数据的个数
    }


    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView tv_xh, tv_gg, tv_name, tv_publish, tv_price;
//        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this,view);
//            iv = (ImageView) view.findViewById(R.id.iv_pic);
//            tv_name = (TextView) view.findViewById(R.id.tv_name);
//            tv_xh = (TextView) view.findViewById(R.id.tv_xh);
//            tv_gg = (TextView) view.findViewById(R.id.tv_gg);
//            tv_publish = (TextView) view.findViewById(R.id.tv_publish);
//            tv_price = (TextView) view.findViewById(R.id.tv_price);
//            view.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {

            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(view, getPosition());
            }

        }
    }
    public void setList(List list){
        this.datas = list;
    }
}


