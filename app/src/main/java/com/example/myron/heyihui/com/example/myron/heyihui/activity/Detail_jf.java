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


import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_Jf;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_Re;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.MyAdapter_jf;

import com.example.myron.heyihui.com.example.myron.heyihui.fragment.MyFragment;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CustomDialog;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;
import com.mph.okdroid.response.JsonResHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class Detail_jf extends AppCompatActivity {
    ImageView iv_back;
    public static TextView total_jf;
    // list
    List list = new ArrayList();
    List data_more = new ArrayList();
    MyAdapter_jf myAdapter_jf;
    SpringView springView;
    ListView mlist;
    int currentPage = 1;
    public static Double totalGoals;
    private CustomDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jf);
        common.changeTitle(this, "积分");
        BindView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.show();
        refresh();

    }

    private void BindView() {
        dialog  = new CustomDialog(this,R.style.CustomDialog,"加载中");
        total_jf = (TextView) findViewById(R.id.tv_total_jf);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common.goBack(Detail_jf.this);
            }
        });
        //list
        springView = (SpringView) findViewById(R.id.springView);
        springView = (SpringView) findViewById(R.id.springView);
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
                        loadMore();
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        //DefaultHeader/Footer是SpringView已经实现的默认头/尾之中的一个
        //很多其它还有MeituanHeader、AliHeader、RotationHeader等如上图7种
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
//        common.createData(list, 0);
        mlist = (ListView) findViewById(R.id.lv);
        myAdapter_jf = new MyAdapter_jf(this, list);

        mlist.setAdapter(myAdapter_jf);
        total_jf.setText(String.format("%.2f", MyFragment.totalGoals));
        common.setListViewHeightBasedOnChildren(mlist, myAdapter_jf);
//        handler.sendEmptyMessage(0);//设置积分数字
    }

    public void refresh() {
//        list.add(new Random().nextInt(200));
//        refreshList();
        currentPage = 1;
        HashMap map = new HashMap();
        map.put("limit", "10");
        map.put("count", String.valueOf((currentPage - 1) * 10));
        getJF(map, new GsonResHandler<Order_Jf>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, Order_Jf order_jf) {
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                if (order_jf.getStatus() == 1) {
//                    getTotal();//同时刷新总分；
                    total_jf.setText(String.format("%.2f", MyFragment.totalGoals));
                    Log.e("jf-size", order_jf.getData().size() + "");
                    list = order_jf.getData();
                    refreshListView(list);
                } else {
                    try {
                        common.logResult("status error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
//        handler.sendEmptyMessage(0);//

    }
    //加载更多
    public void loadMore() {
        //新增数据  loadmore
        currentPage++;
        data_more.clear();
        HashMap map = new HashMap();
        map.put("limit", "10");
        map.put("count", String.valueOf((currentPage - 1) * 10));
        getJF(map, new GsonResHandler<Order_Jf>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, Order_Jf order_jf) {
                if (order_jf.getStatus() == 1) {
//                    getTotal();//同时刷新总分；
                    total_jf.setText(String.format("%.2f", MyFragment.totalGoals));
                    Log.e("jf-size", order_jf.getData().size() + "");
                    data_more = order_jf.getData();
                    list.addAll(data_more);
                    refreshListView(list);
                } else {
                    try {
                        common.logResult("status error");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
    private void refreshList() {
        myAdapter_jf.notifyDataSetChanged();
        common.setListViewHeightBasedOnChildren(mlist, myAdapter_jf);
    }

//    static Handler handler = new Handler() {
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            switch (msg.what) {
//                case 0:
//                    Log.e("total_handler", (int) msg.obj + "");
//                    total_jf.setText(msg.obj + "");
//                    break;
//                case 1:
//                    break;
//            }
//        }
//    };

    //刷新列表辅助
    public void refreshListView(List list) {
        myAdapter_jf.setData(list);
        myAdapter_jf.notifyDataSetChanged();
        common.setListViewHeightBasedOnChildren(mlist, myAdapter_jf);
    }

//    //get total积分；
//    public void getTotal() {
//        getTotalGoals(new JsonResHandler() {
//            @Override
//            public void onFailed(int i, String s) {
//            }
//
//            @Override
//            public void onSuccess(int statusCode, JSONObject response) {
//                super.onSuccess(statusCode, response);
//                try {
//                    int status = response.getInt("status");
//                    if (status == 1) {
//                        //获取总分记录
//                        JSONObject data = (JSONObject) response.get("data");
//                        Log.e("total:", data.getDouble("credit") + "");
//                        totalGoals = data.getDouble("credit");
//                        total_jf.setText(String.format("%.2f", totalGoals));
//                    } else {
//                        common.logResult("status error");
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        });


//    }

    public void getJF(HashMap map, IResponseHandler handler) {
        HttpCore.get(URL.GET_JF, map, handler);
    }

//    public void getTotalGoals(IResponseHandler handler) {
//        HttpCore.get(URL.GET_TOTAL_JF, handler);
//    }
}
