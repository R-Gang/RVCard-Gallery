package com.gang.rvcardgallery.ui;

import android.os.Bundle;

import com.gang.rvcardgallery.R;
import com.gang.rvcardgallery.base.BaseActivity;
import com.gang.rvcardgallery.widget.CircleViewLayout;

/**
 * Created by jameson.hua on 2015/3/30.
 */
public class AnimActivity2 extends BaseActivity {

    private CircleViewLayout mCircleViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim2);

        init();
    }

    private void init() {
        mCircleViewLayout = findViewById(R.id.circle_view);
        mCircleViewLayout.startPlay();
    }
}
