package com.example.myron.heyihui.com.example.myron.heyihui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Account;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.User;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_jf;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_msg;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_msg2;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_sr;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_xq;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_yj;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_zhichu;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.LoginActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Main2Activity;
import com.example.myron.heyihui.com.example.myron.heyihui.circlePic.GlideCircleTransform;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;
import com.mph.okdroid.response.JsonResHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Jason on 2017/10/23.
 */

public class MyFragment extends Fragment implements View.OnClickListener {
    LinearLayout ll_month_get, ll_month_pay, ll_msg;
    RelativeLayout rl_jf, rl_msg, rl_jy, rl_xq,rl_update;
    TextView tv_disLogin, total_jf, tv_zijin, tv_balance, tv_xq, tv_name, tv_theSR, tv_theZC;
    ImageView iv_user;
    public static double totalGoals;
    public TextView tcurrent;//当前版本；
    public TextView tips;//新版本vername
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_myfragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getTotal();
        getCurrentMonth(); // 本月收入支出
        BindView();
    }

    //get total积分；
    public void getTotal() {
        getTotalGoals(new JsonResHandler() {
            @Override
            public void onFailed(int i, String s) {
            }

            @Override
            public void onSuccess(int statusCode, JSONObject response) {
                super.onSuccess(statusCode, response);
                try {
                    int status = response.getInt("status");
                    if (status == 1) {
                        //获取总分记录
                        JSONObject data = (JSONObject) response.get("data");
                        Log.e("total:", data.getDouble("credit") + "");
                        totalGoals = data.getDouble("credit");
                        total_jf.setText(String.format("%.2f", totalGoals));
                    } else {
                        common.logResult("status error");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });


    }

    private void BindView() {
        ll_month_get = (LinearLayout) getActivity().findViewById(R.id.ll_month_get);
        ll_month_pay = (LinearLayout) getActivity().findViewById(R.id.ll_month_pay);

        ll_month_pay.setOnClickListener(this);
        ll_month_get.setOnClickListener(this);

        rl_jf = (RelativeLayout) getActivity().findViewById(R.id.rl_jf);
        rl_jf.setOnClickListener(this);

        rl_msg = (RelativeLayout) getActivity().findViewById(R.id.rl_msg);
        rl_msg.setOnClickListener(this);

        rl_jy = (RelativeLayout) getActivity().findViewById(R.id.rl_jy);
        rl_jy.setOnClickListener(this);

        rl_xq = (RelativeLayout) getActivity().findViewById(R.id.rl_xq);
        rl_xq.setOnClickListener(this);

        tv_disLogin = (TextView) getActivity().findViewById(R.id.tv_disLogin);
        tv_disLogin.setOnClickListener(this);

        iv_user = (ImageView) getActivity().findViewById(R.id.iv_user);

        tcurrent = (TextView) getActivity().findViewById(R.id.tcurrent);
        rl_update = (RelativeLayout) getActivity().findViewById(R.id.rl_update);
        rl_update.setOnClickListener(this);

        tips = (TextView) getActivity().findViewById(R.id.tips);
        tips.setText("立即更新至："+Main2Activity.newVerName);
//        Glide.with(this).load("https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=243942221,3907738121&fm=27&gp=0.jpg")
//                .transform(new GlideCircleTransform(this.getContext()))
//                .into(iv_user);

        Glide.with(this).load(R.mipmap.nav_icon)
                .transform(new GlideCircleTransform(this.getContext()))
                .into(iv_user);
        tv_balance = (TextView) getActivity().findViewById(R.id.tv_balance);//余额
        tv_balance.setText(String.format("%,.2f", HomeFragment.balance));
        tv_zijin = (TextView) getActivity().findViewById(R.id.tv_zijin);//资金
        tv_zijin.setText(String.format("%,.2f", HomeFragment.zijin));
        tv_xq = (TextView) getActivity().findViewById(R.id.tv_xq);//需求
        tv_xq.setText(String.format("%,.2f", HomeFragment.xuqiu));
        total_jf = (TextView) getActivity().findViewById(R.id.tv_jf);//积分
        tv_name = (TextView) getActivity().findViewById(R.id.tv_name);
        tv_name.setText(User.userName);
        tv_theSR = (TextView) getActivity().findViewById(R.id.tv_sr);
        tv_theZC = (TextView) getActivity().findViewById(R.id.tv_zc);
        tcurrent.setText("当前版本："+common.getCurrentVerName());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_month_get:
                common.launchActivity(getActivity(), Detail_sr.class);
                break;
            case R.id.ll_month_pay: //支出 需求
                common.launchActivityWithData(getActivity(), Detail_xq.class, "支出");
                break;
            case R.id.rl_jf:
                common.launchActivity(getActivity(), Detail_jf.class);
                break;
            case R.id.rl_msg:
                common.launchActivity(getActivity(), Detail_msg2.class);
                break;
            case R.id.rl_jy:
                common.launchActivity(getActivity(), Detail_yj.class);
                break;
            case R.id.rl_xq:
                common.launchActivityWithData(getActivity(), Detail_xq.class, "全部");
                break;
            case R.id.tv_disLogin:
                if (common.SP_Clear(getActivity())) {
                    common.launchActivity(getActivity(), LoginActivity.class);
                    getActivity().finish();
                }
                break;
            case R.id.rl_update:
                Main2Activity.isclick = true;
                ((Main2Activity)getActivity()).checkPermission();

                break;
            default:
                break;
        }
    }

    public void getTotalGoals(IResponseHandler handler) {
        HttpCore.get(URL.GET_TOTAL_JF, handler);
    }

    public void getCurrentMonth() {
        HashMap map = new HashMap();
        map.put("t", "0");
        HttpCore.get(URL.GETACCOUNT, map, new GsonResHandler<Account>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, Account acc) {
                if (acc.getStatus() == 1) {
                    double theShouru = acc.getData().getAmount();
                    double theZhichu = acc.getData().getAmount1();
                    tv_theSR.setText(String.format("%,.2f", theShouru));
                    tv_theZC.setText(String.format("%,.2f", theZhichu));
                }
            }
        });
    }

}
