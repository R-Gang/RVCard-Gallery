package com.gang.rvcardgallery.widget;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.gang.library.common.utils.UExtKt;

import java.util.ArrayList;

/**
 * Created by jameson.hua on 2015/3/30.
 */

@TargetApi(11)
public class CircleView extends View {

    public static final int DEFAULT_RADIUS = 50;
    public static final int ANIM_DURATION = 1000;
    public static final float SCALE_INNER = 0.6f;
    public static final float SCALE_OUT = 2f;
    public static final int DEFAULT_NUM = 3;

    private int mRadius = DEFAULT_RADIUS;
    private int mCircleColor = 0xFF4FCB1D;
    private int mTextColor = 0xFFFFFFFF;
    private float mScale = SCALE_INNER;
    private float mTextSize = 40f;
    private Paint mCirlcePaint;
    private Paint mTextPaint;
    private int mNum = DEFAULT_NUM;
    private String mText = String.valueOf(mNum);

    private AnimatorSet animatorSet;

    public CircleView(Context context) {
        super(context);
        init(context, null);
    }

    public CircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mRadius = UExtKt.dip2px(mRadius);
        mTextSize = UExtKt.sp2px(mTextSize);

        mCirlcePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirlcePaint.setColor(mCircleColor);

        mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(mTextColor);
        mTextPaint.setTextSize(mTextSize);

        initAnim();
    }

    private Animator.AnimatorListener mAnimatorListener = new Animator.AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            if (mNum <= 0) {
                mNum = DEFAULT_NUM;
            }

            mText = String.valueOf(--mNum);
            invalidate();
        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    };

    public void startPlay() {
        if (animatorSet != null && !animatorSet.isRunning()) {
            animatorSet.start();
        }
    }

    public void initAnim() {
        animatorSet = new AnimatorSet();
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        ArrayList<Animator> animatorList = new ArrayList<Animator>();
        for (int i = 0; i < DEFAULT_NUM; i++) {

            if (i == DEFAULT_NUM - 1) {
                mScale = SCALE_OUT;
            } else {
                mScale = SCALE_INNER;
            }

            final ObjectAnimator scaleXAnimator = ObjectAnimator.ofFloat(this, "ScaleX", 1.0f, mScale);
            scaleXAnimator.setRepeatCount(0);
            scaleXAnimator.setStartDelay(i * ANIM_DURATION);
            scaleXAnimator.setDuration(ANIM_DURATION);
            animatorList.add(scaleXAnimator);

            final ObjectAnimator scaleYAnimator = ObjectAnimator.ofFloat(this, "ScaleY", 1.0f, mScale);
            scaleYAnimator.setRepeatCount(0);
            scaleYAnimator.setStartDelay(i * ANIM_DURATION);
            scaleYAnimator.setDuration(ANIM_DURATION);
            animatorList.add(scaleYAnimator);

            if (i == DEFAULT_NUM - 1) {
                final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(this, "Alpha", 1.0f, 0f);
                alphaAnimator.setRepeatCount(0);
                alphaAnimator.setStartDelay(2 * ANIM_DURATION);
                alphaAnimator.setDuration(ANIM_DURATION);
                animatorList.add(alphaAnimator);
            }

            scaleXAnimator.addListener(mAnimatorListener);
        }
//        animatorSet.addListener(mAnimatorListener);
        animatorSet.playTogether(animatorList);
    }

    private Handler mAnimHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 3) {

            }
        }
    };

    private void initAnim2() {

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.translate(getWidth() / 2, getHeight() / 2);
        canvas.drawCircle(0, 0, mRadius, mCirlcePaint);

        if (mText != null) {
            Paint.FontMetrics fm = mTextPaint.getFontMetrics();
            //文本的宽度
            float textWidth = mTextPaint.measureText(mText);
            float textCenterVerticalBaselineY = -fm.descent + (fm.descent - fm.ascent) / 2;

            canvas.drawText(mText, -textWidth / 2, textCenterVerticalBaselineY, mTextPaint);
        }

        canvas.restore();
    }

}
