package com.example.appofzhejiang.fragment3;

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
import com.example.appofzhejiang.fragment1.recyclerpage.RecyclerPageActivity;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TicketActivity extends FragmentActivity {
    private ViewPager mViewPager;
    private IndicatorTabBar mIndicatorTabBar;
    private List<String> tableNames = Arrays.asList("门票", "酒店", "包租车", "导游预约", "农家乐", "寻美食", "特产购买");
    private List<Fragment> fragmentList;
    private String value;
    //悬浮按钮
    private FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3;
    private int index=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        mViewPager = findViewById(R.id.viewpager);
        mIndicatorTabBar = findViewById(R.id.indicatorTabBar);

        intFragmentList();

        //初始化悬浮按钮
        initFloatActionButton();

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
    //悬浮按钮配置
    public void initFloatActionButton() {
        floatingActionButton1 = findViewById(R.id.floatingActionButton1);
        floatingActionButton2 = findViewById(R.id.floatingActionButton2);
        floatingActionButton3 = findViewById(R.id.floatingActionButton3);

        floatingActionButton1.setLabelText("首页");
        floatingActionButton2.setLabelText("景点");
        floatingActionButton3.setLabelText("攻略");
        floatingActionButton1.setImageResource(R.drawable.shouye);
        floatingActionButton2.setImageResource(R.drawable.jingdian);
        floatingActionButton3.setImageResource(R.drawable.gonglue);

        //跳转至首页页面
        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //跳转至更多热门景点列表页面（暂无跳转 更多景点页面未写）
        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, RecyclerPageActivity.class);
//                startActivity(intent);
            }
        });

        //跳转至攻略列表页面
        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TicketActivity.this, RecyclerPageActivity.class);
                startActivity(intent);
            }
        });
    }
}