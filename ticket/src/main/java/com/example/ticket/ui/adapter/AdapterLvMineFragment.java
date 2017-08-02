package com.example.ticket.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.ticket.R;


/**
 * Created by Administrator on 2017/1/17.
 */

public class AdapterLvMineFragment extends BaseAdapter {
    private String[] mDatas;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public AdapterLvMineFragment(String[] datas, Context context) {
        mDatas = datas;
        mContext = context;
        mLayoutInflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView=mLayoutInflater.inflate(R.layout.item_lv_mine_fragment,parent,false);
        TextView textView= (TextView) convertView.findViewById(R.id.tv_options_mine_fragment);
        textView.setText(mDatas[position]);
        return convertView;
    }
}
