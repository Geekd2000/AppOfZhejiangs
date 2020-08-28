package com.example.appofzhejiang.xihu;


import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.appofzhejiang.R;
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;

public class Jingqu_around extends AppCompatActivity implements View.OnClickListener, ViewPager.OnPageChangeListener{


    //UI Object
    private TextView Jingqu_jingdian;
    private TextView Jingqu_wc;
    private TextView Jingqu_park;
    private TextView Jingqu_eat;
    private ViewPager viewPager;

    private Toolbar toolbar;


    private Jingqu_FragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    public static final int PAGE_THREE = 2;
    public static final int PAGE_FOUR = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        overridePendingTransition(R.anim.bottom_in, R.anim.bottom_silent);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jingqu_around);
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        mAdapter = new Jingqu_FragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        Jingqu_jingdian.setSelected(true);//进去后选择第一项

        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent, R.anim.bottom_out);
    }

    //UI组件初始化与事件绑定
    private void bindViews() {
        Jingqu_jingdian = findViewById(R.id.Jingqu_jingdian);
        Jingqu_eat = findViewById(R.id.Jingqu_eat);
        Jingqu_park = findViewById(R.id.Jingqu_park);
        Jingqu_wc = findViewById(R.id.Jingqu_wc);

        Jingqu_wc.setOnClickListener(this);
        Jingqu_park.setOnClickListener(this);
        Jingqu_eat.setOnClickListener(this);
        Jingqu_jingdian.setOnClickListener(this);

        viewPager = findViewById(R.id.vPager);
        viewPager.setAdapter(mAdapter);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);
    }

    //重置所有文本的选中状态
    private void setSelected() {
        Jingqu_jingdian.setSelected(false);
        Jingqu_eat.setSelected(false);
        Jingqu_park.setSelected(false);
        Jingqu_wc.setSelected(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.Jingqu_jingdian:
                viewPager.setCurrentItem(PAGE_ONE);
                break;
            case R.id.Jingqu_wc:
                viewPager.setCurrentItem(PAGE_TWO);
                break;
            case R.id.Jingqu_park:
                viewPager.setCurrentItem(PAGE_THREE);
                break;
            case R.id.Jingqu_eat:
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
                    Jingqu_jingdian.setSelected(true);
                    break;
                case PAGE_TWO:
                    setSelected();
                    Jingqu_wc.setSelected(true);
                    break;
                case PAGE_THREE:
                    setSelected();
                    Jingqu_park.setSelected(true);
                    break;
                case PAGE_FOUR:
                    setSelected();
                    Jingqu_eat.setSelected(true);
                    break;
            }
        }
    }
}