package ru.lod_misis.ithappened.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.adapter.StatisticsAdapter;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.StatisticsData;

public class PageFragment extends Fragment {
    StatisticsData statisticsData;
    int pageNumber;

    public PageFragment(StatisticsData statisticsData, int page) {
        this.statisticsData = statisticsData;
        pageNumber = page;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, null);

        TextView month = (TextView) view.findViewById(R.id.tv_moth);
        month.setText(statisticsData.getMonth());

        HashMap<Integer, Integer> data = statisticsData.getData();
        List<Integer> percentList = new ArrayList<>();
        List<Integer> idEventList = new ArrayList<>();

        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            percentList.add(entry.getValue());
            idEventList.add(entry.getKey());
        }

        List<Event> eventList = Controller.getEventFromDBOnId(idEventList);

        initChar(view, percentList, eventList);
        initListView(view, eventList, percentList);

        return view;
    }

    private void initListView(View view, final List<Event> eventList, List<Integer> percentList) {
        ListView listView = (ListView) view.findViewById(R.id.lv_eventStatistic);
        listView.setAdapter(new StatisticsAdapter(getActivity(), R.layout.item_statistics_list_char, eventList, percentList));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Bundle bundle = new Bundle();
                bundle.putInt("idEvent", eventList.get(position).getId());

                FragmentStatisticsTargetEvent fragmentStatisticsTargetEvent = new FragmentStatisticsTargetEvent();
                fragmentStatisticsTargetEvent.setArguments(bundle);
                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fl_fragmentContainer, fragmentStatisticsTargetEvent).commit();
            }
        });
    }

    private void initChar(View view, List<Integer> percentList, List<Event> eventList) {
        PieChart mChart = (PieChart) view.findViewById(R.id.chart);

        // configure pie chart
        mChart.setUsePercentValues(true);
        mChart.setHoleColorTransparent(true);
//        mChart.setDescription("Smartphones Market Share");

        // enable hole and configure
//        mChart.setDrawHoleEnabled(true);
//        mChart.setHoleColorTransparent(true);
//        mChart.setHoleRadius(7);
//        mChart.setTransparentCircleRadius(10);

        // enable rotation of the chart by touch
//        mChart.setRotationAngle(0);
//        mChart.setRotationEnabled(true);

        // add data
        addData(mChart, percentList, eventList);

        // customize legends
//        Legend l = mChart.getLegend();
//        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
//        l.setXEntrySpace(7);
//        l.setYEntrySpace(5);
    }

    private void addData(PieChart mChart, List<Integer> percentList, List<Event> eventList) {
        float[] yData = { 5, 10, 15, 30, 40 };
        String[] xData = { "Sony", "Huawei", "LG", "Apple", "Samsung" };

        ArrayList<Entry> yVals = new ArrayList<>();

        for (int i = 0; i < percentList.size(); i++) {
            yVals.add(new Entry(percentList.get(i), i));
        }

        ArrayList<String> xVals = new ArrayList<String>();

        for (int i = 0; i < eventList.size(); i++) {
            xVals.add(eventList.get(i).getName());
        }

//        for (int i = 0; i < xData.length; i++)
//            xVals.add(xData[i]);

        // create pie data set
        PieDataSet dataSet = new PieDataSet(yVals, "Market Share");
//        dataSet.setSliceSpace(3);
//        dataSet.setSelectionShift(5);

        // add many colors
        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());
        dataSet.setColors(colors);

        // instantiate pie data object now
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.GRAY);

        mChart.setData(data);

        // undo all highlights
//        mChart.highlightValues(null);

        // update pie chart
        mChart.invalidate();
    }
}
