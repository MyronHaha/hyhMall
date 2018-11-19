package com.example.myron.heyihui.com.example.myron.heyihui.fragment.mall_v1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.common.design.MaterialDialog;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.Favor;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.User;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.BaseActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.LoginActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Main2Activity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.ExpenseActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.MallListActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.MallProductDetail;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.MyFavorActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.MyOrdersActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall.RecordsActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.JsonResHandler;

import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Jason on 2017/10/23.
 */

public class MineFragment extends LazyLoadFragment {
    @BindView(R.id.favorView)
    RecyclerView favorView;
    @BindView(R.id.barChart)
    BarChart mBarChart;
    LinearLayoutManager manager1;
    BaseQuickAdapter adapter;
    List<Favor.Data> favorDatas = new ArrayList();
    Unbinder unbinder;
    //records nums
    @BindView(R.id.record_salse)
    TextView recordSalse;
    @BindView(R.id.record_kp)
    TextView recordKp;
    @BindView(R.id.record_kh)
    TextView recordKh;
    @BindView(R.id.record_js)
    TextView recordJs;
    //salesExpense nums
    @BindView(R.id.tv_hk)
    TextView tvHk;
    @BindView(R.id.tv_fwf)
    TextView tvFwf;
    @BindView(R.id.tv_sf)
    TextView tvSf;
//    @BindView(R.id.iv_user)
//            ImageView ivUser;
    //bussiness datas
    @BindView(R.id.tv_user_name)
        TextView userName;
    @BindView(R.id.tv_phone_num)
            TextView phoneNum;
    @OnClick(R.id.tv_logout)
         public void logout(){
        ((Main2Activity)getActivity()).showNormalDialog("注销登录？", new BaseActivity.promptListener() {
            @Override
            public void cancel(MaterialDialog dialog) {
                      dialog.dismiss();
            }

            @Override
            public void prompt(MaterialDialog dialog) {
               common.SP_Clear(getActivity());
               HttpUtils.setLogin(false);
               dialog.dismiss();
               common.launchActivity(getActivity(),LoginActivity.class);
               getActivity().finish();
            }
        });
    }
    List<com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.BarData.Data> Bdatas = new ArrayList<>();
//    private List<String> mlist = new ArrayList<>();

   @BindView(R.id.d3)
   View view; // sc dingbumeishujushi
    @BindView(R.id.title_hk)
    TextView hk_spec;
    @BindView(R.id.title_fwf)
    TextView fwf_spec;
    @BindView(R.id.title_sf)
    TextView sf_spec;
    @Override
    protected void initView() {
        initChart();
        initFavorView();
    }

    //    private void initBarChart() {
//        mBarChart.setDrawBarShadow(false);     //表不要阴影
//        mBarChart.setDrawValueAboveBar(true);
//        Description description=new Description();
//        description.setText("业务分析");
//        mBarChart.setDescription(description);  //表的描述信息
//
//        mBarChart.setPinchZoom(false);
//        mBarChart.setMaxVisibleValueCount(60); //最大显示的个数。超过60个将不再显示
//        mBarChart.setScaleEnabled(false);     //禁止缩放
//        mBarChart.setDragEnabled(true);// 是否可以拖拽
//        mBarChart.setHighlightPerDragEnabled(true);// 拖拽超过图标绘制画布时高亮显示
//        mBarChart.setDrawGridBackground(false);//
//      /*  mBarChart.setAutoScaleMinMaxEnabled(true);*/
//       /* mBarChart.animateX(500);//数据显示动画，从左往右依次显示*/
//       /* mBarChart.getAxisRight().setEnabled(false);*/
//        /*mBarChart.setDragDecelerationEnabled(true);*///拖拽滚动时，手放开是否会持续滚动，默认是true（false是拖到哪是哪，true拖拽之后还会有缓冲）
//        mBarChart.zoom(2.5f,1f,0,0);//显示的时候 是 按照多大的比率缩放显示   1f表示不放大缩小
//        //我默认手机屏幕上显示10  剩下的滑动直方图 然后显示。。假如要显示25个 那么除以10 就是放大2.5f。。同理
//        // 56个民族   那么放大5.6f
//
//        draw();
//    }
    private void initFavorView() {
        manager1 = new LinearLayoutManager(getActivity());
        adapter = new BaseQuickAdapter<Favor.Data,BaseViewHolder>(R.layout.item_favor, favorDatas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, Favor.Data o) {
                if (o != null) {
                    view.setVisibility(View.VISIBLE);
                    if(baseViewHolder.getPosition() == favorDatas.size()-1){
                        baseViewHolder.getView(R.id.view).setVisibility(View.GONE);
                    }
                    Glide.with(getActivity())
                            .load(URL.IMGPATH + o.getImg())
                            .placeholder(R.mipmap.nopictures_bg)
                            .error(R.mipmap.nopictures_bg)
                            .dontAnimate()
                            .into((ImageView) baseViewHolder.getView(R.id.iv));
                    baseViewHolder.setText(R.id.tv_title, common.isNullData(o.getName()));
                    baseViewHolder.setText(R.id.tv_price, "￥" +common.isNullData(o.getSourceParam()));
                }
            }
        };
        favorView.setLayoutManager(manager1);
        favorView.setAdapter(adapter);
        favorView.setFocusable(false);
        favorView.setFocusableInTouchMode(false);
        favorView.setHasFixedSize(true);
        favorView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        favorView.setNestedScrollingEnabled(false);
        manager1.setAutoMeasureEnabled(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(getActivity(), MallProductDetail.class);
                intent.putExtra("id", favorDatas.get(i).getSourceId());
                common.launchActivityWithIntent(getActivity(), intent);
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.layout_mine;
    }

    @Override
    protected void lazyLoad() {
        initDatas();
    }

    @Override
    protected void stopLoad() {
        super.stopLoad();
    }

    public void initDatas() {
        initUser();
        initRecordsNums();
        initSalesExpenseNums();
        initBussinessData();
        initFavorData();
    }

    private void initUser() {
        new Handler(getActivity().getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
            if(HttpUtils.isIsLogin()){
                userName.setText(User.userName);
                String phone = common.SP_Read(getActivity(), "phone");
                String end = phone.substring(phone.length()-4,phone.length());
                String pre = phone.substring(0,phone.length()-8);
                StringBuilder builder = new StringBuilder();
                builder.append(pre).append("****").append(end);
                phoneNum.setText(builder.toString());
            }else{
                userName.setText("登录");
                userName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        common.launchActivity(getActivity(),LoginActivity.class
                        );
                    }
                });
            }

            }
        });
    }

    private void initBussinessData() {
        HttpUtils.getBussinessData(new GsonResHandler<com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.BarData>() {
            @Override
            public void onSuccess(int i, com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.BarData data) {
                if (i == 200) {
                    if(!((Main2Activity)getActivity()).loginTimeOut(data.getStatus())){
                        if (data.getData().size() > 0) {
                            Bdatas.clear();
                            Bdatas.addAll(data.getData());
                            initChart();
                        }
                    }
                } else {
                        MyToast.makeText(getActivity(), "业务数据请求失败，错误码：" + i, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed(int i, String s) {

            }
        });
    }

    private void initSalesExpenseNums() {
        HttpUtils.getExpenseNums(new JsonResHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response1) {
                super.onSuccess(statusCode, response1);
                if (statusCode == 200) {
                    try {
                        if(!((Main2Activity)getActivity()).loginTimeOut(response1.getInt("status"))) {
                            JSONObject response = new JSONObject(response1.getString("data"));
                            Log.e("hk",response.getString("m1"));
                            Log.e("fwf",response.getString("m2"));
                            Log.e("sf",response.getString("m3"));
                            String[] r1 = common.transMoneyPlaform(response.getString("m1").equals("null") ? 0.00 : response.getDouble("m1"));
                            String[] r2 = common.transMoneyPlaform(response.getString("m2").equals("null") ? 0.00 : response.getDouble("m2"));
                            String[] r3 = common.transMoneyPlaform(response.getString("m3").equals("null") ? 0.00 : response.getDouble("m3"));

                            tvHk.setText(r1[0]);
                            tvFwf.setText(r2[0]);
                            tvSf.setText(r3[0]);

                            hk_spec.setText(r1[1]);
                            fwf_spec.setText(r2[1]);
                            sf_spec.setText(r3[1]);
                        }
                    } catch (Exception e) {
                        tvHk.setText("error");
                        tvFwf.setText("error");
                        tvSf.setText("error");
                    }
                } else {
                    Log.e("error----", "salesExpenseNUms --" + statusCode);
                }
            }

            @Override
            public void onFailed(int i, String s) {

            }
        });
    }

    private void initRecordsNums() {
        HttpUtils.getRecordNums(new JsonResHandler() {
            @Override
            public void onSuccess(int statusCode, JSONObject response1) {
                super.onSuccess(statusCode, response1);
                if (statusCode == 200) {
                    try {
                         if(!((Main2Activity)getActivity()).loginTimeOut(response1.getInt("status"))){
                             JSONObject response = new JSONObject(response1.getString("data"));
                             String[] r1 = common.transMoneyPlaform(response.getDouble("m1"));
                             String[] r2 = common.transMoneyPlaform(response.getDouble("m2"));
                             String[] r3 = common.transMoneyPlaform(response.getDouble("m3"));
                             String[] r4 = common.transMoneyPlaform(response.getDouble("m4"));
                             recordSalse.setText(r1[0]+r1[1]);
                             recordKp.setText(r2[0]+r2[1]);
                             recordKh.setText(r3[0]+r3[1]);
                             recordJs.setText(r4[0]+r4[1]);
                          Log.e("m4",response.getDouble("m4")+"") ;
                         }else{
                             Log.e("status_get","error");
                         }
                    } catch (Exception e) {
                        Log.e("status_get","error");
                    }
                } else {
                    Log.e("error----", "recordNums --" + statusCode);
                }
            }

            @Override
            public void onFailed(int i, String s) {

            }
        });
    }
//
    private void initFavorData() {
//        favorDatas.clear();
//        common.createData(favorDatas);
//        favorDatas = favorDatas.subList(0, 2);
//        adapter.notifyDataSetChanged();
        HashMap map = new HashMap();
        map.put("limit", "3");
        map.put("count", "0");
        HttpUtils.getFavor(new GsonResHandler<Favor>() {
            @Override
            public void onSuccess(int i, Favor busData) {
                if (i == 200) {

                    if (busData != null && !((Main2Activity)getActivity()).loginTimeOut(busData.getStatus())) {
                                favorDatas.clear();
                                favorDatas.addAll(busData.getData());
                    }
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailed(int i, String s) {

            }
        }, map);
    }

    @OnClick(R.id.tv_allorders)
    public void getAllOrders() {
        common.launchActivity(getActivity(), MyOrdersActivity.class);
    }

    @OnClick(R.id.dqr)
    public void getType1() {
        Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
        intent.putExtra("type", 1);
        common.launchActivityWithIntent(getActivity(), intent);
    }

    @OnClick(R.id.dzf)
    public void getType2() {
        Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
        intent.putExtra("type", 2);
        common.launchActivityWithIntent(getActivity(), intent);
    }

    @OnClick(R.id.dsh)
    public void getType3() {
        Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
        intent.putExtra("type", 3);
        common.launchActivityWithIntent(getActivity(), intent);
    }

    @OnClick(R.id.dkp)
    public void getType4() {
        Intent intent = new Intent(getActivity(), MyOrdersActivity.class);
        intent.putExtra("type", 4);
        common.launchActivityWithIntent(getActivity(), intent);
    }

    @OnClick(R.id.allfavor)
    public void FavorActivity() {
        common.launchActivity(getActivity(), MyFavorActivity.class);
    }

    @OnClick(R.id.sales_record)
    public void SalesActivity() {
        Intent intent = new Intent(getActivity(), RecordsActivity.class);
        intent.putExtra("type", 1);
        common.launchActivityWithIntent(getActivity(), intent);
    }

    @OnClick(R.id.kp_records)
    public void KPActivity() {
        Intent intent = new Intent(getActivity(), RecordsActivity.class);
        intent.putExtra("type", 2);
        common.launchActivityWithIntent(getActivity(), intent);
    }

    @OnClick(R.id.hk_records)
    public void HKActivity() {
        Intent intent = new Intent(getActivity(), RecordsActivity.class);
        intent.putExtra("type", 3);
        common.launchActivityWithIntent(getActivity(), intent);
    }

    @OnClick(R.id.js_records)
    public void JSActivity() {
        Intent intent = new Intent(getActivity(), RecordsActivity.class);
        intent.putExtra("type", 4);
        common.launchActivityWithIntent(getActivity(), intent);
    }

    @OnClick(R.id.ll_expense)
    public void EXPENSEActivity() {
        common.launchActivity(getActivity(), ExpenseActivity.class);
    }

//    public void draw(){
//
//        //X轴 样式
//        final XAxis xAxis = mBarChart.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
////        xAxis.setLabelRotationAngle(90);//柱的下面描述文字  旋转90度
//        xAxis.setDrawLabels(true);
//        xAxis.setDrawGridLines(false);
//        xAxis.setDrawAxisLine(true);
////        xAxis.setTypeface(Typeface.createFromAsset(getAssets(), "OpenSans-Light.ttf"));//字体的相关的设置
//        xAxis.setGranularity(10f);//设置最小间隔，防止当放大时，出现重复标签。
//        xAxis.setCenterAxisLabels(true);//字体下面的标签 显示在每个直方图的中间
//        xAxis.setLabelCount(5,true);//一个界面显示10个Lable。那么这里要设置11个
//        xAxis.setTextSize(10f);
//        xAxis.setSpaceMin(0.5f);
//
//        //Y轴样式
//        YAxis leftAxis = mBarChart.getAxisLeft();
//        leftAxis.setLabelCount(4);
//        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
////        leftAxis.setSpaceTop(13f);
//        leftAxis.setStartAtZero(true);
////        leftAxis.setYOffset(100f);
//        leftAxis.setDrawAxisLine(true);
//
//        //这个替换setStartAtZero(true)
//        leftAxis.setAxisMaxValue(500f);
//        leftAxis.setAxisMinValue(0f);
//        leftAxis.setDrawGridLines(false);//背景线
//        leftAxis.setAxisLineColor(getResources().getColor(R.color.black_de));
//
//
//
//
//        //.设置比例图标的显示隐藏
//        Legend l = mBarChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
//        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
//        l.setDrawInside(false);
//        //样式
//        l.setForm(Legend.LegendForm.CIRCLE);
//        //字体
//        l.setFormSize(10f);
//        //大小
//        l.setTextSize(13f);
//        l.setFormToTextSpace(10f);
//        l.setXEntrySpace(10f);
//
//
//
//        //模拟数据
//        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
//        yVals1.add(new BarEntry(1,230));
//        yVals1.add(new BarEntry(2, 120));
//        yVals1.add(new BarEntry(3, 300));
//        yVals1.add(new BarEntry(4, 100));
//        yVals1.add(new BarEntry(5, 450));
//        yVals1.add(new BarEntry(6, 500));
//        yVals1.add(new BarEntry(7, 350));
//        yVals1.add(new BarEntry(8, 260));
//        yVals1.add(new BarEntry(9, 140));
//        yVals1.add(new BarEntry(10, 200));
//        yVals1.add(new BarEntry(11, 330));
//        yVals1.add(new BarEntry(12, 440));
////        yVals1.add(new BarEntry(13, 42));
////        yVals1.add(new BarEntry(14, 41));
////        yVals1.add(new BarEntry(15, 12));
////        yVals1.add(new BarEntry(16, 31));
////        yVals1.add(new BarEntry(17, 21));
////        yVals1.add(new BarEntry(18, 20));
////        yVals1.add(new BarEntry(19, 44));
////        yVals1.add(new BarEntry(20, 42));
////        yVals1.add(new BarEntry(21, 41));
////        yVals1.add(new BarEntry(22, 12));
////        yVals1.add(new BarEntry(23, 31));
////        yVals1.add(new BarEntry(24, 21));
////        yVals1.add(new BarEntry(25, 20));
//        setData(yVals1);
//    }
//    private void setData(ArrayList yVals1) {
//        for(int i=1;i<=yVals1.size();i++) {
//            mlist.add(""+i);
//        }
//        IAxisValueFormatter ix=new MyXAxisValueFormatter(mlist);
//        mBarChart.getXAxis().setValueFormatter(ix);
//        BarDataSet set1;
//        if (mBarChart.getData() != null &&
//                mBarChart.getData().getDataSetCount() > 0) {
//            set1 = (BarDataSet) mBarChart.getData().getDataSetByIndex(0);
//            set1.setValues(yVals1);
//            mBarChart.getData().notifyDataChanged();
//            mBarChart.notifyDataSetChanged();
//        } else {
//            set1 = new BarDataSet(yVals1, "销售额（万元）");
//            set1.setColors(ColorTemplate.MATERIAL_COLORS);
//            ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
//            dataSets.add(set1);
//            BarData data = new BarData(dataSets);
//            data.setValueTextSize(10f);
//            data.setBarWidth(1f);
//            mBarChart.setData(data);
//        }
//    }
//
//    public class MyXAxisValueFormatter implements IAxisValueFormatter {
//
//        private List<String> mValues;
//
//        public MyXAxisValueFormatter(List<String> values) {
//            this.mValues = values;
//        }
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//            int x=(int)(value);
//            if (x<0)
//                x=0;
//            if (x>=mValues.size())
//                x=mValues.size()-1;
//            return mValues.get(x);
//        }
//    }

    /**
     * 单数据集。设置柱状图样式，X轴为字符串，Y轴为数值
     *
     * @param barChart
     * @param xAxisValue
     * @param yAxisValue
     * @param title         图例文字
     * @param xAxisTextSize x轴标签字体大小
     * @param barColor
     */
    public static void setBarChart(BarChart barChart, List<String> xAxisValue, List<Float> yAxisValue, String title, float xAxisTextSize, Integer barColor) {
        barChart.getDescription().setEnabled(true);//设置描述
        barChart.getDescription().setText("(月份)");
        barChart.setPinchZoom(false);//设置按比例放缩柱状图
        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setScaleEnabled(false);
//        //设置自定义的markerView
//        MPChartMarkerView markerView = new MPChartMarkerView(barChart.getContext(), R.layout.custom_marker_view);
//        barChart.setMarker(markerView);

        //x坐标轴设置
        IAxisValueFormatter xAxisFormatter = new StringAxisValueFormatter(xAxisValue);//设置自定义的x轴值格式化器
        XAxis xAxis = barChart.getXAxis();//获取x轴
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//设置X轴标签显示位置
        xAxis.setDrawGridLines(false);//不绘制格网线
        xAxis.setGranularity(1f);//设置最小间隔，防止当放大时，出现重复标签。
        xAxis.setValueFormatter(xAxisFormatter);
        xAxis.setTextSize(xAxisTextSize);//设置标签字体大小
        xAxis.setLabelCount(xAxisValue.size());//设置标签显示的个数

        //y轴设置
        YAxis leftAxis = barChart.getAxisLeft();//获取左侧y轴
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);//设置y轴标签显示在外侧
        leftAxis.setAxisMinimum(0f);//设置Y轴最小值
        leftAxis.setDrawGridLines(false);
        leftAxis.setDrawLabels(true);//绘制y轴标签
        leftAxis.setDrawAxisLine(true);//禁止绘制y轴

        barChart.getAxisRight().setEnabled(false);//禁用右侧y轴

        //图例设置
        Legend legend = barChart.getLegend();
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);//图例水平居中
        legend.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);//图例在图表上方
        legend.setOrientation(Legend.LegendOrientation.HORIZONTAL);//图例的方向为水平
        legend.setDrawInside(false);//绘制在chart的外侧
        legend.setDirection(Legend.LegendDirection.LEFT_TO_RIGHT);//图例中的文字方向

        legend.setForm(Legend.LegendForm.LINE);//图例窗体的形状
        legend.setFormSize(0);//图例窗体的大小
        legend.setTextSize(10f);//图例文字的大小
        //legend.setYOffset(-2f);

        //设置柱状图数据
        setBarChartData(barChart, yAxisValue, title, barColor);

        barChart.setExtraBottomOffset(10);//距视图窗口底部的偏移，类似与paddingbottom
        barChart.setExtraTopOffset(0);//距视图窗口顶部的偏移，类似与paddingtop
        barChart.setFitBars(true);//使两侧的柱图完全显示
        barChart.animateXY(1000, 1500);//数据显示动画，从左往右依次显示
    }

    /**
     * 设置柱图
     *
     * @param barChart
     * @param yAxisValue
     * @param title
     * @param barColor
     */
    private static void setBarChartData(BarChart barChart, List<Float> yAxisValue, String title, Integer barColor) {

        ArrayList<BarEntry> entries = new ArrayList<>();

        for (int i = 0, n = yAxisValue.size(); i < n; ++i) {
            entries.add(new BarEntry(i, yAxisValue.get(i)));
        }

        BarDataSet set1;

        if (barChart.getData() != null && barChart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) barChart.getData().getDataSetByIndex(0);
            set1.setValues(entries);
            barChart.getData().notifyDataChanged();
            barChart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(entries, title);
            set1.setColor(ContextCompat.getColor(barChart.getContext(), R.color.colorPrimary));//设置set1的柱的颜色
//            if (barColor == null) {
//                set1.setColor(ContextCompat.getColor(barChart.getContext(), R.color.colorPrimary));//设置set1的柱的颜色
//            } else {
//                set1.setColor(ContextCompat.getColor(barChart.getContext(), R.color.colorPrimary));//设置set1的柱的颜色
//            }
//            int[] colors = new int[]{
//                    Color.parseColor("#ffc0cb"), Color.parseColor("#ffc0cb"), Color.parseColor("#ffc0cb"),
//                    Color.parseColor("#00CD66"), Color.parseColor("#00CD66"), Color.parseColor("#00CD66"),
//                    Color.parseColor("#CD853F"), Color.parseColor("#CD853F"), Color.parseColor("#CD853F"),
//                    Color.parseColor("#B0E2FF"), Color.parseColor("#B0E2FF"), Color.parseColor("#B0E2FF")
//            };
//            set1.setColors(colors);
            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setValueTextSize(10f);
            data.setBarWidth(0.9f);
            data.setValueFormatter(new MyValueFormatter());
            barChart.setData(data);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
//
//    @OnClick({R.id.record_salse, R.id.record_kp, R.id.record_kh, R.id.record_js})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.record_salse:
//                break;
//            case R.id.record_kp:
//                break;
//            case R.id.record_kh:
//                break;
//            case R.id.record_js:
//                break;
//        }
//    }

    static class MyValueFormatter implements IValueFormatter {

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            return Float.toString(value);
        }
    }

    /**
     * Created by Charlie on 2016/9/23.
     * 对字符串类型的坐标轴标记进行格式化
     */
    public static class StringAxisValueFormatter implements IAxisValueFormatter {

        //区域值
        private List<String> mStrs;

        /**
         * 对字符串类型的坐标轴标记进行格式化
         *
         * @param strs
         */
        public StringAxisValueFormatter(List<String> strs) {
            this.mStrs = strs;
        }

        @Override
        public String getFormattedValue(float v, AxisBase axisBase) {
            return mStrs.get((int) v);
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        if (!hidden) {
            initDatas();
            initChart();
        }
    }

    private void initChart() {
        setBarChart(mBarChart, getXdatas(Bdatas), getYdatas(Bdatas), "销售量（万元）", 13f, R.color.colorPrimary);
    }

    public List<String> getXdatas(List<com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.BarData.Data> datas) {
        List<String> list = new ArrayList<>();
        for (com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.BarData.Data item : datas) {
            list.add(item.getM1() + "");
        }
        return list;
    }

    public List<Float> getYdatas(List<com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.BarData.Data> datas) {
        List<Float> list = new ArrayList<>();
        for (com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.BarData.Data item : datas) {
            String s = String.format("%.1f",item.getM2()/10000);
            list.add(Float.parseFloat(s));
        }
        return list;
    }
}
