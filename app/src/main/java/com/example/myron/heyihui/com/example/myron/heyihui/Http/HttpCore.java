package com.example.myron.heyihui.com.example.myron.heyihui.Http;

import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.example.myron.heyihui.com.example.myron.heyihui.Data.AppInfo;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.mph.okdroid.OkDroid;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseDownloadHandler;
import com.mph.okdroid.response.IResponseHandler;
import com.mph.okdroid.response.JsonResHandler;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Jason on 2017/11/3.
 */

public class HttpCore {
    private static OkHttpClient okHttpClient;
    public static boolean ISNOVCODE = false;//测试没有验证码；
    private static OkDroid okDroid;
    private static String token="";
    public static int userId = -100;
    //fir update info
    public static String APPID = "5a0aeba4959d69123b000090";
    public static String API_TOKEN = "4f736d2bd6256a1bd5dd2ffde6782f20";

    public  static void  setToken(String t){
        token=t;
    }
    public static void init() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000, TimeUnit.MILLISECONDS)
                .readTimeout(10000, TimeUnit.MILLISECONDS)
                .build();
        okDroid = new OkDroid(okHttpClient);
        okDroid.setDebug(false);

    }

    public static void post(String url, HashMap map, IResponseHandler handler) {
        Log.e("token",token);
        okDroid.post().url(url)
                .tag(url)
                .addHeader("token",token)
                .params(map)
                .enqueue(handler);
    }
    //with params
    public static void get(String url, HashMap map, IResponseHandler handler) {
        String  newUrl;
        if(map!=null){
              newUrl = common.getUrl(url,map);
        }else{
            newUrl = url;
        }
        Log.e("newURl",newUrl);
        okDroid.get().url(newUrl)
                .tag(newUrl)
                .addHeader("token",token)
                .enqueue(handler);
    }
// no params
    public static void get(String url,IResponseHandler handler) {
        Log.e("newURl",url+"userID:"+HttpCore.userId);
        okDroid.get().url(url)
                .addHeader("token",token)
                .tag(url)
                .enqueue(handler);
    }

    /*
* path foldername +/ apkname.apk*/
    public static void downLoadFile(Context context, String url, String path, IResponseDownloadHandler handler){
        okDroid .download().url(url)
                .tag(context)
                .filePath(Environment.getExternalStorageDirectory()+"/"+path)
                .enqueue(handler);
    }

}
