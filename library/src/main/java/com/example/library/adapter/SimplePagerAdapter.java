package com.example.library.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2016/3/11.
 */
public abstract class SimplePagerAdapter<E> extends PagerAdapter {

    public List<E> mEntities;
    public Context mContext;
    public LayoutInflater mLayoutInflater;
    
    
    public SimplePagerAdapter(List<E> entities, Context context) {
        mEntities = entities;
        mContext = context;
        mLayoutInflater=LayoutInflater.from(context);
    }
    
    public void setVPNoEnd(ViewPager vp){
        vp.setCurrentItem(mEntities.size()*100000/2);
    }
    
    @Override
    public int getCount() {
        return mEntities.size()*100000;
    }
    
    public  abstract int setLayouResID();
    
    public abstract  void initView(View itemView, ViewGroup container, int position, E entity);
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
    
        View itemView=mLayoutInflater.inflate(setLayouResID(),container,false);
        initView(itemView,container,position,mEntities.get(position%mEntities.size()));
        container.addView(itemView);
        return itemView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
