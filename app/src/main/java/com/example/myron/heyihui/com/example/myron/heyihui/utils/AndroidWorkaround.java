package com.example.myron.heyihui.com.example.myron.heyihui.utils;

/**
 * Created by Jason on 2018/1/11.
 */

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.example.myron.heyihui.com.example.myron.heyihui.activity.BaseActivity;

import java.lang.reflect.Method;

/**
 * 解决底部屏幕按键适配
 * Created by Mercury on 2016/10/25.
 */
public class AndroidWorkaround {

    // For more information, see https://code.google.com/p/android/issues/detail?id=5497
    // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

    /**
     * 关联要监听的视图
     *
     * @param viewObserving
     */
    public static void assistActivity(View viewObserving) {
        new AndroidWorkaround(viewObserving);
    }

    private View mViewObserved;//被监听的视图
    private int usableHeightPrevious;//视图变化前的可用高度
    private ViewGroup.LayoutParams frameLayoutParams;

    private AndroidWorkaround(View viewObserving) {
        mViewObserved = viewObserving;
        //给View添加全局的布局监听器
        mViewObserved.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                resetLayoutByUsableHeight(computeUsableHeight());
            }
        });
        frameLayoutParams = mViewObserved.getLayoutParams();
    }

    private void resetLayoutByUsableHeight(int usableHeightNow) {
        //比较布局变化前后的View的可用高度
        if (usableHeightNow != usableHeightPrevious) {
            //如果两次高度不一致
            //将当前的View的可用高度设置成View的实际高度
            frameLayoutParams.height = usableHeightNow;
            mViewObserved.requestLayout();//请求重新布局
            usableHeightPrevious = usableHeightNow;
        }
    }

    /**
     * 计算视图可视高度
     *
     * @return
     */
    private int computeUsableHeight() {
        Rect r = new Rect();
        mViewObserved.getWindowVisibleDisplayFrame(r);
        return (r.bottom - r.top );
    }

    /**
     * <P>shang</P>     * <P>判断是否有虚拟按键</P>     * @param context     * @return
     */
    public static boolean checkDeviceHasNavigationBar(Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
            Method m = systemPropertiesClass.getMethod("get", String.class);
            String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
            if ("1".equals(navBarOverride)) {
                hasNavigationBar = false;
            } else if ("0".equals(navBarOverride)) {
                hasNavigationBar = true;
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

}