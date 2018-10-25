package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.BaseApp;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.BannerData;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.HomeData;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.User;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
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

import okhttp3.Response;

public class FirstPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.null_content);
        BaseApp.getInstance().addActivity(this);
        Log.e("in","first"+common._readBoolean(this,"first"));
       if(common._readBoolean(this,"first")){
           common.launchActivity(this,GuideActivity.class);
       }else{
           common.launchActivity(this,SplashActivity.class);
       }

    }



}
