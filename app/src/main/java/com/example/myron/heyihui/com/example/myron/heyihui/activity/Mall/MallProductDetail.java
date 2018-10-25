package com.example.myron.heyihui.com.example.myron.heyihui.activity.Mall;

import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PointF;
import android.graphics.Rect;
import android.net.Uri;
import android.support.constraint.ConstraintLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.myron.heyihui.R;
import com.example.myron.heyihui.com.example.myron.heyihui.SQL.RecordSQLiteOpenHelper;
import com.example.myron.heyihui.com.example.myron.heyihui.activity.BaseActivity;
import com.example.myron.heyihui.com.example.myron.heyihui.utils.SoftKeyboardStateHelper;
import com.nineoldandroids.animation.ValueAnimator;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jason on 2018/10/22.
 */

public class MallProductDetail extends BaseActivity {
    @BindView(R.id.hotProduct)
    RecyclerView hotProduct;
    BaseQuickAdapter<String, BaseViewHolder> adapter;
    GridLayoutManager manager;
    List datas = new ArrayList();
    @BindView(R.id.subsampling_scale_image_view)
    SubsamplingScaleImageView subsamplingScaleImageView;

    public MallProductDetail() {
        super(R.layout.layout_activity_mall_detail);
    }

    @Override
    public void initView() {
        initHotProduct();
    }

    @Override
    public void initData() {
        super.initData();
        initHotDatas();
        initLongPic();
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
//        subsamplingScaleImageView.setNestedScrollingEnabled(false);
        //        subsamplingScaleImageView.setMaxScale(1.0f);//最大显示比例
//        File file = new File(saveFilePath);
//// 将图片文件给SubsamplingScaleImageView,这里注意设置ImageViewState设置初始显示比例
//// ImageViewState的三个参数为：scale,center,orientation
//        subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(file)),new ImageViewState(1.0f, new PointF(0, 0), 0));
        Glide.with(MallProductDetail.this).load("https://img.tuguaishou.com/ips_templ_small/5f/b6/39/sm_832084_1534065677_5b6ffc0d1fb0b.jpg?auth_key=1540569600-0-0-32a4fe073f02de26f352dee0c80e91a2").downloadOnly(new SimpleTarget<File>() {
            @Override
            public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                float initImageScale = getInitImageScale(resource.getAbsolutePath());
                subsamplingScaleImageView.setMaxScale(initImageScale + 2.0f);//最大显示比例
                subsamplingScaleImageView.setImage(ImageSource.uri(resource.getAbsolutePath()),
                        new ImageViewState(initImageScale, new PointF(0, 0), 0));
            }
        });
//        float initImageScale = getInitImageScale(saveFilePath);
//        subsamplingScaleImageView.setMaxScale(initImageScale + 2.0f);//最大显示比例
//        subsamplingScaleImageView.setImage(ImageSource.uri(Uri.fromFile(file)),new ImageViewState(initImageScale, new PointF(0, 0), 0));

    }

    private void initHotDatas() {
        datas.clear();
        for (int i = 0; i < 10; i++) {
            datas.add("热门产品" + i);
        }
        adapter.notifyLoadMoreToLoading();
    }

    private void initHotProduct() {
        manager = new GridLayoutManager(this, 1, GridLayoutManager.HORIZONTAL, false);
        adapter = new BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_hot_product, datas) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String s) {

                if (baseViewHolder.getPosition() == 0) {
                    GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) baseViewHolder.itemView.getLayoutParams();
                    params.setMargins(0, params.topMargin, params.rightMargin, params.bottomMargin);
                    baseViewHolder.itemView.setLayoutParams(params);
                }

            }
        };
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

    @OnClick(R.id.chooseParmas)
    public void showChooseDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.layout_dialog, null);
        view.findViewById(R.id.close_dialog).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        RecyclerView ggView = (RecyclerView) view.findViewById(R.id.rv_gg);
        List list = new ArrayList();
        for(int i = 0;i<100;i++){
            list.add("tag tag"+i);
        }
        ggView.setLayoutManager(new GridLayoutManager(dialog.getContext(),3,GridLayoutManager.VERTICAL,false));
        ggView.setAdapter(new BaseQuickAdapter<String,BaseViewHolder>(R.layout.item_gg,list) {
            @Override
            protected void convert(BaseViewHolder baseViewHolder, String o) {
                baseViewHolder.setText(R.id.tv_tag,  o);
                if(baseViewHolder.getPosition() == 0 && baseViewHolder.getPosition()%3 ==1 ){
                    GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) baseViewHolder.itemView.getLayoutParams();
                    params.setMargins(12, params.topMargin, params.rightMargin, params.bottomMargin);
                    baseViewHolder.itemView.setLayoutParams(params);
                }
            }
        });
        ggView.setHasFixedSize(true);
        ggView.setNestedScrollingEnabled(false);
        ggView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        ggView.setFocusable(false);
        ggView.setFocusableInTouchMode(false);
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
        Button btn_add = (Button) view.findViewById(R.id.bt_add);
        Button btn_minus = (Button) view.findViewById(R.id.bt_minus);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = getEtCount(etCount);
                count++;
                final int finalCount = count;
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
                if(count<0){
                    count = 0;
                }
                final int finalCount = count;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        etCount.setText(String.valueOf(finalCount));
                    }
                });
            }
        });
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

    private void initChooseDialog() {
    }

    private void listenKeyboardLayout(final View root, final View scrollToView) {
        SoftKeyboardStateHelper keyboardStateHelper = new SoftKeyboardStateHelper(root);
        keyboardStateHelper.addSoftKeyboardStateListener(new SoftKeyboardStateHelper.SoftKeyboardStateListener() {

            @Override
            public void onSoftKeyboardOpened(int keyboardHeightInPx) {
                Rect rect = new Rect();
                // 获取root在窗体的可视区域
                root.getWindowVisibleDisplayFrame(rect);
                int[] location = new int[2];
                // 获取scrollToView在窗体的坐标
                scrollToView.getLocationInWindow(location);
                // 计算root滚动高度，使scrollToView在可见区域的底部
                int srollHeight = (location[1] + scrollToView.getHeight()) - rect.bottom;
                root.scrollTo(0, srollHeight+80);
            }

            @Override
            public void onSoftKeyboardClosed() {
                // 键盘隐藏
                root.scrollTo(0, 0);

            }
        });
    }

    private void scrollToPos(int start, int end, final View view) {
        ValueAnimator animator = ValueAnimator.ofInt(start, end);
        animator.setDuration(250);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                view.scrollTo(0, (Integer) valueAnimator.getAnimatedValue());
            }
        });
        animator.start();
    }

    private int getEtCount(EditText et){
        int result ;
        try{
           result = Integer.parseInt(et.getText().toString());
        }catch (Exception ex){
            return 0;
        }
     return result;
    }

}
