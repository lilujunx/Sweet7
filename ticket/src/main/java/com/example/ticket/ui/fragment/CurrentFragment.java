package com.example.ticket.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ticket.Constant;
import com.example.ticket.R;
import com.example.ticket.base.TBaseFragment;
import com.example.ticket.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import library.utils.SharedPrefrencesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class CurrentFragment extends TBaseFragment{
    private ListView mLvCurrentFragment;
    private List<String> mDatas=new ArrayList<>();



    @Override
    public int initRootLayout() {
        return R.layout.fragment_current;
    }

    @Override
    public void initViews() {
        mDatas= Constant.mCurrentEnd;
        mLvCurrentFragment = (ListView) findViewById(R.id.lv_current_fragment);
        mLvCurrentFragment.setAdapter(new ArrayAdapter<String>(mActivitySelf,android.R.layout.simple_list_item_1,mDatas));
        mLvCurrentFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("xx","选择了"+mDatas.get(position));
                SharedPrefrencesUtil.saveData(mActivitySelf,"x","end",mDatas.get(position));
                goToActivity(MainActivity.class);
            }
        });
    }

    @Override
    public void initDatas() {

    }

    @Override
    public boolean isUseTitleBar() {
        return false;
    }
}
