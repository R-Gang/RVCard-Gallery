package com.gang.rvcardgallery.ui;

import android.os.Bundle;
import android.widget.ImageView;

import com.gang.rvcardgallery.R;
import com.gang.rvcardgallery.base.BaseActivity;

/**
 * Created by jameson.hua on 2015/3/30.
 */
public class ImageActivity2 extends BaseActivity {

    private ImageView mImageView1;
    private ImageView mImageView2;
    private ImageView mImageView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image2);

        init();
    }

    private void init() {
        mImageView1 = findViewById(R.id.image1);
        mImageView2 = findViewById(R.id.image2);
        mImageView3 = findViewById(R.id.image3);

        mImageView1.setImageResource(R.drawable.pic1);
        mImageView2.setImageResource(R.drawable.pic2);
        mImageView3.setImageResource(R.drawable.pic7);

    }


}
