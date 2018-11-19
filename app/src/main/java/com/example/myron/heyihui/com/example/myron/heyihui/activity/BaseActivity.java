package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.design.MaterialDialog;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.BaseApp;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.User;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.AndroidWorkaround;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.NavigationBarUtil;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.SoftKeyboardStateHelper;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.StatusBarUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.mph.okdroid.response.IResponseHandler;
import com.mph.okdroid.response.JsonResHandler;
import com.nineoldandroids.animation.ValueAnimator;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import butterknife.ButterKnife;
import okhttp3.Response;

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
        if(this.layout>0){
            setContentView(layout);
        }
            ButterKnife.bind(this);

//            if(!isXiaomi()){
//                setStatusColor();
//            }
//        if (AndroidWorkaround.checkDeviceHasNavigationBar(this)) {
//            AndroidWorkaround.assistActivity(findViewById(android.R.id.content));
//        }
        if(NavigationBarUtil.hasNavigationBar(this)){
            NavigationBarUtil.initActivity(findViewById(android.R.id.content));
        }
//
        // 系统 6.0 以上 状态栏白底黑字的实现方法
//        this.getWindow()
//                .getDecorView()
//                .setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//        initStateBarUi(this,getResources().getColor(R.color.colorPrimary));
        BaseApp.getInstance().addActivity(this);
        initView();
        initData();
    }
    public  void initView(){
    }
    public void initData(){}
    /*---------------------------------状态栏------------------------------------------------*/
    public void setStatusBackground(int bgRes) {
        setStatusBg(this,bgRes);
    }
//    public void setStatusColor(){
//        setStatusColor(this,android.R.color.white);
//    }

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
    //edittext
    public  void listenKeyboardLayout(final View root, final View scrollToView) {
        SoftKeyboardStateHelper keyboardStateHelper = new SoftKeyboardStateHelper(root);
        keyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {

            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                int[] location = new int[2];
                // 获取scrollToView在窗体的坐标
                scrollToView.getLocationInWindow(location);
                // 计算root滚动高度，使scrollToView在可见区域的底部
                int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                root.scrollTo(0, srollHeight+80);
            }

            @Override
            public void onSoftKeyboardClosed() {
                // 键盘隐藏
                root.scrollTo(0, 0);

            }
        });
    }

    public  void scrollToPos(int start, int end, final View view) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(250);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.scrollTo(0, (Integer) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

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
                            HttpUtils.setLogin(true); // 登录状态
                        } else {
                            HttpUtils.setLogin(false);
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                      common.launchActivity(BaseActivity.this, LoginActivity.class);
                                    try {
                                        MyToast.makeText(BaseActivity.this, json.getString("message") + "请重新登录", 3000).show();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }, 1000);
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

    public boolean  loginTimeOut(int status){
        if(status == 403 ){
            if (!common.SP_Read(this.getApplicationContext(), "phone").equals("")) {
                AutoLogin();
            }
            return true;
        }else{
            return false;
        }
    }
    public void dial(final String phone) {
        // 检查是否获得了权限（Android6.0运行时权限）
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // 没有获得授权，申请授权
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CALL_PHONE)) {
                // 返回值：
//                          如果app之前请求过该权限,被用户拒绝, 这个方法就会返回true.
//                          如果用户之前拒绝权限的时候勾选了对话框中”Don’t ask again”的选项,那么这个方法会返回false.
//                          如果设备策略禁止应用拥有这条权限, 这个方法也返回false.
                // 弹窗需要解释为何需要该权限，再次请求授权
                MyToast.makeText(this, "请先设置应用权限", Toast.LENGTH_LONG).show();

                // 帮跳转到该应用的设置界面，让用户手动授权
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", this.getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            } else {
                // 不需要解释为何需要该权限，直接请求授权
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        1);
            }
        } else {
            // 已经获得授权，可以打电话
//            PromptButton sure = new PromptButton("确定", new PromptButtonListener() {
//                @Override
//                public void onClick(PromptButton promptButton) {
//                    CallPhone(phone);
//                }
//            });
//            sure.setFocusBacColor(Color.parseColor("#3db0ff"));
//            sure.setTextColor(Color.parseColor("#ff5959"));
//            showUiDialog("确认拨号给" + "\n\r" + phone + "?",
//                    sure);
            showNormalDialog("确认拨号给 " + phone, new promptListener() {
                @Override
                public void cancel(MaterialDialog dialog) {
                    dialog.dismiss();
                }

                @Override
                public void prompt(MaterialDialog dialog) {
                    CallPhone(phone);
                    dialog.dismiss();
                }
            });
        }

    }

    // 是否...
    public void showNormalDialog(String alertMsg, final promptListener listener) {
        View view = getLayoutInflater().inflate(R.layout.dialog_normal, null);
        final MaterialDialog dialog = new MaterialDialog.Builder(this).create();
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
//        ((TextView)view.findViewById(R.id.tv_title)).setText(title);
        ((TextView) view.findViewById(R.id.tv_alert)).setText(alertMsg);
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.cancel(dialog);
            }
        });
        view.findViewById(R.id.prompt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.prompt(dialog);
            }
        });
        dialog.show();
    }

    private void CallPhone(String phone) {
        String number = phone;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + number);
        intent.setData(data);
        startActivity(intent);
    }
    public interface promptListener {
        public void cancel(MaterialDialog dialog);

        public void prompt(MaterialDialog dialog);
    }

}
