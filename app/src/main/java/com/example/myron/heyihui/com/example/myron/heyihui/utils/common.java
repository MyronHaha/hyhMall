package com.example.myron.heyihui.com.example.myron.heyihui.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.BaseApp;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Product;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.productSerilize;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.MainAdapter;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jason on 2017/10/19.
 */

public class common {
    static View view;
    static TextView tv;
    static Toolbar tootlbar;
    static ImageView iv;

    public static void goBack(final Context context) {
        iv = (ImageView) ((Activity) context).findViewById(R.id.iv_back);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Activity) context).finish();
                ((Activity) context).overridePendingTransition(R.anim.out, R.anim.in);
            }
        });

    }

    /*
    * 解决scrollView 嵌套listview 只显示一条；
    */
    public static void setListViewHeightBasedOnChildren(ListView mlistView, BaseAdapter mainAdapter) {
        int totalHeight = 0;                                    // 定义、初始化listview总高度值
        for (int i = 0; i < mainAdapter.getCount(); i++) {
            View listItem = mainAdapter.getView(i, null, mlistView);          // 获取单个item
            listItem.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));// 设置item高度为适应内容
            listItem.measure(0, 0);                                        // 测量现在item的高度
            totalHeight += listItem.getMeasuredHeight();                   // 总高度增加一个listitem的高度
        }
        ViewGroup.LayoutParams params = mlistView.getLayoutParams();
        params.height = totalHeight + ((mlistView.getDividerHeight() )* (mainAdapter.getCount() - 1)); // 将分割线高度加上总高度作为最后listview的高度
        mlistView.setLayoutParams(params);
        mlistView.invalidate();
    }
    public static void setListViewHeight(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;

        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {

            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i,null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        listView.postInvalidate();
    }
    public static void setListViewHeight2(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;

        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {

            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i,null, listView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();

        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() *
                (listAdapter.getCount() - 1))+80;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
        listView.postInvalidate();
    }
    public static void launchActivity(Context context, Class c) {
        Intent intent = new Intent(context, c);
        context.startActivity(intent);
    }

    public static void launchActivityWithData(Context context, Class c, String data) {
        Intent intent = new Intent(context, c);
        intent.putExtra("data", data);
        context.startActivity(intent);
    }
    public static void launchActivityWithIntent(Context context,Intent intent){
        context.startActivity(intent);
    }
    public static void launchActivityWithBean(Context context, Class c,Object data) {
        Intent intent = new Intent(context, c);
        intent.putExtra("data", (Serializable) data);
        context.startActivity(intent);
    }

    public static void changeTitle(Context context, String title) {
        tv = (TextView) ((Activity) context).findViewById(R.id.tv_title);
        tv.setText(title);
    }

    public static void SetToolbarVisibility(Boolean visible, Context context) {
        tootlbar = (Toolbar) ((Activity) context).findViewById(R.id.toolbar);
        if (visible) {
            tootlbar.setVisibility(View.VISIBLE);
        } else {
            tootlbar.setVisibility(View.GONE);
        }
    }

    public static void createData(List list) {
        if (list.size() == 0) {
            //没数据刷新；
            for (int i = 0; i < 10; i++) {
                list.add(i + "");
            }
        }
    }

    public static void createData(List list, int type) {

        Random rd = new Random();

        if (list.size() == 0) {
            //没数据刷新；
            for (int i = 0; i < 10; i++) {
                int r = rd.nextInt(300);
                list.add(i + r);
            }
        }
    }

    public static void createURlsData(List list, int type) {
        list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=243942221,3907738121&fm=27&gp=0.jpg");
        list.add("https://ss1.bdstatic.com/70cFuXSh_Q1YnxGkpoWK1HF6hhy/it/u=2476422624,920329811&fm=27&gp=0.jpg");
        list.add("https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1310959670,527650836&fm=27&gp=0.jpg");
        list.add("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=3627954361,2661374011&fm=27&gp=0.jpg");
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=29133.31296,1089761209&fm=27&gp=0.jpg");
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=454262349,220003234&fm=27&gp=0.jpg");
        list.add("https://ss1.bdstatic.com/70cFvXSh_Q1YnxGkpoWK1HF6hhy/it/u=1100621677,2276783790&fm=27&gp=0.jpg");
    }

    public static void hideBackIv(Context context) {
        iv = (ImageView) ((Activity) context).findViewById(R.id.iv_back);
        iv.setVisibility(View.GONE);
    }
    public static void hideRightIv(Context context) {
        FrameLayout right = (FrameLayout) ((Activity) context).findViewById(R.id.iv_right);
        right.setVisibility(View.GONE);
    }
    //url 拼凑；
    public static String getUrl(String url, HashMap<String, String> params) {
        // 添加url参数
        if (params.size()>0) {
            Iterator<String> it = params.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = params.get(key);
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            url += sb.toString();
        }
        return url;
    }

    //请求结果输出
    public static void logResult(String str)throws Exception {
        Log.e("result", str);
    }

    //
    public static void SP_Write(Context context, HashMap map) {
        //创建sharedPreference对象，UserInfo表示文件名，MODE_PRIVATE表示访问权限为私有的
        SharedPreferences sp = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        //获得sp的编辑器
        SharedPreferences.Editor ed = sp.edit();
        if (map != null) {
            Iterator<String> it = map.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = (String) map.get(key);
                //以键值对的显示将用户名和密码保存到sp中
                ed.putString(key, value);
            }
        }
        ed.commit();
    }


    public static void SP_Write_boolean(Context context, HashMap map) {
        //创建sharedPreference对象，UserInfo表示文件名，MODE_PRIVATE表示访问权限为私有的
        SharedPreferences sp = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        //获得sp的编辑器
        SharedPreferences.Editor ed = sp.edit();
        if (map != null) {
            Iterator<String> it = map.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                Boolean value = (Boolean) map.get(key);
                //以键值对的显示将用户名和密码保存到sp中
                ed.putBoolean(key, value);
            }
        }
        ed.commit();
    }

    //读取保存在本地数据
    public static String SP_Read(Context context, String key) {
        //创建SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        //获得保存在SharedPredPreferences中的用户名和密码
        String value = sp.getString(key, "");
        return value;
    }
    public static boolean _readBoolean(Context context, String key) {
        //创建SharedPreferences对象
        SharedPreferences sp = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        //获得保存在SharedPredPreferences中的用户名和密码
        boolean value = sp.getBoolean(key, true);
        return value;
    }
    public static boolean SP_Clear(Context context){
        SharedPreferences sp = context.getSharedPreferences("UserInfo", MODE_PRIVATE);
        if(sp!=null){
            sp.edit().clear().commit();
            return true;
        }
        return false;
    }




    /**
     * 获取应用当前版本代码
     *
     * @return
     */
    public static int getCurrentVerCode() {
        String packageName = BaseApp.getMyApplicationContext().getPackageName();
        int currentVer = -1;
        try {
            currentVer = BaseApp.getMyApplicationContext().getPackageManager().getPackageInfo(packageName, 0).versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVer;
    }

    /**
     * 获取应用当前版本名称
     *
     * @return
     */
    public static String getCurrentVerName() {
        String packageName = BaseApp.getMyApplicationContext().getPackageName();
        String currentVerName = "";
        try {
            currentVerName = BaseApp.getMyApplicationContext().getPackageManager().getPackageInfo(packageName, 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return currentVerName;
    }

    /**
     * 获取应用名称
     *
     * @return
     */
    public static String getAppName() {
        return BaseApp.getMyApplicationContext().getResources().getText(R.string.app_name).toString();
    }


}


