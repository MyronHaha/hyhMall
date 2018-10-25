package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.BaseApp;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.User;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CustomDialog;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;
import com.mph.okdroid.response.JsonResHandler;


import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import okhttp3.Response;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    static Context scontext;
    static Button bt_send;//sendcode;
    Button bt_login;//login;
    private EditText et_phoneNum;
    private EditText et_code;//手机号，验证码;
    //    String code = null;//后台短信验证码；
    ImageView iv_back;
    int recLen = 60;
    //handler tag
    static final int SEND_CODE_ONCLICK = 0;
    static final int SEND_CODE_TIME_OUT = 1;
    CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE); //设置无标题
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        scontext = this.getApplication();
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(this);

        et_phoneNum = (EditText) findViewById(R.id.et_phoneNum);
        et_code = (EditText) findViewById(R.id.et_code);

        bt_send = (Button) findViewById(R.id.bt_sendCode);
        bt_login = (Button) findViewById(R.id.bt_login);

        bt_login.setOnClickListener(this);
        bt_send.setOnClickListener(this);

        initBt();
        et_code.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    bt_login.setEnabled(true);
                    bt_login.setBackgroundResource(R.drawable.round_shape);
                } else {
                    bt_login.setEnabled(false);
                    bt_login.setBackgroundResource(R.drawable.round_shape2);
                }
            }
        });

        et_phoneNum.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    bt_send.setEnabled(true);
                    bt_send.setBackgroundResource(R.drawable.round_shape);
                } else {
                    bt_send.setEnabled(false);
                    bt_send.setBackgroundResource(R.drawable.round_shape2);
                }
            }
        });
        dialog = new CustomDialog(this, R.style.CustomDialog, "登录中...");
        dialog.setText("dfsfd");
    }

    public void initBt() {
        bt_send.setEnabled(false);
        bt_send.setBackgroundResource(R.drawable.round_shape2);

        bt_login.setEnabled(false);
        bt_login.setBackgroundResource(R.drawable.round_shape2);
    }

    /*

    login   setValue:@"1qazxsw2!@" forKey:@"p"];
            setValue:@"1qazxsw2!@QW" forKey:@"code"];
     */
    public void login() {
        if (TextUtils.isEmpty(et_phoneNum.getText()) || TextUtils.isEmpty(et_code.getText())) {
            MyToast.makeText(this, "手机号码或验证码不能为空", Toast.LENGTH_SHORT).show();
        } else {
            dialog.show();
            HashMap map = new HashMap();
            map.put("phone", et_phoneNum.getText().toString());
            if (HttpCore.ISNOVCODE) {
                map.put("code", "1qazxsw2!@QW");
                map.put("p", "1qazxsw2!@");
            } else {
                map.put("code", et_code.getText().toString());
            }
            loginRequest(map, new GsonResHandler<User>() {
                @Override
                public void onFailed(int i, String s) {

                }

                @Override
                public void onSuccess(int i, User user) {
                    dialog.dismiss();
                    String message="",name = "",k = "",phone="";
                    User.UserData data = null;
                    int status = -100;

                    message = user.getMessage();
                    status = user.getStatus();
//                    {
//                        "data": null,
//                            "message": "用户名或密码错误！",
//                            "status": 0,
//                            "total": 0
//                    }
                    Log.e("status",status+"");
//                    HttpCore.userId = user.getInt("id");
//                    common.logResult(user.toString());
//                    int status = user.getInt("status");
//                    String name = user.getString("name");
//                    String k = user.getString("k");
//                    message = user.getString("message");
//                    common.logResult(status + "");
                    if (status == 1) {
                        if(user!=null){

                            HttpCore.userId = user.getData().getId();
                            data = user.getData();
                            name = data.getName();
                            k = data.getK();
                            User.userName = user.getData().getName();
                        }
                        MyToast.makeText(scontext, "登录成功", Toast.LENGTH_SHORT).show();

                        //保存用户数据；
                        HashMap userMap = new HashMap();
                        userMap.put("name", name);
                        userMap.put("k", k);
                        userMap.put("phone", et_phoneNum.getText().toString());
                        common.SP_Write(scontext, userMap);
                        Log.e("saveUser", "true");
                        //跳转主页
                        common.launchActivity(LoginActivity.this, Main2Activity.class);
                        LoginActivity.this.finish();
                    } else {
                        if (message.equals("验证码不对！")) {
                            MyToast.makeText(scontext, "验证码有误，请稍后再试", Toast.LENGTH_SHORT).show();
                        } else {
                            MyToast.makeText(scontext, message+"请稍后再试", Toast.LENGTH_SHORT).show();

                        }
                    }
                }


            });

        }
    }

    /*
    sendCode
     */
    public void sendCode() {
        //手机判空，缺规则；
        if (TextUtils.isEmpty(et_phoneNum.getText())) {
            MyToast.makeText(this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
        } else {
            final Timer timer = new Timer();
            // 倒计时
            TimerTask task = new TimerTask() {

                @Override

                public void run() {

                    runOnUiThread(new Runnable() {  // UI thread

                        @Override

                        public void run() {

                            recLen--;
                            sendHandlerMessage(SEND_CODE_ONCLICK, recLen);
                            if (recLen <= 0) {
                                timer.cancel();
                                sendHandlerMessage(SEND_CODE_TIME_OUT, recLen);
                                recLen = 60;
                            }

                        }

                    });

                }

            };
            timer.schedule(task, 1000, 1000);
            HashMap map = new HashMap();
            map.put("phone", et_phoneNum.getText().toString());
/*请求发送验证码*/
            sendCodeRequest(map, new JsonResHandler() {
                @Override
                public void onSuccess(int statusCode, JSONObject response) {
                    Log.e("result", response.toString());
                    try {
                        if (response.getString("status").equals("1")) {
                            MyToast.makeText(scontext, "验证码发送成功", Toast.LENGTH_SHORT).show();
                        } else {
                            MyToast.makeText(scontext, "验证码发送失败，请稍后再试", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailed(int i, String s) {
                    try {
                        common.logResult(s);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        Log.e("click", "in");
        if (view == bt_send) {
            sendCode();
        } else if (view == bt_login) {
            login();
        } else if (view == iv_back) {
//            common.launchActivity(LoginActivity.this,MainActivity.class);
            this.finish();
        }
    }

    //发送验证码；
    public void sendCodeRequest(HashMap map, IResponseHandler handler) {
        HttpCore.post(URL.SENDVERIFYCODE, map, handler);
    }

    //登录
    public void loginRequest(HashMap map, final IResponseHandler handler) {
        Log.e("login", URL.LOGIN+"--map"+map.toString());
        HttpCore.post(URL.LOGIN, map, new IResponseHandler() {
            @Override
            public void onSuccess(Response response) {
                String token = response.header("token");
                HttpCore.setToken(token);
                handler.onSuccess(response);
            }

            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
    }


    //ui线程回调处理
    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SEND_CODE_ONCLICK:
                    bt_send.setEnabled(false);
                    bt_send.setBackgroundResource(R.drawable.round_shape2);
                    bt_send.setText("" + msg.obj);
                    break;
                case SEND_CODE_TIME_OUT:
                    bt_send.setEnabled(true);
                    bt_send.setBackgroundResource(R.drawable.round_shape);
                    bt_send.setText("发送验证码");
                    break;
            }
        }
    };

    //sendmessage
    public void sendHandlerMessage(int what, Object obj) {
        Message msg = handler.obtainMessage();
        msg.what = what;
        msg.obj = obj;
        handler.sendMessage(msg);
    }

    @Override
    public void onBackPressed() {
//        common.launchActivity(LoginActivity.this,MainActivity.class);
        this.finish();
    }
}


