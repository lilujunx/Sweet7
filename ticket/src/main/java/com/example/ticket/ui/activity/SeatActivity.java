package com.example.ticket.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket.Constant;
import com.example.ticket.R;
import com.example.ticket.base.TBaseActivity;
import com.example.ticket.db.DBConfig;
import com.example.ticket.db.ticket.ShowTicket;
import com.example.ticket.db.ticket.TicketRecord;
import com.example.ticket.entity.passenger.PassengerEntity;
import com.example.ticket.entity.ticket.TicketEntity;
import com.example.ticket.entity.user.User;
import com.example.ticket.ui.adapter.AdapterSeatGV;


import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import library.utils.DateUtils;
import library.utils.SDUtil;
import xutils.ex.DbException;
import xutils.x;

public class SeatActivity extends TBaseActivity {
    private TextView mTvSeatActivity;
    private GridView mGvSeatActivity;
    private Button mBtSubmitTicketDetailActivity;
    private AdapterSeatGV mAdapterSeatGV;
    private int num = Constant.passengerNum;
    private int chose = 0;
    private TicketEntity.TicketsBean mTicketNow;
    private User mLoger;
    private List<Integer> pos = new ArrayList<>();
    private ArrayList<PassengerEntity.PassengerBean> mPassengerBeen =new ArrayList<>();

    @Override
    public int initRootLayout() {
        return R.layout.activity_seat;
    }

    @Override
    public void initViews() {
        mTvSeatActivity = (TextView) findViewById(R.id.tv_seat_activity);
        mGvSeatActivity = (GridView) findViewById(R.id.gv_seat_activity);
        mBtSubmitTicketDetailActivity = (Button) findViewById(R.id.bt_submit_TicketDetailActivity);
        mTvSeatActivity.setText("目前选择了0/" + num);

        Bundle bundle = this.getIntent().getBundleExtra("passenger");
        mPassengerBeen = (ArrayList<PassengerEntity.PassengerBean>) bundle.getSerializable("passenger");
        mAdapterSeatGV = new AdapterSeatGV(mActivitySelf);
        mTicketNow = Constant.TicketsBeanNow;
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SDUtil.getExternalStoragePath() + "/haokeyun/x.txt"));
            mLoger = (User) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.e("xx","mPassengerBeen为："+mPassengerBeen.toString());
        mAdapterSeatGV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                CheckBox cb = (CheckBox) buttonView;
                int position = (int) cb.getTag();

                if (isChecked) {
                    pos.add(position);
                    chose++;
                } else {
                    Integer i = position;
                    pos.remove(i);
                    chose--;
                }
                if (chose > num) {
                    Toast.makeText(mActivitySelf, "您已选够人数，请选取消某个座位再点击", Toast.LENGTH_SHORT).show();
                    cb.setChecked(false);
                    chose--;

                }
                mTvSeatActivity.setText("目前选择了" + chose + "/" + num);

            }
        });
        mBtSubmitTicketDetailActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (chose < num) {
                    Toast.makeText(mActivitySelf, "您可能尚未选座完毕", Toast.LENGTH_SHORT).show();
                    return;
                }
                TicketRecord ticketRecord = new TicketRecord();
                ticketRecord.setPhone(mLoger.getPhone());
                ticketRecord.setDate(mTicketNow.getDate());
                ticketRecord.setEnd(mTicketNow.getEnd());
                ticketRecord.setPrice(mTicketNow.getPrice());
                ticketRecord.setTime(mTicketNow.getTime());
                ticketRecord.setState("未支付");
                ticketRecord.setType(mTicketNow.getType());
                ticketRecord.setBuyTime(getBuyTime());
                ticketRecord.setPassenger(mPassengerBeen);
                ticketRecord.setSeat(getSeat());
                try {
                    x.getDb(DBConfig.getDaoConfig()).save(ticketRecord);
                    Log.e("xx","存储了:"+ticketRecord);
                } catch (DbException e) {
                    Toast.makeText(mActivitySelf, "出错了", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                Log.e("xx","ticketRecord:"+ticketRecord);
                Bundle bundle = new Bundle();

                ShowTicket showTicket = new ShowTicket();
                showTicket.setType(mTicketNow.getType());
                showTicket.setTime(mTicketNow.getTime());
                showTicket.setPrice(mTicketNow.getPrice());
                showTicket.setDate(mTicketNow.getDate());
                showTicket.setEnd(mTicketNow.getEnd());
                showTicket.setTotalPrice(mTicketNow.getPrice() * num);

                if (mPassengerBeen != null) {
                    showTicket.setPassengerBeen(mPassengerBeen);
                }else{
                    Toast.makeText(SeatActivity.this, "mPasse为空", Toast.LENGTH_SHORT).show();
                    Log.e("xx","mPasser为空");
                }
                showTicket.setSeat(getSeat());
                Log.e("xx","pos:"+pos);
                bundle.putSerializable("show", showTicket);
                goToActivity(ShowActivity.class, "show", bundle);
            }
        });

    }

    private String getBuyTime() {
        return DateUtils.getDateString("", 0);
    }

    private String getSeat() {
       StringBuffer sb = new StringBuffer();
        for (Integer po : pos) {

            sb.append(po / 6 + 1 + "排" + (char) (po % 6 + 64) + "座\r\n");
        }

        return sb.toString();
    }


    @Override
    public void initDatas() {
        mGvSeatActivity.setAdapter(mAdapterSeatGV);
    }

    @Override
    public void initOthers() {
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivitySelf.finish();
            }
        });
        setTitleCenter("选择座位");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }
}