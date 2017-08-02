package com.example.ticket.ui.activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.entity.user.User;
import com.example.ticket.ui.inter.IUser;
import com.example.ticket.utils.NetUtil;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import library.utils.SDUtil;
import library.utils.SharedPrefrencesUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends TBaseActivity implements View.OnClickListener {
    private EditText mEditUsernameLoginActivity;
    private EditText mEditUserpwdLoginActivity;
//    private CheckBox mCbRememberpwdLoginActivity;
//    private CheckBox mCbAutologinLoginActivity;
    private ImageView mImgvWechatLoginActivity;
    private ImageView mImgvQqLoginActivity;
    private ImageView mImgvSinaLoginActivity;
    private Button mBtRegLoginActivity;
    private Button mBtLoginLoginActivity;
    private String mUsername;
    private String mUserpwd;
    private String mFrom;
    private Dialog mDig;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mDig.dismiss();
            Toast.makeText(mActivitySelf, "登录成功", Toast.LENGTH_SHORT).show();
            goToActivity(MainActivity.class, "back", mBundle);
        }
    };
    private Bundle mBundle = new Bundle();
    private Map<String, String> mLoginMap = new HashMap<>();
    private Map<String, String> mRegMap = new HashMap<>();
    private Call<User> mUserCall;


    @Override
    public int initRootLayout() {
        return R.layout.activity_login;
    }


    //第三方登录回调接口
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            switch (platform) {
                case QQ:
                    getDetailMsgQQ();
                    break;
                case SINA:
                    getDetailMsgSina();
                    break;
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText(getApplicationContext(), "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(getApplicationContext(), "您取消了授权", Toast.LENGTH_SHORT).show();
        }
    };

    //获取QQ用户详细信息
    public void getDetailMsgQQ() {
        UMShareAPI mShareAPI = UMShareAPI.get(mActivitySelf);
        mShareAPI.getPlatformInfo(mActivitySelf, SHARE_MEDIA.QQ, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, final Map<String, String> map) {

// is_yellow_vip=0, screen_name=。。。。, msg=, vip=0, city=廊坊, gender=男, province=河北, is_yellow_year_vip=0, openid=8E4B46E9FA6CB6CBB067A52904054F34, yellow_vip_level=0, profile_image_url=http://q.qlogo.cn/qqapp/1105705865/8E4B46E9FA6CB6CBB067A52904054F34/100, access_token=554E6281A11A2711C9D475B26B728858, uid=8E4B46E9FA6CB6CBB067A52904054F34, expires_in=7776000, level=0}
                IUser iUser = NetUtil.getRetrofitCall(IUser.class);
                initRegMap("", "", map.get("uid"), map.get("screen_name"));
                Call<User> userCall = iUser.doGet(mRegMap);
                mDig.setContentView(R.layout.dig_wait);
                mDig.setCancelable(false);
                mDig.setCanceledOnTouchOutside(false);
                mDig.show();
                userCall.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {

                        User u = response.body();
                        u.setIcon(map.get("profile_image_url"));
                        saveLoger(u);
                        SharedPrefrencesUtil.saveData(mActivitySelf, "x", "isLogin", true);
                        mBundle.putSerializable("loger", u);
                        mBundle.putString("from", mFrom);
                        Toast.makeText(mActivitySelf, mFrom, Toast.LENGTH_SHORT).show();
                        mHandler.sendEmptyMessageDelayed(1, 1500);
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(mActivitySelf, "大侠你的网有点渣", Toast.LENGTH_SHORT).show();
                        mDig.dismiss();
                    }
                });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(mActivitySelf, "获取错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(mActivitySelf, "取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //存储用户信息
    public void saveLoger(User user){
        try {
            String root= SDUtil.getExternalStoragePath() + "/haokeyun";
            File file=new File(root);
            if(!file.exists()){
                file.mkdirs();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(root+"/x.txt"));
            oos.writeObject(user);
            Log.e("xx","存储用户信息"+SDUtil.getExternalStoragePath() + "/haokeyun/x.txt");
        } catch (IOException e) {
            Toast.makeText(mActivitySelf, "出了点意外", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }
    //获取Sina用户详细信息
    public void getDetailMsgSina() {
        UMShareAPI mShareAPI = UMShareAPI.get(mActivitySelf);
        mShareAPI.getPlatformInfo(mActivitySelf, SHARE_MEDIA.SINA, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
                System.out.println(map);

            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(mActivitySelf, "获取错误", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(mActivitySelf, "取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //初始化登录参数
    public void initLoginMap(String phone, String pwd, String uid, String screen_name) {
        mLoginMap.put("action", "login");
        mLoginMap.put("phone", phone);
        mLoginMap.put("pwd", pwd);
        mLoginMap.put("uid", uid);
        mLoginMap.put("screen_name", screen_name);
    }

    //初始化注册参数
    public void initRegMap(String phone, String pwd, String uid, String screen_name) {
        mRegMap.put("action", "reg");
        mRegMap.put("phone", phone);
        mRegMap.put("pwd", pwd);
        mRegMap.put("uid", uid);
        mRegMap.put("screen_name", screen_name);
    }

    @Override
    public void initViews() {
        mEditUsernameLoginActivity = (EditText) findViewById(R.id.edit_username_login_activity);
        mEditUserpwdLoginActivity = (EditText) findViewById(R.id.edit_userpwd_login_activity);
//        mCbRememberpwdLoginActivity = (CheckBox) findViewById(R.id.cb_rememberpwd_LoginActivity);
//        mCbAutologinLoginActivity = (CheckBox) findViewById(R.id.cb_autologin_LoginActivity);
        mBtRegLoginActivity = (Button) findViewById(R.id.bt_reg_LoginActivity);
        mBtLoginLoginActivity = (Button) findViewById(R.id.bt_login_LoginActivity);

        mImgvWechatLoginActivity = (ImageView) findViewById(R.id.imgv_wechat_LoginActivity);
        mImgvQqLoginActivity = (ImageView) findViewById(R.id.imgv_qq_LoginActivity);
        mImgvSinaLoginActivity = (ImageView) findViewById(R.id.imgv_sina_LoginActivity);
        mDig = new Dialog(mActivitySelf);
        Bundle extras = this.getIntent().getExtras();
        if(extras!=null){
            String username=extras.getString("username");
            mEditUsernameLoginActivity.setText(username);
        }

        mImgvQqLoginActivity.setOnClickListener(this);
        mImgvWechatLoginActivity.setOnClickListener(this);
        mImgvSinaLoginActivity.setOnClickListener(this);
    }

    //图片点击监听
    @Override
    public void onClick(View v) {
        UMShareAPI mShareAPI = UMShareAPI.get(mActivitySelf);
        switch (v.getId()) {
            case R.id.imgv_qq_LoginActivity:
                mShareAPI.doOauthVerify(mActivitySelf, SHARE_MEDIA.QQ, umAuthListener);
                break;
            case R.id.imgv_sina_LoginActivity:
                mShareAPI.doOauthVerify(mActivitySelf, SHARE_MEDIA.SINA, umAuthListener);
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Bundle bundle = this.getIntent().getBundleExtra("success");
        if (bundle != null) {
            String username = bundle.getString("username");
            mEditUsernameLoginActivity.setText(username);
        }
    }


    @Override
    public void initDatas() {
        Bundle bundle = getIntent().getBundleExtra("go");
        if (bundle != null) {
            mFrom = bundle.getString("from");
        }

    }

    //标题栏 + 注册按钮事件处理
    @Override
    public void initOthers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        setTitleCenter("请登录");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        mBtRegLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goToActivity(RegActivity.class);
            }
        });
        mBtLoginLoginActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doLogin();
            }
        });
    }

    //登录按钮事件处理,网络访问
    private void doLogin() {
        mBtLoginLoginActivity.setEnabled(false);
        mUsername = mEditUsernameLoginActivity.getText().toString();
        mUserpwd = mEditUserpwdLoginActivity.getText().toString();
        if ("".equals(mUsername) || "".equals(mUserpwd)) {
            Toast.makeText(mActivitySelf, "请输入用户名与密码", Toast.LENGTH_SHORT).show();
            mBtLoginLoginActivity.setEnabled(true);
            return;
        } else {
            mBtLoginLoginActivity.setText("正在登录中");
            mBtLoginLoginActivity.post(new Runnable() {
                @Override
                public void run() {
                    IUser iUser = NetUtil.getRetrofitCall(IUser.class);
                    initLoginMap(mUsername, mUserpwd, "", "");
                    mUserCall = iUser.doGet(mLoginMap);
                    mUserCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            User user = response.body();
                            if (!"请核对用户名和密码".equals(user.getPhone())) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("loger", user);
                                bundle.putString("from", mFrom);
                                SharedPrefrencesUtil.saveData(mActivitySelf, "x", "isLogin", true);
                                saveLoger(user);
                                Toast.makeText(mActivitySelf, "登录成功", Toast.LENGTH_SHORT).show();
                                goToActivity(MainActivity.class, "back", bundle);
                            } else {
                                Toast.makeText(mActivitySelf, "登录失败，用户名或者密码错误", Toast.LENGTH_SHORT).show();
                                mBtLoginLoginActivity.setEnabled(true);
                                mBtLoginLoginActivity.setText("登录");
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(mActivitySelf, "大侠你的网有点渣", Toast.LENGTH_SHORT).show();
                            mBtLoginLoginActivity.setEnabled(true);
                            mBtLoginLoginActivity.setText("登录");
                        }
                    });

                }
            });
        }
    }

    @Override
    public boolean isUseTitleBar() {
        return true;
    }

    //第三方接口回调,必加
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
