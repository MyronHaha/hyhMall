package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CustomDialog;
import com.mph.okdroid.response.JsonResHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class Detail_yj extends AppCompatActivity {
    ImageView iv_back;
    Button btn_submit;
    EditText et_jy;
    TextView tv_call;
    private CustomDialog dialog;
    static boolean canSubmit = true;
    static int i = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_jy);
        common.changeTitle(this, "意见");
        BindView();
    }

    private void BindView() {
        dialog = new CustomDialog(this,R.style.CustomDialog,"提交中...");
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common.goBack(Detail_yj.this);
            }
        });

        et_jy = (EditText) findViewById(R.id.et_jy);
        et_jy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!editable.toString().equals("")){
                    btn_submit.setEnabled(true);
                    btn_submit.setBackgroundResource(R.drawable.round_shape);
                }else{
                    btn_submit.setEnabled(false);
                    btn_submit.setBackgroundResource(R.drawable.round_shape2);
                }
            }
        });

        tv_call = (TextView) findViewById(R.id.tv_call);
        tv_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(Detail_yj.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    MyToast.makeText(Detail_yj.this,"应用需要拨打电话请求",Toast.LENGTH_LONG).show();
                    return;
                }
                Detail_yj.this.startActivity(new Intent(Intent.ACTION_CALL, Uri.parse("tell:" + "020-39155180")));
            }
        });

        btn_submit = (Button) findViewById(R.id.bt_submit);
        btn_submit.setEnabled(false);
        btn_submit.setBackgroundResource(R.drawable.round_shape2);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //接口
                if(TextUtils.isEmpty(et_jy.getText().toString()) ){
                    MyToast.makeText(Detail_yj.this,"内容不能为空！", Toast.LENGTH_SHORT).show();
                }else{
//                    MyToast.makeText(Detail_yj.this,et_jy.getText().toString(), Toast.LENGTH_SHORT).show();

                    Log.e("canSub",canSubmit+"");
                    if (canSubmit) {
                        dialog.show();
                        postIssue();
                    }else{
                        MyToast.makeText(Detail_yj.this,"请勿在一小时内重复提交！", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }

    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:

                    final Timer timer = new Timer();
                    final TimerTask task=  new TimerTask() {
                        @Override
                        public void run() {
                                if (i<3600) {
                                    canSubmit = false;
                                    i++;
                                }else{
                                    canSubmit = true;
                                    i = 0;
                                    timer.cancel();
                                }


//                            canSubmit = true;

//                            timer.cancel();
                        }
                    };
                    timer.schedule(task,1000,1000);
                    break;
                case 1:
                    break;
            }
        }
    };

    public void postIssue(){
        HashMap map = new HashMap();
        HashMap map2 = new HashMap();
        map.put("content",et_jy.getText().toString());
        map2.put("bean",new JSONObject(map).toString());
        HttpCore.post(URL.POSTISSUE, map2, new JsonResHandler() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
                if(dialog.isShowing()){
                    dialog.dismiss();
                }
                try {
                    int status  = response.getInt("status");
                    String message = response.getString("message");
                    if(status == 1 ){
                        MyToast.makeText(Detail_yj.this,"提交成功",2000).show();
                        handler.sendEmptyMessage(0);
//                        Detail_yj.this.finish();
                    }else{
                        MyToast.makeText(Detail_yj.this,message+",请稍后重试",2000).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
