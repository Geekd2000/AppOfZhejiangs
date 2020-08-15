package com.example.appofzhejiang.pay;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.appofzhejiang.R;

public class PayActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView mTvAll, mTvPendingPayment, mTvPaid, mTvCompleted;
    private ViewPager viewPager;
    private Toolbar mBtnBack;

    private OrderFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);


        mAdapter = new OrderFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        int  id = getIntent().getIntExtra("id",0);
        if (id == 1) {
            viewPager.setCurrentItem(PAGE_TWO);
            mTvPendingPayment.setSelected(true);//进去后选择第一项
        }
        if (id == 2) {
            viewPager.setCurrentItem(PAGE_THREE);
            mTvPaid.setSelected(true);//进去后选择第一项
        }
        if (id == 3) {
            viewPager.setCurrentItem(PAGE_FOUR);
            mTvCompleted.setSelected(true);//进去后选择第一项
        }
        if (id == 0) {
            viewPager.setCurrentItem(PAGE_ONE);
            mTvAll.setSelected(true);//进去后选择第一项
        }
    }

    //重置所有文本的选中状态
    private void setSelected() {
        mTvPaid.setSelected(false);
        mTvPendingPayment.setSelected(false);
        mTvCompleted.setSelected(false);
        mTvAll.setSelected(false);
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        mTvAll = findViewById(R.id.my_order_all);
        mTvCompleted = findViewById(R.id.my_order_completed);
        mTvPaid = findViewById(R.id.my_order_paid);
        mTvPendingPayment = findViewById(R.id.my_order_pending_payment);
        mBtnBack = findViewById(R.id.pay_toolbar);

        mTvAll.setOnClickListener(this);
        mTvCompleted.setOnClickListener(this);
        mTvPaid.setOnClickListener(this);
        mTvPendingPayment.setOnClickListener(this);

        //返回按钮
        mBtnBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.my_order_all:
                viewPager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.my_order_pending_payment:
                viewPager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.my_order_paid:
                viewPager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.my_order_completed:
                viewPager.setCurrentItem(PAGE_FOUR);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (viewPager.getCurrentItem()) {
                case PAGE_ONE:
                    setSelected();
                    mTvAll.setSelected(true);
                    break;
                case PAGE_TWO:
                    setSelected();
                    mTvPendingPayment.setSelected(true);
                    break;
                case PAGE_THREE:
                    setSelected();
                    mTvPaid.setSelected(true);
                    break;
                case PAGE_FOUR:
                    setSelected();
                    mTvCompleted.setSelected(true);
                    break;
            }
        }
    }
}