package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_Item;

import java.util.List;

/**
 * Created by Jason on 2017/10/20.

 "details": [
 {
 "amount": 58730.625,
 "content": "",
 "flag": 0,
 "goodsCode": "HGT-200III",
 "goodsId": 5218,
 "goodsName": "亚低温治疗仪",
 "id": 24,
 "manufacturer": "珠海和佳医疗设备股份有限公司",
 "orderId": 21,
 "price": 58730.625,
 "price1": null,
 "price2": null,
 "qty": 1,
 "rebate": null,
 "sales": null,
 "spec": "HGT-200III",
 "unit": "套"
 }
 ],
 */

public class MyAdapter_zc_detail extends BaseAdapter {
    Context context;
    List list;

    public MyAdapter_zc_detail(Context context, List list) {
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
            view = LayoutInflater.from(context).inflate(R.layout.item_product_zc, null);
            viewholder = new ViewHolder();
            viewholder.tv_name = (TextView) view.findViewById(R.id.tv_name);
            viewholder.tv_dw = (TextView) view.findViewById(R.id.tv_dw);
            viewholder.tv_gg = (TextView) view.findViewById(R.id.tv_gg);
            viewholder.tv_publish = (TextView) view.findViewById(R.id.tv_publish);
            viewholder.tv_xh = (TextView) view.findViewById(R.id.tv_xh);
            viewholder.tv_single  = (TextView) view.findViewById(R.id.tv_single_price);
            viewholder.tv_amount = (TextView) view.findViewById(R.id.tv_amount);

            view.setTag(viewholder);
        } else {
            viewholder = (ViewHolder) view.getTag();
        }
        Order_Item.Data.Details item = (Order_Item.Data.Details) list.get(i);
          viewholder.tv_publish.setText(item.getManufacturer());
          viewholder.tv_single.setText("¥"+String.format("%.2f",item.getPrice())+"元");
          viewholder.tv_gg.setText(item.getSpec());
          viewholder.tv_xh.setText(item.getGoodsCode());
          viewholder.tv_name.setText(item.getGoodsName());
          viewholder.tv_dw.setText(item.getUnit());
          viewholder.tv_amount.setText("X"+item.getQty());
        return view;
    }


    static class ViewHolder {
        TextView tv_name, tv_publish, tv_xh,tv_gg,tv_dw,tv_single,tv_amount;
    }
    public void setData(List data){
        this.list = data;
    }

}
