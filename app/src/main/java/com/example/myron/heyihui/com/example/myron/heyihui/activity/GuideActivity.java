package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.BaseApp;
import com.example.myron.heyihui.com.example.myron.heyihui.guide.PageFrameLayout;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;

import java.util.HashMap;

public class GuideActivity extends AppCompatActivity {
PageFrameLayout contentFrameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        BaseApp.getInstance().addActivity(this);
        HashMap map = new HashMap();
        map.put("first",false);
        common.SP_Write_boolean(this,map);
        contentFrameLayout = (PageFrameLayout) findViewById(R.id.contentFrameLayout);
        // 设置资源文件和选中圆点
        contentFrameLayout.setUpViews(new int[]{
                R.layout.page_tab1,
                R.layout.page_tab2,
                R.layout.page_tab4
        }, R.mipmap.banner_on, R.mipmap.banner_off);

    }

}
