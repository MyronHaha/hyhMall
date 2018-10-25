package com.example.myron.heyihui.com.example.myron.heyihui.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myron.heyihui.R;

/**
 * Created by Jason on 2017/10/23.
 */

public class ProductChildFragment3 extends Fragment {
    public Context context;
    public ProductChildFragment3(){}
    @SuppressLint("ValidFragment")
    public ProductChildFragment3(Context context){
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.fragment_fylqx,null);
        return view;
    }
}
