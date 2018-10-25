package com.example.myron.heyihui.com.example.myron.heyihui.fragment.mall_v1;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.GuideItem;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.HomeProduct;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.MallList;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.SplashActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.Banner;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CircleImageView;
import com.example.myron.heyihui.com.example.myron.heyihui.view.RecyclerViewItemDecoration;
import com.mph.okdroid.response.IResponseHandler;
import com.recker.flybanner.FlyBanner;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jason on 2017/10/23.
 */

public class MallHomeFragment extends Fragment {
    @BindView(R.id.banner)
    FlyBanner banner;
    @BindView(R.id.guideView)
    RecyclerView guideView;
    @BindView(R.id.productView)
    RecyclerView productView;
    @BindView(R.id.productView2)
    RecyclerView productView2;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_mallhome, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
//        getTotal();
//        getCurrentMonth(); // 本月收入支出
//        BindView();
    }

    private void initData() {

    }

    private void initView() {
        initBanner();
        initGuideView();
        initProductView();
        initProductView2();
    }

    private void initProductView2() {
        List<HomeProduct.Data> data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            HomeProduct.Data item = new HomeProduct.Data();
            item.setImg("http://www.pptbz.com/pptpic/UploadFiles_6909/201211/2012111719294197.jpg");
            data.add(item);
        }
        BaseQuickAdapter<HomeProduct.Data, BaseViewHolder> adapter = new BaseQuickAdapter<HomeProduct.Data, BaseViewHolder>(R.layout.item_img1, data) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, HomeProduct.Data homeProduct) {
                Glide.with(mContext).load(homeProduct.getImg()).crossFade().into((ImageView) baseViewHolder.getView(R.id.iv));
                baseViewHolder.getView(R.id.b_devider).setVisibility(View.VISIBLE);
            }
        };
        productView2.addItemDecoration(new RecyclerViewItemDecoration.Builder(getActivity())
                .mode(RecyclerViewItemDecoration.MODE_VERTICAL)
                .thickness(2)
                .create());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setAutoMeasureEnabled(true);
        productView2.setHasFixedSize(true);
        productView2.setNestedScrollingEnabled(false);
        productView2.setLayoutManager(manager);
        productView2.setAdapter(adapter);
    }

    private void initProductView() {
        List<HomeProduct.Data> data = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            HomeProduct.Data item = new HomeProduct.Data();
            item.setImg("http://www.pptbz.com/pptpic/UploadFiles_6909/201211/2012111719294197.jpg");
            data.add(item);
        }
        BaseQuickAdapter<HomeProduct.Data, BaseViewHolder> adapter = new BaseQuickAdapter<HomeProduct.Data, BaseViewHolder>(R.layout.item_img1, data) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, HomeProduct.Data homeProduct) {
                Glide.with(mContext).load(homeProduct.getImg()).crossFade().into((ImageView) baseViewHolder.getView(R.id.iv));
            }
        };
        productView.addItemDecoration(new RecyclerViewItemDecoration.Builder(getActivity())
                .mode(RecyclerViewItemDecoration.MODE_GRID)
                .thickness(2)
                .create());
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
        manager.setAutoMeasureEnabled(true);
        productView.setHasFixedSize(true);
        productView.setNestedScrollingEnabled(false);
        productView.setLayoutManager(manager);
        productView.setAdapter(adapter);

    }

    private void initGuideView() {
        List<GuideItem> data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            GuideItem item = new GuideItem();
            item.setImg("http://www.pptbz.com/pptpic/UploadFiles_6909/201211/2012111719294197.jpg");
            item.setTitle("title" + i);
            data.add(item);
        }
        BaseQuickAdapter<GuideItem, BaseViewHolder> adapter = new BaseQuickAdapter<GuideItem, BaseViewHolder>(R.layout.item_guide, data) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, GuideItem guideItem) {
                baseViewHolder.setText(R.id.tv_title, guideItem.getTitle());
                Glide.with(mContext).load(guideItem.getImg()).crossFade().into((CircleImageView) baseViewHolder.getView(R.id.iv));
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                common.launchActivity(getActivity(),MallList.class);
            }
        });
//        guideView. addItemDecoration(new RecyclerViewItemDecoration.Builder(getActivity())
//                .mode(RecyclerViewItemDecoration.MODE_GRID)
//                // .dashWidth(8)
//                //  .dashGap(5)
//                .thickness(2)
//                //.drawableID(R.color.line3)
//                .create());
        guideView.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false));
        guideView.setAdapter(adapter);
    }

    private void initBanner() {
        Banner.initNetBanner(getActivity(), R.id.banner, banner, SplashActivity.imgArray);
    }
    //获取全部标签
    public void getProductBig(IResponseHandler handler) {
        HttpCore.get(URL.GETTAG, handler);
    }


}
