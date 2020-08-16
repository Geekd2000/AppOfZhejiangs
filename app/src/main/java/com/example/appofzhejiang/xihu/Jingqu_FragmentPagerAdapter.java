package com.example.appofzhejiang.xihu;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Jingqu_FragmentPagerAdapter extends FragmentPagerAdapter {

    private final int PAGER_COUNT = 4;
    private Jingqu_Fragment jf1;
    private Jingqu_Fragment2 jf2;
    private Jingqu_Fragment3 jf3;
    private Jingqu_Fragment4 jf4;

    public Jingqu_FragmentPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
        jf1 = new Jingqu_Fragment();
        jf2 = new Jingqu_Fragment2();
        jf3 = new Jingqu_Fragment3();
        jf4 = new Jingqu_Fragment4();
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

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case Jingqu_around.PAGE_ONE:
                fragment = jf1;
                break;
            case Jingqu_around.PAGE_TWO:
                fragment = jf2;
                break;
            case Jingqu_around.PAGE_THREE:
                fragment = jf3;
                break;
            case Jingqu_around.PAGE_FOUR:
                fragment = jf4;
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }
}
