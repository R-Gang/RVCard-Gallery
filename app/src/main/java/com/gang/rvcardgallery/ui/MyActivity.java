package com.gang.rvcardgallery.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gang.rvcardgallery.R;
import com.gang.rvcardgallery.base.BaseActivity;

public class MyActivity extends BaseActivity implements View.OnClickListener {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        init();
    }

    private void init() {
        findViewById(R.id.btn1).setOnClickListener(this);
        findViewById(R.id.btn2).setOnClickListener(this);
        findViewById(R.id.btn3).setOnClickListener(this);
        findViewById(R.id.btn4).setOnClickListener(this);
        findViewById(R.id.btn5).setOnClickListener(this);
        findViewById(R.id.btn6).setOnClickListener(this);

        // findViewById(R.id.btn3).performClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                startActivity(new Intent(MyActivity.this, AnimActivity.class));
                break;

            case R.id.btn2:
                startActivity(new Intent(MyActivity.this, AnimActivity2.class));
                break;

            case R.id.btn3:
                startActivity(new Intent(MyActivity.this, ImageActivity.class));
                break;

            case R.id.btn4:
                startActivity(new Intent(MyActivity.this, ImageActivity2.class));
                break;

            case R.id.btn5:

                break;

            case R.id.btn6:
                startActivity(new Intent(MyActivity.this, MainActivity.class));
                break;

        }
    }
}
