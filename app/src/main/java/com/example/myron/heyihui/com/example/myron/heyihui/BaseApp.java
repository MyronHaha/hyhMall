package com.example.myron.heyihui.com.example.myron.heyihui;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.Density;
import com.mph.okdroid.OkDroid;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Jason on 2017/11/2.
 */

public class BaseApp extends Application {
    private static OkHttpClient okHttpClient;
    private static OkDroid okDroid;
    private List<Activity> list = new ArrayList<Activity>();
    private static BaseApp ea;
    private static Context mcontext;
    public BaseApp() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mcontext = BaseApp.this;
        HttpCore.init();
        Density.setDensity(this, 375);
//        URL.initUrl(true);
    }

    public static BaseApp getInstance() {
        if (null == ea) {
            ea = new BaseApp();
        }
        return ea;
    }

    public void addActivity(Activity activity) {
        list.add(activity);
    }

    public void exit(Context context) {
        for (Activity activity : list) {
            activity.finish();
        }
        System.exit(0);
    }
    public static Context getMyApplicationContext() {
        return mcontext;
    }


}
