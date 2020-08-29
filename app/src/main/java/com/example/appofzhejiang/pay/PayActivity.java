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

import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.fragment3.TicketActivity;
import com.example.appofzhejiang.xihu.more;
import com.github.clans.fab.FloatingActionButton;

public class PayActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private TextView mTvAll, mTvPendingPayment, mTvPaid, mTvCompleted;
    private ViewPager viewPager;
    private Toolbar mBtnBack;
    private OrderFragmentPagerAdapter mAdapter;

    //悬浮按钮
    private FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.right_in,R.anim.right_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);

        //初始化悬浮按钮
        initFloatActionButton();
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.right_silent,R.anim.right_out);
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

    //悬浮按钮配置
    public void initFloatActionButton() {
        floatingActionButton1 = findViewById(R.id.floatingActionButton1);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton3 = findViewById(R.id.floatingActionButton3);

        floatingActionButton1.setLabelText("首页");
        floatingActionButton2.setLabelText("景点");
        floatingActionButton3.setLabelText("商城");
        floatingActionButton1.setImageResource(R.drawable.shouye);
        floatingActionButton2.setImageResource(R.drawable.jingdian);
        floatingActionButton3.setImageResource(R.drawable.shangcheng);

        //跳转至首页页面
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //跳转至更多热门景点列表页面（暂无跳转 更多景点页面未写）
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayActivity.this, more.class);
                startActivity(intent);
            }
        });

        //跳转至商品列表页面(默认进入商品列表的门票页面)
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PayActivity.this, TicketActivity.class);
                intent.putExtra("num", "0");
                startActivity(intent);
            }
        });
    }
}