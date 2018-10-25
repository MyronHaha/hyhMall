package com.example.myron.heyihui.com.example.myron.heyihui.fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.ProductAdapter1;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jason on 2017/10/23.
 */

public class ProductChildFragment1 extends Fragment {
    public Context context;
    public ProductChildFragment1(){}
    @SuppressLint("ValidFragment")
    public ProductChildFragment1(Context context){
        this.context = context;
    }
    //listview

    private List<String> mList,list;
    private ProductAdapter1 productAdapter1;
    private int page = 1; //页码；
    private ProgressDialog dialog;
    boolean footerShow = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_ylhc,null);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mList = new ArrayList<>();
        list = new ArrayList<>();
        dialog = new ProgressDialog(getActivity());
        productAdapter1 = new ProductAdapter1(getActivity(), mList);


    }



    private void showPd(){

        dialog.show();
        dialog.setMessage("正在加载中...");
    }
    private void cancelPd(){
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }



}
