package com.example.ticket.ui.activity;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.db.DBConfig;
import com.example.ticket.db.ticket.ShowTicket;
import com.example.ticket.db.ticket.TicketRecord;
import com.example.ticket.ui.adapter.ItemLvPassengerTicketActivityAdapter;


import java.util.List;

import xutils.ex.DbException;
import xutils.x;

public class ShowActivity extends TBaseActivity implements View.OnClickListener {
    private TextView mTvDateShowActivity;
    private TextView mTvTimeShowActivity;
    private TextView mTvEndShowActivity;
    private TextView mTvPriceShowActivity;
    private ListView mLvShowActivity;
    private Button mBtPayShowActivity;
    private ShowTicket mShowTicket;
    private ItemLvPassengerTicketActivityAdapter mItemLvPassengerTicketActivityAdapter;
    private int id;
    public TicketRecord mTicketRecord;
    private TextView mTvSeatShowActivity;

    @Override
    public int initRootLayout() {
        return R.layout.activity_show;
    }

    @Override
    public void initViews() {

        mTvSeatShowActivity = (TextView) findViewById(R.id.tv_seat_showActivity);

        mTvDateShowActivity = (TextView) findViewById(R.id.tv_date_showActivity);
        mTvTimeShowActivity = (TextView) findViewById(R.id.tv_time_showActivity);
        mTvEndShowActivity = (TextView) findViewById(R.id.tv_end_showActivity);
        mTvPriceShowActivity = (TextView) findViewById(R.id.tv_price_showActivity);
        mLvShowActivity = (ListView) findViewById(R.id.lv_showActivity);
        mBtPayShowActivity = (Button) findViewById(R.id.bt_pay_showActivity);
        mBtPayShowActivity.setOnClickListener(this);
        Bundle extras = getIntent().getBundleExtra("show");
        if(extras!=null){
            mShowTicket= (ShowTicket) extras.getSerializable("show");
            Log.e("xx",mShowTicket.toString());
        }else{
            Toast.makeText(mActivitySelf, "没有Bundler", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void initDatas() {
        if(mShowTicket!=null){
            mTvDateShowActivity.setText(mShowTicket.getDate());
            mTvEndShowActivity.setText(mShowTicket.getEnd());
            mTvTimeShowActivity.setText(mShowTicket.getTime());
            mTvPriceShowActivity.setText("￥"+mShowTicket.getTotalPrice());
            mTvSeatShowActivity.setText(mShowTicket.getSeat());
            mItemLvPassengerTicketActivityAdapter=new ItemLvPassengerTicketActivityAdapter(mActivitySelf,mShowTicket.getPassengerBeen());
            Log.e("xx","mShowTicket.getPassengerBeen()=="+mShowTicket.getPassengerBeen());
            mLvShowActivity.setAdapter(mItemLvPassengerTicketActivityAdapter);
            try {
                List<TicketRecord> list = x.getDb(DBConfig.getDaoConfig()).findAll(TicketRecord.class);
                mTicketRecord=list.get(list.size()-1);
                id=mTicketRecord.getId();
            } catch (DbException e) {
                e.printStackTrace();
            }
            if(mTicketRecord!=null && mTicketRecord.getState().equals("未支付")){
                mBtPayShowActivity.setVisibility(View.VISIBLE);
            }else{
                mBtPayShowActivity.setVisibility(View.INVISIBLE);
            }
        }else{
            Toast.makeText(mActivitySelf, "mShowTicket为空", Toast.LENGTH_SHORT).show();
            Log.e("xx","mShowTicket为空");
        }

    }

    @Override
    public boolean isUseTitleBar() {

        return false;
    }

    @Override
    public void initOthers() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onClick(View v) {

            Bundle bundle=new Bundle();
            bundle.putInt("id",id);
            goToActivity(PayZFBActivity.class,"id",bundle);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        goToActivity(MainActivity.class);
    }
}
