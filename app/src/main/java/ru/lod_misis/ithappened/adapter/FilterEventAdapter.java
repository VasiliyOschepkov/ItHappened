package ru.lod_misis.ithappened.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.PastEvent;

public class FilterEventAdapter extends ArrayAdapter<Event> {
    private Activity context;
    private int resource;
    private List<Event> events;
    private List<String> listIdCheckedEvent;

    public FilterEventAdapter(Context context, int resource, List<Event> events, List<String> listIdCheckedEvent) {
        super(context, resource, events);
        this.context = (Activity) context;
        this.resource = resource;
        this.events = events;
        this.listIdCheckedEvent = listIdCheckedEvent;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resource, null);

        TextView nameEvent = (TextView) convertView.findViewById(R.id.tv_event);
        nameEvent.setText(events.get(position).getName());

        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.cb_checkEvent);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    listIdCheckedEvent.add(String.valueOf(events.get(position).getId()));
                }else {
                    listIdCheckedEvent.remove(events.get(position).getId());
                }
            }
        });

        return convertView;
    }


}
