package com.example.ticket.ui.fragment;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ticket.Constant;
import com.example.ticket.R;
import com.example.ticket.base.TBaseFragment;
import com.example.ticket.ui.activity.EndActivity;
import com.example.ticket.ui.activity.TicketSelectActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import library.utils.SharedPrefrencesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends TBaseFragment {
    private TextView mTvHomeFragment;
    private Button mBtEndHomeFragment;
    private Button mBtDateHomeFragment;
    private Button mBtTimeHomeFragment;
    private Button mBtSelectHomeFragment;
    private DatePickerDialog mDatePickerDialog;
    private AlertDialog.Builder mDialog;
    private String[] mStrings = {"7:00-10:00", "10:00-13:00", "13:00-16:00", "16:00-19:00"};
    private String[] mLevel = {"1", "2", "3", "4"};
    private String mEnd;
    private String mDate;
    private String mTimeLevel="1";

    @Override
    public int initRootLayout() {
        return R.layout.fragment_home;
    }

    public void setEnd(String end) {
        mEnd = end;
    }

    @Override
    public void initViews() {
        mTvHomeFragment = (TextView) findViewById(R.id.tv_HomeFragment);
        mBtEndHomeFragment = (Button) findViewById(R.id.bt_end_HomeFragment);
        mBtDateHomeFragment = (Button) findViewById(R.id.bt_date_HomeFragment);
        mBtTimeHomeFragment = (Button) findViewById(R.id.bt_time_HomeFragment);
        mBtSelectHomeFragment = (Button) findViewById(R.id.bt_select_HomeFragment);
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mDate = sDateFormat.format(new Date());
        mBtDateHomeFragment.setText(mDate);

    }

    @Override
    public void onResume() {
        super.onResume();
        mEnd = SharedPrefrencesUtil.getData(mActivitySelf, "x", "end", "终点");
        if (mEnd != null) {
            mBtEndHomeFragment.setText(mEnd);
        }
    }

    @Override
    public void initDatas() {
        setTitleCenter("欢迎订票");
        setTitleLeft("", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showExitDig();
            }
        });
        mBtEndHomeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToActivity(EndActivity.class);
            }
        });
        mBtDateHomeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDig();
            }
        });
        mBtTimeHomeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeDig();
            }
        });
        mBtSelectHomeFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("终点".equals(mBtEndHomeFragment.getText().toString())) {
                    Toast.makeText(mActivitySelf, "请先选择终点站", Toast.LENGTH_SHORT).show();
                } else {
                    if (!Constant.mCurrentEnd.contains(mEnd)) {
                        Constant.mCurrentEnd.add(mEnd);
                    }
                    mDate=mBtDateHomeFragment.getText().toString();

                    SharedPrefrencesUtil.saveData(mActivitySelf, "x", "date", mDate);
                    SharedPrefrencesUtil.saveData(mActivitySelf, "x", "timeLevel", mTimeLevel);
                    goToActivity(TicketSelectActivity.class);
                }
            }
        });
    }

    private void showExitDig() {
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

    private void showDateDig() {
        mDatePickerDialog = new DatePickerDialog(mActivitySelf, R.style.DateDig, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if(monthOfYear<9 && monthOfYear>=0) {
                    //2017-02-21
                    mBtDateHomeFragment.setText(year + "-0" + (monthOfYear + 1) + "-" + dayOfMonth);
                }else{
                    //2017-10-01
                    mBtDateHomeFragment.setText(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                }
            }
        }, 2017, 0, 21);
        DatePicker datePicker = mDatePickerDialog.getDatePicker();
        datePicker.setMaxDate(System.currentTimeMillis()+7*24*60*60*1000);
        datePicker.setMinDate(System.currentTimeMillis());

        mDatePickerDialog.show();
    }

    private void showTimeDig() {
        mDialog = new AlertDialog.Builder(mActivitySelf)
                .setItems(R.array.time, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mBtTimeHomeFragment.setText(mStrings[which]);
                        mTimeLevel=mLevel[which];
                    }
                });
        mDialog.create().show();

    }


    @Override
    public boolean isUseTitleBar() {
        return true;
    }


}
