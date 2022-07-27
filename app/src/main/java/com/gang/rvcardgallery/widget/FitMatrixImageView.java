package com.gang.rvcardgallery.widget;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by jameson.hua on 2015/4/4.
 */
public class FitMatrixImageView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private boolean mOnce;
    private Matrix mMatrix;

    public FitMatrixImageView(Context context) {
        this(context, null);
    }

    public FitMatrixImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FitMatrixImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setScaleType(ScaleType.MATRIX);
        mMatrix = new Matrix();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getViewTreeObserver().removeGlobalOnLayoutListener(this);
        // .removeOnGlobalLayoutListener(this);
    }

    @Override
    public void onGlobalLayout() {
        if (getDrawable() == null || mOnce) {
            return;
        }
        mOnce = true;

        Drawable d = getDrawable();
        int width = getWidth();
        int height = getHeight();
        // 拿到图片的宽和高
        int dw = d.getIntrinsicWidth();
        int dh = d.getIntrinsicHeight();

        System.out.println(getId() + " width========" + getWidth() + ", " + dw);
        System.out.println(getId() + " height=======" + getHeight() + ", " + dh);

        float scaleX = width * 1.0f / dw;
        float scaleY = height * 1.0f / dh;

        mMatrix.postTranslate((width - dw) / 2, (height - dh) / 2);
        mMatrix.postScale(scaleX, scaleX, width / 2, height / 2);
        setImageMatrix(mMatrix);

    }
}
