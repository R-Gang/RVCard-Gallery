package com.gang.rvcardgallery.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by jameson.hua on 2015/4/4.
 */
public class FitImageView extends AppCompatImageView implements ViewTreeObserver.OnGlobalLayoutListener {

    private boolean mOnce;

    public FitImageView(Context context) {
        this(context, null);
    }

    public FitImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FitImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        setScaleType(ScaleType.CENTER_CROP);
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

        int height = (int) ((float) getWidth() / getDrawable().getMinimumWidth() * getDrawable().getMinimumHeight());
//        if (height > getMaxHeight() && getMaxHeight() > 0) {
//            height = getMaxHeight();
//        }

        ViewGroup.LayoutParams params = getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = height;

        setLayoutParams(params);
    }
}
