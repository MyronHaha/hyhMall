package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Msg;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/11/16.
 */

public class ProductItemAdapter extends RecyclerView.Adapter<ProductItemAdapter.MyViewHolder> {

    private Context mContext;
    private List< Product.Data> datas = new ArrayList<>();//数据


    //自定义监听事件
    public static interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);
    }

    private static OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    //适配器初始化
    public ProductItemAdapter(Context context, List<Product.Data> data) {
        mContext = context;
        this.datas = data;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //根据item类别加载不同ViewHolder
        Log.e("onCreateViewHolder", "");
        View view = LayoutInflater.from(mContext
        ).inflate(R.layout.item_product, parent,
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
        Product.Data data =  datas.get(i);
        viewholder.tv_name.setText(data.getName());
        viewholder.tv_gg.setText(data.getSpec());
        viewholder.tv_price.setText(String .format("%.2f",data.getPrice()));
        viewholder.tv_xh.setText(data.getCode());
        viewholder.tv_publish.setText(data.getManufacturer());
        Glide.with(mContext)
                .load("http://hyh.hyhscm.com/"+data.getImg())
                .placeholder(R.mipmap.nopictures_bg)
                .crossFade()
                .into(viewholder.iv);

    }

    @Override
    public int getItemCount() {
        return datas.size();//获取数据的个数
    }


    static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_xh, tv_gg, tv_name, tv_publish, tv_price;
        ImageView iv;

        public MyViewHolder(View view) {
            super(view);
            iv = (ImageView) view.findViewById(R.id.iv_pic);
            tv_name = (TextView) view.findViewById(R.id.tv_name);
            tv_xh = (TextView) view.findViewById(R.id.tv_xh);
            tv_gg = (TextView) view.findViewById(R.id.tv_gg);
            tv_publish = (TextView) view.findViewById(R.id.tv_publish);
            tv_price = (TextView) view.findViewById(R.id.tv_price);
            view.setOnClickListener(this);

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
