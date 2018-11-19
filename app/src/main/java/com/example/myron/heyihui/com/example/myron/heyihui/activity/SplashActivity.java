package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.BaseApp;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.BannerData;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.HomeData;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.ProductOrAd;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.User;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.guide.PageFrameLayout;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.Banner;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.StatusBarUtils;
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

public class SplashActivity extends BaseActivity {
    //    public static double totalSales = -100;  //总售额
//    public static double ingSales = -100; // 进行中售额
//    public static double edSales = -100; // 已完成的首恶
//    public static double lastSales = -100; //上月销售额
//    public static double theSalse = -100; //当月销售额
//    public static int orderNum; //订单数；
//    public static int orderingNum;//进行中订单数；
//    public static int orderedNum;//已完成订单数；
//    public static float data_A  = 0, data_cso = 0, data_cj = 0, data_ps = 0, data_dl = 0;
    boolean auto = false;
    public static Context context;
    public static List imgArray = new ArrayList();

    public SplashActivity() {
        super(0);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(common._readBoolean(this,"first")){
            common.launchActivity(this,GuideActivity.class);
        }else{
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    common.launchActivity(SplashActivity.this, Main2Activity.class);
                }
            }, 2000);        }
    }

    @Override
    public void initView() {
        super.initView();
        StatusBarUtils.setTranslucentStatus(this);
    }

    @Override
    public void initData() {
        super.initData();
        context = this;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                common.launchActivity(SplashActivity.this, Main2Activity.class);
//            }
//        }, 2000);
        if (!common.SP_Read(this.getApplicationContext(), "phone").equals("")) {
            AutoLogin();
        }
        getBanner();
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_splash);
//        BaseApp.getInstance().addActivity(this);
//        context = this;
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                common.launchActivity(SplashActivity.this, Main2Activity.class);
//            }
//        }, 1500);
//        getBanner();
//        if (!common.SP_Read(this.getApplicationContext(), "phone").equals("")) {
//            AutoLogin();
//        }
//
//    }

//    @Override
//    protected void onResume() {
//        Log.e("splashshow", "");
//        super.onResume();
//    }

    public void AutoLogin() {
        HashMap map2 = new HashMap();
        String phone = common.SP_Read(this.getApplicationContext(), "phone");
        String k = common.SP_Read(this.getApplicationContext(), "k");
        Log.e("phone" + phone + "," + "k" + k, "");
        map2.put("phone", phone);
        map2.put("p", k);
        try {
            loginRequest(map2, new JsonResHandler() {
                @Override
                public void onFailed(int i, String s) {
                }

                @Override
                public void onSuccess(int i, final JSONObject json) {
                    //                HttpCore.userId = user.getData().getId();
                    try {
                        Log.e("result", json.toString());
                        if (json != null && json.getInt("status") == 1) {
                            try {
                                JSONObject job = new JSONObject(json.getString("data").toString());
                                HttpCore.userId = job.getInt("id");
                                User.userName = job.getString("name");
                                common.logResult(HttpCore.userId + "" + User.userName);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            auto = true;
                            HttpUtils.setLogin(true); // 登录状态
                        } else {
                            HttpUtils.setLogin(false);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    common.launchActivity(SplashActivity.this, LoginActivity.class);
                                    try {
                                        MyToast.makeText(SplashActivity.this, json.getString("message") + ",请重新登录", 3000).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 1000);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    SplashActivity.this.finish();
                                }
                            }, 2000);
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

    //自动登录
    public static void loginRequest(HashMap map, final IResponseHandler handler) throws Exception {
        Log.e("login", URL.LOGIN + "--" + map.toString());
        HttpCore.post(URL.LOGIN, map, new IResponseHandler() {
            @Override
            public void onSuccess(Response response) {
                if (response.code() == 200) {
                    Log.e("code", response.code() + "");
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
        HashMap mapbannner = new HashMap();
        mapbannner.put("type", "3");
        HttpUtils.getHomeBanner(new GsonResHandler<ProductOrAd>() {
            @Override
            public void onSuccess(int i, ProductOrAd productOrAd) {
                if (i == 200) {
                    if (productOrAd.getData().size() > 0) {
                        imgArray.clear();
                        imgArray.addAll(productOrAd.getData());
                    }
                } else {
                    MyToast.makeText(SplashActivity.this, "请求失败" + i, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int i, String s) {

            }
        }, mapbannner);

    }
}
