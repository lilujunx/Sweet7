package com.example.ticket.base;

import android.content.Context;

import com.example.ticket.R;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import cn.smssdk.SMSSDK;
import library.base.BaseApp;
import library.base.TitleBarConfig;

/**
 * Created by Administrator on 2017/1/4.
 */

public class TBaseApp extends BaseApp {
    public static Context mContextGlobal;


    @Override
    public void initOthers() {
        mContextGlobal=this;
        SMSSDK.initSDK(this, "1ada57eae6564", "774a332f62d51fba64f7f749ed664ed9");
        PlatformConfig.setQQZone("1105705865", "WaNdUxq0IlPLxDoy");
        PlatformConfig.setSinaWeibo("3835917172", "ff97c9c57e4691ac25c3bcfb9d23d768");
        UMShareAPI.get(this);

    }

    @Override
    public void initTitleBar() {
        TitleBarConfig.titleBarResID= R.layout.titlebar;
    }

    @Override
    public boolean isDebugMode() {
        return true;
    }
}
