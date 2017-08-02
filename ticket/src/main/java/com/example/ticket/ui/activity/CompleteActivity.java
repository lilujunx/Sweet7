package com.example.ticket.ui.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;


import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.entity.user.User;
import com.example.ticket.ui.inter.IUser;
import com.example.ticket.utils.NetUtil;
import com.example.ticket.utils.StringCheck;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;
import library.utils.EdtUtil;
import library.utils.SDUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteActivity extends TBaseActivity implements View.OnClickListener, Callback<User> {
    private EditText mEditPeoplenameCompleteActivity;
    private RadioButton mRbManCompleteActivity;
    private RadioButton mRbWomanCompleteActivity;
    private EditText mEditPhoneCompleteActivity;
    private Button mBtGetcodeCompleteActivity;
    private EditText mEditCodeCompleteActivity;
    private Button mBtCheckCodeCompleteActivity;
    private EditText mEditIdCompleteActivity;
    private Button mBtSubmitCompleteActivity;
    private Dialog mDig;
    private int time = 60;
    private User mLoger;
    private String mCode;
    private boolean isOk;
    private Call<User> mUserCall;
    private Map<String, String> mUpdateMap = new HashMap<>();
    private String phone;
    private String name;
    private String sex;
    private String idNum;
    private String uid;


    //倒计时60秒
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (time > 0) {
                time--;
                mBtGetcodeCompleteActivity.setText(time + " S");
                mHandler.sendEmptyMessageDelayed(1, 1000);
            } else {
                mBtGetcodeCompleteActivity.setText("获取验证码");
                mBtGetcodeCompleteActivity.setEnabled(true);
            }
        }
    };

    EventHandler eh = new EventHandler() {
        @Override
        public void afterEvent(int event, int result, Object data) {
            if (result == SMSSDK.RESULT_COMPLETE) {
                //回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    isOk = true;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(mActivitySelf, "验证成功", Toast.LENGTH_SHORT).show();
                            mBtCheckCodeCompleteActivity.setEnabled(false);
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

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mBtCheckCodeCompleteActivity.setEnabled(true);
                        mEditPhoneCompleteActivity.setEnabled(true);
                        Toast.makeText(mActivitySelf, "请输入正确验证码", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }
    };

    @Override
    public int initRootLayout() {
        return R.layout.activity_complete;
    }

    @Override
    public void initViews() {

        mEditPeoplenameCompleteActivity = (EditText) findViewById(R.id.edit_peoplename_CompleteActivity);
        mRbManCompleteActivity = (RadioButton) findViewById(R.id.rb_man_CompleteActivity);
        mRbWomanCompleteActivity = (RadioButton) findViewById(R.id.rb_woman_CompleteActivity);
        mEditPhoneCompleteActivity = (EditText) findViewById(R.id.edit_phone_CompleteActivity);
        mBtGetcodeCompleteActivity = (Button) findViewById(R.id.bt_getcode_CompleteActivity);
        mEditCodeCompleteActivity = (EditText) findViewById(R.id.edit_code_CompleteActivity);
        mBtCheckCodeCompleteActivity = (Button) findViewById(R.id.bt_checkCode_CompleteActivity);
        mEditIdCompleteActivity = (EditText) findViewById(R.id.edit_id_CompleteActivity);
        mBtSubmitCompleteActivity = (Button) findViewById(R.id.bt_submit_CompleteActivity);
        mRbManCompleteActivity.setEnabled(false);
        mRbWomanCompleteActivity.setEnabled(false);

    }

    @Override
    public void initDatas() {
        SMSSDK.registerEventHandler(eh); //注册短信回调


        mBtSubmitCompleteActivity.setOnClickListener(this);
        mBtCheckCodeCompleteActivity.setOnClickListener(this);
        mBtGetcodeCompleteActivity.setOnClickListener(this);
        doPrepare();
    }

    @Override
    public void onClick(View v) {
        String phone = EdtUtil.getEdtText(mEditPhoneCompleteActivity);
        switch (v.getId()) {
            //验证按钮
            case R.id.bt_checkCode_CompleteActivity:
                if (EdtUtil.isEdtEmpty(mEditCodeCompleteActivity)) {
                    Toast.makeText(mActivitySelf, "请输入验证码", Toast.LENGTH_SHORT).show();
                    return;
                }
                mCode = EdtUtil.getEdtText(mEditCodeCompleteActivity);
                SMSSDK.submitVerificationCode("86", phone, mCode);
                break;

            //获取验证码按钮
            case R.id.bt_getcode_CompleteActivity:

                if (StringCheck.checkPhone(phone)) {
                    mBtGetcodeCompleteActivity.setEnabled(false);
                    mBtGetcodeCompleteActivity.setText("60 S");
                    Message message = mHandler.obtainMessage();
                    mHandler.sendEmptyMessageDelayed(1, 1000);

                    SMSSDK.getVerificationCode("86", phone);
                    mEditPhoneCompleteActivity.setEnabled(false);
                } else {
                    Toast.makeText(mActivitySelf, "手机号输入错误", Toast.LENGTH_SHORT).show();
                }

                break;

            //提交按钮
            case R.id.bt_submit_CompleteActivity:
                name = EdtUtil.getEdtText(mEditPeoplenameCompleteActivity);
                idNum = EdtUtil.getEdtText(mEditIdCompleteActivity);

                String name = EdtUtil.getEdtText(mEditPeoplenameCompleteActivity);
                String sex = mRbManCompleteActivity.isChecked() ? "男" : "女";
                String idNum = EdtUtil.getEdtText(mEditIdCompleteActivity);
                if (!StringCheck.checkIdNum(idNum)) {
                    Toast.makeText(this, "请输入正确的身份证号", Toast.LENGTH_SHORT).show();
                    break;
                }
                initUpdateMap(phone, uid, name, sex, idNum);
                if ("".equals(name) || "".equals(idNum) || name == null || idNum == null) {
                    Toast.makeText(this, "请完善所有信息再提交", Toast.LENGTH_SHORT).show();

                } else {
                    if (isOk && phone != null) {
                        IUser iUser = NetUtil.getRetrofitCall(IUser.class);

                        mUserCall = iUser.doGet(mUpdateMap);
                        mUserCall.enqueue(this);

                        mDig = new Dialog(mActivitySelf);
                        mDig.setContentView(R.layout.dig_wait);
                        mDig.setTitle("请稍后。。。");
                        mDig.setCancelable(false);
                        mDig.setCanceledOnTouchOutside(false);
                        mDig.show();
                    } else {
                        Toast.makeText(this, "请输入手机号并验证", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private void initUpdateMap(String phone, String uid, String name, String sex, String idNum) {
        mUpdateMap.put("action", "update");
        mUpdateMap.put("phone", phone);
        mUpdateMap.put("uid", uid);
        mUpdateMap.put("name", name);
        mUpdateMap.put("sex", sex);
        mUpdateMap.put("idNum", idNum);
    }

    //界面数据设置
    private void doPrepare() {
        if (mLoger == null) {
            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SDUtil.getExternalStoragePath() + "/haokeyun/x.txt"));
                mLoger = (User) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (mLoger.getUid() != null) {
            Log.e("xx", "uid不为空，是QQ用户");
            uid = mLoger.getUid();

        }
        mEditPeoplenameCompleteActivity.setText(mLoger.getName());
        mEditPhoneCompleteActivity.setText(mLoger.getPhone());
        mEditIdCompleteActivity.setText(mLoger.getIdNum());
        doCheck(mEditIdCompleteActivity);
        doCheck(mEditPeoplenameCompleteActivity);
        doCheck(mEditPhoneCompleteActivity);
    }

    //设置EditView的可用性
    private void doCheck(EditText editText) {
        if (EdtUtil.getEdtText(editText) == null) {
            editText.setEnabled(true);
        } else {
            editText.setEnabled(false);
        }

    }

    //标题
    @Override
    public void initOthers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setTitleCenter("个人信息");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        setTitleRight("修改", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditIdCompleteActivity.setEnabled(true);
                mEditPeoplenameCompleteActivity.setEnabled(true);
                mEditPhoneCompleteActivity.setEnabled(true);
                mRbWomanCompleteActivity.setEnabled(true);
                mRbManCompleteActivity.setEnabled(true);

                mBtCheckCodeCompleteActivity.setVisibility(View.VISIBLE);
                mBtGetcodeCompleteActivity.setVisibility(View.VISIBLE);
                mEditCodeCompleteActivity.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eh); //注册短信回调
    }

    @Override
    public void onResponse(Call<User> call, Response<User> response) {
        User body = response.body();

        try {
            String root = SDUtil.getExternalStoragePath() + "/haokeyun";
            File file = new File(root);
            if (!file.exists()) {
                file.mkdirs();
            }
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(root + "/x.txt"));
            oos.writeObject(body);
            Log.e("xx", "存储用户信息" + SDUtil.getExternalStoragePath() + "/haokeyun/x.txt");
        } catch (IOException e) {
            Toast.makeText(mActivitySelf, "出了点意外", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
        mDig.dismiss();
        Toast.makeText(mActivitySelf, "提交成功", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        bundle.putString("from", "mine");
        goToActivity(MainActivity.class, "back", bundle);
    }

    @Override
    public void onFailure(Call<User> call, Throwable t) {
        Toast.makeText(mActivitySelf, "少侠你的网友点渣，等会儿呗？", Toast.LENGTH_SHORT).show();
        mDig.dismiss();
    }
}