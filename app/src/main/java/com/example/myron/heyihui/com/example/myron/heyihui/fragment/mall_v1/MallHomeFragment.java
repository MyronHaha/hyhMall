package com.example.myron.heyihui.com.example.myron.heyihui.fragment.mall_v1;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.GuideItem;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.HomeProduct;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.MallProduct;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.ProductDetail;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.ProductOrAd;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.MallListActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.MallProductDetail;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.SearchActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.SplashActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.circlePic.GlideCircleTransform;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.GlideImageLoader;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.TransformationUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CircleImageView;
import com.example.myron.heyihui.com.example.myron.heyihui.view.RecyclerViewItemDecoration;
import com.example.myron.heyihui.com.example.myron.heyihui.view.RoundCornerImageView;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jason on 2017/10/23.
 */

public class MallHomeFragment extends LazyLoadFragment {
    @BindView(R.id.banner)
    com.youth.banner.Banner banner;
    @BindView(R.id.guideView)
    RecyclerView guideView;
    //    @BindView(R.id.productView)
//    RecyclerView productView;
    @BindView(R.id.hotProduct)
    RecyclerView hotProduct;
    @BindView(R.id.productView2)
    RecyclerView productView2;

    List<GuideItem.Data> guideData = new ArrayList<>();
    BaseQuickAdapter<GuideItem.Data, BaseViewHolder> guideAdapter;
    //    List<ProductOrAd.Data> spData = new ArrayList<>();
//    BaseQuickAdapter<ProductOrAd.Data, BaseViewHolder> spAdapter;
    List<ProductOrAd.Data> bigData = new ArrayList<>();
    BaseQuickAdapter<ProductOrAd.Data, BaseViewHolder> bigAdapter;

    List<ProductOrAd.Data> hotDatas = new ArrayList();
    BaseQuickAdapter hotAdapter;
    private void initData() {
//        initHotDatas();
        HashMap mapSmpro = new HashMap();
        mapSmpro.put("type", "1");
        HashMap mapBigpro = new HashMap();
        mapBigpro.put("type", "2");
        HttpUtils.getCatogory(new GsonResHandler<GuideItem>() {
            @Override
            public void onSuccess(int i, GuideItem item) {
                if (i == 200) {
                    if (item.getData().size() > 0) {
                        guideData.clear();
                        guideData.addAll(item.getData());
                        guideAdapter.notifyDataSetChanged();
                    }
                } else {
                    MyToast.makeText(getActivity(), "请求失败" + i, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int i, String s) {

            }
        });  //标签
        HttpUtils.getHomeData(new GsonResHandler<ProductOrAd>() {
            @Override
            public void onSuccess(int i, ProductOrAd item) {
                if (i == 200) {
                    if (item.getData().size() > 0) {
//                        spData.clear();
//                        spData.addAll(item.getData());
//                        spAdapter.notifyDataSetChanged();
                        hotDatas.clear();
                        hotDatas.addAll(item.getData());
                        hotAdapter.notifyDataSetChanged();
                    }
                } else {
                    MyToast.makeText(getActivity(), "请求失败" + i, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int i, String s) {

            }
        }, mapSmpro); //小产品
        HttpUtils.getHomeData(new GsonResHandler<ProductOrAd>() {
            @Override
            public void onSuccess(int i, ProductOrAd item) {
                if (i == 200) {
                    if (item.getData().size() > 0) {
                        bigData.clear();
                        bigData.addAll(item.getData());
                        bigAdapter.notifyDataSetChanged();
                    }
                } else {
                    MyToast.makeText(getActivity(), "请求失败" + i, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int i, String s) {

            }
        }, mapBigpro);//大产品
    }
//    private void initHotDatas() {
//        HashMap map = new HashMap();
//        map.put("hot", "2");
//        map.put("count","0");
//        map.put("limit","6");
//        HttpUtils.getHomeData(new GsonResHandler<ProductOrAd>() {
//            @Override
//            public void onSuccess(int i, ProductOrAd productOrAd) {
//                if (i == 200) {
//                    if (productOrAd.getData().size() > 0) {
//                        hotDatas.clear();
//                        hotDatas.addAll(productOrAd.getData());
//                        hotAdapter.notifyDataSetChanged();
//                    }
//                } else {
//                    MyToast.makeText(getActivity(), "请求失败" + i, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailed(int i, String s) {
//
//            }
//        }, map);
//    }
    @Override
    protected void initView() {
        initBanner();
        initGuideView();
//        initProductView();
        initHotProduct();
        initProductView2();
    }

    @Override
    protected int setContentView() {
        return R.layout.layout_mallhome;
    }

    @Override
    protected void lazyLoad() {
        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        lazyLoad();
    }

    private void initHotProduct() {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1, GridLayoutManager.HORIZONTAL, false){
            @Override
            public boolean canScrollVertically() {
                return true;
            }
        };
        hotAdapter = new BaseQuickAdapter<ProductOrAd.Data, BaseViewHolder>(R.layout.item_hot_product, hotDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, ProductOrAd.Data s) {
                baseViewHolder.setText(R.id.name, s.getName());
                baseViewHolder.setText(R.id.price, HttpUtils.isIsLogin() ? "￥" + s.getPrice() : "价格登录可见");
                Glide.with(getActivity())
                        .load(URL.IMGPATH + s.getImg())
                        .placeholder(R.mipmap.nopictures_bg)
                        .error(R.mipmap.nopictures_bg)
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .dontAnimate()
                        .into((ImageView) baseViewHolder.getView(R.id.iv));
                //边距
                if (baseViewHolder.getOldPosition() == 0) {
                    GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) baseViewHolder.itemView.getLayoutParams();
                    params.setMargins(10, params.topMargin, params.rightMargin, params.bottomMargin);
                    baseViewHolder.itemView.setLayoutParams(params);
//                    baseViewHolder.itemView.setPadding(12,0,0,0);

                }

            }
        };
        hotAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getActivity(), MallProductDetail.class);
                intent.putExtra("id", Integer.parseInt(hotDatas.get(i).getSourceParam()));
                common.launchActivityWithIntent(getActivity(), intent);
            }
        });
        hotProduct.setLayoutManager(manager);
        hotProduct.setAdapter(hotAdapter);
        hotProduct.setHasFixedSize(true);
        hotProduct.setNestedScrollingEnabled(false);
        hotProduct.setOverScrollMode(View.OVER_SCROLL_NEVER);
        hotProduct.setFocusableInTouchMode(false);
        hotProduct.setFocusable(false);
        hotProduct.setItemViewCacheSize(20);
    }

    private void initProductView2() {
        bigAdapter = new BaseQuickAdapter<ProductOrAd.Data, BaseViewHolder>(R.layout.item_img1, bigData) {
            @Override
            protected void convert(final BaseViewHolder baseViewHolder, ProductOrAd.Data homeProduct) {
                Glide.with(mContext)
                        .load(URL.IMGPATH + homeProduct.getImg())
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .dontAnimate()
                        .into((RoundCornerImageView) baseViewHolder.getView(R.id.iv));
//                baseViewHolder.getView(R.id.b_devider).setVisibility(View.VISIBLE);
                baseViewHolder.setText(R.id.name, homeProduct.getName());
//                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) baseViewHolder.itemView.getLayoutParams();
//                params.setMargins(params.leftMargin, 10, params.rightMargin, params.bottomMargin);
//                baseViewHolder.itemView.setLayoutParams(params);
                List<String> list = new ArrayList<>();
                String[] labels = homeProduct.getLabels().split(",");
                for(String s:labels){
                    if(!TextUtils.isEmpty(s)){
                        list.add(s);
                    }
                }
                if(list.size()==0) {
                       list.add("暂无标签");
                }
                    RecyclerView lables = baseViewHolder.getView(R.id.lableView);
                    lables.setLayoutManager(new LinearLayoutManager(getActivity()));
                    lables.setAdapter(new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_label,list) {
                        @Override
                        protected void convert(BaseViewHolder holder, String o) {
                                ( (TextView)holder.getView(R.id.tv_tag)).setText(o);
                        }
                    });
//                }
            }
        };
        bigAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getActivity(), MallProductDetail.class);
                intent.putExtra("id", Integer.parseInt(bigData.get(i).getSourceParam()));
                common.launchActivityWithIntent(getActivity(), intent);
            }
        });
//        productView2.addItemDecoration(new RecyclerViewItemDecoration.Builder(getActivity())
//                .mode(RecyclerViewItemDecoration.MODE_VERTICAL)
//                .thickness(2)
//                .create());
        LinearLayoutManager manager = new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setAutoMeasureEnabled(true);
        productView2.setHasFixedSize(true);
//        productView2.setNestedScrollingEnabled(true);
        productView2.setLayoutManager(manager);
        productView2.setAdapter(bigAdapter);
        productView2.setFocusable(false);
        productView2.setFocusableInTouchMode(false);
    }

//    private void initProductView() {
//
//        spAdapter = new BaseQuickAdapter<ProductOrAd.Data, BaseViewHolder>(R.layout.item_img1, spData) {
//            @Override
//            protected void convert(BaseViewHolder baseViewHolder, ProductOrAd.Data homeProduct) {
//                Glide.with(mContext).load(URL.IMGPATH + homeProduct.getImg()).crossFade().into((ImageView) baseViewHolder.getView(R.id.iv));
//                baseViewHolder.setText(R.id.name, homeProduct.getName());
//            }
//        };
//        productView.addItemDecoration(new RecyclerViewItemDecoration.Builder(getActivity())
//                .mode(RecyclerViewItemDecoration.MODE_GRID)
//                .thickness(2)
//                .create());
//        GridLayoutManager manager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false){
//            @Override
//            public boolean canScrollVertically() {
//                return false;
//            }
//        };
////        manager.setAutoMeasureEnabled(true);
////        productView.setHasFixedSize(true);
//        productView.setNestedScrollingEnabled(true);
//        productView.setLayoutManager(manager);
//        productView.setAdapter(spAdapter);
//        spAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                Intent intent = new Intent(getActivity(), MallProductDetail.class);
//                intent.putExtra("id", Integer.parseInt(spData.get(i).getSourceParam()));
//                common.launchActivityWithIntent(getActivity(), intent);
//            }
//        });
//    }

    private void initGuideView() {
        guideAdapter = new BaseQuickAdapter<GuideItem.Data, BaseViewHolder>(R.layout.item_guide, guideData) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, GuideItem.Data guideItem) {
                baseViewHolder.setText(R.id.tv_title, guideItem.getName());
//                Glide.with(mContext)
//                        .load(URL.IMGPATH+guideItem.getImg())
//                        .crossFade()
//                        .fitCenter()
//                        .into((CircleImageView) baseViewHolder.getView(R.id.iv));
//                Glide.with(mContext)
//                        .load(URL.IMGPATH+guideItem.getImg())
//                        .asBitmap()
//                        .transform(new GlideCircleTransform(mContext))
//                        .into(new TransformationUtils((ImageView) baseViewHolder.getView(R.id.iv)));
                Glide.with(mContext)
                        .load(URL.IMGPATH + guideItem.getImg())
                        .transform(new GlideCircleTransform(mContext))
                        .fitCenter()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .dontAnimate()
                        .into((ImageView) baseViewHolder.getView(R.id.iv));
            }
        };
        guideAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getActivity(), MallListActivity.class);
                intent.putExtra("sid", guideData.get(i).getSourceId());
                common.launchActivityWithIntent(getActivity(), intent);
            }
        });
//        guideView. addItemDecoration(new RecyclerViewItemDecoration.Builder(getActivity())
//                .mode(RecyclerViewItemDecoration.MODE_GRID)
//                // .dashWidth(8)
//                //  .dashGap(5)
//                .thickness(2)
//                //.drawableID(R.color.line3)
//                .create());
        guideView.setLayoutManager(new GridLayoutManager(getActivity(), 3, GridLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        guideView.setAdapter(guideAdapter);
        guideView.setNestedScrollingEnabled(true);
        guideView.setFocusable(false);
        guideView.setFocusableInTouchMode(false);
    }

    private void initBanner() {
        banner.clearFocus();
//        ProductOrAd.initNetBanner(getActivity(), R.id.banner, banner, SplashActivity.imgArray);
        //设置banner样式
//        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(SplashActivity.imgArray);
        //设置banner动画效果
//        banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //设置标题集合（当banner样式有显示title时）
//        banner.setBannerTitles(null);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(5000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
//        banner.setImages(images).setImageLoader(new GlideImageLoader()).start();
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (SplashActivity.imgArray.size() > 0) {
                    ProductOrAd.Data ad = (ProductOrAd.Data) SplashActivity.imgArray.get(position);
                    if(ad.getSourceType() == 2){
                        Intent intent = new Intent(getActivity(),MallProductDetail.class);
                        intent.putExtra("id", Integer.parseInt(ad.getSourceParam()));
                        common.launchActivityWithIntent(getActivity(), intent);
                    }
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        //开始轮播
        banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        //结束轮播
        banner.stopAutoPlay();
    }

    @OnClick(R.id.ll_search)
    public void goSearch(){
        common.launchActivity(getActivity(), SearchActivity.class);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            lazyLoad();
        }
    }
}
