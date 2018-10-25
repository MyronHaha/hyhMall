package com.example.myron.heyihui.com.example.myron.heyihui.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Product;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.productSerilize;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.Detail_Product;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.SplashActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.adapter.ProductItemAdapter;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.example.myron.heyihui.com.example.myron.heyihui.view.CustomDialog;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.IResponseHandler;
import com.mph.okdroid.response.JsonResHandler;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * Created by Myron on 2017/10/23.
 */

public class ProductFragment2 extends Fragment implements View.OnClickListener {
    public static Context context;
    public List<String> titileList = new ArrayList<>();
    View head,nomore;
    //pagerTag
    private ImageView line_tab;
    private int moveOne = 0; // 下划线移动一个选项卡
    private static TextView title1, title2, title3;
    //筛选tag
    public static TagFlowLayout mFlowLayout;
    private List<String> tagList, tagList1, tagList2, tagList3;//实际装载的数据
    private static List<productSerilize.Data> allTagList, parentTagList, childTagList1, childTagList2, childTagList3;
    private TagAdapter<String> tagAdapter;
    private static String c1, c2, c3;
    private static String choose_tag = "";//所选标签
    private static int choose_tag_pos = 0;//所选标签位置
    //搜索框
    private EditText et_search;
    private static String search_content = "";
    //listview

    //    ArrayAdapter<String> adapter;// 测试
//    ProductAdapter1 adapter;
    ProductItemAdapter adapter;
    XRecyclerView recyclerView;
    LinearLayoutManager manager;
    public static ArrayList<Product.Data> data_refresh = new ArrayList<>();
    private ArrayList<Product.Data> data_more = new ArrayList<>();
    String currentTag = "0";
    public static int currentPage = 1;
    //
    public static final int GETTAGDATA = 1;
    CustomDialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_productfragment2, container, false);
        return view;
    }

    private void bindView() {
        head = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.layout_product_head, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
//        nodata = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.layout_nodata, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        nomore = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.layout_nomore, (ViewGroup) getActivity().findViewById(android.R.id.content), false);
        context = getActivity().getApplicationContext();
        parentTagList = new ArrayList<>(); // 头tag
        allTagList = new ArrayList<>();// 所有tag
        childTagList1 = new ArrayList<>();//子tag1
        childTagList2 = new ArrayList<>();//子tag2
        childTagList3 = new ArrayList<>();//子tag3
        //筛选
        tagList = new ArrayList<>();
        tagList1 = new ArrayList<>();
        tagList2 = new ArrayList<>();
        tagList3 = new ArrayList<>();
        tagList1.add("全部");
        tagList2.add("全部");
        tagList3.add("全部");
        titileList.add("医疗耗材");
        titileList.add("医疗器械");
        titileList.add("非医疗器械");
        title1 = (TextView) head.findViewById(R.id.tv5);
        title2 = (TextView) head.findViewById(R.id.tv2);
        title3 = (TextView) head.findViewById(R.id.tv3);
        title1.setOnClickListener(this);
        title2.setOnClickListener(this);
        title3.setOnClickListener(this);
        line_tab = (ImageView) head.findViewById(R.id.cursor);
        dialog = new CustomDialog(getActivity(), R.style.CustomDialog, "加载中");
        movePositionX(0);
        setTextColor(0);

        mFlowLayout = (TagFlowLayout) head.findViewById(R.id.id_flowlayout);
        mFlowLayout.setFocusable(false);
        mFlowLayout.setFocusableInTouchMode(false);
        mFlowLayout.setMaxSelectCount(1);
        //筛选
        et_search = (EditText) head.findViewById(R.id.et_search);
        et_search.setFocusable(true);
        et_search.setFocusableInTouchMode(true);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        bindView();
        initLineImage();
        dialog.show();
        getTagData(new GsonResHandler<productSerilize>() {
            @Override
            public void onFailed(int i, String s) {
                try {
//                    common.logResult(s);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onSuccess(int i, productSerilize productSerilize) {

                try {
                    common.logResult(productSerilize.getMessage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (productSerilize.getStatus() == 1) {
                    allTagList = productSerilize.getData();
                    for (productSerilize.Data data : productSerilize.getData()) {
                        if (data.getParentId() == 0) {

                            parentTagList.add(data);
                            Log.e("tagname", data.getName() + "");
                        }
                    }
                    if (parentTagList.size() >= 3) {
                        c1 = String.valueOf(parentTagList.get(0).getId());
                        c2 = String.valueOf(parentTagList.get(1).getId());
                        c3 = String.valueOf(parentTagList.get(2).getId());
                        Log.e("c", c1 + "," + c2 + "," + c3);
                    }

                    initData();//获取第一页
                    createChildTagList();
//                    Log.e("parentlistSize:", parentTagList.size() + "");
//                    Log.e("tag2Size:", tagList2.size() + "");

                    title1.setText(parentTagList.get(0).getName());
                    title2.setText(parentTagList.get(1).getName());
                    title3.setText(parentTagList.get(2).getName());
                }
            }
        });


        //设置数据；
        mFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int i, FlowLayout flowLayout) {
                if (view.isPressed()) {
                }
                return false;
            }
        });
        //  标签回调；
        mFlowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {

            @Override
            public void onSelected(Set<Integer> selectPosSet) {
                // 清空页数；
                currentPage = 1;
                if (selectPosSet.size() == 0) {
                    // 0 时报错；
                } else {
                    int value = (int) selectPosSet.toArray()[0];
                    choose_tag_pos = value;
                    if (currentTag.equals("0")) {
                        choose_tag = tagList1.get(value);
                    } else if (currentTag.equals("1")) {
                        choose_tag = tagList2.get(Integer.valueOf(value));
                    } else if (currentTag.equals("2")) {
                        choose_tag = tagList3.get(Integer.valueOf(value));
                    }
//                    MyToast.makeText(getActivity().getApplicationContext(), choose_tag + ""+selectPosSet.toString(), Toast.LENGTH_SHORT).show();
                    refresh();
                }
            }
        });


        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(final Editable editable) {
//                Log.e("textChange", editable.toString());
//                Message msg = handler.obtainMessage();
                search_content = editable.toString();
//                msg.obj = search_content;
//                msg.what = 0;
//                handler.sendMessage(msg);
                refresh();
            /*
            * 接口筛选*/


            }
        });

        //recyclerView
        recyclerView = (XRecyclerView) getActivity().findViewById(R.id.recyle);
        manager = new LinearLayoutManager(getContext().getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new ProductItemAdapter(getActivity(), data_refresh);
        recyclerView.setAdapter(adapter);
        recyclerView.addHeaderView(head);
//        recyclerView.setEmptyView(nodata);


        adapter.setOnItemClickListener(new ProductItemAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                MyToast.makeText(getActivity().getApplicationContext(),""+position,Toast.LENGTH_SHORT).show();
                common.launchActivityWithBean(getActivity(), Detail_Product.class, data_refresh.get(position-2));
            }
        });
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                refresh();
            }

            @Override
            public void onLoadMore() {
                loadMore();
            }
        });
    }


    //在tag 获取成功时调用；
    private void initData() {
        currentPage = 1;
        final HashMap map = new HashMap();
        map.put("c", c1);
        map.put("limit", "10");
        map.put("count", "0");
        getProducts(map, new GsonResHandler<Product>() {
            @Override
            public void onFailed(int statusCode, String errMsg) {
//                Log.e("failed to get products ", errMsg);
            }

            @Override
            public void onSuccess(int statusCode, Product response) {
                dialog.dismiss();
                if (response.getStatus() == 1) {
//                    Log.e("status_getpro", response.getStatus() + "");
//                    springView.onFinishFreshAndLoad();
                    data_refresh.clear();
                    data_refresh = (ArrayList<Product.Data>) response.getData();
                    adapter.setList(data_refresh);
                    adapter.notifyDataSetChanged();
                } else {
                    try {
                        common.logResult("status!=1");
                        if (response.getStatus() == 403) {
                            HashMap map2 = new HashMap();
                            String phone = common.SP_Read(getActivity().getApplicationContext(), "phone");
                            String k = common.SP_Read(getActivity().getApplicationContext(), "k");
                            Log.e("phone" + phone + "," + "k" + k, "");
                            map2.put("phone", phone);
                            map2.put("p", k);
                            SplashActivity.loginRequest(map2, new JsonResHandler() {
                                @Override
                                public void onFailed(int i, String s) {

                                }

                                @Override
                                public void onSuccess(int statusCode, JSONObject response) {
                                    super.onSuccess(statusCode, response);
                                    try {
                                        if (response.getInt("status") == 1) {
                                            MyToast.makeText(getActivity().getApplicationContext(), "已为你自动登录,正在重新加载...", Toast.LENGTH_LONG).show();
                                            initData();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    /**
     * 重新设定line的宽度
     */
    private void initLineImage() {
        /** * 获取屏幕的宽度 */
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels; /** * 重新设置下划线的宽度 */
        ViewGroup.LayoutParams lp = line_tab.getLayoutParams();
        lp.width = screenW / 3;
        line_tab.setLayoutParams(lp);
        moveOne = lp.width; // 滑动一个页面的距离
    }

    // tag 移动 位置 0,1,2   linetab positon
    private void movePositionX(int toPosition) {
        // TODO Auto-generated method stub
        float curTranslationX = line_tab.getTranslationX();
        float toPositionX = moveOne * toPosition;
        ObjectAnimator animator = ObjectAnimator.ofFloat(line_tab, "translationX", curTranslationX, toPositionX);
        animator.setDuration(200);
        animator.start();
    }

    // title 颜色；
    public void setTextColor(int pos) {
        TextView[] titleArray = {title1, title2, title3};
        for (int i = 0; i < titileList.size(); i++) {
            if (i == pos) {
                Log.e("currentCLick", pos + "");
                titleArray[i].setTextColor(getActivity().getResources().getColor(R.color.colorPrimary));
            } else {
                titleArray[i].setTextColor(getActivity().getResources().getColor(R.color.text));
            }
        }
    }

    //  切换，底部和筛选数据触发更改；
    /*1.ui改变，两个接口数据 tag 和 listview*/
    @Override
    public void onClick(View view) {
        //切换时，当前页面currentpage 清空，已选标签清空choose_tag和位置；
        currentPage = 1;
        choose_tag = "";
        choose_tag_pos = 0;

        if (view.getId() == R.id.tv5) {
//            mPager.setCurrentItem(0);
            Log.e("tv1", "11111111");
            currentTag = "0";
            tagList.clear();
//            createTagDate(0);//生成模拟数据
            refreshTagList(tagList1);
//list 刷新
            data_refresh.clear();
            refresh();
            refreshListView(data_refresh);
//样式位置
            movePositionX(0);
            setTextColor(0);
        } else if (view.getId() == R.id.tv2) {
            currentTag = "1";
            tagList.clear();
            refreshTagList(tagList2);
            //list 刷新
            data_refresh.clear();
            refresh();
            refreshListView(data_refresh);

//样式位置
            movePositionX(1);
            setTextColor(1);
        } else if (view.getId() == R.id.tv3) {
//            mPager.setCurrentItem(2);
            currentTag = "2";
            tagList.clear();
//            createTagDate(2);
            refreshTagList(tagList3);
            //list 刷新
            data_refresh.clear();
            refresh();
            refreshListView(data_refresh);
//样式位置
            movePositionX(2);
            setTextColor(2);

        }

    }

    //加载更多
    public void loadMore() {
        //新增数据  loadmore
        currentPage++;
        data_more.clear();
        HashMap map = new HashMap();
        //默认全部
        if (choose_tag.equals("") || choose_tag.equals("全部") || choose_tag_pos == 0) {
            if (currentTag.equals("0")) {
                map.put("c", c1);
            } else if (currentTag.equals("1")) {
                map.put("c", c2);
            } else if (currentTag.equals("2")) {
                map.put("c", c3);
            }
        } else {
            if (currentTag.equals("0")) {
                map.put("c", String.valueOf(childTagList1.get(choose_tag_pos - 1).getId()));
            } else if (currentTag.equals("1")) {
                map.put("c", String.valueOf(childTagList2.get(choose_tag_pos - 1).getId()));
            } else if (currentTag.equals("2")) {
                map.put("c", String.valueOf(childTagList3.get(choose_tag_pos - 1).getId()));
            }
        }
        if (!search_content.equals("")) {
            map.put("n", search_content);
        }

        map.put("limit", "10");
        map.put("count", String.valueOf((currentPage - 1) * 10));
        getProducts(map, new GsonResHandler<Product>() {
            @Override
            public void onFailed(int statusCode, String errMsg) {
//                Log.e("loadmore:pro", errMsg);
            }

            @Override
            public void onSuccess(int statusCode, Product response) {
                recyclerView.refreshComplete();
                if (response.getStatus() == 1) {
                    if(response.getData().size()==0){
                        recyclerView.setNoMore(true);
                    }
                    Log.e("listmore", response.getData().size() + "");
//                springView.onFinishFreshAndLoad();
                    data_more.clear();
                    data_more = (ArrayList<Product.Data>) response.getData();
                    data_refresh.addAll(data_more);
                    adapter.notifyDataSetChanged();
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
        //默认全部
        if (choose_tag.equals("") || choose_tag.equals("全部") || choose_tag_pos == 0) {
            if (currentTag.equals("0")) {
                map.put("c", c1);
            } else if (currentTag.equals("1")) {
                map.put("c", c2);
            } else if (currentTag.equals("2")) {
                map.put("c", c3);
            }
        } else {
            if (currentTag.equals("0")) {
                map.put("c", String.valueOf(childTagList1.get(choose_tag_pos - 1).getId()));
            } else if (currentTag.equals("1")) {
                map.put("c", String.valueOf(childTagList2.get(choose_tag_pos - 1).getId()));
            } else if (currentTag.equals("2")) {
                map.put("c", String.valueOf(childTagList3.get(choose_tag_pos - 1).getId()));
            }
        }

        if (!search_content.equals("")) {
            map.put("n", search_content);
        }

        map.put("limit", "10");
        map.put("count", String.valueOf((currentPage - 1) * 10));
        getProducts(map, new GsonResHandler<Product>() {
            @Override
            public void onFailed(int statusCode, String errMsg) {
//                Log.e("refresh:pro", errMsg);
            }

            @Override
            public void onSuccess(int statusCode, Product response) {
                recyclerView.refreshComplete();
                if (response.getStatus() == 1) {
                    Log.e("status", response.getStatus() + "");
//                springView.onFinishFreshAndLoad();

                    data_refresh.clear();
                    data_refresh = (ArrayList<Product.Data>) response.getData();
                    adapter.setList(data_refresh);
                    adapter.notifyDataSetChanged();
                    if (response.getData().size() == 0) {
                        MyToast.makeText(getActivity(), "暂无相关内容", Toast.LENGTH_SHORT).show();
                        et_search.getText().clear();
                        et_search.requestFocus();
                    }else{
                       et_search.clearFocus();
                    }

                } else {
                    try {
                        common.logResult("status!=1");
                        if (response.getStatus() == 403) {
                            HashMap map2 = new HashMap();
                            String phone = common.SP_Read(getActivity().getApplicationContext(), "phone");
                            String k = common.SP_Read(getActivity().getApplicationContext(), "k");
                            Log.e("phone" + phone + "," + "k" + k, "");
                            map2.put("phone", phone);
                            map2.put("p", k);
                            SplashActivity.loginRequest(map2, new JsonResHandler() {
                                @Override
                                public void onFailed(int i, String s) {

                                }

                                @Override
                                public void onSuccess(int statusCode, JSONObject response) {
                                    super.onSuccess(statusCode, response);
                                    try {
                                        if (response.getInt("status") == 1) {
                                            MyToast.makeText(getActivity().getApplicationContext(), "已为你自动登录,正在重新加载...", Toast.LENGTH_LONG).show();
                                            refresh();
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }

    //ui线程辅助；
    static Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    MyToast.makeText(context, msg.obj.toString(), Toast.LENGTH_SHORT).show();
                    break;
                case GETTAGDATA:

                    break;
            }
        }
    };

    // 初始化子类标签 同时加载第一页子类
    //分类
    public void createChildTagList() {
        if (allTagList.size() > 0) {
            for (productSerilize.Data tag : allTagList) {
                if (tag.getParentId() == parentTagList.get(0).getId()) {
                    tagList1.add(tag.getName());
                    childTagList1.add(tag);
                } else if (tag.getParentId() == parentTagList.get(1).getId()) {
                    tagList2.add(tag.getName());
                    childTagList2.add(tag);
                } else if (tag.getParentId() == parentTagList.get(2).getId()) {
                    tagList3.add(tag.getName());
                    childTagList3.add(tag);
                }
            }
        } else {
            Log.e("tagSize", "null");
        }
        if (currentTag.equals("0")) {
            Log.e("size", tagList2.size() + "");
//            tagAdapter.notifyDataChanged();
            refreshTagList(tagList1);
        }
    }

    //刷新标签辅助
    public void refreshTagList(List tag) {
        TagAdapter ta = new TagAdapter<String>(tag) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                View veiw = (TextView) LayoutInflater.from(getContext()).inflate(R.layout.layout_flowitem,
                        mFlowLayout, false);
                TextView tv = (TextView) veiw.findViewById(R.id.tag);
                tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
                tv.setText(s);
                return tv;
            }
        };
        mFlowLayout.setAdapter(ta);
        ta.setSelectedList(0);
        ta.notifyDataChanged();
    }

    //刷新列表辅助
    public void refreshListView(List list) {
        adapter.notifyDataSetChanged();
    }


    //获取全部标签
    public void getTagData(IResponseHandler handler) {
        HttpCore.get(URL.GETTAG, handler);
    }

    //获取产品列表  c参数区分产品类别
    public void getProducts(HashMap map, IResponseHandler handler) {
        HttpCore.get(URL.GETPRODUCTS, map, handler);
    }


}
