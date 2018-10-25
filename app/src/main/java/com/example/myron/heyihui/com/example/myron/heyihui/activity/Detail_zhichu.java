package com.example.myron.heyihui.com.example.myron.heyihui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.Adapter_state;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.MyAdapter_sr;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.MyAdapter_zc;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.DensityUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Detail_zhichu extends AppCompatActivity implements View.OnClickListener {
   SpringView springView;
   private List<String> data_refresh = new ArrayList<>();
   ListView mlist;
   MyAdapter_zc adapter;
//筛选时间；
    private TextView tv_start,tv_end;
    private String start_time,end_time,title;
    TimePickerView pvTime;
    Calendar startDate,endDate;
    //spinner
    Spinner spinner;
    private ImageView iv_choose;
    //状态
    private String[] items = { "本月", "上月" , "本季" , "本年" , "本周"};
    private ListView lv_choose;
    private ArrayAdapter<String> adapter_choose;
    private String state = "本月";
    private TextView tv_state;
      PopupWindow popup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_zhichu);
        common.changeTitle(this,"支出");
        BindView();
        common.goBack(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        common.createData(data_refresh);
        refreshList();
    }

    private void BindView() {

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
                        refresh();
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
        tv_state = (TextView) findViewById(R.id.tv_state);
        tv_start = (TextView) findViewById(R.id.tv_start);
        tv_end = (TextView) findViewById(R.id.tv_end);
        tv_start.setOnClickListener(this);
        tv_end.setOnClickListener(this);
        start_time = getCurrentDate();
        end_time = getCurrentDate();
        //tv 的一些初始值；
        tv_start.setText(start_time); //开始
        tv_end.setText(end_time);//结束
        tv_state.setText(state);//筛选时间 默认本月
        //list
        mlist = (ListView) findViewById(R.id.lv);
        adapter = new MyAdapter_zc(this,data_refresh);
        mlist.setAdapter(adapter);
        common.setListViewHeightBasedOnChildren(mlist,adapter);

        iv_choose = (ImageView) findViewById(R.id.iv2);
        iv_choose.setOnClickListener(this);
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

        popup();
    }
    //模拟数据生成
    public void refresh(){
        common.createData(data_refresh);
        refreshList();
    }
    //刷新list
    public void refreshList(){
        common.setListViewHeightBasedOnChildren(mlist,adapter);
        adapter.notifyDataSetChanged();
    }
    //picker初始化和监听；
    @SuppressLint("WrongConstant")
    public void showTimePicker(){
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date,View v) {//选中事件回调
                if (title.equals("开始时间"))
                tv_start.setText(getDate(date));
                else
                    tv_end.setText(getDate(date));
            }
        })
                .setTitleText(title)//标题文字
                .setTitleColor(Color.parseColor("#3d3d3d"))
                .setSubmitColor(Color.parseColor("#16cce2"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#16cce2"))//取消按钮文字颜色
                .setType(new boolean[]{true, true, true, false, false, false})// 年月日
                .build();

        startDate = Calendar.getInstance();
        endDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        startDate.set(startDate.get(Calendar.YEAR) ,startDate.get(Calendar.MONTH),startDate.get(Calendar.DAY_OF_MONTH));
        endDate.set(startDate.get(Calendar.YEAR) ,startDate.get(Calendar.MONTH),startDate.get(Calendar.DAY_OF_MONTH));
        pvTime.setDate(startDate);
        pvTime.show();
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
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
                if(popup.isShowing()){
                     popup.dismiss();
                    setBackgroundAlpha(this,1.0f);
                }else {
//                     popup.showAsDropDown(findViewById(R.id.rl), 0, 0);
//
                    popup.showAsDropDown(view);
                    //将popupWindow显示在制定位置，在这里作为mTvSet组件的下拉组件显示出来
                    popup.showAtLocation(findViewById(R.id.rl),
                            Gravity.CENTER, 0, 12);
                    setBackgroundAlpha(this,0.5f);
                }
                break;
            default:
                break;
        }
    }
    public String getCurrentDate(){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        Log.e("date",str);
        return str;
    }
    public String getDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat ("yyyy-MM-dd");
        String str = formatter.format(date);
        Log.e("date",str);
        return str;
    }
    public void popup() {
        //
        View root = this.getLayoutInflater().inflate(R.layout.popup, null);
        // 创建PopupWindow对象
        popup = new PopupWindow(root, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        ColorDrawable dw = new ColorDrawable(Color.parseColor("#ffffff"));
        //点击空白处时，隐藏掉pop窗口
        popup.setFocusable(true);
        popup.setBackgroundDrawable(dw);
//        popup.setBackgroundDrawable(new BitmapDrawable());
        popup.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(Detail_zhichu.this,1.0f);
            }
        });
        lv_choose = (ListView) root.findViewById(R.id.list_pop);
        Adapter_state adapter_state = new Adapter_state(Detail_zhichu.this,items);
        lv_choose.setAdapter(adapter_state);
        lv_choose.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MyToast.makeText(Detail_zhichu.this,items[i], Toast.LENGTH_SHORT).show();
                tv_state.setText(items[i]);
                popup.dismiss();
            }
        });
        common.setListViewHeightBasedOnChildren(lv_choose,adapter_state);

    }
    /**
     * 设置页面的透明度
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
}
