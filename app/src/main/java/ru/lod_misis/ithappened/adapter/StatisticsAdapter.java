package ru.lod_misis.ithappened.adapter;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.model.Event;

public class StatisticsAdapter extends ArrayAdapter<Event> {
    private Activity context;
    private  int resource;
    private List<Event> eventList;
    private List<Integer> percentList;

    public StatisticsAdapter(Context context, int resource, List<Event> eventList, List<Integer> percentList) {
        super(context, resource, eventList);
        this.context = (Activity) context;
        this.resource = resource;
        this.eventList = eventList;
        this.percentList = percentList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resource, null);

        Log.d("Lod", String.valueOf(percentList.get(position)));

        TextView eventStatistics = (TextView) convertView.findViewById(R.id.tv_eventStatistics);
        eventStatistics.setText(eventList.get(position).getName());

        TextView percent = (TextView) convertView.findViewById(R.id.tv_percent);
        percent.setText(String.valueOf(percentList.get(position)));

        return convertView;
    }
}
