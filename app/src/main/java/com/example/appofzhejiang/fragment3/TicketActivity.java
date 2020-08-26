package com.example.appofzhejiang.fragment3;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.appofzhejiang.MainActivity;
import com.example.appofzhejiang.R;
<<<<<<< HEAD
import com.example.appofzhejiang.fragment1.recyclerpage.RecyclerPageActivity;
=======
import com.example.appofzhejiang.StatusBarUtil.StatusBarUtil;
import com.example.appofzhejiang.coolweather.gson.Now;
import com.example.appofzhejiang.recyclerpage.RecyclerPageActivity;
import com.example.appofzhejiang.xihu.more;
>>>>>>> 3c9bbdde65df78e0ff718c43d76954956b5fb80e
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private IndicatorTabBar mIndicatorTabBar;
    private List<String> tableNames = Arrays.asList("门票", "酒店", "包租车", "导游预约", "农家乐", "寻美食", "特产购买");
    private List<Fragment> fragmentList;
    private Toolbar mBack;
    private String value;
    private int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        mViewPager = findViewById(R.id.viewpager);
        mIndicatorTabBar = findViewById(R.id.indicatorTabBar);
        mBack = findViewById(R.id.toolbar);
        //返回按钮
        mBack.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        //设置沉浸式
        StatusBarUtil.setTransparent(this);
        StatusBarUtil.setDarkFont(this);

        intFragmentList();

        mViewPager.setAdapter(new MyViewPager(getSupportFragmentManager(), fragmentList));
        mIndicatorTabBar.initView(tableNames, mViewPager, 5);

        //取得从上一个Activity当中传递过来的Intent对象
        Intent intent = getIntent();
        //从Intent当中根据key取得value
        if (intent != null) {
            value = intent.getStringExtra("num");
            index = Integer.parseInt(value);
        }
        mViewPager.setCurrentItem(index);
    }

    /**
     * 初始化Fragment
     */
    private void intFragmentList() {
        fragmentList = new ArrayList<>();
        for (int i = 0; i < tableNames.size(); i++) {
///           FragmentFactory fragment = FragmentFactory.newInstance(tableNames.get(i));
            FragmentFactory fragment = FragmentFactory.newInstance(i);
            fragmentList.add(fragment);
        }
    }


    class MyViewPager extends FragmentPagerAdapter {

        private List<Fragment> fragmentList;

        public MyViewPager(FragmentManager fm, List<Fragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int i) {
            return fragmentList.get(i);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
}