package com.example.ticket.ui.fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import com.example.ticket.R;
import com.example.ticket.base.TBaseFragment;
import com.example.ticket.entity.user.User;
import com.example.ticket.ui.activity.CompleteActivity;
import com.example.ticket.ui.activity.MainActivity;
import com.example.ticket.ui.activity.MinePassengerActivity;
import com.example.ticket.ui.activity.PassengerActivity;
import com.example.ticket.ui.adapter.AdapterLvMineFragment;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import library.control.ActivityControl;
import library.utils.SDUtil;
import library.utils.SharedPrefrencesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends TBaseFragment {
    private TextView mTvNameMineFragment;
    private ListView mLvMineFragment;
    private ImageView mImgvIconMineFragment;
    private AdapterLvMineFragment mAdapterLvMineFragment;
    private String[] mStrings = {"基本资料", "我的乘客", "版本升级", "客服电话", "关于"};
    private AlertDialog.Builder mCompleteDig;
    private AlertDialog.Builder mExitDig;
    private MainActivity mMainActivity;
    private Handler mHandler = new Handler();
    private Button mBtExitMineFragment;
    private User mLoger;


    @Override
    public int initRootLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initViews() {
        MainActivity mainActivity = (MainActivity) ActivityControl.getActivity(MainActivity.class);
        mLoger = mainActivity.getLoger();

        mTvNameMineFragment = (TextView) findViewById(R.id.tv_name_mine_fragment);
        mLvMineFragment = (ListView) findViewById(R.id.lv_mine_fragment);
        mImgvIconMineFragment = (ImageView) findViewById(R.id.imgv_icon_mine_fragment);
        mBtExitMineFragment = (Button) findViewById(R.id.bt_exit_mine_fragment);

        mLvMineFragment.setAdapter(new ArrayAdapter<String>(mActivitySelf, android.R.layout.simple_list_item_1, mStrings));
        mMainActivity = (MainActivity) this.getActivity();
        mBtExitMineFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doExit();
            }
        });
    }

    @Override
    public void initDatas() {
        setTitleCenter("个人中心");
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

        mLvMineFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
//                    "基本资料", "我的乘客","版本升级","客服电话", "关于"
                    case 0:
                        goToActivity(CompleteActivity.class);
                        break;
                    case 1:
                        goToActivity(MinePassengerActivity.class);
                        break;
                    case 2:
                        Toast.makeText(mMainActivity, "您现在已经是最新版本了", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(mMainActivity, "12345678", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(mMainActivity, "CopyRight ©夏文浩", Toast.LENGTH_LONG).show();
                        break;
                }
            }
        });

    }

    private void doPareare() {

            try {
                ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SDUtil.getExternalStoragePath() + "/haokeyun/x.txt"));
                mLoger = (User) ois.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }

        if (mLoger.getUid() == null||"".equals(mLoger.getUid())) {
            mTvNameMineFragment.setText(mLoger.getPhone());
            Glide.with(mActivitySelf).load(R.drawable.portrait).asBitmap().centerCrop().error(R.drawable.portrait)
                    .into(new BitmapImageViewTarget(mImgvIconMineFragment) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(mActivitySelf.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            mImgvIconMineFragment.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        } else {
            mTvNameMineFragment.setText(mLoger.getScreen_name());
            Glide.with(mActivitySelf).load(mLoger.getIcon()).asBitmap().centerCrop().error(R.drawable.portrait)
                    .into(new BitmapImageViewTarget(mImgvIconMineFragment) {
                        @Override
                        protected void setResource(Bitmap resource) {
                            RoundedBitmapDrawable circularBitmapDrawable =
                                    RoundedBitmapDrawableFactory.create(mActivitySelf.getResources(), resource);
                            circularBitmapDrawable.setCircular(true);
                            mImgvIconMineFragment.setImageDrawable(circularBitmapDrawable);
                        }
                    });
        }

        if (!mLoger.isComplete()) {
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    doComplete();
                }
            }, 500);
        }

    }


    private void doComplete() {
        mCompleteDig = new AlertDialog.Builder(mActivitySelf)
                .setTitle("提示：")
                .setMessage("您尚未完善个人信息，请先完善")

                .setPositiveButton("好", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Bundle bundle=new Bundle();
                        bundle.putSerializable("loger",mLoger);
                        goToActivity(CompleteActivity.class,"x",bundle);
                    }
                });
        AlertDialog alertDialog = mCompleteDig.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.setCancelable(false);
        alertDialog.show();
    }

    private void doExit() {
        mExitDig = new AlertDialog.Builder(mActivitySelf)
                .setMessage("确定要注销吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPrefrencesUtil.saveData(mActivitySelf, "x", "isLogin", false);
                        Toast.makeText(mActivitySelf, "注销完成", Toast.LENGTH_SHORT).show();
                        mLoger=null;
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mMainActivity.getRbReserveMainActivity().setChecked(true);
                            }
                        }, 1000);

                    }
                })
                .setNegativeButton("取消", null);
        mExitDig.create().show();
    }

    @Override
    public void onResume() {
        super.onResume();
        doPareare();
    }
}
