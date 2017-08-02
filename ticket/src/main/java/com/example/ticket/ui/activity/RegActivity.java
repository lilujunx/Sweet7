package com.example.ticket.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.entity.user.User;
import com.example.ticket.ui.inter.IUser;
import com.example.ticket.utils.NetUtil;
import com.example.ticket.utils.StringCheck;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import library.utils.EdtUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegActivity extends TBaseActivity  {
    private EditText mEditUserpwdRegActivity;
    private EditText mEditPhoneRegActivity;
    private EditText mEditCodeRegActivity;
    private Button mBtGetcodeRegActivity;
    private Button mBtSubmitRegActivity;
    private ImageView mImgvChangeRegActivity;

    private Map<String, String> mRegMap = new HashMap<>();
    private int time = 60;
    private String mCode;
    private String mUserpwd;
    private boolean isOk;
    private String phone;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (time > 0) {
                time--;
                mBtGetcodeRegActivity.setText(time + " S");
                mHandler.sendEmptyMessageDelayed(1, 1000);
            } else {
                mBtGetcodeRegActivity.setText("获取验证码");
                mBtGetcodeRegActivity.setEnabled(true);
            }
        }
    };


    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mActivitySelf, "验证成功", Toast.LENGTH_SHORT).show();
                            isOk=true;
                            IUser iUser= NetUtil.getRetrofitCall(IUser.class);
                            initRegMap(phone,mUserpwd,"","");
                            Call<User> userCall = iUser.doGet(mRegMap);
                            userCall.enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    User u=response.body();
                                    if(u.getPhone().equals("此号码已经注册，可以直接登录")){
                                        Toast.makeText(mActivitySelf, "此号码已经注册，可以直接登录", Toast.LENGTH_LONG).show();
                                    }
                                    Bundle bundle = new Bundle();
                                    bundle.putString("username", phone);
                                    goToActivity(LoginActivity.class, "success", bundle);
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Toast.makeText(mActivitySelf, "少侠你的网有点渣，稍后再试试？", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    //获取验证码成功
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mActivitySelf, "获取成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    //返回支持发送验证码的国家列表
                }
            } else {
                ((Throwable) data).printStackTrace();
            }
        }
    };


    @Override
    public int initRootLayout() {
        return R.layout.activity_reg;
    }

    @Override
    public void initViews() {
        mEditUserpwdRegActivity = (EditText) findViewById(R.id.edit_userpwd_RegActivity);
        mEditPhoneRegActivity = (EditText) findViewById(R.id.edit_phone_RegActivity);
        mEditCodeRegActivity = (EditText) findViewById(R.id.edit_code_RegActivity);
        mBtGetcodeRegActivity = (Button) findViewById(R.id.bt_getcode_RegActivity);
        mBtSubmitRegActivity = (Button) findViewById(R.id.bt_submit_RegActivity);
        mImgvChangeRegActivity = (ImageView) findViewById(R.id.imgv_change_RegActivity);


        mEditUserpwdRegActivity.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                String str= EdtUtil.getEdtText(mEditUserpwdRegActivity);
                if(str.equals("请输入6-15位数字字母")){
                    mEditUserpwdRegActivity.setText("");
                    mEditUserpwdRegActivity.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    mEditUserpwdRegActivity.setTextColor(Color.BLACK);
                }
                if(!StringCheck.check(str)){
                    mEditUserpwdRegActivity.setInputType(InputType.TYPE_CLASS_TEXT);
                    mEditUserpwdRegActivity.setText("请输入6-15位数字字母");
                    mEditUserpwdRegActivity.setTextColor(Color.RED);

                }
            }
        });
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
    public void initDatas() {
        SMSSDK.registerEventHandler(eh); //注册短信回调
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        setTitleCenter("用户注册");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh); //注册短信回调
    }

    @Override
    public void initOthers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            //透明导航栏
//            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        mBtGetcodeRegActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phone = EdtUtil.getEdtText(mEditPhoneRegActivity);
                mUserpwd=EdtUtil.getEdtText(mEditUserpwdRegActivity);
                if (StringCheck.check(mUserpwd)) {
                    if (StringCheck.checkPhone(phone)) {
                        mBtGetcodeRegActivity.setEnabled(false);
                        mBtGetcodeRegActivity.setText("60 S");
                        mHandler.sendEmptyMessageDelayed(1, 1000);
                        SMSSDK.getVerificationCode("86", phone);
                        Toast.makeText(mActivitySelf, "获取", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mActivitySelf, "手机号输入错误", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mActivitySelf, "请选输入正确的密码", Toast.LENGTH_SHORT).show();
                }
            }
        });
        mBtSubmitRegActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (EdtUtil.isEdtEmpty(mEditCodeRegActivity)) {
                    Toast.makeText(mActivitySelf, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                mCode = EdtUtil.getEdtText(mEditCodeRegActivity);
                SMSSDK.submitVerificationCode("86", phone, mCode);
            }
        });

    }


}
