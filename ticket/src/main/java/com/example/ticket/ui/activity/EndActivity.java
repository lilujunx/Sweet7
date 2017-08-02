
package com.example.ticket.ui.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.ui.adapter.AdapterVpEndActivity;

import library.utils.AnimatorString;
import xutils.common.util.DensityUtil;


public class EndActivity extends TBaseActivity implements View.OnClickListener {
    private TextView mTvCurrentEndActivity;
    private TextView mTvHotEndActivity;
    private TextView mTvAllEndActivity;
    private TextView mTvTranslateActivityMain;
    private ViewPager mVpEndActivity;
    private AdapterVpEndActivity mAdapterVpEndActivity;
    private String[] mTitles={"所有","热门","最近"};


    @Override
    public int initRootLayout() {
        return R.layout.activity_end;
    }

    @Override
    public void initViews() {
        mTvCurrentEndActivity = (TextView) findViewById(R.id.tv_current_EndActivity);
        mTvHotEndActivity = (TextView) findViewById(R.id.tv_hot_EndActivity);
        mTvAllEndActivity = (TextView) findViewById(R.id.tv_all_EndActivity);
        mTvTranslateActivityMain = (TextView) findViewById(R.id.tv_translate_activity_main);
        mVpEndActivity = (ViewPager) findViewById(R.id.vp_EndActivity);
        mTvCurrentEndActivity.setOnClickListener(this);
        mTvHotEndActivity.setOnClickListener(this);
        mTvAllEndActivity.setOnClickListener(this);
    }

    @Override
    public void initDatas() {
        mAdapterVpEndActivity=new AdapterVpEndActivity(mFragmentManager);
        mVpEndActivity.setAdapter(mAdapterVpEndActivity);
        mVpEndActivity.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                doTranslate(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mVpEndActivity.setOffscreenPageLimit(2);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTvTranslateActivityMain.getLayoutParams();
        layoutParams.width = DensityUtil.getScreenWidth() / 3;
        mTvTranslateActivityMain.setLayoutParams(layoutParams);
    }

    private void doTranslate(final int pos) {
        mVpEndActivity.setCurrentItem(pos);
        ObjectAnimator objectAnimator=
                ObjectAnimator.ofFloat(
                        mTvTranslateActivityMain,
                        AnimatorString.translationX,mTvTranslateActivityMain.getX(),DensityUtil.getScreenWidth()/3*pos);
        objectAnimator.setDuration(100);
        objectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mTvTranslateActivityMain.setText(mTitles[pos]);
            }
        });
        objectAnimator.start();
    }
    @Override
    public void initOthers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
//        setTitleCenter("选择终点");
//        setTitleLeft("", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mActivitySelf.finish();
//            }
//        });
    }

    @Override
    public boolean isUseTitleBar() {
        return false;
    }

    @Override
    public void onClick(View v) {
        int pos=0;
        switch (v.getId()) {
            case R.id.tv_all_EndActivity:
                pos=0;
                break;
            case R.id.tv_hot_EndActivity:
                pos=1;
                break;
            case R.id.tv_current_EndActivity:
                pos=2;
                break;
        }
        doTranslate(pos);
    }
}
