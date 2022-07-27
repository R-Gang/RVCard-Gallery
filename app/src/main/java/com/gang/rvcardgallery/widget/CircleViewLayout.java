package com.gang.rvcardgallery.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gang.library.common.utils.UExtKt;

/**
 * Created by jameson.hua on 2015/3/31.
 */
public class CircleViewLayout extends RelativeLayout {

    public static final int DEFAULT_RADIUS = 60;
    public static final int ANIM_DURATION = 1000;
    public static final float SCALE_INNER = 0.6f;
    public static final float SCALE_OUT = 2f;
    public static final int DEFAULT_NUM = 3;

    private int mRadius = DEFAULT_RADIUS;
    private int mCircleColor = 0xFF4FCB1D;
    private int mTextColor = 0xFFFFFFFF;
    private float mTextSize = 30f;
    private int mCount = DEFAULT_NUM;

    private CircleView2 mCircleView;
    private TextView mCountView;

    private Animation mInnerScaleAnimation;
    private AnimationSet mAnimSet;

    public CircleViewLayout(Context context) {
        super(context);
        init(context, null);
    }

    public CircleViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CircleViewLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mTextSize = UExtKt.sp2px(mTextSize);

        // LayoutParams circleParams = new LayoutParams(2 * mRadius, 2 * mRadius);
        LayoutParams circleParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        circleParams.addRule(CENTER_IN_PARENT, TRUE);

        mCircleView = new CircleView2(context);
        addView(mCircleView, circleParams);

        LayoutParams textParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textParams.addRule(CENTER_IN_PARENT, TRUE);

        mCountView = new TextView(context);
        mCountView.setTextColor(mTextColor);
        mCountView.setTextSize(mTextSize);
        mCountView.setText(String.valueOf(mCount));
        addView(mCountView, textParams);

        initAnim();
    }

    private Handler mAnimHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mCountView.setText(String.valueOf(mCount));

            if (mCount == 1) {
                mCircleView.startAnimation(mAnimSet);
            } else {
                mCircleView.startAnimation(mInnerScaleAnimation);
            }

            mCount--;
            if (mCount > 0) {
                startPlay();
            } else {
                removeCallbacksAndMessages(null);
            }
        }
    };

    public void startPlay() {
        mAnimHandler.sendEmptyMessageDelayed(mCount, ANIM_DURATION);
    }

    private void initAnim() {
        // 1 ~ n-1帧动画
        mInnerScaleAnimation = new ScaleAnimation(1f, SCALE_INNER, 1f, SCALE_INNER, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        mInnerScaleAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        mInnerScaleAnimation.setDuration(ANIM_DURATION);
        mInnerScaleAnimation.setRepeatCount(0);

        // 最后一帧动画
        mAnimSet = new AnimationSet(true);
        mAnimSet.setInterpolator(new AccelerateDecelerateInterpolator());

        Animation outScaleAnimation = new ScaleAnimation(1f, SCALE_OUT, 1f, SCALE_OUT, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        outScaleAnimation.setDuration(ANIM_DURATION);
        mAnimSet.addAnimation(outScaleAnimation);

        Animation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(ANIM_DURATION);
        mAnimSet.addAnimation(alphaAnimation);
        mAnimSet.setFillAfter(true);

        mAnimSet.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mCountView.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    public class CircleView2 extends View {

        private Paint mCirlcePaint;

        public CircleView2(Context context) {
            super(context);
            init(context, null);
        }

        public CircleView2(Context context, AttributeSet attrs) {
            super(context, attrs);
            init(context, attrs);
        }

        public CircleView2(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            init(context, attrs);
        }

        private void init(Context context, AttributeSet attrs) {
            mRadius = UExtKt.dip2px(mRadius);

            mCirlcePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mCirlcePaint.setColor(mCircleColor);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            canvas.save();
            canvas.translate(getWidth() / 2, getHeight() / 2);
            canvas.drawCircle(0, 0, mRadius, mCirlcePaint);

            canvas.restore();
        }

    }


}
