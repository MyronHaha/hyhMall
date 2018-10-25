package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/10/24.
 */

public class ProductAdapter1 extends BaseAdapter{
    Context context ;
    List list;
    //防止大意引用错误
    public void setData(List data){
        this.list = data;
    }
    public ProductAdapter1(Context context, List list){
        this.context = context;
        this.list = list;
        Log.e("size",String.valueOf(list.size()));
    }
    @Override
    public int getCount() {
        if(list==null){
            return 0;
        }
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
        ProductAdapter1.ViewHolder viewholder;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.item_product,null);

            viewholder = new ProductAdapter1.ViewHolder();
            viewholder.iv = (ImageView) view.findViewById(R.id.iv_pic);
            viewholder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewholder.tv_xh = (TextView) view.findViewById(R.id.tv_xh);
            viewholder.tv_gg = (TextView) view.findViewById(R.id.tv_gg);
            viewholder.tv_publish = (TextView) view.findViewById(R.id.tv_publish);
            viewholder.tv_price = (TextView) view.findViewById(R.id.tv_price);
            view.setTag(viewholder);
        }else{
            viewholder = (ProductAdapter1.ViewHolder) view.getTag();
        }

        Product.Data data = (Product.Data) list.get(i);
        viewholder.tv_name.setText(data.getName());
        viewholder.tv_gg.setText(data.getSpec());
        viewholder.tv_price.setText(String .format("%.2f",data.getPrice()));
        viewholder.tv_xh.setText(data.getCode());
        viewholder.tv_publish.setText(data.getManufacturer());
        Glide.with(context)
                .load("http://hyh.hyhscm.com/"+data.getImg())
                .placeholder(R.mipmap.nopictures_bg)
                .crossFade()
                .into(viewholder.iv);
        return view;
    }
    public void addLast(ArrayList<String> list) {
        this.list.addAll(list);
    }
    static class  ViewHolder{
        TextView tv_xh,tv_gg,tv_name,tv_publish,tv_price;
        ImageView iv;
    }
}
