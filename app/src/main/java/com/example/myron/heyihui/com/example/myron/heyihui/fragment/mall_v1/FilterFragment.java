package com.example.myron.heyihui.com.example.myron.heyihui.fragment.mall_v1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.CatagoryData;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.MallListActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.MallProductDetail;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.mph.okdroid.response.GsonResHandler;

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
    BaseQuickAdapter<CatagoryData.Data,BaseViewHolder> adapter;

    List<CatagoryData.Data> datas = new ArrayList<CatagoryData.Data>();
    //记录选择
    List<CatagoryData.Data> selList = new ArrayList<CatagoryData.Data>();
   //重置
    private boolean isReset = false;
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

    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        initCatagoryData();
    }
    private void initCatagoryData() {
        HttpUtils.getCatagorys(new GsonResHandler<CatagoryData>() {
            @Override
            public void onFailed(int i, String s) {

            }

            @Override
            public void onSuccess(int i, CatagoryData catagoryData) {
                if(i==200){
                    datas.clear();
                    datas.addAll(catagoryData.getData());
                    matchListWithSid(((MallListActivity)getActivity()).productCatogory);
                }else{
                    MyToast.makeText(getActivity(), "请求失败" + i, Toast.LENGTH_SHORT).show();
                }
                adapter.notifyDataSetChanged();
            }

        });
    }

    private void initView() {
        manager = new GridLayoutManager(getActivity(),3,GridLayoutManager.VERTICAL,false);
        filterView.setHasFixedSize(true);
        filterView.setLayoutManager(manager);
        filterView.setNestedScrollingEnabled(true);
        filterView.setFocusable(false);
        filterView.setFocusableInTouchMode(false);
        filterView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        adapter = new BaseQuickAdapter<CatagoryData.Data, BaseViewHolder>( R.layout.item_tag, datas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, CatagoryData.Data s) {
                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) baseViewHolder.itemView.getLayoutParams();
//                if(baseViewHolder.getPosition()==0){
//                    params.setMargins(params.leftMargin, 0, params.rightMargin, params.bottomMargin);
//                    baseViewHolder.itemView.setLayoutParams(params);
//                }
                //重置
                if(isReset){
                    s.setSelected(false);
                    if(baseViewHolder.getPosition() == datas.size()-1){
                          isReset = false;
                    }
                }

                TextView tag = baseViewHolder.getView(R.id.tv_tag);
                if(s.isSelected()){
                      tag.setBackgroundResource(R.drawable.bg_notfill_cp);
                    tag.setTextColor(Color.parseColor("#16cce2"));
                }else{
                    tag.setBackgroundResource(R.drawable.bg_fill_cgrey);
                    tag.setTextColor(Color.parseColor("#242529"));
                }
                baseViewHolder.setText(R.id.tv_tag,s.getName());
            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
              CatagoryData.Data citem =  datas.get(i);
                if(citem.isSelected()){
                    selList.remove(citem);
                    if(citem.getId() == ((MallListActivity)getActivity()).productCatogory){
                        ((MallListActivity)getActivity()).productCatogory = 0;
                    }
                    //
                }else{
                    selList.add(citem);
                }
              citem.setSelected(!citem.isSelected());
                adapter.notifyItemChanged(i);
            }
        });
        filterView.setAdapter(adapter);
    }
    @OnClick(R.id.bt_sure)
    public void btSure(){
        ((MallListActivity)getActivity()).showFilter();
         Log.e("tag--size",selList.size()+"");
        if(selList.size()>0){
            String cs ="";
            for(int i =0;i<selList.size();i++){
              if(i == selList.size()-1){
                    cs+=selList.get(i).getId();
                }else{
                  cs+=selList.get(i).getId()+",";
              }
            }
            Log.e("cscscs---",cs);
            ((MallListActivity)getActivity()).map.put("cs",cs);
            ((MallListActivity)getActivity()).getListDatas(true);
        }else{
            ((MallListActivity)getActivity()).map.put("cs","");
            ((MallListActivity)getActivity()).getListDatas(true);
        }
    }
   @OnClick(R.id.bt_reset)
    public void reset(){
        isReset = !isReset;
        if(adapter != null){
            adapter.notifyDataSetChanged();
        }
        if(isReset){
            selList.clear();
        }
   }

   public boolean getHasSel(){
     if(selList.size()>0){
         return true;
     }
     return false;
   }

   public void matchListWithSid(int sid){
       for(int i = 0;i< datas.size();i++){
           CatagoryData.Data cg = datas.get(i);
           if(cg.getId() == sid){
               cg.setSelected(true);
               selList.add(cg);
//               adapter.notifyItemChanged(i);
           }
       }

   }
}
