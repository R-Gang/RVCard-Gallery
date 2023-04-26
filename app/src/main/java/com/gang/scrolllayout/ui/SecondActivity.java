package com.gang.scrolllayout.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gang.rvcardgallery.R;
import com.gang.scroll.ScrollLayout;
import com.gang.scrolllayout.model.Address;
import com.gang.scrolllayout.model.Constant;
import com.gang.scrolllayout.viewpager.MainPagerAdapter;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

/**
 * @function 次页
 * @auther: Created by yinglan
 * @time: 16/3/16
 */
public class SecondActivity extends AppCompatActivity {

    private ScrollLayout mScrollLayout;
    private ArrayList<Address> mAllAddressList;
    private TextView mGirlDesText;
    private Toolbar toolbar;

    private MainPagerAdapter.OnClickItemListenerImpl mOnClickItemListener = new MainPagerAdapter.OnClickItemListenerImpl() {
        @Override
        public void onClickItem(View item, int position) {
            if (mScrollLayout.getCurrentStatus() == ScrollLayout.Status.OPENED) {
                mScrollLayout.scrollToClose();
            } else {
                startActivity(new Intent(SecondActivity.this, ThreeActivity.class));
            }
        }
    };

    private ScrollLayout.OnScrollChangedListener mOnScrollChangedListener = new ScrollLayout.OnScrollChangedListener() {
        @Override
        public void onScrollProgressChanged(float currentProgress) {
            if (currentProgress >= 0) {
                float precent = 255 * currentProgress;
                if (precent > 255) {
                    precent = 255;
                } else if (precent < 0) {
                    precent = 0;
                }
                mScrollLayout.getBackground().setAlpha(255 - (int) precent);
                toolbar.getBackground().setAlpha(255 - (int) precent);
            }
        }

        @Override
        public void onScrollFinished(ScrollLayout.Status currentStatus) {
            if (currentStatus.equals(ScrollLayout.Status.EXIT)) {
                finish();
            }
        }

        @Override
        public void onChildScroll(int top) {
        }
    };

    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            mGirlDesText.setText(mAllAddressList.get(position).getDesContent());
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initGirlUrl();
        initView();
    }

    private void initView() {
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        mGirlDesText = (TextView) findViewById(R.id.text_view);
        mScrollLayout = (ScrollLayout) findViewById(R.id.scroll_down_layout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.getBackground().setAlpha(0);
        toolbar.setNavigationIcon(R.mipmap.action_bar_return);
        toolbar.setTitle("ScrollLayout");
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);

        mScrollLayout.setOnScrollChangedListener(mOnScrollChangedListener);
        mScrollLayout.getBackground().setAlpha(0);

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(this);
        mainPagerAdapter.setOnClickItemListener(mOnClickItemListener);
        viewPager.setAdapter(mainPagerAdapter);
        viewPager.setOnPageChangeListener(mOnPageChangeListener);
        mainPagerAdapter.initViewUrl(mAllAddressList);
        mGirlDesText.setText(mAllAddressList.get(0).getDesContent());
        toolbar.setNavigationOnClickListener(v -> onBackPressed());
    }

    private void initGirlUrl() {
        mAllAddressList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Address address = new Address();
            address.setImageUrl(Constant.ImageUrl[i]);
            address.setDesContent(Constant.DesContent[i]);
            mAllAddressList.add(address);
        }
    }

}