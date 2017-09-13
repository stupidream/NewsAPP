package com.txs.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.txs.adapter.NewsFragmentAdapter;
import com.txs.R;

import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.titles.SimplePagerTitleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by ZHAO on 2017/7/8.
 */

public class NewsFragment  extends Fragment{

    @BindView(R.id.newsViewPager)
    ViewPager viewPager;

    @BindView(R.id.sortTab)
    MagicIndicator magicIndicator;

    private static final String[] CHANNELS = new String[]{"国内", "国际", "科技", "军事", "社会", "体育", "教育"};
    private List<String> mDataList = Arrays.asList(CHANNELS);

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_news,container,false);
        ButterKnife.bind(this,view);
        initNewsListFragment();
        initNewsSort();
        return view;
    }


    public void initNewsListFragment(){
        List<Fragment> list=new ArrayList<>();
        list.add(new NewsListFragment("BD29LPUBwangning"));
        list.add(new NewsListFragment("BD29MJTVwangning"));
        list.add(new NewsListFragment("BA8D4A3Rwangning"));
        list.add(new NewsListFragment("BAI67OGGwangning"));
        list.add(new NewsListFragment("BCR1UC1Qwangning"));
        list.add(new NewsListFragment("BA8E6OEOwangning"));
        list.add(new NewsListFragment("BA8FF5PRwangning"));
        NewsFragmentAdapter fragmentAdapter=new NewsFragmentAdapter(getChildFragmentManager(),list);
        viewPager.setAdapter(fragmentAdapter);
    }

    public void initNewsSort(){
        magicIndicator.setBackgroundColor(Color.parseColor("#ffffff"));
        CommonNavigator navigator=new CommonNavigator(getActivity());
        navigator.setAdapter(new CommonNavigatorAdapter() {
            @Override
            public int getCount() {
                return mDataList == null ? 0 : mDataList.size();
            }

            @Override
            public IPagerTitleView getTitleView(Context context, final int index) {
                SimplePagerTitleView simplePagerTitleView=new SimplePagerTitleView(context);
                simplePagerTitleView.setText(mDataList.get(index));
                simplePagerTitleView.setTextSize(15);
                simplePagerTitleView.setNormalColor(Color.parseColor("#999999"));
                simplePagerTitleView.setSelectedColor(Color.parseColor("#000000"));
                simplePagerTitleView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        viewPager.setCurrentItem(index);
                    }
                });
                return simplePagerTitleView;
            }

            @Override
            public IPagerIndicator getIndicator(Context context) {
                LinePagerIndicator linePagerIndicator=new LinePagerIndicator(context);
                linePagerIndicator.setMode(LinePagerIndicator.MODE_WRAP_CONTENT);
                linePagerIndicator.setColors(Color.parseColor("#000000"));
                return linePagerIndicator;
            }
        });
        magicIndicator.setNavigator(navigator);
        ViewPagerHelper.bind(magicIndicator,viewPager);
    }
}
