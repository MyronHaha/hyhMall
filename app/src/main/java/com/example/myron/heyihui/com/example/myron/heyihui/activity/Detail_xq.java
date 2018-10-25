package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.TimePickerView;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_Re;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Product;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.Adapter_state;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.MyAdapter_xq;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.MyAdapter_zc;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CustomDialog;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class Detail_xq extends AppCompatActivity implements View.OnClickListener {
    SpringView springView;
    private List<Order_Re.Data> data_refresh = new ArrayList<>();
    private List<Order_Re.Data> data_more = new ArrayList<>();
    ListView mlist;
    MyAdapter_xq adapter;
    private static int currentPage = 1;
    LinearLayout ll_nothing;
    //筛选时间；
    private TextView tv_start, tv_end;
    private String start_time, end_time, title;
    TimePickerView pvTime;
    Calendar startDate, endDate;
    private ImageView iv_choose;
    //
    private String[] items = {"全部", "进行中", "已完成"}; // 012
    private ListView lv_choose;
    private ArrayAdapter<String> adapter_choose;
    //当前选中状态；
    private TextView tv_state;
    private String current_state = "全部";
    private String state_str = "0";
    private Intent from;
    private PopupWindow popup;
    private CustomDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_xq);
        common.changeTitle(this, "需求");
        BindView();
        common.goBack(this);
// 获取上级数据；
        from = getIntent();
        current_state = from.getStringExtra("data");
        changeCurrentState(current_state);
        refresh();
    }
    @Override
    protected void onResume() {
        super.onResume();
//        common.createData(data_refresh);
        refresh();
    }

    private void changeCurrentState(String state) {

        //这里更改ui选中，请求url地址，
        if (state.equals("全部")) {
            //
//            MyToast.makeText(Detail_xq.this, "选中全部", Toast.LENGTH_SHORT).show();
            tv_state.setText("全部");
            state_str = "0";
        } else if (state.equals("已完成")) {
//            MyToast.makeText(Detail_xq.this, "选中已完成", Toast.LENGTH_SHORT).show();
            tv_state.setText("已完成");
            state_str = "2";
        } else if(state.equals("进行中")){
//            MyToast.makeText(Detail_xq.this, "选中进行中", Toast.LENGTH_SHORT).show();
            tv_state.setText("进行中");
            state_str = "1";
        }
        if(state.equals("支出")){
            common.changeTitle(this, "支出");
        }else{
            common.changeTitle(this, "需求");
        }

    }

    private void BindView() {
        dialog = new CustomDialog(this,R.style.CustomDialog,"加载中");
        dialog.show();
        springView = (SpringView) findViewById(R.id.springView);
        springView.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
            @Override
            public void onLoadmore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMore();
                        springView.onFinishFreshAndLoad();
                    }
                }, 1000);
            }
        });
        //DefaultHeader/Footer是SpringView已经实现的默认头/尾之中的一个
        //很多其它还有MeituanHeader、AliHeader、RotationHeader等如上图7种
        springView.setHeader(new DefaultHeader(this));
        springView.setFooter(new DefaultFooter(this));
        //筛选时间
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_end = (TextView) findViewById(R.id.tv_end);
        tv_start.setOnClickListener(this);
        tv_end.setOnClickListener(this);
        start_time = getCurrentDate();
        end_time = getCurrentDate();
        tv_start.setText(start_time);
        tv_end.setText(end_time);
        //list
        mlist = (ListView) findViewById(R.id.lv);
        adapter = new MyAdapter_xq(this, data_refresh);
        mlist.setAdapter(adapter);
        ll_nothing = (LinearLayout) findViewById(R.id.ll_nothing);
        common.setListViewHeightBasedOnChildren(mlist, adapter);
        mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int id  = data_refresh.get(i).getId();
                common.launchActivityWithData(Detail_xq.this, Detail_zc_detail.class,String.valueOf(id));
            }
        });
        iv_choose = (ImageView) findViewById(R.id.iv2);
        iv_choose.setOnClickListener(this);
        //需求状态
        tv_state = (TextView) findViewById(R.id.tv_state);
//
//        adapter_choose =  new ArrayAdapter<String>( this,
//                R.layout.item_popup , items );
//        lv_choose.setAdapter(adapter_choose);
//        spinner = (Spinner) findViewById(R.id.field_item_spinner_content);
//        adapter_choose.setDropDownViewResource(R.layout.item_popup);
//        spinner.setAdapter(adapter_choose);

//        Spinner spinner = (Spinner) findViewById(R.id.field_item_spinner_content);
//
//        //构造ArrayAdapter
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                R.layout.simple_item, items);
//        //设置下拉样式以后显示的样式
//        adapter.setDropDownViewResource(R.layout.item_popup);
//
//        spinner.setAdapter(adapter);
// popupWindow 初始化；
        popup();
    }

    //加载更多
    public void loadMore() {
        //新增数据  loadmore
        currentPage++;
        data_more.clear();
        HashMap map = new HashMap();
        map.put("limit", "10");
        map.put("count", String.valueOf((currentPage - 1) * 10));
        map.put("startDate", start_time);
        map.put("endDate", end_time);
        map.put("s",state_str);
        getReOrders(map, new GsonResHandler<Order_Re>() {
            @Override
            public void onFailed(int statusCode, String errMsg) {
                Log.e("loadmore:pro", errMsg);
            }

            @Override
            public void onSuccess(int statusCode, Order_Re response) {

                if (response.getStatus() == 1) {

                    try {
                        common.logResult(response.getData().size() + "'");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                    Log.e("listmore", response.getData().size() + "");
//                springView.onFinishFreshAndLoad();
                    data_more = response.getData();
                    data_refresh.addAll(data_more);
                    refreshListView(data_refresh);

                } else {
                    try {
                        common.logResult("status:" + response.getStatus());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

    //刷新
    public void refresh() {
        currentPage = 1;
//        Log.e("size", data_refresh.size() + "");
        HashMap map = new HashMap();
//        //默认全部
//        if (choose_tag.equals("") || choose_tag.equals("全部") || choose_tag_pos == 0) {
//            if (currentTag.equals("0")) {
//                map.put("c", c1);
//            } else if (currentTag.equals("1")) {
//                map.put("c", c2);
//            } else if (currentTag.equals("2")) {
//                map.put("c", c3);
//            }
//        } else {
//            if (currentTag.equals("0")) {
//                map.put("c", String.valueOf(childTagList1.get(choose_tag_pos - 1).getId()));
//            } else if (currentTag.equals("1")) {
//                map.put("c", String.valueOf(childTagList2.get(choose_tag_pos - 1).getId()));
//            } else if (currentTag.equals("2")) {
//                map.put("c", String.valueOf(childTagList3.get(choose_tag_pos - 1).getId()));
//            }
//        }

//        if (!search_content.equals("")) {
//            map.put("n", search_content);
//        }
//        http://10.10.10.88:8080/scm/hyh/order?count=0&limit=10&startDate=20170301&endDate=20170501

        map.put("limit", "10");
        map.put("count", String.valueOf((currentPage - 1) * 10));
        map.put("startDate", start_time);
        map.put("endDate", end_time);
        map.put("s",state_str);
        getReOrders(map, new GsonResHandler<Order_Re>() {
            @Override
            public void onFailed(int statusCode, String errMsg) {
                Log.e("refresh:orders", errMsg);
            }

            @Override
            public void onSuccess(int statusCode, Order_Re response) {
                      if(dialog.isShowing()){
                          dialog.dismiss();
                      }
                if (response.getStatus() == 1) {
                    if (response.getData().size() == 0) {
                        ll_nothing.setVisibility(View.VISIBLE);
                        data_refresh.clear();
                        refreshListView(data_refresh);
                    } else {
                        ll_nothing.setVisibility(View.GONE);
                        try {
                            common.logResult(response.getData().size() + "");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                springView.onFinishFreshAndLoad();
                        data_refresh.clear();
                        data_refresh = response.getData();
                        refreshListView(data_refresh);
                    }
                } else {
                    try {
                        common.logResult("status:" + response.getStatus());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }

    //刷新列表辅助
    public void refreshListView(List list) {
        adapter.setData(list);
        adapter.notifyDataSetChanged();
        common.setListViewHeightBasedOnChildren(mlist, adapter);
    }

    @SuppressLint("WrongConstant")
    public void showTimePicker() {
        //日期选择回调
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调

                if (title.equals("开始时间")) {
                    tv_start.setText(getDate(date));
                    start_time = getDate(date);
                } else {
                    tv_end.setText(getDate(date));
                    end_time = getDate(date);
                }
                refresh();
            }
        })
                .setTitleText(title)//标题文字
                .setSubmitColor(Color.parseColor("#16cce2"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#16cce2"))//取消按钮文字颜色
                .setType(new boolean[]{true, true, true, false, false, false})// 年月日
                .build();

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();


        //正确设置方式 原因：注意事项有说明
        startDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
        endDate.set(startDate.get(Calendar.YEAR), startDate.get(Calendar.MONTH), startDate.get(Calendar.DAY_OF_MONTH));
        pvTime.setDate(startDate);
        pvTime.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                title = "开始时间";
                showTimePicker();
                break;
            case R.id.tv_end:
                title = "结束时间";
                showTimePicker();
                break;
            case R.id.iv2:
//                spinner.setVisibility(View.VISIBLE);
                if (popup.isShowing()) {
                    popup.dismiss();
                    setBackgroundAlpha(this, 1.0f);
                } else {
//                     popup.showAsDropDown(findViewById(R.id.rl), 0, 0);
//
                    popup.showAsDropDown(view);
                    //将popupWindow显示在制定位置，在这里作为mTvSet组件的下拉组件显示出来
                    popup.showAtLocation(findViewById(R.id.rl),
                            Gravity.CENTER, 0, 12);
                    setBackgroundAlpha(this, 0.5f);
                }
                break;
            default:
                break;
        }
    }

    public String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        Log.e("date", str);
        return str;
    }

    public String getDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String str = formatter.format(date);
        Log.e("date", str);
        return str;
    }

    public void popup() {
        //
        View root = this.getLayoutInflater().inflate(R.layout.popup, null);
        // 创建PopupWindow对象
        popup = new PopupWindow(root, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //点击空白处时，隐藏掉pop窗口
        popup.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#ffffff"));
        popup.setBackgroundDrawable(dw);
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(Detail_xq.this, 1.0f);
            }
        });
        lv_choose = (ListView) root.findViewById(R.id.list_pop);
        Adapter_state adapter_state = new Adapter_state(Detail_xq.this, items);
        lv_choose.setAdapter(adapter_state);
        lv_choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                MyToast.makeText(Detail_xq.this, items[i], Toast.LENGTH_SHORT).show();
//                tv_state.setText(items[i]);
                dialog.show();
                changeCurrentState(items[i]);
                popup.dismiss();
                refresh();
            }
        });
        common.setListViewHeightBasedOnChildren(lv_choose, adapter_state);

    }

    /**
     * 设置页面的透明度
     *
     * @param bgAlpha 1表示不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        if (bgAlpha == 1) {
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
        } else {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
        }
        activity.getWindow().setAttributes(lp);
    }

    public static void getReOrders(HashMap map, IResponseHandler handler) {
        HttpCore.get(URL.GETRE_ORDERS, map, handler);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(dialog.isShowing()){
            dialog.dismiss();
        }
        this.finish();
    }
}
