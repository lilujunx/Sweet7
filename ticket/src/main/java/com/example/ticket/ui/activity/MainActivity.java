package com.example.ticket.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.base.TBaseFragment;
import com.example.ticket.entity.user.User;
import com.example.ticket.ui.fragment.HomeFragment;
import com.example.ticket.ui.fragment.MineFragment;
import com.example.ticket.ui.fragment.PauseFragment;
import com.example.ticket.ui.fragment.RecordFragment;
import com.example.ticket.ui.fragment.WelcomeFragment;

import library.control.ActivityControl;
import library.utils.SharedPrefrencesUtil;


public class MainActivity extends TBaseActivity implements RadioGroup.OnCheckedChangeListener {
    private RelativeLayout mActivityMain;
    private FrameLayout mContainerActivityMain;
    private RadioGroup mRgActivityMain;
    private RadioButton mRbReserveMainActivity;
    private TBaseFragment showFrag;
    private RadioButton mRbSelectMainActivity;
    private RadioButton mRbMineMainActivity;
    private boolean mIsFirst;
    private boolean mIsLogin;
    private String mEnd;
    private boolean mExit;
    private long mTimeBackPress1;
    private User mLoger;

    private HomeFragment mHomeFragment = new HomeFragment();
    private RecordFragment mRecordFragment = new RecordFragment();
    private MineFragment mMineFragment = new MineFragment();
    private TBaseFragment mFragmentAll[] = {mHomeFragment, mRecordFragment, mMineFragment};
    private TextView mTvRbReserveMainActivity;
    private TextView mTvRbRecordMainActivity;
    private TextView mTvRbMineMainActivity;

    public RadioButton getRbReserveMainActivity() {
        return mRbReserveMainActivity;
    }

    public String getEnd() {
        return mEnd;
    }

    public MineFragment getMineFragment() {
        return mMineFragment;
    }

    @Override
    public int initRootLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initViews() {

        mRgActivityMain = (RadioGroup) findViewById(R.id.rg_activity_main);
        mActivityMain = (RelativeLayout) findViewById(R.id.activity_main);
        mContainerActivityMain = (FrameLayout) findViewById(R.id.container_activity_main);
        mRgActivityMain = (RadioGroup) findViewById(R.id.rg_activity_main);
        mRbReserveMainActivity = (RadioButton) findViewById(R.id.rb_reserve_MainActivity);
        mRgActivityMain.setOnCheckedChangeListener(this);
        mRbSelectMainActivity = (RadioButton) findViewById(R.id.rb_select_MainActivity);
        mRbMineMainActivity = (RadioButton) findViewById(R.id.rb_mine_MainActivity);

        mTvRbReserveMainActivity = (TextView) findViewById(R.id.tv_rb_reserve_MainActivity);
        mTvRbRecordMainActivity = (TextView) findViewById(R.id.tv_rb_record_MainActivity);
        mTvRbMineMainActivity = (TextView) findViewById(R.id.tv_rb_mine_MainActivity);
        mRbReserveMainActivity.setChecked(true);

    }

    public User getLoger() {
        return mLoger;
    }

    //扩大Radio点击范围
    @Override
    public void initDatas() {
        Log.e("xx", "选定首页");
        mTvRbReserveMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRbReserveMainActivity.setChecked(true);
            }
        });
        mTvRbRecordMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRbSelectMainActivity.setChecked(true);
            }
        });
        mTvRbMineMainActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRbMineMainActivity.setChecked(true);
            }
        });
    }

    @Override
    public boolean isUseTitleBar() {
        return false;
    }

    //透明状态栏
    @Override
    public void initOthers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        mIsLogin = SharedPrefrencesUtil.getData(mActivitySelf, "x", "isLogin", false);
        switch (checkedId) {
            case R.id.rb_reserve_MainActivity:
                showFrag = mHomeFragment;
                break;
            case R.id.rb_select_MainActivity:

                if (mIsLogin) {
                    showFrag = mRecordFragment;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "record");
                    goToActivity(LoginActivity.class, "go", bundle);

                }
                break;
            case R.id.rb_mine_MainActivity:
                if (mIsLogin) {
                    showFrag = mMineFragment;
                } else {
                    Bundle bundle = new Bundle();
                    bundle.putString("from", "mine");
                    goToActivity(LoginActivity.class, "go", bundle);

                }
                break;
        }
        changeFragHS(showFrag);

    }

    private void changeFragHS(TBaseFragment showFrag) {
        if (!showFrag.isAdded()) {
            addFrag(R.id.container_activity_main, showFrag);
        }
        for (TBaseFragment scBaseFragment : mFragmentAll) {
            if (scBaseFragment != showFrag) {
                hideFrag(scBaseFragment);
            }
        }
        showFrag(showFrag);
    }



        //3秒内点击两次退出
    @Override
    public void onBackPressed() {
        long timeNow = System.currentTimeMillis();
        if (timeNow - mTimeBackPress1 <= 3000) {
            mExit = true;
        } else {
            Toast.makeText(mActivitySelf, "再次点击退出", Toast.LENGTH_SHORT).show();
        }
        mTimeBackPress1 = timeNow;
        if (mExit) {
            ActivityControl.killAll();
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        mIsLogin = SharedPrefrencesUtil.getData(mActivitySelf, "x", "isLogin", false);
        if (!mIsLogin) {
            mRbReserveMainActivity.setChecked(true);
        }
        Bundle back = intent.getBundleExtra("back");
        if (back != null) {
            Log.e("xx", back.getString("from"));
            if (back.getString("from").equals("record")) {      //未登录，点击record，登录完成/未完成后返回
                showFrag = mRecordFragment;
                mLoger= (User) back.getSerializable("loger");
                changeFragHS(showFrag);
            }
            if (back.getString("from").equals("mine")) {
                showFrag = mMineFragment;
                mLoger= (User) back.getSerializable("loger");
                changeFragHS(showFrag);
            }
        } else {
            Log.e("xx", "back为空");
            mRbReserveMainActivity.setChecked(true);
        }
    }




}
