package ru.lod_misis.ithappened.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.model.Event;

public class MainListEventAdapter extends ArrayAdapter<Event> {
    private Activity context;
    private  int resource;
    private List<Event> events;

    public MainListEventAdapter(Context context, int resource, List<Event> events) {
        super(context, resource, events);
        this.context = (Activity) context;
        this.resource = resource;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resource, null);

        TextView titleEvent = (TextView) convertView.findViewById(R.id.tv_titleEvent);
        titleEvent.setText(events.get(position).getName());


        if (position == events.size() - 1) {
            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) titleEvent.getLayoutParams();
            float scale = context.getResources().getDisplayMetrics().density;
            int margin_1dp = (int) (1 * scale + 0.5f);
            layoutParams.bottomMargin = margin_1dp;
        }

        return convertView;
    }
}
