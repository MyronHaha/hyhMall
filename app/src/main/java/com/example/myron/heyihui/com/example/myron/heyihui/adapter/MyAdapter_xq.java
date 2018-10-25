package com.example.myron.heyihui.com.example.myron.heyihui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_Re;

import java.util.List;

/**
 * Created by Jason on 2017/10/20.
 *{
 "address": "",
 "addressId": 0,
 "amount": 1100000,
 "belong": "",
 "belongid": 0,
 "bmodel": "500002005,500002004,500002003,500002002,500002001",
 "bmodels": [],
 "companyId": 628,
 "companyName": "广州天成医疗技术股份有限公司",
 "content": "",
 "creationTime": "2017-03-16 14:17:15",
 "creatorId": 1,
 "credit": null,
 "creditin": null,
 "creditout": null,
 "deliveryType": 0,
 "details": [],
 "docs": [],
 "ext": "",
 "feecso": "",
 "feedist": "",
 "feefin": "",
 "feein": "",
 "hascso": 1,
 "hasdist": 0,
 "hasfin": 0,
 "hasin": 0,
 "id": 24,
 "linkman": "",
 "linkmanTel": "",
 "numbers": "DD17031614170001",
 "orderDate": "2017-03-16 14:17:15",
 "orgId": 3,
 "orgName": "广东合壹汇",
 "remark": "",
 "state": 500001002,
 "stateText": "待付款",
 "states": [],
 "total": null,
 "type": 0,
 "updateId": 1,
 "updateTime": "2017-06-19 14:22:54",
 "userName": "陈志红"
 },
 */

public class MyAdapter_xq extends BaseAdapter{
    Context context ;
    List list;
    public MyAdapter_xq(Context context, List list){
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
            view = LayoutInflater.from(context).inflate(R.layout.item_zc,null);
            viewholder = new ViewHolder();
            viewholder.tv_gs  = (TextView) view.findViewById(R.id.tv_gs);//公司
            viewholder.tv_date = (TextView) view.findViewById(R.id.tv_date);//riqi
            viewholder.tv_dh  = (TextView) view.findViewById(R.id.tv_dh);//单号
            viewholder.tv_price = (TextView) view.findViewById(R.id.tv_price);//价格
            viewholder.tv_state  = (TextView) view.findViewById(R.id.tv_state);
            view.setTag(viewholder);
        }else{
            viewholder = (ViewHolder) view.getTag();
        }
        Order_Re.Data data = (Order_Re.Data) list.get(i);
        viewholder.tv_gs.setText(data.getCompanyName());
        viewholder.tv_date.setText(data.getOrderDate());
        viewholder.tv_dh.setText(data.getNumbers());
        viewholder.tv_price.setText(String .format("%.2f",data.getAmount())+"元");
        viewholder.tv_state.setText(data.getStateText());
        return view;
    }

    static class  ViewHolder{
        TextView tv_gs,tv_dh,tv_date,tv_price,tv_state;
    }
    public  void setData(List data){
        this.list = data;
    }
}
