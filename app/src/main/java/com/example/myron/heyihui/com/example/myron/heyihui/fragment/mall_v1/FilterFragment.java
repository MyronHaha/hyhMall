package com.example.myron.heyihui.com.example.myron.heyihui.fragment.mall_v1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.MallList;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jason on 2017/10/23.
 */

public class FilterFragment extends Fragment {
    @BindView(R.id.filterView)
    RecyclerView filterView;
    public Context context;
    GridLayoutManager manager;
    BaseQuickAdapter<String,BaseViewHolder> adapter;

    List<String> datas = new ArrayList<>();
    public FilterFragment() {
    }

    @SuppressLint("ValidFragment")
    public FilterFragment(Context context) {
        this.context = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_filter, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView();
        initData();
    }

    private void initData() {
        datas.clear();
        for(int i= 0;i<10;i++){
            datas.add("分类标签"+i);
        }
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        manager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        filterView.setHasFixedSize(true);
        filterView.setLayoutManager(manager);
        adapter = new BaseQuickAdapter<String, BaseViewHolder>( R.layout.item_tag, datas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String s) {
                baseViewHolder.setText(R.id.tv_tag,s);
            }
        };
        filterView.setAdapter(adapter);
    }
    @OnClick(R.id.bt_sure)
    public void btSure(){
        ((MallList)getActivity()).showFilter();
    }
}
