package com.gang.rvcardgallery.ui;

import android.os.Bundle;

import com.gang.rvcardgallery.R;
import com.gang.rvcardgallery.base.BaseActivity;
import com.gang.rvcardgallery.widget.CircleView;

/**
 * Created by jameson.hua on 2015/3/30.
 */
public class AnimActivity extends BaseActivity {

    private CircleView mCircleView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anim);
        
        init();
    }

    private void init() {
        mCircleView = findViewById(R.id.circle_view);
        mCircleView.startPlay();
    }
}
