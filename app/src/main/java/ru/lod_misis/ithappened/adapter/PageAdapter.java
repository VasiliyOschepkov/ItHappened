package ru.lod_misis.ithappened.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

import ru.lod_misis.ithappened.fragments.PageFragment;
import ru.lod_misis.ithappened.model.StatisticsData;

public class PageAdapter extends FragmentPagerAdapter {
    List<StatisticsData> statisticsDatas;

    public PageAdapter(FragmentManager fm, List<StatisticsData> statisticsDatas) {
        super(fm);
        this.statisticsDatas = statisticsDatas;
    }

    @Override
    public Fragment getItem(int position) {
        return new PageFragment(statisticsDatas.get(position), position);
    }

    @Override
    public int getCount() {
        return statisticsDatas.size();
    }
}
