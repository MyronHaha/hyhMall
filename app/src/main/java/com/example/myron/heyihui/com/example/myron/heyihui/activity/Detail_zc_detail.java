package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_Item;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.MyAdapter_zc_detail;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
//{
//        "data": {
//        "address": "",
//        "addressId": 0,
//        "amount": 234922.5,
//        "belong": "",
//        "belongid": 0,
//        "bmodel": "",
//        "bmodels": [],
//        "companyId": 103,
//        "companyName": "广东合壹汇供应链管理有限公司",
//        "content": "",
//        "creationTime": "2017-03-10 09:33:00",
//        "creatorId": 1,
//        "credit": null,
//        "creditin": null,
//        "creditout": null,
//        "deliveryType": 0,
//        "docs": [],
//        "ext": "",
//        "feecso": "",
//        "feedist": "",
//        "feefin": "",
//        "feein": "",
//        "hascso": 1,
//        "hasdist": 1,
//        "hasfin": 0,
//        "hasin": 0,
//        "id": 21,
//        "linkman": "",
//        "linkmanTel": "",
//        "numbers": "DD17031009330001",
//        "orderDate": null,
//        "orgId": 3,
//        "orgName": "广东合壹汇",
//        "remark": "",
//        "state": 500001002,
//        "stateText": "待付款",
//        "states": [],
//        "total": null,
//        "type": 0,
//        "updateId": 421,
//        "updateTime": "2017-03-10 13:40:14",
//        "userName": "彭钊"
//        },
//        "message": "获取成功！",
//        "status": 1,
//        "total": 0
//        }
/*
* 订单详情页面*/
public class Detail_zc_detail extends AppCompatActivity {
    ImageView iv_back;//返回
    TextView tv_gs,tv_date,tv_dh,tv_state,tv_total;//公司，日期，单号，顶大状态
    RelativeLayout rl_ddgz;//订单跟踪；
    String orderId; // 上级传递要查询的orderid;
   //
    ListView mlist;
    MyAdapter_zc_detail myAdapter_zc_detail;
    List list = new ArrayList();
    Order_Item  item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_zc_detail);
        common.changeTitle(this, "支出详情");
        orderId = getIntent().getStringExtra("data");
        BindView();
        initData();
    }

    private void initData() {
        HashMap map = new HashMap();
        Log.e("id",orderId);
        map.put("id",orderId);
        getBuyList(map, new GsonResHandler<Order_Item>() {
            @Override
            public void onFailed(int i, String s) {

            }
            @Override
            public void onSuccess(int i, Order_Item order_item) {
                if(order_item.getStatus()==1){
                    try {
                        common.logResult("size"+(order_item.getData().getDetails().size()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    list = order_item.getData().getDetails();
                    refreshListView(list);
                    item = order_item;
                   Order_Item.Data data = order_item.getData();
                    tv_gs.setText(data.getCompanyName());
                    tv_date.setText(data.getCreationTime());
                    tv_dh.setText(data.getNumbers());
                    tv_state.setText(data.getStateText());
                    tv_total.setText(String.format("%.2f",order_item.getData().getAmount()));
                }else{
                    try {
                        common.logResult("status error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void BindView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common.goBack(Detail_zc_detail.this);
            }
        });
//        common.createData(list);
        tv_gs = (TextView) findViewById(R.id.tv_gs);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_total = (TextView) findViewById(R.id.tv_total);
        tv_dh = (TextView) findViewById(R.id.tv_dh);
        tv_state  = (TextView) findViewById(R.id.tv_state);
        rl_ddgz = (RelativeLayout) findViewById(R.id.rl_ddgz);
        rl_ddgz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                MyToast.makeText(Detail_zc_detail.this,"跟踪",Toast.LENGTH_SHORT).show();
                String numbers = item.getData().getNumbers();
                Log.e("numbers",numbers);
                Intent intent = new Intent(Detail_zc_detail.this,OrderProcess.class);
                intent.putExtra("numbers",numbers);
                intent.putExtra("data",orderId);
                common.launchActivityWithIntent(Detail_zc_detail.this,intent);
            }
        });
//listview
        mlist = (ListView) findViewById(R.id.lv);
        myAdapter_zc_detail = new MyAdapter_zc_detail(this,list);
        mlist.setAdapter(myAdapter_zc_detail);
        common.setListViewHeightBasedOnChildren(mlist,myAdapter_zc_detail);
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id = ((Order_Item.Data.Details)list.get(i)).getGoodsId();
                common.launchActivityWithData(Detail_zc_detail.this,Detail_Product2.class,String.valueOf(id));

            }
        });

    }

    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    break;
                case 1:
                    break;
            }
        }
    };
    public void  getBuyList(HashMap map , IResponseHandler handler){
        HttpCore.get(URL.GETRE_ORDER,map,handler);
    }
    //刷新列表辅助
    public void refreshListView(List list) {
        myAdapter_zc_detail.setData(list);
        myAdapter_zc_detail.notifyDataSetChanged();
        common.setListViewHeightBasedOnChildren(mlist, myAdapter_zc_detail);
    }

}
