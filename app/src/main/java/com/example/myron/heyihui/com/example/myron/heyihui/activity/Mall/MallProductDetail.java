package com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PointF;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.CartProvider;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.HomeProduct;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.MallProduct;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.ProductDetail;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.ProductOrAd;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.MallHome.ShoppingCart;
import com.example.myron.heyihui.com.example.myron.heyihui.Data.Order_Item;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpCore;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.HttpUtils;
import com.example.myron.heyihui.com.example.myron.heyihui.Http.URL;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.BaseActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.LoginActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.MyToast;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.common;
import com.mph.okdroid.response.GsonResHandler;
import com.mph.okdroid.response.JsonResHandler;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Jason on 2018/10/22.
 */

public class MallProductDetail extends BaseActivity {
    @BindView(R.id.iv)
    ImageView iv;
    @BindView(R.id.hotProduct)
    RecyclerView hotProduct;
    BaseQuickAdapter<ProductOrAd.Data, BaseViewHolder> adapter;
    GridLayoutManager manager;
    List<ProductOrAd.Data> datas = new ArrayList();
    @BindView(R.id.subsampling_scale_image_view)
    SubsamplingScaleImageView subsamplingScaleImageView;
    int count_cart = 1; // 保存当前商品选择数量

    //item data
    ProductDetail.Data item = new ProductDetail.Data();
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_catagory)
    TextView tvCatagory;
    @BindView(R.id.tv_danwei)
    TextView tvDanwei;
    @BindView(R.id.tv_brand)
    TextView tvBrand;
    @BindView(R.id.tv_pzwh)
    TextView tvPzwh;
    @BindView(R.id.tv_mt)
    TextView tvMt;
    @BindView(R.id.tv_ctgy)
    TextView tvCtgy;
    @BindView(R.id.favor)
    ImageView favorTag;

    // spec s
    List<ProductDetail.Data.Details> specs = new ArrayList<>();
    ProductDetail.Data.Details selDetail;

    public MallProductDetail() {
        super(R.layout.layout_activity_mall_detail);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    @Override
    public void initView() {
        initToolBar();
        initHotProduct();
    }

    private void initToolBar() {
        common.goBack(this);
        common.changeTitle(this, "产品详情");
        common.hideRightIv(this);
    }

    @Override
    public void initData() {
        super.initData();
        initDetailData();
        initHotDatas();
        initLongPic();
    }

    private void initDetailData() {
        int sourceid = getIntent().getIntExtra("id", -1);
        HashMap map = new HashMap();
        map.put("gid", sourceid + "");
//        map.put("uid","1");
        HttpUtils.getProductDetail(new GsonResHandler<ProductDetail>() {
            @Override
            public void onSuccess(int i, ProductDetail productDetail) {
                if (i == 200) {
                    if (!loginTimeOut(productDetail.getStatus())) {
                        if (productDetail.getData() != null) {
                            item = productDetail.getData();
                            Log.e("id", item.getId() + "");
                            refreshDetailInfo(item);
                            specs.clear();
                            specs.addAll(item.getDetails());
                        }
                    }
                } else {
                    MyToast.makeText(MallProductDetail.this, "请求失败" + i, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(int i, String s) {

            }
        }, map);
    }


    public void refreshDetailInfo(final ProductDetail.Data data) {
        new Handler(getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Log.e("refreshitem,login?", HttpUtils.isIsLogin() + "");
//                if (HttpUtils.isIsLogin()) {
//                    tvPrice.setText("￥" + data.getPrice());
//                } else {
//                    tvPrice.setText("价格登录可见");
//                }

                tvPrice.setText(HttpUtils.isIsLogin() ? ("￥" + data.getPrice()) : "价格登录可见");
                tvName.setText(data.getName());
                tvCatagory.setText(common.isStringNull(data.getLabels()));
                tvDanwei.setText(common.isStringNull(data.getPackUint()));
                tvBrand.setText(common.isStringNull(data.getBrand()));
                tvPzwh.setText(common.isStringNull(data.getWarrant()));
                tvMt.setText(common.isStringNull(data.getCompanyName()));
                tvCtgy.setText(common.isStringNull(data.getCategory()));
                Glide.with(MallProductDetail.this)
                        .load(URL.IMGPATH + data.getImg())
                        .placeholder(R.mipmap.nopictures_bg)//图片加载出来前，显示的图片
                        .error(R.mipmap.nopictures_bg)//图片加载失败后，显示的图片
                        .dontAnimate()
                        .diskCacheStrategy(DiskCacheStrategy.RESULT)
                        .into(iv);
                //收藏
                if (item.getIsCollect() > 0) {
                    favorTag.setImageResource(R.mipmap.icon_sc_sel);
                } else {
                    favorTag.setImageResource(R.mipmap.icon_sc);
                }
                //long img
                if (!TextUtils.isEmpty(item.getRemark())) {
                    Glide.with(MallProductDetail.this).load(URL.IMGPATH + getLongImgPath()).downloadOnly(new SimpleTarget<File>() {
                        @Override
                        public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                            float initImageScale = getInitImageScale(resource.getAbsolutePath());
                            subsamplingScaleImageView.setMaxScale(initImageScale + 2.0f);//最大显示比例
                            subsamplingScaleImageView.setParallelLoadingEnabled(true);
                            subsamplingScaleImageView.setImage(ImageSource.uri(resource.getAbsolutePath()),
                                    new ImageViewState(initImageScale, new PointF(0, 0), 0));
                        }
                    });
                }
            }
        });

    }

    private void initLongPic() {
//        subsamplingScaleImageView.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        subsamplingScaleImageView.setMinScale(1.0F);//最小显示比例
        subsamplingScaleImageView.setZoomEnabled(false);
        subsamplingScaleImageView.setFocusableInTouchMode(false);
        subsamplingScaleImageView.setFocusable(false);
        subsamplingScaleImageView.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                return false;
            }
        });
        subsamplingScaleImageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
//        subsamplingScaleImageView.setNestedScrollingEnabled(false);
        //        subsamplingScaleImageView.setMaxScale(1.0f);//最大显示比例
//        File file = new File(saveFilePath);
//// 将图片文件给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
//// ImageViewState的三个参数为：scale,center,orientation
//        subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(file)),new ImageViewState(1.0f, new PointF(0, 0), 0));
        Glide.with(MallProductDetail.this).load(URL.IMGPATH+item.getRemark()).downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                float initImageScale = getInitImageScale(resource.getAbsolutePath());
                subsamplingScaleImageView.setMaxScale(initImageScale + 2.0f);//最大显示比例
                subsamplingScaleImageView.setImage(ImageSource.uri(resource.getAbsolutePath()),
                        new ImageViewState(initImageScale, new PointF(0, 0), 0));
            }
        });
//        "https://img.tuguaishou.com/ips_templ_small/5f/b6/39/sm_832084_1534065677_5b6ffc0d1fb0b.jpg?auth_key=1540569600-0-0-32a4fe073f02de26f352dee0c80e91a2"
//        float initImageScale = getInitImageScale(saveFilePath);
//        subsamplingScaleImageView.setMaxScale(initImageScale + 2.0f);//最大显示比例
//        subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(file)),new ImageViewState(initImageScale, new PointF(0, 0), 0));

    }

    private void initHotDatas() {
//        datas.clear();
//        for (int i = 0; i < 10; i++) {
//            datas.add("热门产品" + i);
//        }
//        adapter.notifyLoadMoreToLoading();
        HashMap map = new HashMap();
        map.put("hot", "2");
        map.put("count","0");
        map.put("limit","6");
        HttpUtils.getHomeData(new GsonResHandler<ProductOrAd>() {
            @Override
            public void onSuccess(int i, ProductOrAd productOrAd) {
                if (i == 200) {
                    if (productOrAd.getData().size() > 0) {
                        datas.clear();
                        datas.addAll(productOrAd.getData());
                        adapter.notifyDataSetChanged();
                    }
                } else {
                    MyToast.makeText(MallProductDetail.this, "请求失败" + i, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailed(int i, String s) {

            }
        }, map);
    }

    private void initHotProduct() {
        manager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        adapter = new BaseQuickAdapter<ProductOrAd.Data, BaseViewHolder>(R.layout.item_hot_product, datas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, ProductOrAd.Data s) {
                baseViewHolder.setText(R.id.name, s.getName());
                baseViewHolder.setText(R.id.price, HttpUtils.isIsLogin() ? "￥" + s.getPrice() : "价格登录可见");
                Glide.with(MallProductDetail.this)
                        .load(URL.IMGPATH + s.getImg())
                        .placeholder(R.mipmap.nopictures_bg)
                        .error(R.mipmap.nopictures_bg)
                        .into((ImageView) baseViewHolder.getView(R.id.iv));
                //边距
                if (baseViewHolder.getAdapterPosition() == 0) {
                    GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) baseViewHolder.itemView.getLayoutParams();
                    params.setMargins(10, params.topMargin, params.rightMargin, params.bottomMargin);
                    baseViewHolder.itemView.setLayoutParams(params);
//                    baseViewHolder.itemView.setPadding(12,0,0,0);

                }

            }
        };
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Intent intent = new Intent(MallProductDetail.this, MallProductDetail.class);
                intent.putExtra("id", Integer.parseInt(datas.get(i).getSourceParam()));
                common.launchActivityWithIntent(MallProductDetail.this, intent);
            }
        });
        hotProduct.setLayoutManager(manager);
        hotProduct.setAdapter(adapter);
        hotProduct.setHasFixedSize(true);
        hotProduct.setNestedScrollingEnabled(false);
        hotProduct.setOverScrollMode(View.OVER_SCROLL_NEVER);
        hotProduct.setFocusableInTouchMode(false);
        hotProduct.setFocusable(false);
    }

    /**
     * 计算出图片初次显示需要放大倍数
     *
     * @param imagePath 图片的绝对路径
     */
    public float getInitImageScale(String imagePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        WindowManager wm = this.getWindowManager();
        int width = wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        // 拿到图片的宽和高
        int dw = bitmap.getWidth();
        int dh = bitmap.getHeight();
        float scale = 1.0f;
        //图片宽度大于屏幕，但高度小于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh <= height) {
            scale = width * 1.0f / dw;
        }
        //图片宽度小于屏幕，但高度大于屏幕，则放大图片至填满屏幕宽
        if (dw <= width && dh > height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都小于屏幕，则放大图片至填满屏幕宽
        if (dw < width && dh < height) {
            scale = width * 1.0f / dw;
        }
        //图片高度和宽度都大于屏幕，则缩小图片至填满屏幕宽
        if (dw > width && dh > height) {
            scale = width * 1.0f / dw;
        }
        return scale;
    }


    private int getEtCount(EditText et) {
        int result;
        try {
            result = Integer.parseInt(et.getText().toString());
        } catch (Exception ex) {
            return 0;
        }
        return result;
    }

    public ShoppingCart toCartData() {
//        MallProduct.Data product = new MallProduct.Data();
//        product.setId(i);
//        product.setPrice("2354.00");
//        product.setSpec("ACU h2d 型");
//        product.setBrand("合壹汇牌");
//        product.setBtype(1);
//        product.setCatalog("类别");
//        product.setCompanyName("公司");
//        product.setName("商品商品商品商品" + product.getId());
        ShoppingCart cart = new ShoppingCart();
        selDetail.setQuantity(count_cart + "");
        Log.e("detailId----", selDetail.getId() + "");
        return cart.toShoppinfCart(item, selDetail);
    }

    @OnClick(R.id.chooseParmas)
    public void showChooseDialog() {
        if (HttpUtils.isIsLogin()) {
            final AlertDialog dialog = new AlertDialog.Builder(this).create();
            View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null);
            final TextView price = (TextView) view.findViewById(R.id.tv_price);
            final TextView specSel = (TextView) view.findViewById(R.id.tv_selected);
            price.setText("￥" + item.getPrice());
            specSel.setText("已选规格：" + "");
//            new Handler(getMainLooper()).post(new Runnable() {
//                @Override
//                public void run() {
//                    price.setText("￥" + item.getPrice());
//                    specSel.setText("已选规格：" + "");
//                }
//            });
            ImageView iv = (ImageView) view.findViewById(R.id.iv);
            Glide.with(MallProductDetail.this)
                    .load(URL.IMGPATH + item.getImg())
                    .placeholder(R.mipmap.nopictures_bg)
                    .error(R.mipmap.nopictures_bg)
                    .into(iv);
            view.findViewById(R.id.close_dialog).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (dialog.isShowing()) {
                        dialog.dismiss();
                    }
                }
            });
//            RecyclerView ggView = (RecyclerView) view.findViewById(R.id.rv_gg);
//            ggView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
            TagFlowLayout flowLayout = view.findViewById(R.id.id_flowlayout);
            final TagAdapter adapter = new TagAdapter<ProductDetail.Data.Details>(specs) {
                @Override
                public View getView(FlowLayout flowLayout, int i, ProductDetail.Data.Details o) {
                    TextView tv = (TextView) LayoutInflater.from(MallProductDetail.this).inflate(R.layout.item_gg, flowLayout, false);
                    tv.setText(o.getSpec());
                    if (o.getSelect()) {
                        tvPrice.setText(HttpUtils.isIsLogin() ? ("￥" + o.getPrice()) : "价格登录可见");
                        tv.setBackgroundResource(R.drawable.bg_gg_sel);
                        price.setText("￥" + o.getPrice());
                        specSel.setText("已选规格：" + o.getSpec());
                        tv.setTextColor(Color.parseColor("#16cce2"));
                    } else {
                        tv.setBackgroundResource(R.drawable.bg_gg_unsel);
                        tv.setTextColor(Color.parseColor("#242529"));
                    }

//                    if (i == 0 && i.getPosition() % 3 == 1) {
//                        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) baseViewHolder.itemView.getLayoutParams();
//                        params.setMargins(12, params.topMargin, params.rightMargin, params.bottomMargin);
//                        baseViewHolder.itemView.setLayoutParams(params);
//                    }
                    return tv;
                }
            };
//            flowLayout.setFocusable(false);
//            flowLayout.setFocusableInTouchMode(false);
            flowLayout.setMaxSelectCount(1);
            flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int i, FlowLayout flowLayout) {
//                    searchToList(hisDatas.get(i));
                    for (int a = 0; a < specs.size(); a++) {
                        if (a == i) {
                            if (specs.get(i).getSelect()) {  //取消
                                specs.get(i).setSelect(false);
                                selDetail = null;
                            } else {
                                specs.get(a).setSelect(true);
                                selDetail = specs.get(a);
                            }
                        } else {
                            specs.get(a).setSelect(false);
                        }

                    }
                    adapter.notifyDataChanged();
                    return false;
                }
            });
            flowLayout.setAdapter(adapter);
            adapter.notifyDataChanged();
//            ggView.setLayoutManager(new GridLayoutManager(dialog.getContext(), 3, GridLayoutManager.VERTICAL, false));
//            final BaseQuickAdapter adapter = new BaseQuickAdapter<ProductDetail.Data.Details, BaseViewHolder>(R.layout.item_gg, specs) {
//                @Override
//                protected void convert(BaseViewHolder baseViewHolder, ProductDetail.Data.Details o) {
//                    baseViewHolder.setText(R.id.tv_tag, o.getSpec());
//                    //初始化
////                    price.setText("￥" + item.getPrice());
////                    specSel.setText("已选规格：" + "");
//                    //选了规格刷新信息价格等
//                    TextView tv = (TextView) baseViewHolder.getView(R.id.tv_tag);
//                    if (o.getSelect()) {
//                        tv.setBackgroundResource(R.drawable.bg_gg_sel);
//                        price.setText("￥" + o.getPrice());
//                        specSel.setText("已选规格：" + o.getSpec());
//                        tv.setTextColor(Color.parseColor("#16cce2"));
//                    } else {
//                        tv.setBackgroundResource(R.drawable.bg_gg_unsel);
//                        tv.setTextColor(Color.parseColor("#242529"));
//                    }
//
//                    if (baseViewHolder.getAdapterPosition() == 0 && baseViewHolder.getPosition() % 3 == 1) {
//                        GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) baseViewHolder.itemView.getLayoutParams();
//                        params.setMargins(12, params.topMargin, params.rightMargin, params.bottomMargin);
//                        baseViewHolder.itemView.setLayoutParams(params);
//                    }
//                }
//            };
//            ggView.setAdapter(adapter);
//            adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
//                    for (int a = 0; a < specs.size(); a++) {
//                        if (a == i) {
//                            if (specs.get(i).getSelect()) {  //取消
//                                specs.get(i).setSelect(false);
//                                selDetail = null;
//                            } else {
//                                specs.get(a).setSelect(true);
//                                selDetail = specs.get(a);
//                            }
//                        } else {
//                            specs.get(a).setSelect(false);
//                        }
//
//                    }
//                    adapter.notifyDataSetChanged();
//
//                }
//            });
//            ggView.setHasFixedSize(true);
//            ggView.setNestedScrollingEnabled(true);
//            ggView.setOverScrollMode(View.OVER_SCROLL_NEVER);
//            ggView.setFocusable(false);
//            ggView.setFocusableInTouchMode(false);
            dialog.show();
            dialog.setContentView(view);
            dialog.setCancelable(true);
            Window window = dialog.getWindow();
            dialog.getWindow().setBackgroundDrawable(null);

            window.setLayout(
                    window.getContext().getResources().getDisplayMetrics().widthPixels,
                    (int) (window.getContext().getResources().getDisplayMetrics().heightPixels * 0.8));
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.dialog_anim);
            window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

            window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

            // add minus
            final EditText etCount = (EditText) view.findViewById(R.id.chang_count);
            etCount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    etCount.setCursorVisible(true);
                    etCount.setSelection(etCount.getText().toString().length());
                }
            });
            etCount.setCursorVisible(false);
            etCount.clearFocus();
            etCount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    try {
                        count_cart = Integer.parseInt(s.toString());
                        if (s.toString().equals("")) {
                            count_cart = 1;
                            etCount.setText(count_cart);
                        }
                    } catch (Exception e) {
                        Log.e("countcart_error", "error");
                    }
                }
            });
            etCount.setText(count_cart + "");
            Button btn_add = (Button) view.findViewById(R.id.bt_add);
            Button btn_minus = (Button) view.findViewById(R.id.bt_minus);
            LinearLayout btn_add_cart = (LinearLayout) view.findViewById(R.id.btn_add_cart);
            LinearLayout btn_submit = (LinearLayout) view.findViewById(R.id.btn_submit);
            btn_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = getEtCount(etCount);
                    count++;
                    final int finalCount = count;
                    count_cart = count;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etCount.setText(String.valueOf(finalCount));
                        }
                    });
                }
            });
            btn_minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int count = getEtCount(etCount);
                    count--;
                    if (count < 0) {
                        count = 0;
                    }
                    final int finalCount = count;
                    count_cart = count;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            etCount.setText(String.valueOf(finalCount));
                        }
                    });
                }
            });
            btn_add_cart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addCart();
                    dialog.dismiss();
                }
            });
            btn_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    submitOrder();
                    dialog.dismiss();
                }
            });
        } else {
            MyToast.makeText(MallProductDetail.this, "你还没登录哦！", Toast.LENGTH_SHORT).show();
            common.launchActivity(MallProductDetail.this, LoginActivity.class);
        }
//        ConstraintLayout et_parent = (ConstraintLayout) view.findViewById(R.id.et_count);
//        final LinearLayout rootView = (LinearLayout) view.findViewById(R.id.rootView);
//        LinearLayout ll = (LinearLayout) view.findViewById(R.id.ll);
//        listenKeyboardLayout(rootView,et_parent);
        //根布局
//        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                int scrollHegit = 0;
//                int[] sc = new int[2];
//                Rect r = new Rect();
//                rootView.getWindowVisibleDisplayFrame(r);
//                if (sc == null) {
//                    sc = new int[2];
//                   et_count.getLocationOnScreen(sc);
//                }
//                //r.top 是状态栏高度
//                int screenHeight = rootView.getRootView().getHeight();
//                int softHeight = screenHeight - r.bottom;
//                if (scrollHegit == 0&&softHeight>120)
//                    scrollHegit = sc[1] +et_count.getHeight() -(screenHeight-softHeight);//可以加个5dp的距离这样，按钮不会挨着输入法
//                if (softHeight > 120) {//当输入法高度大于100判定为输入法打开了  设置大点，有虚拟键的会超过100
//                    if (rootView.getScrollY() != scrollHegit)
//                        scrollToPos(0, scrollHegit,rootView);
//                } else {//否则判断为输入法隐藏了
//                    if (rootView.getScrollY() != 0)
//                        scrollToPos(scrollHegit, 0,rootView);
//                }
//            }
//        });
    }

    @OnClick(R.id.btn_submit)
    public void submitOrder() {
        if (HttpUtils.isIsLogin()) {
            if (selDetail == null) {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.makeText(MallProductDetail.this, "请选择规格！", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            if (count_cart <= 0) {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.makeText(MallProductDetail.this, "请输入数量！", Toast.LENGTH_SHORT).show();
                    }
                });
                return;
            }
            List<ShoppingCart> cartlist = new ArrayList<>();
            cartlist.add(toCartData());
            common.launchActivityWithBean(this, ConfirmOrdersActivity.class, cartlist);
        } else {
            MyToast.makeText(MallProductDetail.this, "你还没登录哦！", Toast.LENGTH_SHORT).show();
            common.launchActivity(MallProductDetail.this, LoginActivity.class);
        }
    }

    @OnClick(R.id.btn_add_cart)
    public void addCart() {
        if (HttpUtils.isIsLogin()) {
            if (selDetail == null) {
                new Handler(getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        MyToast.makeText(MallProductDetail.this, "请选择规格！", Toast.LENGTH_SHORT).show();
                        showChooseDialog();
                    }
                });
                return;
            }
            CartProvider.getCartProvider(this).put(toCartData());
            new Handler(getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    MyToast.makeText(MallProductDetail.this, "添加成功！", Toast.LENGTH_SHORT).show();
                }
            }, 500);
        } else {
            MyToast.makeText(MallProductDetail.this, "你还没登录哦！", Toast.LENGTH_SHORT).show();
            common.launchActivity(MallProductDetail.this, LoginActivity.class);
        }
    }
    @OnClick(R.id.kefu)
    public void kefu(){
        dial("020-37104018");
    }
    @OnClick(R.id.ll_favor)
    public void favorAction() {
        if (HttpUtils.isIsLogin()) {
            if (item.getIsCollect() > 0) {
                HashMap map = new HashMap();
                map.put("id", item.getIsCollect() + "");
                Log.e("取消收藏", "api");

                HttpUtils.delFavor(new JsonResHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        super.onSuccess(statusCode, response);
                        if (statusCode == 200) {
                            try {
                                if (!loginTimeOut(response.getInt("status"))) {
                                    if (response.getInt("status") == 1) {
                                        MyToast.makeText(MallProductDetail.this, "取消成功！", 1000).show();
                                        favorTag.setImageResource(R.mipmap.icon_sc);
                                        item.setIsCollect(0);
                                    } else {
                                        MyToast.makeText(MallProductDetail.this, "取消失败！" + response.getString("message"), 1000).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailed(int i, String s) {

                    }
                }, map);
            } else {
                HashMap map = new HashMap();
                map.put("sid", item.getId() + "");
                map.put("t", "1");
                HttpUtils.setFavor(new JsonResHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        super.onSuccess(statusCode, response);
                        if (statusCode == 200) {
                            try {
                                if (!loginTimeOut(response.getInt("status"))) {
                                    if (response.getInt("status") == 1) {
                                        favorTag.setImageResource(R.mipmap.icon_sc_sel);
                                        MyToast.makeText(MallProductDetail.this, "收藏成功！", 1000).show();
                                        JSONObject data = new JSONObject(response.getString("data"));
                                        item.setIsCollect(data.getInt("id"));
                                    } else {
                                        favorTag.setImageResource(R.mipmap.icon_sc);
                                        MyToast.makeText(MallProductDetail.this, "收藏失败！", 1000).show();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }

                    @Override
                    public void onFailed(int i, String s) {

                    }
                }, map);
            }
        } else {
            MyToast.makeText(MallProductDetail.this, "你还没登录哦！", Toast.LENGTH_SHORT).show();
            common.launchActivity(MallProductDetail.this, LoginActivity.class);
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        HttpCore.okDroid.cancel(URL.PRODUCT_DETAIL);
    }

    public String getLongImgPath() {
        if (item != null) {
            int uploadIndex = item.getRemark().indexOf("uploadFile");
            int prefix = item.getRemark().indexOf(".");
            return item.getRemark().substring(uploadIndex, prefix) + ".jpg";
        } else {
            return "";
        }
    }
}
