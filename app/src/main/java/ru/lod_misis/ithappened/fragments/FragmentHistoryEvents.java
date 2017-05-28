package ru.lod_misis.ithappened.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;


import java.util.List;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.DialogEvent;
import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.adapter.HistoryEventAdapter;
import ru.lod_misis.ithappened.model.PastEvent;


public class FragmentHistoryEvents extends Fragment {
    private ListView listView;
    private TextView filter_on_date;
    private TextView filter_on_event;
    private HistoryEventAdapter historyEventAdapter;
    private List<PastEvent> listForAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_events, container, false);

        listView = (ListView) view.findViewById(R.id.lv_historyEvent);
        filter_on_event = (TextView) view.findViewById(R.id.tv_filter_on_event);
        filter_on_date = (TextView) view.findViewById(R.id.tv_filter_on_data);
//        listForAdapter = Controller.getPastEventListFromDB();

        filter_on_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEvent dialogEvent = new DialogEvent(getActivity());
                dialogEvent.initDialogFilterOnEvent(getActivity(), listForAdapter, historyEventAdapter);
                dialogEvent.show();
            }
        });

        filter_on_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogEvent dialogEvent = new DialogEvent(getActivity());
                dialogEvent.initDialogFilterOnDate(listForAdapter, historyEventAdapter);
                dialogEvent.show();
            }
        });

        initAdapter();
        return view;
    }

    private void initAdapter() {
        historyEventAdapter = new HistoryEventAdapter(getActivity(), listForAdapter);
        listView.setAdapter(historyEventAdapter);
    }


}
