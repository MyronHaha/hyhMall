package com.example.myron.heyihui.com.example.myron.heyihui.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.graphics.Typeface;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.OvershootInterpolator;

import com.example.myron.heyihui.R;
import com.nineoldandroids.animation.ValueAnimator;

/**
 * Created by Jason on 2018/10/11.
 */

public class CustomView extends View {


    //画笔
    private Paint paint;
    private RectF oval;


    //圆弧颜色
    private int roundColor;
    //进度颜色
    private int progressColor;
    //文字内容
    private boolean textIsShow;
    //字体大小
    private float textSize = 14;
    //文字颜色
    private int textColor;
    //最大进度
    private int max = 0;
    //当前进度
    private int progress = 0;
    //圆弧宽度
    private int roundWidth = 30;

    private int viewWidth; //宽度--控件所占区域

    private float nowPro = 0;//用于动画

    private ValueAnimator animator;

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs, context);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs, context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(attrs, context);
    }


    private void initAttrs(AttributeSet attr, Context context) {
        TypedArray array = context.obtainStyledAttributes(attr, R.styleable.CustomView);


        roundColor = array.getColor(R.styleable.CustomView_roundColor, Color.BLACK);//环形颜色
        progressColor = array.getColor(R.styleable.CustomView_progressColor, Color.RED);//进度颜色
        textIsShow = array.getBoolean(R.styleable.CustomView_textIsShow, false);//文字
        textSize = array.getDimension(R.styleable.CustomView_textSize, 14);//文字大小
        textColor = array.getColor(R.styleable.CustomView_textColor, Color.BLACK);//文字颜色
        roundWidth = array.getInt(R.styleable.CustomView_roundWidth, 30);//圆环宽度

        array.recycle();

        //动画
        animator = ValueAnimator.ofFloat(0, progress);
        animator.setDuration(1800);
        animator.setInterpolator(new OvershootInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                nowPro = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthSpecMode == MeasureSpec.AT_MOST) {//可获得最大空间
            setMeasuredDimension(widthMeasureSpec, (widthSpecSize / 2) + (int) (Math.cos(20) * (widthSpecSize / 2)));
        } else if (widthMeasureSpec == MeasureSpec.EXACTLY) {//一般指精确值
            setMeasuredDimension(widthMeasureSpec, (widthSpecSize / 2) + (int) (Math.cos(20) * (widthSpecSize / 2)));
        } else {
            setMeasuredDimension(widthMeasureSpec, (viewWidth / 2) + (int) (Math.cos(20) * (viewWidth / 2)));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        viewWidth = w;//得到宽度以此来计算控件所占实际大小

        //计算画布所占区域
        oval = new RectF();
        oval.left = roundWidth + getPaddingLeft();
        oval.top = roundWidth + getPaddingTop();
        oval.right = viewWidth - roundWidth - getPaddingRight();
        oval.bottom = viewWidth - roundWidth - getPaddingBottom();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);                        //设置画笔为无锯齿
        paint.setColor(roundColor);                     //设置画笔颜色
        paint.setStrokeWidth(roundWidth);                //线宽
        paint.setStyle(Paint.Style.STROKE);              //空心
        canvas.drawArc(oval, 160, 220, false, paint);    //绘制圆弧

        //画进度层
        paint.setColor(progressColor);
        paint.setStrokeWidth(roundWidth + 1);
        canvas.drawArc(oval, 160, 220 * nowPro / max, false, paint);  //绘制圆弧


        if (textIsShow) {
            paint.setColor(textColor);
            paint.setStrokeWidth(0);
            paint.setTypeface(Typeface.DEFAULT);
            paint.setTextSize(textSize * 2);
            float textWidth = paint.measureText((int) ((nowPro / (float) max) * 100) + "%");
            canvas.drawText((int) ((nowPro / (float) max) * 100) + "%", viewWidth / 2 - textWidth / 2, viewWidth / 2, paint);
        }

    }


    private int getDefaultHeight() {
        return 0;
    }

    private int getDefaultWidth() {
        return 0;
    }


    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }

    public int getProgressColor() {
        return progressColor;
    }

    public void setProgressColor(int progressColor) {
        this.progressColor = progressColor;
    }

    public boolean getText() {
        return textIsShow;
    }

    public void setText(boolean text) {
        this.textIsShow = text;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    public int getMax() {
        return max;
    }

    public void setMax(int max) {
        this.max = max;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }
}