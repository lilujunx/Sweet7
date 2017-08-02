package com.example.ticket.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.quicksidebar.QuickSideBarTipsView;
import com.bigkoo.quicksidebar.QuickSideBarView;
import com.bigkoo.quicksidebar.listener.OnQuickSideBarTouchListener;
import com.example.ticket.R;
import com.example.ticket.base.TBaseFragment;
import com.example.ticket.entity.end.City;
import com.example.ticket.entity.end.DataConstants;
import com.example.ticket.ui.activity.MainActivity;
import com.example.ticket.ui.adapter.CityListWithHeadersAdapter;
import com.example.ticket.ui.views.DividerDecoration;
import com.example.ticket.ui.views.OnItemClick;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.timehop.stickyheadersrecyclerview.StickyRecyclerHeadersDecoration;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import library.utils.SharedPrefrencesUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class AllFragment extends TBaseFragment implements OnQuickSideBarTouchListener {

    private RecyclerView mRecyclerView;
    private QuickSideBarTipsView mQuickSideBarTipsView;
    private QuickSideBarView mQuickSideBarView;
    HashMap<String, Integer> letters = new HashMap<>();

    @Override
    public int initRootLayout() {
        return R.layout.fragment_all;
    }

    @Override
    public boolean isUseTitleBar() {
        return false;
    }

    @Override
    public void initViews() {
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mQuickSideBarTipsView = (QuickSideBarTipsView) findViewById(R.id.quickSideBarTipsView);
        mQuickSideBarView = (QuickSideBarView) findViewById(R.id.quickSideBarView);

    }

    @Override
    public void initDatas() {
        //设置监听
        mQuickSideBarView.setOnQuickSideBarTouchListener(this);
        //设置列表数据和浮动header
        final LinearLayoutManager layoutManager = new LinearLayoutManager(mActivitySelf, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        // Add the sticky headers decoration
        CityListWithHeadersAdapter adapter = new CityListWithHeadersAdapter();
        adapter.setOnItemClick(new OnItemClick() {
            @Override
            public void onClick(int position,String text) {
                SharedPrefrencesUtil.saveData(mActivitySelf,"x","end",text);
                goToActivity(MainActivity.class);
            }

            @Override
            public void onLongClick(int position,String text) {
                SharedPrefrencesUtil.saveData(mActivitySelf,"x","end",text);
                goToActivity(MainActivity.class);
            }
        });


        //GSON解释出来
        Type listType = new TypeToken<LinkedList<City>>() {
        }.getType();
        Gson gson = new Gson();
        LinkedList<City> cities = gson.fromJson(DataConstants.cityDataList, listType);

        ArrayList<String> customLetters = new ArrayList<>();

        int position = 0;
        for (City city : cities) {
            String letter = city.getFirstLetter();
            //如果没有这个key则加入并把位置也加入
            if (!letters.containsKey(letter)) {
                letters.put(letter, position);
                customLetters.add(letter);
            }
            position++;
        }

        //不自定义则默认26个字母
        mQuickSideBarView.setLetters(customLetters);
        adapter.addAll(cities);
        mRecyclerView.setAdapter(adapter);

        final StickyRecyclerHeadersDecoration headersDecor = new StickyRecyclerHeadersDecoration(adapter);
        mRecyclerView.addItemDecoration(headersDecor);

        // Add decoration for dividers between list items
        mRecyclerView.addItemDecoration(new DividerDecoration(mActivitySelf));
    }

    @Override
    public void onLetterChanged(String letter, int position, float y) {
        mQuickSideBarTipsView.setText(letter, position, y);
        //有此key则获取位置并滚动到该位置
        if (letters.containsKey(letter)) {
            mRecyclerView.scrollToPosition(letters.get(letter));
        }
    }

    @Override
    public void onLetterTouching(boolean touching) {
        //可以自己加入动画效果渐显渐隐
        mQuickSideBarTipsView.setVisibility(touching ? View.VISIBLE : View.INVISIBLE);
    }
}
