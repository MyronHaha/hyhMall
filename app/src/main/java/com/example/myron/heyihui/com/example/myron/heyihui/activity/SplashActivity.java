package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.BaseApp;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.BannerData;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.HomeData;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.User;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.guide.PageFrameLayout;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.Banner;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;
import com.mph.okdroid.response.JsonResHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import okhttp3.Response;

public class SplashActivity extends AppCompatActivity {
    public static double totalSales = -100;  //总售额
    public static double ingSales = -100; // 进行中售额
    public static double edSales = -100; // 已完成的首恶
    public static double lastSales = -100; //上月销售额
    public static double theSalse = -100; //当月销售额
    public static int orderNum; //订单数；
    public static int orderingNum;//进行中订单数；
    public static int orderedNum;//已完成订单数；
    public static float data_A  = 0, data_cso = 0, data_cj = 0, data_ps = 0, data_dl = 0;
    boolean auto = false;
    public static Context context;
    public static List imgArray = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        BaseApp.getInstance().addActivity(this);
        context  = this;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                common.launchActivity(SplashActivity.this, Main2Activity.class);            }
        },1500);
        getBanner();
        if(!common.SP_Read(this.getApplicationContext(), "phone").equals("")){
            AutoLogin();
        }

    }

    @Override
    protected void onResume() {
        Log.e("splashshow","");
        super.onResume();
    }

    public void AutoLogin() {
        HashMap map2 = new HashMap();
        String phone = common.SP_Read(this.getApplicationContext(), "phone");
        String k = common.SP_Read(this.getApplicationContext(), "k");
        Log.e("phone" + phone + "," + "k" + k, "");
        map2.put("phone", phone);
        map2.put("p", k);
        try {
            loginRequest(map2, new JsonResHandler(){
                @Override
                public void onFailed(int i, String s) {
                }

                @Override
                public void onSuccess(int i, JSONObject json) {
    //                HttpCore.userId = user.getData().getId();
                    try {
                        Log.e("result",json.toString());
                        if (json!=null&&json.getInt("status")== 1) {
                            try {
                                JSONObject job = new JSONObject(json.getString("data").toString());
                                HttpCore.userId = job.getInt("id");
                                User.userName = job.getString("name");
                                common.logResult(HttpCore.userId+""+ User.userName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            auto = true;
                            HomeData();
                        } else {
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    common.launchActivity(SplashActivity.this, LoginActivity.class);
                                    MyToast.makeText(SplashActivity.this, "账号错误，请重新登录", 3000).show();
                                }
                            }, 3000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    SplashActivity.this.finish();
                                }
                            },2000);


                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void HomeData() {
        HashMap map = new HashMap();
        getHomeData(map, new GsonResHandler<HomeData>() {
            @Override
            public void onFailed(int i, String s) {
            }

            @Override
            public void onSuccess(int i, HomeData homeData) {
                if (homeData!=null&&homeData.getStatus() == 1) {
                    try {
                        common.logResult(homeData.getStatus() + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    totalSales = homeData.getData().getAmount();
                    ingSales = homeData.getData().getTotal(); // 进行中销售额
                    if (totalSales != -100 && ingSales != -100) {
                        edSales = totalSales - ingSales;
                    }
                    orderNum = homeData.getData().getHasin();
                    orderingNum = homeData.getData().getHasfin();
                    //
                    if (homeData.getData().getFeecso()!="") {
                        data_cso = Float.parseFloat(homeData.getData().getFeecso());
                    }
                    if (homeData.getData().getFeein()!="") {
                        data_cj = Float.parseFloat(homeData.getData().getFeein());
                    }
                    if (homeData.getData().getFeefin()!="") {
                        data_dl = Float.parseFloat(homeData.getData().getFeefin());
                    }
                    if (homeData.getData().getFeedist()!="") {
                        data_ps = Float.parseFloat(homeData.getData().getFeedist());
                    }
                    data_A = homeData.getData().getCredit();
                    Log.e("data-data", String.valueOf(data_A));
                    if (orderNum > 0) {
                        orderedNum = orderNum - orderingNum;
                    }
                    if (theSalse == -100) {
                        getTheMonthSales();
                    }

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

    public static void  getHomeData(HashMap map, IResponseHandler handler) {
        HttpCore.get(URL.GETHOMEDATA, map, handler);
    }

    public static void getTheMonthSales() {
        HashMap map = new HashMap();
        map.put("t", "0");
        getHomeData(map, new GsonResHandler<HomeData>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, HomeData homeData) {
                if (homeData.getStatus() == 1) {

                    theSalse = homeData.getData().getAmount();
                    try {
                        common.logResult("getthemonth：" + theSalse);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    getLastMonthSales();
//
//                    lastSales = homeData.getData().getAmount();
//                    common.logResult("lastMonth：" + lastSales);
                }

                if (theSalse != -100 && lastSales != -100&&imgArray.size()>0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((Activity)context).finish();
                            common.launchActivity(context, Main2Activity.class);

                        }
                    },2000);

//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//
//                        }
//                    },2000);
                }
            }
        });
    }

    public static void getLastMonthSales() {
        HashMap map = new HashMap();
        map.put("t", "1");
        getHomeData(map, new GsonResHandler<HomeData>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, HomeData homeData) {
                if (homeData.getStatus() == 1) {
//                    theSalse = homeData.getData().getAmount();
//                    common.logResult("themonth：" + theSalse);
                    lastSales = homeData.getData().getAmount();
                    try {
                        common.logResult("getlastMonth：" + lastSales);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (theSalse != -100 && lastSales != -100&&imgArray.size()>0) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((Activity)context).finish();
                            common.launchActivity(context, Main2Activity.class);

                        }
                    },2000);
                }
            }
        });
    }
    //自动登录
    public static void loginRequest(HashMap map, final IResponseHandler handler) throws Exception {
        Log.e("login", URL.LOGIN + "--" + map.toString());
        HttpCore.post(URL.LOGIN, map, new IResponseHandler() {
            @Override
            public void onSuccess(Response response) {

                if (response.code() == 200) {
                    Log.e("code",response.code()+"");
                    String token = response.header("token");
                    HttpCore.setToken(token);
                    handler.onSuccess(response);
                }

            }

            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
    }


    private void getBanner() {
        HttpCore.get(URL.GETBANNER, new GsonResHandler<BannerData>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, BannerData bd) {
                if (bd.getSuccess()) {

                    for (BannerData.Biz biz : bd.getBiz()) {
                        String imgUrl = biz.getImg();
                        if (!imgUrl.equals("")) {
                            imgArray.add(URL.IMGPATH + imgUrl);
                        }
                    }

//                    if (theSalse != -100 && lastSales != -100&&imgArray.size()>0) {
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                common.launchActivity(context, Main2Activity.class);
//                                ((Activity)context).finish();
//                            }
//                        },2000);
//                    }
//
//                    if(common.SP_Read(SplashActivity.this.getApplicationContext(), "phone").equals("")){
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                common.launchActivity(SplashActivity.this, Main2Activity.class);
//                            }
//                        },2000);
//                    }
                }
            }
        });
    }
}
