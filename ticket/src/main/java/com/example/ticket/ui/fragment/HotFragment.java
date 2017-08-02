package com.example.ticket.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.ticket.R;
import com.example.ticket.base.TBaseFragment;
import com.example.ticket.ui.activity.MainActivity;

import java.util.ArrayList;
import java.util.List;

import library.utils.SharedPrefrencesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends TBaseFragment{
    private ListView mLvHotFragment;
    private List<String> mDatas=new ArrayList<>();


    @Override
    public int initRootLayout() {
        return R.layout.fragment_hot;
    }

    @Override
    public void initViews() {
        mDatas.add("安顺");
        mDatas.add("遵义");
        mDatas.add("武汉");
        mLvHotFragment = (ListView) findViewById(R.id.lv_hot_fragment);
        mLvHotFragment.setAdapter(new ArrayAdapter<String>(mActivitySelf,android.R.layout.simple_list_item_1,mDatas));
        mLvHotFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPrefrencesUtil.saveData(mActivitySelf,"x","end",mDatas.get(position));
                goToActivity(MainActivity.class);
            }
        });
    }

    @Override
    public boolean isUseTitleBar() {
        return false;
    }

    @Override
    public void initDatas() {

    }
}
