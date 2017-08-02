package com.example.ticket.ui.activity;

import android.app.Dialog;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.entity.passenger.PassengerEntity;
import com.example.ticket.entity.user.User;
import com.example.ticket.ui.inter.SelectPassenger;
import com.example.ticket.utils.NetUtil;
import com.example.ticket.utils.StringCheck;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import library.utils.EdtUtil;
import library.utils.SDUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddPassengerActivity extends TBaseActivity implements View.OnClickListener, Callback<PassengerEntity> {
    private EditText mEditPeoplenameAddPassengerActivity;
    private EditText mEditPIdNumAddPassengerActivity;
    private Button mBtSubmitAddPassengerActivity;
    private Map<String, String> mAddMap = new HashMap<>();
    private String pName;
    private String pIdNum;
    private Dialog mDialog;
    private User mLoger;

    @Override
    public int initRootLayout() {
        return R.layout.activity_add_passenger;
    }

    @Override
    public void initViews() {
        mDialog = new Dialog(mActivitySelf);
        mDialog.setContentView(R.layout.dig_wait);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mEditPeoplenameAddPassengerActivity = (EditText) findViewById(R.id.edit_peoplename_AddPassengerActivity);
        mEditPIdNumAddPassengerActivity = (EditText) findViewById(R.id.edit_pIdNum_AddPassengerActivity);
        mBtSubmitAddPassengerActivity = (Button) findViewById(R.id.bt_submit__AddPassengerActivity);
        mBtSubmitAddPassengerActivity.setOnClickListener(this);
    }

    @Override
    public void initDatas() {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SDUtil.getExternalStoragePath() + "/haokeyun/x.txt"));
            mLoger = (User) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void initAddMap(String phone, String pName, String pIdNum) {
        mAddMap.put("action", "add");
        mAddMap.put("phone", phone);
        mAddMap.put("pName", pName);
        mAddMap.put("pIdNum", pIdNum);
    }

    @Override
    public void initOthers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setTitleCenter("添加乘客");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        pName = EdtUtil.getEdtText(mEditPeoplenameAddPassengerActivity);
        pIdNum = EdtUtil.getEdtText(mEditPIdNumAddPassengerActivity);
        if(pName.equals("")){
            Toast.makeText(mActivitySelf, "请输入名字", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!StringCheck.checkIdNum(pIdNum)) {
            Toast.makeText(mActivitySelf, "身份证为18位数字", Toast.LENGTH_SHORT).show();
            return;
        }

        SelectPassenger retrofitCall = NetUtil.getRetrofitCall(SelectPassenger.class);
        initAddMap(mLoger.getPhone(), pName, pIdNum);
        Call<PassengerEntity> passengerEntityCall = retrofitCall.doGet(mAddMap);
        passengerEntityCall.enqueue(this);
        mDialog.show();
    }

    @Override
    public void onResponse(Call<PassengerEntity> call, Response<PassengerEntity> response) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        PassengerEntity body = response.body();
        Toast.makeText(mActivitySelf, body.getNo(), Toast.LENGTH_SHORT).show();
        goToActivity(PassengerActivity.class);
    }

    @Override
    public void onFailure(Call<PassengerEntity> call, Throwable t) {
        Toast.makeText(mActivitySelf, "少侠，你的网有点渣，稍后再试试?", Toast.LENGTH_SHORT).show();
        if(mDialog!=null){
            mDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        goToActivity(PassengerActivity.class);
    }
}