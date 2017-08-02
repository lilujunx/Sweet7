package com.example.ticket.ui.fragment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ticket.R;
import com.example.ticket.base.TBaseFragment;
import com.example.ticket.ui.activity.MainActivity;


import java.util.Timer;
import java.util.TimerTask;

import library.utils.AnimatorString;
import xutils.common.util.DensityUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class PauseFragment extends TBaseFragment {
    private ImageView mImgvPauseFragment;
    private TextView mTvPauseFragment;
    private Timer mTimer = new Timer();
    private int time = 4;
    private RelativeLayout mRootPauseFragment;
    private ObjectAnimator mObjectAnimator;
    private Handler mHandler = new Handler();

    @Override
    public int initRootLayout() {
        return R.layout.fragment_pause;
    }

    @Override
    public void initViews() {
        mRootPauseFragment = (RelativeLayout) findViewById(R.id.root_pause_fragment);
        mImgvPauseFragment = (ImageView) findViewById(R.id.imgv_pause_fragment);
        mTvPauseFragment = (TextView) findViewById(R.id.tv_pause_fragment);
        Glide.with(mActivitySelf).load(R.drawable.pause).into(mImgvPauseFragment);
        mTvPauseFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTimer.cancel();
                mHandler = null;
                mActivitySelf.hideFrag(PauseFragment.this);
                mObjectAnimator.start();
            }
        });

    }

    @Override
    public void initDatas() {
        mObjectAnimator =
                ObjectAnimator.ofFloat(
                        mRootPauseFragment,
                        AnimatorString.translationX, 0, -DensityUtil.getScreenWidth());
        mObjectAnimator.setDuration(1500);
        mObjectAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                mActivitySelf.hideFrag(PauseFragment.this);

            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                goToActivity(MainActivity.class);
            }
        });
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                //子线程
                mActivitySelf.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //主线程
                        if (time > 0) {
                            mTvPauseFragment.setText("剩 余 " + time + " 秒");
                            time--;
                        }
                        if (time == 0) {
                            mObjectAnimator.start();
                            time--;
                            mTimer.cancel();
                        }
                    }
                });
            }
        }, 0, 1000);

    }

    @Override
    public boolean isUseTitleBar() {
        return false;
    }

}
