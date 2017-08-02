package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by Administrator on 2017/2/19.
 */

public class AdapterLv extends BaseAdapter {
    private Context mContext;
    private int[] mInts;
    private LayoutInflater mLayoutInflater;
    private static int height;
    private static int width;

    public AdapterLv(Context context, int[] ints) {
        mContext = context;
        mInts = ints;
        mLayoutInflater = LayoutInflater.from(context);
        width = DensityUtil.getScreenWidth(mContext);
        height = DensityUtil.getScreenHeight(mContext);
    }

    @Override
    public int getCount() {
        return mInts.length;
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
        convertView = mLayoutInflater.inflate(R.layout.item_lv, parent, false);


        ImageView imageView = (ImageView) convertView.findViewById(R.id.imgv);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) imageView.getLayoutParams();
        /*layoutParams.height=height;
        layoutParams.width=width;
        imageView.setLayoutParams(layoutParams);*/
        imageView.setImageResource(mInts[position]);
        return convertView;
    }
}
