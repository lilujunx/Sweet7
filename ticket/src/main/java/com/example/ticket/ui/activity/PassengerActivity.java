package com.example.ticket.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;


import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.entity.passenger.PassengerEntity;
import com.example.ticket.entity.user.User;
import com.example.ticket.ui.adapter.ItemLvPassengerActivityAdapter;
import com.example.ticket.ui.inter.SelectPassenger;
import com.example.ticket.utils.NetUtil;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import library.utils.SDUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PassengerActivity extends TBaseActivity implements Callback<PassengerEntity>, View.OnClickListener {
    private ListView mLvPassengerActivity;
    private Map<String, String> mSelectMap = new HashMap<>();
    private Map<String, String> mDeleteMap = new HashMap<>();
    private User mLoger;
    private int mPosition;
    private AlertDialog.Builder mBuilder;
    public ItemLvPassengerActivityAdapter mItemLvPassengerActivityAdapter;
    private Dialog mDialog;
    private Button mBtSubmitPassengerActivity;
    private ArrayList<PassengerEntity.PassengerBean> mChoose = new ArrayList<>();
    public PassengerEntity mBody;
    public List<PassengerEntity.PassengerBean> mPassenger;

    @Override
    public int initRootLayout() {
        return R.layout.activity_passenger;
    }

    @Override
    public void initViews() {
        mLvPassengerActivity = (ListView) findViewById(R.id.lv_PassengerActivity);
        mBtSubmitPassengerActivity = (Button) findViewById(R.id.bt_submit_PassengerActivity);

        mDialog = new Dialog(mActivitySelf);
        mDialog.setContentView(R.layout.dig_wait);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void initDatas() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SDUtil.getExternalStoragePath() + "/haokeyun/x.txt"));
            mLoger = (User) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        doSelete();
        mDialog.show();
        mBtSubmitPassengerActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (PassengerEntity.PassengerBean entity : mPassenger) {
                    if (entity.isChecked()) {
                        mChoose.add(entity);
                    }
                }
                if(mChoose.size()!=0) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("passenger", mChoose);
                    bundle.putInt("passengerNum",mChoose.size());
                    goToActivity(TicketDetailActivity.class, "passenger", bundle);
                }else{
                    Toast.makeText(mActivitySelf, "尚未选择乘客", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void doSelete() {
        initSelectMap(mLoger.getPhone(), mLoger.getUid(), mLoger.getUserpwd());
        SelectPassenger selectPassenger = NetUtil.getRetrofitCall(SelectPassenger.class);
        Call<PassengerEntity> passengerEntityCall = selectPassenger.doGet(mSelectMap);
        passengerEntityCall.enqueue(this);

    }

    private void initSelectMap(String phone, String uid, String pwd) {
        mSelectMap.put("action", "select");
        mSelectMap.put("phone", phone);
        mSelectMap.put("uid", uid);
        mSelectMap.put("pwd", pwd);
    }

    private void initDeleteMap(String phone, String pName, String pIdNum) {
        mDeleteMap.put("action", "delete");
        mDeleteMap.put("phone", phone);
        mDeleteMap.put("pName", pName);
        mDeleteMap.put("pIdNum", pIdNum);
    }

    @Override
    public void onResponse(Call<PassengerEntity> call, Response<PassengerEntity> response) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        mBody = response.body();
        mPassenger = mBody.getPassenger();
        mItemLvPassengerActivityAdapter = new ItemLvPassengerActivityAdapter(mActivitySelf, mPassenger);
        mItemLvPassengerActivityAdapter.setOnClickListener(this);
        mLvPassengerActivity.setAdapter(mItemLvPassengerActivityAdapter);

    }

    @Override
    public void onFailure(Call<PassengerEntity> call, Throwable t) {
        t.printStackTrace();
        Log.e("xx", "boom");
        Log.e("xx", "t", t);
        Toast.makeText(mActivitySelf, "少侠，你的网略渣，稍后再试试?", Toast.LENGTH_SHORT).show();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void initOthers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setTitleCenter("我的乘客");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        setTitleRight("添加", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(AddPassengerActivity.class);
            }
        });
    }

    @Override
    public boolean isUseTitleBar() {
        return true;
    }

    @Override
    public void onClick(View v) {
        if (v instanceof Button) {
            mPosition = (int) v.getTag();
            mBuilder = new AlertDialog.Builder(mActivitySelf)
                    .setTitle("删除乘客")
                    .setMessage("确定要删除该乘客吗？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //网络访问
                            PassengerEntity.PassengerBean passengerEntity = mPassenger.get(mPosition);
                            initDeleteMap(passengerEntity.getPhone(), passengerEntity.getPName(), passengerEntity.getPIdNum());
                            SelectPassenger selectPassenger = NetUtil.getRetrofitCall(SelectPassenger.class);
                            Call<PassengerEntity> passengerEntityCall = selectPassenger.doGet(mDeleteMap);
                            mDialog.show();
                            passengerEntityCall.enqueue(new Callback<PassengerEntity>() {
                                @Override
                                public void onResponse(Call<PassengerEntity> call, Response<PassengerEntity> response) {
                                    if (mDialog != null) {
                                        mDialog.dismiss();
                                    }
                                    PassengerEntity body = response.body();
                                    Toast.makeText(mActivitySelf, body.getNo(), Toast.LENGTH_SHORT).show();
                                    doSelete();
                                }

                                @Override
                                public void onFailure(Call<PassengerEntity> call, Throwable t) {
                                    Toast.makeText(mActivitySelf, "少侠，你的网略渣，稍后再试试?", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    })
                    .setNegativeButton("否", null);
            mBuilder.create().show();
        }


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        doSelete();
    }
}
