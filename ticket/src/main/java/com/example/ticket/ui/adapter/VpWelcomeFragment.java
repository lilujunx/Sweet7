package com.example.ticket.ui.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.ticket.R;
import com.example.ticket.base.TBaseApp;

/**
 * Created by Administrator on 2017/1/12.
 */

public class VpWelcomeFragment extends PagerAdapter {
    private Activity mActivity;
    private LayoutInflater mLayoutInflater;
    private int[] mDatas;

    public VpWelcomeFragment(Activity activity, int[] datas) {
        mActivity = activity;
        mDatas=datas;
        mLayoutInflater=mActivity.getLayoutInflater();
    }

    @Override
    public int getCount() {
        return 3;
    }


    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView=mLayoutInflater.inflate(R.layout.item_vp_welcome,container,false);
        ImageView imageView= (ImageView) itemView.findViewById(R.id.imgv_welcome_fragment);
        imageView.setImageResource(mDatas[position]);
        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }


}
