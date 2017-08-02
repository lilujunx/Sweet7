package com.example.ticket.ui.activity;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ticket.Constant;
import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.entity.passenger.PassengerEntity;
import com.example.ticket.entity.ticket.TicketEntity;
import com.example.ticket.ui.adapter.ItemLvSelectTicketAdapter;
import com.example.ticket.ui.inter.SelectTicket;
import com.example.ticket.utils.AllCom;
import com.example.ticket.utils.ComFactory;
import com.example.ticket.utils.NetUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import library.utils.SharedPrefrencesUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TicketSelectActivity extends TBaseActivity implements Callback<TicketEntity>, View.OnClickListener {

    private TextView mTvTicketSelectActivity;
    private ListView mLvTicketSelectActivity;
    private TextView mTvNullTicketSelectActivity;
    private CheckBox mCbTimeTicketSelectActivity;
    private CheckBox mCbTypeTicketSelectActivity;
    private CheckBox mCbPriceTicketSelectActivity;
    private CheckBox mCbSurplusTicketSelectActivity;
    private List<CheckBox> mCheckBoxes = new ArrayList<>();
    private AllCom mAllCom;
    private String mEnd;
    private String mDate;
    private String mTimeLevel;
    private Call<TicketEntity> mTicketEntityCall;
    private TicketEntity mTicketEntity;
    private ItemLvSelectTicketAdapter mItemLvSelectTicketAdapter;
    private Dialog mDialog;
    private List<TicketEntity.TicketsBean> mTicketsBeen;
    private ComFactory mComFactory = ComFactory.getComFactory();
    private Handler mHandler = new Handler();

    @Override
    public int initRootLayout() {
        return R.layout.activity_ticket_select;
    }

    @Override
    public void initViews() {
        mDialog = new Dialog(mActivitySelf);
        mDialog.setContentView(R.layout.dig_wait);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(false);
        mTvTicketSelectActivity = (TextView) findViewById(R.id.tv_TicketSelectActivity);
        mLvTicketSelectActivity = (ListView) findViewById(R.id.lv_TicketSelectActivity);
        mTvNullTicketSelectActivity = (TextView) findViewById(R.id.tv_null_TicketSelectActivity);
        mEnd = SharedPrefrencesUtil.getData(mActivitySelf, "x", "end", "");
        mDate = SharedPrefrencesUtil.getData(mActivitySelf, "x", "date", "");
        mTimeLevel = SharedPrefrencesUtil.getData(mActivitySelf, "x", "timeLevel", "");
        initCheckBox();
        if (!"".equals(mDate)) {
            mTvTicketSelectActivity.setText(mDate);
        }

    }

    private void initCheckBox() {
        mCbTimeTicketSelectActivity = (CheckBox) findViewById(R.id.cb_time_TicketSelectActivity);
        mCbTypeTicketSelectActivity = (CheckBox) findViewById(R.id.cb_type_TicketSelectActivity);
        mCbPriceTicketSelectActivity = (CheckBox) findViewById(R.id.cb_price_TicketSelectActivity);
        mCbSurplusTicketSelectActivity = (CheckBox) findViewById(R.id.cb_surplus_TicketSelectActivity);
        mCheckBoxes.add(mCbTimeTicketSelectActivity);
        mCheckBoxes.add(mCbTypeTicketSelectActivity);
        mCheckBoxes.add(mCbPriceTicketSelectActivity);
        mCheckBoxes.add(mCbSurplusTicketSelectActivity);
        mCbTimeTicketSelectActivity.setOnClickListener(this);
        mCbTypeTicketSelectActivity.setOnClickListener(this);
        mCbPriceTicketSelectActivity.setOnClickListener(this);
        mCbSurplusTicketSelectActivity.setOnClickListener(this);
    }

    public void doCom(final View v, final String key1, final String key2) {
        mDialog.show();
        final CheckBox cb = (CheckBox) v;
        otherCheck(cb);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
                if (cb.isChecked()) {
                    mAllCom = mComFactory.getCom(key1);
                } else {
                    mAllCom = mComFactory.getCom(key2);
                }
                if (mTicketsBeen != null && mItemLvSelectTicketAdapter != null) {
                    Collections.sort(mTicketsBeen, mAllCom);
                    mItemLvSelectTicketAdapter.setEntities(mTicketsBeen);
                    return;
                }

            }
        }, 1000);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cb_time_TicketSelectActivity:
                doCom(v, "timeComAes", "timeComDes");

                break;
            case R.id.cb_type_TicketSelectActivity:
                doCom(v, "typeComDes", "typeComAes");
                break;
            case R.id.cb_price_TicketSelectActivity:
                doCom(v, "priceComDes", "priceComAes");

                break;
            case R.id.cb_surplus_TicketSelectActivity:
                doCom(v, "surplusComDes", "surplusComAes");
                break;
        }
    }

    //早出单选的假象
    public void otherCheck(View v) {
        CheckBox cb = (CheckBox) v;
        if (((CheckBox) v).isChecked()) {
            for (CheckBox checkBox : mCheckBoxes) {
                checkBox.setChecked(false);
            }
            cb.setChecked(true);
        }
    }

    @Override
    public void initDatas() {
        setTitleCenter("东站  ---->  " + mEnd);
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });

    }

    //标题栏+网络访问
    @Override
    public void initOthers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }


        SelectTicket mSelectTicket = NetUtil.getRetrofitCall(SelectTicket.class);
        Log.e("xx", mEnd + "---" + mDate + "---" + mTimeLevel);

        mDialog.show();
        if (!"".equals(mEnd) && !"".equals(mDate) && !"".equals(mTimeLevel)) {
            mTicketEntityCall = mSelectTicket.doGet(mDate, mEnd, mTimeLevel);
            System.out.println(mEnd + "---" + mDate + "---" + mTimeLevel);
            //http://localhost:8080/Ticket/selectticket?date=2017-01-21&end=x1&timeLevel=1
            mTicketEntityCall.enqueue(this);
        }


    }



    @Override
    public void onResponse(Call<TicketEntity> call, Response<TicketEntity> response) {
        if (mDialog != null) {
            mDialog.dismiss();
        }
        TicketEntity ticketEntity = response.body();
        List<TicketEntity.TicketsBean> tickets = ticketEntity.getTickets();
        mTicketsBeen = tickets;
        if (tickets.size() != 0) {
            mTvNullTicketSelectActivity.setVisibility(View.INVISIBLE);
            mItemLvSelectTicketAdapter = new ItemLvSelectTicketAdapter(mActivitySelf, tickets);
            mItemLvSelectTicketAdapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TicketEntity.TicketsBean ticketsBean = (TicketEntity.TicketsBean) v.getTag();
                    Constant.TicketsBeanNow=ticketsBean;
                    goToActivity(TicketDetailActivity.class);
                }
            });
            mLvTicketSelectActivity.setAdapter(mItemLvSelectTicketAdapter);
        } else {
            mTvNullTicketSelectActivity.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onFailure(Call<TicketEntity> call, Throwable t) {
        Toast.makeText(mActivitySelf, "出了点小意外，少侠稍等再试？", Toast.LENGTH_SHORT).show();
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToActivity(MainActivity.class);
    }
}
