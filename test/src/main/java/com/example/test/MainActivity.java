package com.example.test;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    private ListView mLv;
    private ViewPager mVp;
    private int[] mInts={R.drawable.background,R.drawable.background1,R.drawable.background2,R.drawable.background5,
            R.drawable.background4,R.drawable.background3};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLv = (ListView) findViewById(R.id.lv);
       // mVp = (ViewPager) findViewById(R.id.vp);
        mLv.setAdapter(new AdapterLv(this,mInts));
        //mVp.setAdapter(new AdapterVp(this.getSupportFragmentManager()));

    }


}
