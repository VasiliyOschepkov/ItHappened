package ru.lod_misis.ithappened.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.model.Statistics;
import ru.lod_misis.ithappened.model.StatisticsData;

public class FragmentStatisticsTargetEvent extends Fragment {
    private int progressType;
    private List<StatisticsData> list;
    private LineChart lineChart;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_statistics_target_event, container, false);

        Bundle bundle = getArguments();
        int idEvent = bundle.getInt("idEvent");

        list = Controller.getStatisticsDataOnEvent(idEvent);

        TextView filter = (TextView) view.findViewById(R.id.tv_filter_event);
        TextView typeShow = (TextView) view.findViewById(R.id.tv_type_show);
        lineChart = (LineChart) view.findViewById(R.id.char_statistics_event);
        SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        initChar(view, lineChart);

        seekBar.setMax(3);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progressType = progress;
                initChar(view, lineChart);
                lineChart.notifyDataSetChanged();
                lineChart.invalidate();

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        typeShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void initChar(View view, LineChart lineChart) {
        lineChart = (LineChart) view.findViewById(R.id.char_statistics_event);
        ArrayList<Entry> entries = new ArrayList<>();
        StatisticsData statisticsData = list.get(progressType);
        initEntries(entries, statisticsData);

        String type = "";
        switch (statisticsData.getMonth()) {
            case "Day":
                type = "Часы";
                break;
            case "Week":
                type = "Недели";
                break;
            case "Month":
                type = "Месяцы";
                break;
            case "Year":
                type = "Годы";
                break;
        }

        LineDataSet dataset = new LineDataSet(entries , type);
        ArrayList<String> labels = new ArrayList<>();

        initLabels(labels, statisticsData, dataset, entries);

        LineData data = new LineData(labels, dataset);
        lineChart.setData(data);
        lineChart.setDescription("Description");
        dataset.setDrawFilled(true);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
    }

    private void initLabels(ArrayList<String> labels, StatisticsData statisticsData, LineDataSet dataset, ArrayList<Entry> entries) {
        switch (statisticsData.getMonth()) {
            case "Day":
                for (int i = 1; i < 25; i++) {
                    labels.add(String.valueOf(i));
                }
                break;
            case "Week":
                labels.add("Понедельник");
                labels.add("Вторник");
                labels.add("Среда");
                labels.add("Четверг");
                labels.add("Пятница");
                labels.add("Суббота");
                labels.add("Воскресенье");
                break;
            case "Month":
                labels.add("Январь");
                labels.add("Февраль");
                labels.add("Март");
                labels.add("Апрель");
                labels.add("Май");
                labels.add("Июнь");
                labels.add("Июль");
                labels.add("Август");
                labels.add("Сентябрь");
                labels.add("Октябрь");
                labels.add("Ноябрь");
                labels.add("Декабрь");
                break;
            case "Year":
                for (int i = 2010; i < 2017; i++) {
                    labels.add(String.valueOf(i));
                }
                break;
        }
    }

    private void initEntries(ArrayList<Entry> entries, StatisticsData statisticsData) {
        for (Map.Entry<Integer, Integer> integerIntegerEntry : statisticsData.getData().entrySet()) {
            entries.add(new Entry(integerIntegerEntry.getKey(), integerIntegerEntry.getValue()));
        }
    }
}
