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
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Msg;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_Jf;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.MyAdapter_jf;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.MyAdapter_msg;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CustomDialog;
import com.example.myron.heyihui.com.example.myron.heyihui.view.MyListView;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Detail_msg extends AppCompatActivity {
    ImageView iv_back;
    // list
    public static List list = new ArrayList();
    List data_more = new ArrayList();
    public static MyAdapter_msg myAdapter_msg;
    SpringView springView;
    public static ListView mlist;
    int currentPage = 1;
    private CustomDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_msg);
        common.changeTitle(this, "消息");
        BindView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        dialog.show();
        refresh();
    }

    private void BindView() {
        dialog = new CustomDialog(this,R.style.CustomDialog,"加载中...");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common.goBack(Detail_msg.this);
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
//        common.createData(list);
        mlist = (ListView) findViewById(R.id.lv);
        myAdapter_msg = new MyAdapter_msg(this, list);

        mlist.setAdapter(myAdapter_msg);
//        common.setListViewHeightBasedOnChildren(mlist, myAdapter_msg);
        common.setListViewHeight(mlist);
        myAdapter_msg.notifyDataSetChanged();
    }

    public void refresh() {
//        list.add(new Random().nextInt(200));
//        refreshList();
        MyAdapter_msg.lastShowid = -1;
        currentPage = 1;
        HashMap map = new HashMap();
        map.put("limit", "10");
        map.put("count", String.valueOf((currentPage - 1) * 10));
        getMessage(map, new GsonResHandler<Msg>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, Msg msg) {

                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                if (msg.getStatus() == 1) {

                    Log.e("msg-size", msg.getData().size() + "");
                    list = msg.getData();
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
        getMessage(map, new GsonResHandler<Msg>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, Msg msg) {
                if (msg.getStatus() == 1) {
                    Log.e("msg-size", msg.getData().size() + "");
                    data_more = msg.getData();
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

    public void getMessage(HashMap map, IResponseHandler handler) {
        HttpCore.get(URL.GETMESSAGES, map, handler);
    }

    //刷新列表辅助
    public static void refreshListView(List list) {
        myAdapter_msg.setData(list);
        myAdapter_msg.notifyDataSetChanged();
//        common.setListViewHeightBasedOnChildren(mlist,myAdapter_msg);
//        changeHeight();
        common.setListViewHeight(mlist);
    }

    public static void changeHeight() {
        common.setListViewHeight2(mlist);
    }
}
