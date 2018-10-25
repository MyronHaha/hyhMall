package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.BaseApp;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.AndroidWorkaround;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.NavigationBarUtil;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.StatusBarUtils;

import butterknife.ButterKnife;

/**
 * Created by Jason on 2018/10/22.
 */

public class BaseActivity extends AppCompatActivity {
    private int layout;
    public BaseActivity(int layoutId) {
        this.layout = layoutId;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(layout);
            ButterKnife.bind(this);

            if(!isXiaomi()){
//                setStatusColor();
            }
//        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
//            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
//        }
        if(NavigationBarUtil.hasNavigationBar(this)){
            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
        }

        BaseApp.getInstance().addActivity(this);
        initView();
        initData();
    }
    public  void initView(){}
    public void initData(){}
    /*---------------------------------状态栏------------------------------------------------*/
    public void setStatusBackground(int bgRes) {
        setStatusBg(this,bgRes);
    }
    public void setStatusColor(){
        setStatusColor(this,R.color.colorPrimary);
    }

    public static void setStatusBg(Activity activity,int bgRes) {
        StatusBarUtils.setTranslucentStatus(activity);
        // 生成一个状态栏大小的矩形
        View StatusView = createStatusView2(activity,bgRes);
        // 添加statusView到布局中
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(StatusView);
        // 设置根布局的参数
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content))                    .getChildAt(0);
        rootView.setFitsSystemWindows(true);
        rootView.setClipToPadding(true);
    }
    private static View createStatusView2(Activity activity, int bgRes) {
        // 获得状态栏的高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height","dimen","android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        // 绘制一个和状态栏一样高度的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundResource(bgRes);
        return statusView;
    }

    private static View createStatusView(Activity activity, int color) {
        // 获得状态栏的高度
        int resourceId = activity.getResources().getIdentifier("status_bar_height","dimen","android");
        int statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        // 绘制一个和状态栏一样高度的矩形
        View statusView = new View(activity);
        LinearLayout.LayoutParams params = new LinearLayout                .LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,statusBarHeight);
        statusView.setLayoutParams(params);
        statusView.setBackgroundResource(color);
        return statusView;
    }
    public static void setStatusColor(Activity activity, int color) {
        StatusBarUtils.setTranslucentStatus(activity);
        // 生成一个状态栏大小的矩形
        View StatusView = createStatusView(activity,color);
        // 添加statusView到布局中
        ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
        decorView.addView(StatusView);
        // 设置根布局的参数
        ViewGroup rootView = (ViewGroup) ((ViewGroup) activity.findViewById(android.R.id.content))                    .getChildAt(0);
        rootView.setFitsSystemWindows(true);
//        rootView.setClipToPadding(true);
    }
    /**     * 使状态栏透明  留出状态栏高度    * 适用于图片作为背景的界面，此时需要图片填充到状态栏     * @param activity 需要设置的activity     */
    public static void setTranslucent(Activity activity){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            // 设置状态栏透明
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // 设置根布局的参数
            ViewGroup rootView = (ViewGroup)((ViewGroup)activity                    .findViewById(android.R.id.content)).getChildAt(0);
//               rootView.setFitsSystemWindows(true);
//             rootView.setClipToPadding(true);
        }
    }
    // 是否是小米手机
    public static boolean isXiaomi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }
}
