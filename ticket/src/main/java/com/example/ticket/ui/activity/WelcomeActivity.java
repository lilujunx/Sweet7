package com.example.ticket.ui.activity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.ui.fragment.PauseFragment;
import com.example.ticket.ui.fragment.WelcomeFragment;
import com.example.ticket.utils.RequstPermissionsUtil;

import library.utils.SharedPrefrencesUtil;

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

public class WelcomeActivity extends TBaseActivity{
    private boolean mIsFirst;
    private WelcomeFragment mWelcomeFragment = new WelcomeFragment();
    private PauseFragment mPauseFragment = new PauseFragment();
    private String[] PERMISSIONS = new String[]{
            READ_CONTACTS, READ_PHONE_STATE, WRITE_EXTERNAL_STORAGE,
              READ_SMS, ACCESS_FINE_LOCATION, GET_TASKS
    };

    @Override
    public int initRootLayout() {
        return R.layout.activity_welcome;
    }

    @Override
    public void initViews() {

    }

    @Override
    protected void onStart() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= 23) {
            //检查系统是否已经授予了指定权限
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, PERMISSIONS[0]);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                ActivityCompat.requestPermissions(this,PERMISSIONS, 110);
                return;
            } else {
                System.out.println("已经授予了");
            }
        }
    }

    @Override
    public void initDatas() {



    }

    @Override
    protected void onResume() {
        super.onResume();
        mIsFirst = SharedPrefrencesUtil.getData(this, "x", "isFirst", true);
        if (mIsFirst) {
            addFrag(R.id.welcome_activity, mWelcomeFragment);
            SharedPrefrencesUtil.saveData(this, "x", "isFirst", false);
        } else {
            addFrag(R.id.welcome_activity, mPauseFragment);
        }
    }

    @Override
    public void initOthers() {

    }

    @Override
    public boolean isUseTitleBar() {
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
