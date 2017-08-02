package com.example.ticket.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import com.example.ticket.Constant;
import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.entity.passenger.PassengerEntity;
import com.example.ticket.entity.ticket.TicketEntity;
import com.example.ticket.ui.adapter.ItemLvPassengerActivityAdapter;
import com.example.ticket.ui.adapter.ItemLvPassengerTicketActivityAdapter;

import java.util.ArrayList;

import library.utils.SharedPrefrencesUtil;

public class TicketDetailActivity extends TBaseActivity {
    private ImageView mImgvTypeTicketDetailActivity;
    private TextView mTvTypeTicketDetailActivity;
    private TextView mTvEndTicketDetailActivity;
    private TextView mTvTimeTicketDetailActivity;
    private TextView mTvPriceTicketDetailActivity;
    private TextView mTvSurplusTicketDetailActivity;
    private TextView mTvAddpassengerTicketDetailActivity;
    private ListView mLvAddPassengerTicketDetailActivity;
    private Button mBtSubmitTicketDetailActivity;
    private boolean mIsLogin;
    private TicketEntity.TicketsBean mTicketsBean;
    public ArrayList<PassengerEntity.PassengerBean> mPassenger;
    private int[] mIcons={R.drawable.daxing1,R.drawable.daxing2,R.drawable.zhongxing1,R.drawable.zhongxing2,R.drawable.wopugao1,R.drawable.wopugao2};

    @Override
    public int initRootLayout() {
        return R.layout.activity_ticket_detail;
    }

    @Override
    public void initViews() {
        if(mTicketsBean==null){
            mTicketsBean= Constant.TicketsBeanNow;
        }
        mImgvTypeTicketDetailActivity = (ImageView) findViewById(R.id.imgv_type_TicketDetailActivity);
        mTvTypeTicketDetailActivity = (TextView) findViewById(R.id.tv_type_TicketDetailActivity);
        mTvEndTicketDetailActivity = (TextView) findViewById(R.id.tv_end_TicketDetailActivity);
        mTvTimeTicketDetailActivity = (TextView) findViewById(R.id.tv_time_TicketDetailActivity);
        mTvPriceTicketDetailActivity = (TextView) findViewById(R.id.tv_price_TicketDetailActivity);
        mTvSurplusTicketDetailActivity = (TextView) findViewById(R.id.tv_surplus_TicketDetailActivity);
        mTvAddpassengerTicketDetailActivity = (TextView) findViewById(R.id.tv_addpassenger_TicketDetailActivity);
        mLvAddPassengerTicketDetailActivity = (ListView) findViewById(R.id.lv_addPassenger_TicketDetailActivity);
        mBtSubmitTicketDetailActivity = (Button) findViewById(R.id.bt_submit_TicketDetailActivity);
        mIsLogin = SharedPrefrencesUtil.getData(mActivitySelf, "x", "isLogin", false);
    }

    //界面数据初始化
    @Override
    public void initDatas() {
        getImge(mTicketsBean.getType());
        mTvTypeTicketDetailActivity.setText(mTicketsBean.getType());
        mTvEndTicketDetailActivity.setText(mTicketsBean.getEnd());
        mTvTimeTicketDetailActivity.setText(mTicketsBean.getTime());

        mTvSurplusTicketDetailActivity.setText(mTicketsBean.getSurplus()+"");
        mTvPriceTicketDetailActivity.setText(mTicketsBean.getPrice()+"");
        //添加乘客按钮
        mTvAddpassengerTicketDetailActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(PassengerActivity.class);
            }
        });

        //提交按钮
        mBtSubmitTicketDetailActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPassenger.size()>mTicketsBean.getSurplus()){
                    Toast.makeText(mActivitySelf, "没有这么多票", Toast.LENGTH_SHORT).show();
                    return;
                }
                Bundle bundle=new Bundle();
                bundle.putSerializable("passenger",mPassenger);
                //---------------------------------------
                Log.e("xx",mPassenger.toString());
                goToActivity(SeatActivity.class,"passenger",bundle);
            }
        });
    }

    private void getImge(String type){
        int position=0;
        switch (type){
            case "大型高一":
                position=0;
                break;
            case "大型高二":
                position=1;
                break;
            case "中型高一":
                position=2;
                break;
            case "中型高二":
                position=3;
                break;
            case "卧铺高一":
                position=4;
                break;
            case "卧铺高二":
                position=5;
                break;
        }
        Glide.with(mActivitySelf).load(mIcons[position]).into(mImgvTypeTicketDetailActivity);
    }

    @Override
    public void initOthers() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setTitleCenter("车票详情");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        if(!mIsLogin){
            Bundle bundle = new Bundle();
            bundle.putString("from", "mine");
            goToActivity(LoginActivity.class, "go", bundle);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Bundle bundle=intent.getBundleExtra("passenger");
        if(bundle!=null) {
            mPassenger = (ArrayList<PassengerEntity.PassengerBean>) bundle.getSerializable("passenger");
            Constant.passengerNum=bundle.getInt("passengerNum");
            mLvAddPassengerTicketDetailActivity.setAdapter(new ItemLvPassengerTicketActivityAdapter(mActivitySelf, mPassenger));
        }
    }



    @Override
    public boolean isUseTitleBar() {
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToActivity(TicketSelectActivity.class);
    }
}
