package com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.HomeProduct;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.ListProduct;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.BaseActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.fragment.mall_v1.FilterFragment;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.mph.okdroid.response.GsonResHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jason on 2018/10/23.
 */

public class MallListActivity extends BaseActivity {
    //筛选
    @BindView(R.id.drawer_layout)
    DrawerLayout mdrawerLayout;
    @BindView(R.id.fl_content)
    FrameLayout mDrawerContent;
    // tag textView
    @BindView(R.id.tv_filter)
    TextView textFilter;
    @BindView(R.id.tag1)
    TextView tv_zh;
    @BindView(R.id.tag2)
    TextView tv_tj;
    @BindView(R.id.tag3)
    TextView tv_xl;
    List<TextView> tvs = new ArrayList<>();
    //chanpin
    BaseQuickAdapter<ListProduct.Data, BaseViewHolder> adapter;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.rv)
    RecyclerView mallView;
    List<ListProduct.Data> datas = new ArrayList<>();
    public static FilterFragment fragment = null;
    public HashMap map = new HashMap();
    public int productCatogory = -1; // 记录产品的分类

    public MallListActivity() {
        super(R.layout.layout_activity_malllist);
    }

    @Override
    public void initData() {
        getListDatas(true);
    }

    public void getListDatas(final boolean isRefresh) {
        // 搜索页面传来
        if (getIntent().getStringExtra("key") != null) {
            String key = getIntent().getStringExtra("key");
            map.put("name", key);
            common.changeTitle(this, "产品\"" + key + "\"搜索结果");
        }
        //常规
        map.put("limit", "10");
        if (isRefresh) {
            map.put("count", "0");
        } else {
            map.put("count", adapter.getItemCount() + "");
        }
        HttpUtils.getGoods(new GsonResHandler<ListProduct>() {
            @Override
            public void onSuccess(int i, ListProduct listProduct) {
                if (i == 200) {
                    if (listProduct != null && !loginTimeOut(listProduct.getStatus())) {
                        if (isRefresh) {
                            datas.clear();
                            datas.addAll(listProduct.getData());
                            if (datas.size() == 0) {
                                View view = LayoutInflater.from(MallListActivity.this).inflate(R.layout.empty, null, false);
                                adapter.setEmptyView(view);
                            }
                        } else {
                            datas.addAll(listProduct.getData());
                        }
                        if (listProduct.getData().size() < 10) {
                            MyToast.makeText(MallListActivity.this, "已全部加载完毕！", Toast.LENGTH_SHORT).show();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(int i, String s) {

            }
        }, map);
    }

    @Override
    public void initView() {
        tvs.add(tv_zh);
        tvs.add(tv_tj);
        tvs.add(tv_xl);
        initToolbar();
        initDrawerLayout();
        initMallList();

    }

    private void initDrawerLayout() {
        mdrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        fragment = new FilterFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fl_content, fragment, "filter").commit();

        if (getIntent().getIntExtra("sid", 0) > 0) {
            productCatogory = getIntent().getIntExtra("sid", 0);
            changeFilterText(true);
            map.put("cs", productCatogory + "");
        }
    }

    private void initMallList() {
        initSpringView();
//        View footerView = LayoutInflater.from(this).inflate(R.layout.item_msg, null);
        mallView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<ListProduct.Data, BaseViewHolder>(R.layout.item_mall_product, datas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, ListProduct.Data data) {

                   Glide.with(MallListActivity.this)
                           .load(URL.IMGPATH + data.getImg())
                           .placeholder(R.mipmap.nopictures_bg)
                           .error(R.mipmap.nopictures_bg)
                           .centerCrop()
                           .into((ImageView) baseViewHolder.getView(R.id.iv));


                baseViewHolder.setText(R.id.tv_title, data.getName());
                baseViewHolder.setText(R.id.tv_company, data.getCompanyName());
                baseViewHolder.setText(R.id.tv_catagory, data.getLabels());
                baseViewHolder.setText(R.id.tv_price, HttpUtils.isIsLogin() ? "￥" + data.getPrice() : "价格登录可见");
            }
        };
        adapter.openLoadAnimation();
        adapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(MallListActivity.this, MallProductDetail.class);
                intent.putExtra("id", datas.get(i).getId());
                common.launchActivityWithIntent(MallListActivity.this, intent);
            }
        });
        mallView.setAdapter(adapter);
    }

    private void initSpringView() {
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);

            }

            @Override
            public void onLoadmore() {
                loadMore();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
    }

    private void loadMore() {
        getListDatas(false);

    }

    private void refresh() {
        getListDatas(true);
    }

    private void initToolbar() {
        common.changeTitle(this, "医疗设备");
        common.goBack(this);
    }


    @OnClick(R.id.tag4)
    public void showFilter() {
//        mdrawerLayout.openDrawer(mDrawerContent);
        if (mdrawerLayout.isDrawerOpen(Gravity.RIGHT)) {
            mdrawerLayout.closeDrawer(Gravity.RIGHT);
            changeFilterText(fragment.getHasSel());
        } else {
            mdrawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    @OnClick(R.id.iv_right)
    public void goSearch() {
        common.launchActivity(this, SearchActivity.class);
    }

    public void changeFilterText(boolean hasSel) {
        if (hasSel) {
            textFilter.setTextColor(getResources().getColor(R.color.colorPrimary));
        } else {
            textFilter.setTextColor(Color.parseColor("#010101"));
        }
    }

    public void changeTextState(int hasClick) {
        for (int i = 0; i < tvs.size(); i++) {
            final int temp = i;
            if (i == hasClick) {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        tvs.get(temp).setTextColor(getResources().getColor(R.color.colorPrimary));
                    }
                });
            } else {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        tvs.get(temp).setTextColor(Color.parseColor("#010101"));
                    }
                });
            }
        }
    }

    @OnClick(R.id.tag1)
    public void tag1() {
        changeTextState(0);
        map.put("s", "0");
        getListDatas(true);
    }

    @OnClick(R.id.tag2)
    public void tag2() {
        changeTextState(1);
        map.put("s", "1");
        getListDatas(true);
    }

    @OnClick(R.id.tag3)
    public void tag3() {
        changeTextState(2);
        map.put("s", "2");
        getListDatas(true);
    }

}
