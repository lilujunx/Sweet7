package com.example.test;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Administrator on 2017/2/19.
 */

public class AdapterVp extends FragmentPagerAdapter {
    private Fragment[] mFragments={new LeftFragment() ,new RightFragment()};

    public AdapterVp(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments[position];
    }

    @Override
    public int getCount() {
        return mFragments.length;
    }
}
