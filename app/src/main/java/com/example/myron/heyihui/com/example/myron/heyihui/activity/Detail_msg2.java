package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Msg;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.SingleItemAdapter;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CustomDialog;
import com.jcodecraeer.xrecyclerview.ArrowRefreshHeader;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Detail_msg2 extends AppCompatActivity {
    ImageView iv_back;
    private SingleItemAdapter mAdapter;
    LinearLayoutManager linearLayoutManager;
    // list
    public static List list = new ArrayList();
    List data_more = new ArrayList();
    XRecyclerView rv;
    int currentPage = 1;
    private CustomDialog dialog;
    int lastShow = -1;
    SingleItemAdapter.MyViewHolder lasthold = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_msg);
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

        dialog = new CustomDialog(this, R.style.CustomDialog, "加载中...");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common.goBack(Detail_msg2.this);
            }
        });
        //list
        mAdapter = new SingleItemAdapter(Detail_msg2.this, list);
        mAdapter.setOnItemClickListener(new SingleItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                SingleItemAdapter.MyViewHolder holder = (SingleItemAdapter.MyViewHolder) rv.getChildViewHolder(view);

                Log.e("position",""+position);
                if (holder.msg_detail.getVisibility() == View.VISIBLE) {
                    holder.msg_detail.setVisibility(View.GONE);
                } else {
                    if (lasthold != null) {
//                        MyToast.makeText(Detail_msg2.this, "content" + lastShow, Toast.LENGTH_SHORT).show();
//                        View last = linearLayoutManager.getChildAt(lastShow);
//                        if(last != null){
//                            lasthold = (SingleItemAdapter.MyViewHolder) rv.getChildViewHolder(last);
                            lasthold.msg_detail.setVisibility(View.GONE);
//                        }

                    }
                    holder.msg_detail.setVisibility(View.VISIBLE);
                    lasthold = holder;
//                    lastShow = position;
                }


            }
        });
        rv = (XRecyclerView) findViewById(R.id.recyclerview);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(linearLayoutManager);
        //这句就是添加我们自定义的分隔线
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.devider2);
        rv.addItemDecoration(rv.new DividerItemDecoration(dividerDrawable));
        rv.setAdapter(mAdapter);
//        rv.setHasFixedSize(true);
        //加载样式
        rv.setRefreshProgressStyle(ProgressStyle.SysProgress);
        rv.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        rv.setArrowImageView(R.drawable.iconfont_downgrey);

        rv.setRefreshHeader(new ArrowRefreshHeader(this));
//        View header = LayoutInflater.from(this).inflate(R.layout.recyclerview_header, (ViewGroup)findViewById(android.R.id.content),false);
//        rv.addHeaderView(header);
        rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                //refresh data here
                refresh();
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLoadMore() {
                // load more data here
                loadMore();

            }
        });
    }

    public void refresh() {
//        list.add(new Random().nextInt(200));
//        refreshList();
        currentPage = 1;
        final HashMap map = new HashMap();
        map.put("limit", "10");
        map.put("count", String.valueOf((currentPage - 1) * 10));
        getMessage(map, new GsonResHandler<Msg>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, Msg msg) {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (msg.getStatus() == 1) {


                    Log.e("msg-size", msg.getData().size() + "");
                    list.clear();
                    list.addAll(msg.getData());
//                    list = msg.getData();
                    mAdapter.notifyDataSetChanged();
//                    refreshListView(list);
                    rv.refreshComplete();
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
                    if (data_more.size() == 0) {
//                        rv.setNoMore(true);
                    } else {
//                        rv.setNoMore(false);
                    }
                    list.addAll(data_more);
                    mAdapter.notifyDataSetChanged();
                    rv.loadMoreComplete();
//                    rv.refreshComplete();
//                    refreshListView(list);
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
}
