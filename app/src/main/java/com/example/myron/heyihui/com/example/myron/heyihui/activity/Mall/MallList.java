package com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Filter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.HomeProduct;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.BaseActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.fragment.mall_v1.FilterFragment;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jason on 2018/10/23.
 */

public class MallList extends BaseActivity {
    //筛选
    @BindView(R.id.drawer_layout)
    DrawerLayout mdrawerLayout;
    @BindView(R.id.fl_content)
    FrameLayout mDrawerContent;
    //chanpin
    BaseQuickAdapter<HomeProduct.Data, BaseViewHolder> adapter;
    @BindView(R.id.springView)
    SpringView springView;
    @BindView(R.id.rv)
    RecyclerView mallView;
    List<HomeProduct.Data> datas = new ArrayList<>();

    public MallList() {
        super(R.layout.layout_activity_malllist);
    }

    @Override
    public void initData() {
        getListDatas();
    }

    private void getListDatas() {
        for (int i = 0; i < 10; i++) {
            HomeProduct.Data item = new HomeProduct.Data();
            datas.add(item);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void initView() {
        initToolbar();
        initDrawerLayout();
        initMallList();
    }

    private void initDrawerLayout() {
        FilterFragment fragment = new FilterFragment();
        android.support.v4.app.FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fl_content, fragment,"filter").commit();
    }

    private void initMallList() {
        initSpringView();
        View footerView = LayoutInflater.from(this).inflate(R.layout.item_msg, null);
        mallView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new BaseQuickAdapter<HomeProduct.Data, BaseViewHolder>(R.layout.item_mall_product, datas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, HomeProduct.Data data) {

            }
        };
        adapter.openLoadAnimation();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                   common.launchActivity(MallList.this,MallProductDetail.class);
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
        if (datas.size() < 50) {
            List tempdata = datas;
            datas.addAll(tempdata);
            adapter.notifyDataSetChanged();
        } else {
            Toast.makeText(this, "no more", 1000).show();
        }

    }

    private void refresh() {
        getListDatas();
    }

    private void initToolbar() {
        common.changeTitle(this, "医疗设备");
        common.goBack(this);
    }

    @OnClick(R.id.tag4)
    public void showFilter() {
//        mdrawerLayout.openDrawer(mDrawerContent);
        if(mdrawerLayout.isDrawerOpen(Gravity.RIGHT)){
            mdrawerLayout.closeDrawer(Gravity.RIGHT);
        }else{
            mdrawerLayout.openDrawer(Gravity.RIGHT);
        }
    }

    @OnClick(R.id.iv_right)
    public void goSearch() {
        common.launchActivity(this, SearchActivity.class);
    }
}
