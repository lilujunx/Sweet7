package com.example.ticket.ui.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ticket.R;
import com.example.ticket.base.TBaseFragment;
import com.example.ticket.db.DBConfig;
import com.example.ticket.db.ticket.ShowTicket;
import com.example.ticket.db.ticket.TicketRecord;
import com.example.ticket.ui.activity.ShowActivity;
import com.example.ticket.ui.adapter.ItemLvRecordFragmentAdapter;




import java.util.List;

import xutils.ex.DbException;
import xutils.x;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordFragment extends TBaseFragment {
    private ListView mLvRecordFragment;
    private ItemLvRecordFragmentAdapter mItemLvRecordFragmentAdapter;
    private List<TicketRecord> mTicketRecords;
    private TextView mTvRecordFragment;


    @Override
    public int initRootLayout() {
        return R.layout.fragment_record;
    }

    @Override
    public void initViews() {
        mLvRecordFragment = (ListView) findViewById(R.id.lv_record_fragment);
        mTvRecordFragment = (TextView) findViewById(R.id.tv_record_fragment);

        getDatas();
        mLvRecordFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                ShowTicket showTicket = new ShowTicket();
                TicketRecord ticketRecord = mTicketRecords.get(position);

                showTicket.setId(ticketRecord.getId());
                showTicket.setPassengerBeen(ticketRecord.getPassenger());
                showTicket.setSeat(ticketRecord.getSeat());
                showTicket.setTotalPrice(ticketRecord.getPrice());
                showTicket.setEnd(ticketRecord.getEnd());
                showTicket.setDate(ticketRecord.getDate());
                showTicket.setTime(ticketRecord.getTime());
                showTicket.setType(ticketRecord.getType());
                bundle.putSerializable("show", showTicket);
                goToActivity(ShowActivity.class, "show", bundle);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        getDatas();
    }

    public void getDatas() {
        try {
            mTicketRecords = x.getDb(DBConfig.getDaoConfig()).findAll(TicketRecord.class);
            if (mTicketRecords != null) {
                mItemLvRecordFragmentAdapter = new ItemLvRecordFragmentAdapter(mActivitySelf, mTicketRecords);
                Log.e("xx", "所有TicketRecord:" + mTicketRecords.toString());

                if (mTicketRecords.size() == 0) {
                    mTvRecordFragment.setVisibility(View.VISIBLE);
                    mLvRecordFragment.setVisibility(View.INVISIBLE);
                } else {
                    mLvRecordFragment.setAdapter(mItemLvRecordFragmentAdapter);
                    mItemLvRecordFragmentAdapter.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            final TicketRecord ticketRecord = (TicketRecord) v.getTag();
                            AlertDialog.Builder builder = new AlertDialog.Builder(mActivitySelf);
                            builder.setMessage("确定要删除此记录吗？")
                                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            try {
                                                x.getDb(DBConfig.getDaoConfig()).delete(ticketRecord);
                                                mTicketRecords = x.getDb(DBConfig.getDaoConfig()).findAll(TicketRecord.class);
                                                mItemLvRecordFragmentAdapter.setEntities(mTicketRecords);

                                            } catch (DbException e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    })
                                    .setNegativeButton("否", null);
                            builder.create().show();

                        }
                    });
                    mLvRecordFragment.setVisibility(View.VISIBLE);
                    mTvRecordFragment.setVisibility(View.INVISIBLE);
                }
            } else {
                mLvRecordFragment.setVisibility(View.VISIBLE);
                mTvRecordFragment.setVisibility(View.INVISIBLE);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void initDatas() {
        setTitleCenter("购票查询");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mActivitySelf)
                        .setTitle("退出")
                        .setMessage("确认要退出应用吗？")
                        .setPositiveButton("是的", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mActivitySelf.finish();
                            }
                        })
                        .setNegativeButton("取消", null);
                builder.create().show();
            }
        });
    }
}
