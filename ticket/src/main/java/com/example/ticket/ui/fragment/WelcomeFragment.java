package com.example.ticket.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.ticket.R;
import com.example.ticket.base.TBaseFragment;
import com.example.ticket.ui.activity.MainActivity;
import com.example.ticket.ui.adapter.VpWelcomeFragment;
import com.example.ticket.utils.RequstPermissionsUtil;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.GET_TASKS;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

/**
 * A simple {@link Fragment} subclass.
 */
public class WelcomeFragment extends TBaseFragment {
    private ViewPager mVpWelcomeFragement;
    private Button mBtGoWelcomeFragment;
    private PauseFragment mPauseFragment=new PauseFragment();
    private int[] mDatas = {R.drawable.welcome_1, R.drawable.welcome_2, R.drawable.welcome_3};

    @Override
    public int initRootLayout() {
        return R.layout.fragment_welcome;
    }

    @Override
    public void initViews() {
        mVpWelcomeFragement = (ViewPager) findViewById(R.id.vp_welcome_fragement);
        mBtGoWelcomeFragment = (Button) findViewById(R.id.bt_go_welcome_fragment);


    }

    @Override
    public void initDatas() {

        initVp();
initBt();

    }

    @Override
    public boolean isUseTitleBar() {
        return false;
    }

    private void initBt() {
        mBtGoWelcomeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.hideFrag(WelcomeFragment.this);
                mActivitySelf.addFrag(R.id.welcome_activity, mPauseFragment);
            }
        });
    }

    private void initVp() {
        VpWelcomeFragment vpWelcomeFragment = new VpWelcomeFragment(mActivitySelf, mDatas);
        mVpWelcomeFragement.setAdapter(vpWelcomeFragment);
        mVpWelcomeFragement.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                if (mVpWelcomeFragement.getCurrentItem() == 2) {
                    mBtGoWelcomeFragment.setVisibility(View.VISIBLE);
                }else{
                    mBtGoWelcomeFragment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
