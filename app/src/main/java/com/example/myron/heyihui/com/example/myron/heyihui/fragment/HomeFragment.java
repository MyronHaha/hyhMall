package com.example.myron.heyihui.com.example.myron.heyihui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Account;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.BannerData;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.HomeData;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_xq;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.SplashActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.Banner;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CustomDialog;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CustomView;
import com.example.myron.heyihui.com.example.myron.heyihui.view.RadarView;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;
import com.recker.flybanner.FlyBanner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jason on 2017/10/23.
 */

public class HomeFragment extends Fragment implements View.OnClickListener {
    public Context context;
    //雷达图
    RadarView rv;
    public static FlyBanner banner;
    float max;
    float[] data;
    //            {323251,280023,12000,84000,230000};
    //
    ImageView iv_xy;
    //
    LinearLayout ll_done, ll_doing;
    // rect
    ImageView iv_rec, iv_rec2;
    public static Account account;
    int MaxHeight = 100;
    private TextView tv_totalSales, tv_theSales, tv_lastSales, tv_orderTotal, tv_edNum, tv_ingNum, tv_edSales, tv_ingSales, tv_edu;
    public static BannerData bannerData;
    public static List imgArray = new ArrayList();
    // 个人页面  资金 需求 余额;
    public static double zijin;
    public static double xuqiu;
    public static double balance;
    public static String name;
    private CustomDialog dialog;
    @BindView(R.id.progressView)
    CustomView progressView;

    public HomeFragment() {
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_homefragment, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        HomeData();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        BindView();
        dialog.show();
        getAccounts();
//        getBanner();
        Log.e("eee", "onActivityCreated");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.e("hidenchage", "onHiddenChanged");
        HomeData();
    }
//
//    private void getBanner() {
//        HttpCore.get(URL.GETBANNER, new GsonResHandler<BannerData>() {
//            @Override
//            public void onFailed(int i, String s) {
//
//            }
//
//            @Override
//            public void onSuccess(int i, BannerData bd) {
//
//                if (dialog.isShowing()) {
//                    dialog.dismiss();
//                }
//                if (bd.getSuccess()) {
//                    bannerData = bd;
//                    for (BannerData.Biz biz : bd.getBiz()) {
//                        String imgUrl = biz.getImg();
//                        if (!imgUrl.equals("")) {
//                            imgArray.add(URL.IMGPATH + imgUrl);
//                        }
//                    }
//                    ProductOrAd.initNetBanner(getActivity(), R.id.banner_1, banner, imgArray);
//                }
//            }
//        });
//    }

    private void BindView() {

        dialog = new CustomDialog(getActivity(), R.style.CustomDialog, "加载中...");

        //雷达图初始化;
        rv = (RadarView) getActivity().findViewById(R.id.rv);
//
        banner = (FlyBanner) getActivity().findViewById(R.id.banner_1);
        Banner.initNetBanner(getActivity(), R.id.banner_1, banner, SplashActivity.imgArray);
        //信用动画；
        iv_xy = (ImageView) getActivity().findViewById(R.id.iv_xy);
        iv_xy.setBackgroundResource(R.drawable.annimation_list);
        AnimationDrawable animationDrawable = (AnimationDrawable) iv_xy.getBackground();
        animationDrawable.start();
        //完成，进行中
        ll_done = (LinearLayout) getActivity().findViewById(R.id.ll_orderdone);
        ll_doing = (LinearLayout) getActivity().findViewById(R.id.ll_ordering);
        ll_done.setOnClickListener(this);
        ll_doing.setOnClickListener(this);

        iv_rec = (ImageView) getActivity().findViewById(R.id.rect);
        iv_rec2 = (ImageView) getActivity().findViewById(R.id.rect2);
        changeRectangleHeight(0);
        changeRectangleHeight(1);
        tv_totalSales = (TextView) getActivity().findViewById(R.id.tv_salesTotal);
        tv_theSales = (TextView) getActivity().findViewById(R.id.tv_thesales);
        tv_lastSales = (TextView) getActivity().findViewById(R.id.tv_lastsales);
        tv_orderTotal = (TextView) getActivity().findViewById(R.id.tv_order_total);
        tv_edNum = (TextView) getActivity().findViewById(R.id.ed_num);
        tv_ingNum = (TextView) getActivity().findViewById(R.id.ing_num);
        tv_edSales = (TextView) getActivity().findViewById(R.id.tv_endsales);
        tv_ingSales = (TextView) getActivity().findViewById(R.id.tv_ingsales);
        tv_edu = (TextView) getActivity().findViewById(R.id.tv_edu);

//        tv_totalSales.setText(String.format("%,.2f", SplashActivity.totalSales));
//        tv_lastSales.setText(String.format("%,.2f", SplashActivity.lastSales));
//        tv_theSales.setText(String.format("%,.2f", SplashActivity.theSalse));
        //change progress view data

        Log.e("max",""+progressView.getMax());
        Log.e("progress",""+progressView.getProgress());
        progressView.setMax(5000);
        progressView.setProgress(1000);

        //change height

//        tv_orderTotal.setText(SplashActivity.orderNum + "");
//        tv_edNum.setText(SplashActivity.orderedNum + "");
//        tv_ingNum.setText(SplashActivity.orderingNum + "");
//        tv_edSales.setText(String.format("%,.2f", SplashActivity.edSales));
//        tv_ingSales.setText(String.format("%,.2f", SplashActivity.ingSales));
//        tv_edu.setText(Main2Activity.account.getData().getLimit()+"");

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_orderdone:
                common.launchActivityWithData(getActivity(), Detail_xq.class, "已完成");
                break;
            case R.id.ll_ordering:
                common.launchActivityWithData(getActivity(), Detail_xq.class, "进行中");
                break;
            default:
                break;
        }
    }

    public void changeRectangleHeight(final int i) {
////
////        final double d1 = SplashActivity.theSalse;
////        final double d2 = SplashActivity.lastSales;
//        final double ratio1 = d1 / (d1 + d2);
//        final double ratio2 = d2 / (d1 + d2);
//
//        if (i == 1) {
//            if (d2 == 0) {
//
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(76, 2);
////                        params.width = 76;
////                        params.height = 2;
//                iv_rec2.setLayoutParams(params);
//                iv_rec2.setBackgroundResource(R.drawable.rect2);
//
//            } else {
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(76, (int) (100 * ratio2));
////                        params.width = 76;
////                        params.height = (int) (100 * ratio2);
//                iv_rec2.setLayoutParams(params);
//                iv_rec2.setBackgroundResource(R.drawable.rect2);
//            }
//            iv_rec2.postInvalidate();
//        } else {
//            if (d1 == 0) {
//
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(76, 2);
////                        params.width = 76;
////                        params.height = 2;
//                iv_rec.setLayoutParams(params);
//                iv_rec.setBackgroundResource(R.drawable.rect);
//
//            } else {
//                Log.e("ratio", ratio1 + "");
//                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(76, (int) (100 * ratio1));
////                        params.width = 76;
////                        params.height = (int) (100 * ratio1);
//                iv_rec.setLayoutParams(params);
//                iv_rec.setBackgroundResource(R.drawable.rect);
//                iv_rec.postInvalidate();
//            }
//        }
    }

    public void getAccounts() {
        HttpCore.get(URL.GETACCOUNT, new GsonResHandler<Account>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, Account acc) {

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (acc.getStatus() == 1) {
                    account = acc;
                    tv_edu.setText(account.getData().getLimit() + "");
                    zijin = acc.getData().getAmount();
                    xuqiu = acc.getData().getAmount1();
                    balance = acc.getData().getBalance();
                    name = acc.getData().getName();
                }
            }
        });
    }


    public void HomeData() {
        HashMap map = new HashMap();
        getHomeData(map, new GsonResHandler<HomeData>() {
            @Override
            public void onFailed(int i, String s) {
            }

            @Override
            public void onSuccess(int i, HomeData homeData) {
                if (homeData.getStatus() == 1) {
                    try {
                        common.logResult(homeData.getStatus() + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    double totalSales = homeData.getData().getAmount();
                    tv_totalSales.setText(String.format("%,.2f", totalSales));
                    double ingSales = homeData.getData().getTotal(); // 进行中销售额
                    tv_ingSales.setText(String.format("%,.2f", ingSales));
                    if (totalSales != -100 && ingSales != -100) {
                        double edSales = totalSales - ingSales;
                        tv_edSales.setText(String.format("%,.2f", edSales));
                    }
                    int orderNum = homeData.getData().getHasin();
                    tv_orderTotal.setText(orderNum + "");
                    int orderingNum = homeData.getData().getHasfin();
                    tv_ingNum.setText(orderingNum + "");
                    float data_cso = 0, data_cj = 0, data_dl = 0, data_ps = 0;
                    //
                    if (homeData.getData().getFeecso() != "") {
                        data_cso = Float.parseFloat(homeData.getData().getFeecso());
                    }
                    if (homeData.getData().getFeein() != "") {
                        data_cj = Float.parseFloat(homeData.getData().getFeein());
                    }
                    if (homeData.getData().getFeefin() != "") {
                        data_dl = Float.parseFloat(homeData.getData().getFeefin());
                    }
                    if (homeData.getData().getFeedist() != "") {
                        data_ps = Float.parseFloat(homeData.getData().getFeedist());
                    }
                    float data_A = homeData.getData().getCredit();
                    data = new float[]{data_A, data_dl, data_ps, data_cso, data_cj};
                    max = 0;
                    for (float j : data) {
                        max += j;
                    }
                    rv.postChangeData(new String[]{"A类产品", "代理投标", "配送", "CSO服务", "采集"}, data, max);
//                    SplashActivity.data_cso = data_cso;
//                    SplashActivity.data_A = data_A;
//                    SplashActivity.data_cj = data_cj;
//                    SplashActivity.data_dl = data_dl;
//                    SplashActivity.data_ps = data_ps;
                    if (orderNum > 0) {
                        int orderedNum = orderNum - orderingNum;
                        tv_edNum.setText(orderedNum + "");
                    }
                    //获取本月售额
                    getTheMonthSales();

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

    public void getHomeData(HashMap map, IResponseHandler handler) {
        HttpCore.get(URL.GETHOMEDATA, map, handler);
    }

    public void getTheMonthSales() {
        HashMap map = new HashMap();
        map.put("t", "0");
        getHomeData(map, new GsonResHandler<HomeData>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, HomeData homeData) {
                if (homeData.getStatus() == 1) {
                    double theSalse = homeData.getData().getAmount();
                    tv_theSales.setText(String.format("%,.2f", theSalse));
                    try {
                        common.logResult("getthemonth：" + theSalse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getLastMonthSales();
                }
            }
        });
    }

    public void getLastMonthSales() {
        HashMap map = new HashMap();
        map.put("t", "1");
        getHomeData(map, new GsonResHandler<HomeData>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, HomeData homeData) {
                if (homeData.getStatus() == 1) {
                    double lastSales = homeData.getData().getAmount();
                    try {
                        common.logResult("getlastMonth：" + lastSales);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    tv_lastSales.setText(String.format("%,.2f", lastSales));
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        new Handler().post(new Runnable() {
                               @Override
                               public void run() {
                                   banner.stopAutoPlay();

                               }
                           }
        );
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        new Handler().post(new Runnable() {
                               @Override
                               public void run() {
                                   banner.stopAutoPlay();

                               }
                           }
        );
    }
}
