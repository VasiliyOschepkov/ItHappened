package ru.lod_misis.ithappened.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.adapter.PageAdapter;
import ru.lod_misis.ithappened.model.StatisticsData;

public class FragmentStatisticsEvents extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics_events, container, false);

        List<StatisticsData> statisticsDatas = Controller.getStatisticsData();

        ViewPager pager = (ViewPager) view.findViewById(R.id.pager);
        PagerAdapter pagerAdapter = new PageAdapter(getFragmentManager(), statisticsDatas);
        pager.setAdapter(pagerAdapter);

        return view;
    }
}
