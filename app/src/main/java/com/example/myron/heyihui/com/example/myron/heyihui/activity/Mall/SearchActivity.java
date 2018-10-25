package com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.BaseApp;
import com.example.myron.heyihui.com.example.myron.heyihui.SQL.RecordSQLiteOpenHelper;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.BaseActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.AndroidWorkaround;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.StatusBarUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jason on 2018/10/22.
 */

public class SearchActivity extends BaseActivity {
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.tv_cancel)
    TextView cancel;
    @BindView(R.id.id_flowlayout)
    TagFlowLayout mFlowLayout;
    TagAdapter<String> hisAdapter;
    List<String> hisDatas = new ArrayList<>();
    @BindView(R.id.moreView)
    RecyclerView moreView;
    /*数据库变量*/
    private RecordSQLiteOpenHelper helper;
    private SQLiteDatabase db;


    public SearchActivity(){
        super(R.layout.activity_search);
    }

    @Override
    public void initView() {
        //实例化数据库SQLiteOpenHelper子类对象
        helper = new RecordSQLiteOpenHelper(this);
        initSearchView();
        initFlowLayout();
        initMoreView();
    }

    private void initSearchView() {
        et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //先隐藏键盘
                    ((InputMethodManager) SearchActivity.this.getSystemService(INPUT_METHOD_SERVICE))
                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    String inputContent = et_search.getText().toString();
                    if (inputContent.equals("")) {
                        Toast.makeText(SearchActivity.this, "请输入关键字！", 1000).show();
                    } else {
                        Toast.makeText(SearchActivity.this, inputContent, 1000).show();
                        if (!hasData(inputContent)) {
                            insertData(inputContent);
                            refreshFlowLayout();
                        }
                    }
                    return true;
                }
                return false;
            }
        });

//        et_search.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {
//                    //先隐藏键盘
//                    ((InputMethodManager) SearchActivity.this.getSystemService(INPUT_METHOD_SERVICE))
//                            .hideSoftInputFromWindow(SearchActivity.this.getCurrentFocus()
//                                    .getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                   String inputContent = et_search.getText().toString();
//                    if (inputContent.equals("")) {
//                    } else {
//                        //做相应的操作
//                        Toast.makeText(SearchActivity.this,inputContent,1000).show();
//                    }
//                }
//                return false;
//            }
//        });
    }

    private void initMoreView() {
        List<String> data = new ArrayList<>();
        data.add("一张庄");
        data.add("管理技术看");
        data.add("nijkdjfkfd");
        data.add("分几536456");
        data.add("fdsfsd");
        data.add("一张庄");
        data.add("管理技术看");
        data.add("一张庄");
        data.add("管理技术看");
        data.add("一张庄");
        data.add("管理技术看");
        BaseQuickAdapter<String, BaseViewHolder> adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.layout_item_keytext, data) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String s) {
                baseViewHolder.setText(R.id.tv_keytext, s);
            }
        };
        GridLayoutManager manager = new GridLayoutManager(SearchActivity.this, 2, GridLayoutManager.VERTICAL, false);
        moreView.setLayoutManager(manager);
        moreView.setAdapter(adapter);
    }
//刷新历史搜
    public void refreshFlowLayout() {
        hisDatas.clear();
        hisDatas = queryData();
        hisAdapter = new TagAdapter<String>(hisDatas) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView view = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.layout_hisitem, parent, false);
                view.setText(o);
                return view;
            }
        };
        mFlowLayout.setAdapter(hisAdapter);
        hisAdapter.notifyDataChanged();
    }

    private void initFlowLayout() {
        mFlowLayout.setFocusable(false);
        mFlowLayout.setFocusableInTouchMode(false);
        mFlowLayout.setMaxSelectCount(1);
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int i, FlowLayout flowLayout) {

                return false;
            }
        });
        hisDatas = queryData();
        hisAdapter = new TagAdapter<String>(hisDatas) {
            @Override
            public View getView(FlowLayout parent, int position, String o) {
                TextView view = (TextView) LayoutInflater.from(SearchActivity.this).inflate(R.layout.layout_hisitem, parent, false);
                view.setText(o);
                return view;
            }
        };
        mFlowLayout.setAdapter(hisAdapter);
    }

    @OnClick(R.id.tv_cancel)
    public void cancelSearch() {
        this.finish();
    }

    @OnClick(R.id.delete_his)
    public void deleteHis() {
        deleteData();
        hisDatas.clear();
        hisDatas = queryData();
        hisAdapter.notifyDataChanged();
    }

    @OnClick(R.id.getMore)
    public void getMore() {

    }

    /*插入数据*/
    private void insertData(String tempName) {
        db = helper.getWritableDatabase();
        db.execSQL("insert into records(name) values('" + tempName + "')");
        db.close();
    }

    /*模糊查询数据 */
    private List<String> queryData() {
        List<String> datas = new ArrayList<>();
        Cursor cursor = helper.getReadableDatabase().rawQuery("select id as _id,name from records  order by id desc ", null);
        if (cursor!=null) {//判断数据表里有数据
            while (cursor.moveToNext()) {//遍历数据表中的数据
                String inputContent = cursor.getString(cursor.getColumnIndex("name"));//通过列名“time”获取该列索引，再根据索引获取对应的数据。
                Log.i("TAG", "index=" + cursor.getColumnIndex("name")
                        + ",name=" + inputContent);
                datas.add(inputContent);
            }
            cursor.close();
        }
        return datas;
    }

    /*检查数据库中是否已经有该条记录*/
    private boolean hasData(String tempName) {
        //从Record这个表里找到name=tempName的id
        Cursor cursor = helper.getReadableDatabase().rawQuery(
                "select id as _id,name from records where name =?", new String[]{tempName});
        //判断是否有下一个
        return cursor.moveToNext();
    }

    /*清空数据*/
    private void deleteData() {
        db = helper.getWritableDatabase();
        db.execSQL("delete from records");
        db.close();
    }
}
