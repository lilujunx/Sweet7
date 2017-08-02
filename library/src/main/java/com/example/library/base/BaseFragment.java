package com.example.library.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.library.R;
import com.example.library.control.FragmentControl;


/**
 * Created by Administrator on 2016/10/21.
 */
public abstract class BaseFragment extends Fragment {
    
    public String mTag;
    public BaseActivity mActivitySelf;
    public LayoutInflater mLayoutInflater;
    public FragmentManager mFragmentManager;
    public View mViewRoot;
    public TextView mTitleLeft, mTitleCenter, mTitleRight;
    public BaseFragment() {
        mTag=this.getClass().getSimpleName();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentControl.add(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        FragmentControl.remove(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mActivitySelf= (BaseActivity) this.getActivity();
        mLayoutInflater=LayoutInflater.from(mActivitySelf);
        mFragmentManager=this.getFragmentManager();
        
        
        int rootLayout = initRootLayout();

        mViewRoot=mLayoutInflater.inflate(rootLayout,container,false);

        if (TitleBarConfig.isUseTitleBar && isUseTitleBar()) {
            //需要使用标题栏
            addTitleBar(rootLayout);
        } 
    
        initViews();
        initDatas();
        return mViewRoot;
    }
    //当前Activity是不是要标题栏
    public boolean isUseTitleBar() {
        return true;
    }
    
    private void addTitleBar(int rootLayout) {
        LinearLayout linearLayout = new LinearLayout(mActivitySelf);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(layoutParams);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        
        
        View titleView = mLayoutInflater.inflate(TitleBarConfig.titleBarResID, linearLayout, false);
        linearLayout.addView(titleView);
        
        linearLayout.addView(mViewRoot);
    
    
        mViewRoot=linearLayout;
        mTitleLeft = (TextView) titleView.findViewById(R.id.title_left);
        mTitleCenter = (TextView) titleView.findViewById(R.id.title_center);
        mTitleRight = (TextView) titleView.findViewById(R.id.title_right);
        if (mTitleLeft != null) {
            mTitleLeft.setVisibility(View.INVISIBLE);
        }
        if (mTitleRight != null) {
            mTitleRight.setVisibility(View.INVISIBLE);
        }


    }
    
    public void setTitleLeft(String text, View.OnClickListener onClickListener) {
        if (mTitleLeft != null) {
            mTitleLeft.setVisibility(View.VISIBLE);
            mTitleLeft.setText(text);
            if (onClickListener != null) {
                mTitleLeft.setOnClickListener(onClickListener);
            }
          
        }
        
    }
    
    public View findViewById(int resId){
        return   mViewRoot.findViewById(resId);
    }
    
    public void setTitleLeft(String text) {
        setTitleLeft(text, null);
    }
    
    public void setTitleRight(String text, View.OnClickListener onClickListener) {
        if (mTitleRight != null) {
            mTitleRight.setVisibility(View.VISIBLE);
            mTitleRight.setText(text);
            if (onClickListener != null) {
                mTitleRight.setOnClickListener(onClickListener);
            }
        }
        
    }
    
    public void setTitleRight(String text) {
        setTitleRight(text, null);
    }
    
    
    public void setTitleCenter(String text) {
        if (mTitleCenter != null) {
            mTitleCenter.setText(text);
        }
    }
    
    public abstract int initRootLayout();
    
    public abstract void initViews();
    
    public abstract void initDatas();
    
    /**
     * 启动Activity
     **/
    public void goToActivity(Class activity) {
        goToActivity(activity, null, null);
    }
    
    public void goToActivity(Class activity, String key, Bundle bundle) {
        Intent intent = new Intent(mActivitySelf, activity);
        
        if (key != null && bundle != null) {
            intent.putExtra(key, bundle);
        }
        
        this.startActivity(intent);
    }
    
    /**启动Activity**/
    
    
    /**
     * 启动Service
     **/
    public void goToService(Class service) {
        goToService(service, null, null);
    }
    
    public void goToService(Class service, String key, Bundle bundle) {
        Intent intent = new Intent(mActivitySelf, service);
        
        if (key != null && bundle != null) {
            intent.putExtra(key, bundle);
        }
    
        mActivitySelf.startService(intent);
    }
    
    /**启动Service**/
    
    
    /**
     * 快捷操作Fragmnet
     **/
    public void addFrag(int desId, BaseFragment fragment) {
        
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(desId, fragment, fragment.mTag);
        transaction.commitAllowingStateLoss();

//        fragmentManager.findFragmentByTag()
    }
    
    public void removeFrag(BaseFragment fragment) {
        
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.remove(fragment);
        transaction.commitAllowingStateLoss();
        
    }
    
    public void replaceFrag(int desId, BaseFragment fragment) {
        
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(desId, fragment, fragment.mTag);
        transaction.commitAllowingStateLoss();

//        fragmentManager.findFragmentByTag()
    }
    
    public void hideFrag(BaseFragment fragment) {
        
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.hide(fragment);
        transaction.commit();
        
    }
    
    public void showFrag(BaseFragment fragment) {
        
        FragmentManager fragmentManager = this.getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.show(fragment);
        transaction.commitAllowingStateLoss();
        
    }
    /**快捷操作Fragmnet**/
}

