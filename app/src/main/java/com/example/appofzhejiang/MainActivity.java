package com.example.appofzhejiang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    //UI Object
    private TextView tab_main;
    private TextView tab_flag;
    private TextView tab_location;
    private TextView tab_my;
    private ViewPager viewPager;

    private MyFragmentPagerAdapter mAdapter;


    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*****隐藏系统顶部栏****
         ActionBar actionbar = getSupportActionBar();
         if (actionbar != null) {
         actionbar.hide();
         }*************/

        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        tab_main.setSelected(true);//进去后选择第一项
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        tab_main = findViewById(R.id.tab_main);
        tab_flag = findViewById(R.id.tab_flag);
        tab_location = findViewById(R.id.tab_location);
        tab_my = findViewById(R.id.tab_my);

        tab_main.setOnClickListener(this);
        tab_flag.setOnClickListener(this);
        tab_location.setOnClickListener(this);
        tab_my.setOnClickListener(this);

        viewPager = findViewById(R.id.vPager);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected() {
        tab_main.setSelected(false);
        tab_flag.setSelected(false);
        tab_location.setSelected(false);
        tab_my.setSelected(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_main:
                viewPager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.tab_flag:
                viewPager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.tab_location:
                viewPager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.tab_my:
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
                    tab_main.setSelected(true);
                    break;
                case PAGE_TWO:
                    setSelected();
                    tab_flag.setSelected(true);
                    break;
                case PAGE_THREE:
                    setSelected();
                    tab_location.setSelected(true);
                    break;
                case PAGE_FOUR:
                    setSelected();
                    tab_my.setSelected(true);
                    break;
            }
        }
    }
}