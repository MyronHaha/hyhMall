package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_Item_detail;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Product;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.google.gson.Gson;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;
import com.mph.okdroid.response.JsonResHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;

/*产品详情
页面需要几个参数  集合可以定义为static 只需要传一个position
* 产品名称
* 公司名称
* 价格
* 规格
* 型号
* 品牌
* 单位
* 包装材料
* 说明*/
public class Detail_Product2 extends AppCompatActivity {
  ImageView iv_back,iv_pic;
  Order_Item_detail.Data data;
  String productId; //上级
  TextView tv_name,tv_publish,tv_price,tv_gg,tv_xh,tv_pp,tv_dw,tv_bz,tv_sm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product__detail);
        common.changeTitle(this,"产品详情");
        productId = getIntent().getStringExtra("data");
        BindView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        HashMap map  = new HashMap();
        map.put("id",productId);
        getOrder(map, new JsonResHandler() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
                Gson gson = new Gson();
            data = gson.fromJson(response.toString(),Order_Item_detail.class).getData();
            Log.e("hh",data.toString());
            initViewData();
            }
        });
    }

    private void BindView() {
        iv_pic = (ImageView) findViewById(R.id.iv_pic);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                common.goBack(Detail_Product2.this);
            }
        });
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_publish = (TextView) findViewById(R.id.tv_publish);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_gg = (TextView) findViewById(R.id.tv_gg);
        tv_xh = (TextView) findViewById(R.id.tv_xh);
        tv_pp = (TextView) findViewById(R.id.tv_pp);
        tv_dw = (TextView) findViewById(R.id.tv_dwgg);
        tv_bz = (TextView) findViewById(R.id.tv_cl);
        tv_sm = (TextView) findViewById(R.id.tv_sm);
    }
    private void initViewData() {
        Glide.with(this.getApplicationContext())
                .load(URL.IMGPATH+data.getImg())
                .placeholder(R.mipmap.nopictures_bg)
                .crossFade()
                .into(iv_pic);
        tv_name.setText(data.getName());
        tv_price.setText(String .format("%.2f",data.getPrice())+"元");
        tv_publish.setText(data.getManufacturer());
        tv_bz.setText(data.getPackaging());
        tv_dw.setText(data.getUnit());
        tv_gg.setText(data.getSpec());
        tv_xh.setText(data.getCode());
        tv_pp.setText(data.getBrand());
        tv_sm.setText(Html.fromHtml(data.getRemark()));
    }

    //通过id查询商品；
    public void getOrder(HashMap map, IResponseHandler handler){
        HttpCore.get(URL.GET_PRODUCT,map,handler);
    }
}
