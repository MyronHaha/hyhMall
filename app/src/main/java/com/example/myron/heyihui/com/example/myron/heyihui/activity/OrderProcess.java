package com.example.myron.heyihui.com.example.myron.heyihui.activity;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v7.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_doc;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.OrderProcess_Adapter;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;


public class OrderProcess extends AppCompatActivity {
    ImageView iv_back;
    List list = new ArrayList<>();
    ListView mlist;
    OrderProcess_Adapter orderProcess_adapter;
    TextView tv_orderNum;
    SpringView springView;
    String orderId;// 上级传递的订单id；
    String numbers;//上级传递的订单编号；
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        common.changeTitle(this, "订单跟踪");
        orderId = getIntent().getStringExtra("data");
        numbers = getIntent().getStringExtra("numbers");
        BindView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    private void BindView() {
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common.goBack(OrderProcess.this);
            }
        });
//        common.createData(list);
        tv_orderNum = (TextView) findViewById(R.id.tv_orderNum);
        tv_orderNum.setText(numbers);
        springView  = (SpringView) findViewById(R.id.sv);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }

            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        //DefaultHeader/Footer是SpringView已经实现的默认头/尾之中的一个
        //很多其它还有MeituanHeader、AliHeader、RotationHeader等如上图7种
        springView.setHeader(new DefaultHeader(this));
//        springView.setFooter(new DefaultFooter(this));
        mlist = (ListView) findViewById(R.id.lv);
        orderProcess_adapter = new OrderProcess_Adapter(this, list);
        mlist.setAdapter(orderProcess_adapter);
        common.setListViewHeightBasedOnChildren(mlist,orderProcess_adapter);

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

    //刷新列表辅助
    public void refreshListView(List list) {
        orderProcess_adapter.setData(list);
        orderProcess_adapter.notifyDataSetChanged();
        common.setListViewHeightBasedOnChildren(mlist, orderProcess_adapter);
    }
    public  static  void getOrder_doc(HashMap map , IResponseHandler handler){
        HttpCore.get(URL.GET_ORDER_DOC,map,handler);
    }
    public void setData(List data){
        this.list = data;
    }

    public  void refresh(){
        if(orderId == null||orderId.equals("")){
            Log.e("上级传递数据错误","error");
            return;
        }
        HashMap map = new HashMap();
        map.put("id",orderId);
        getOrder_doc(map, new GsonResHandler<Order_doc>() {
            @Override
            public void onFailed(int i, String s) {
                try {
                    common.logResult("status error");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int i, Order_doc order_doc) {
                if (order_doc.getStatus()==1){
                    list = order_doc.getData();
                    Collections.reverse(list);
//                    Log.e("跟踪",list.size()+"");
                    refreshListView(list);
                    if(order_doc.getData().size()==0){
                            MyToast.makeText(OrderProcess.this.getApplicationContext(),"暂时没有更多跟踪信息",3000).show();
                    }
                }
            }
        });
    }
}
