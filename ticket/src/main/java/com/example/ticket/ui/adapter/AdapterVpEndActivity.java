package com.example.ticket.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ticket.base.TBaseFragment;
import com.example.ticket.ui.fragment.AllFragment;
import com.example.ticket.ui.fragment.CurrentFragment;
import com.example.ticket.ui.fragment.HotFragment;

/**
 * Created by Administrator on 2017/1/15.
 */

public class AdapterVpEndActivity extends FragmentPagerAdapter {

    private TBaseFragment[] mTBaseFragments={new AllFragment(),new HotFragment(),new CurrentFragment() };

    public AdapterVpEndActivity(FragmentManager fm) {
        super(fm);
    }

    public TBaseFragment[] getTBaseFragments() {
        return mTBaseFragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mTBaseFragments[position];
    }

    @Override
    public int getCount() {
        return mTBaseFragments.length;
    }
}
