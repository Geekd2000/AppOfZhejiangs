package com.example.appofzhejiang.pay;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.appofzhejiang.MainActivity;

public class OrderFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGER_COUNT = 4;
    private OrderFragment1 orderFragment1 = null;
    private OrderFragment2 orderFragment2 = null;
    private OrderFragment3 orderFragment3 = null;
    private OrderFragment4 orderFragment4 = null;

    public OrderFragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        orderFragment1 = new OrderFragment1("全部");
        orderFragment2 = new OrderFragment2("待付款");
        orderFragment3 = new OrderFragment3("已付款");
        orderFragment4 = new OrderFragment4("已完成");
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case MainActivity.PAGE_ONE:
                fragment = orderFragment1;
                break;
            case MainActivity.PAGE_TWO:
                fragment = orderFragment2;
                break;
            case MainActivity.PAGE_THREE:
                fragment = orderFragment3;
                break;
            case MainActivity.PAGE_FOUR:
                fragment = orderFragment4;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

}
