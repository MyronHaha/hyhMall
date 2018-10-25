package com.example.myron.heyihui.com.example.myron.heyihui.view;

/**
 * Created by Myron on 2017/11/14.
 */

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.myron.heyihui.R;

/**
 * 加载对话框
 */
public class CustomDialog extends ProgressDialog
{
    static TextView tv ;
    static View view;
    String text;
    public CustomDialog(Context context)
    {
        super(context);
    }

    public CustomDialog(Context context, int theme,String loadtext)
    {
        super(context, theme);
        this.text = loadtext;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        init(getContext());
    }
    private void init(Context context)
    {
        //设置不可取消，点击其他区域不能取消
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        view = LayoutInflater.from(getContext()).inflate(R.layout.load_dialog,null);
        tv = (TextView) view.findViewById(R.id.tv_load_dialog);
        tv.setText(text);
        setContentView(view);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }
    @Override
    public void show()
    {
        super.show();
    }
    public  void setText(final String text){
        Log.e("","set");
        if(tv!=null){
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    tv.setText(text);
                }
            });

        }
    }
}