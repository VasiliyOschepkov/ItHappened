package ru.lod_misis.ithappened.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.realm.RealmResults;
import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.DialogEvent;
import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.adapter.MainListEventAdapter;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.PastEvent;

public class FragmentListEvents extends Fragment {
    private GridView gridView;
    private MainListEventAdapter mainListEventAdapter;
    private Event event;
    private RealmResults<Event> events = Controller.getEvents();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("Lod", "onCreateView Fragment ListEvents");

        View view = inflater.inflate(R.layout.fragment_list_events, container, false);

        gridView = (GridView) view.findViewById(R.id.gv_listEvent);
        gridView.setNumColumns(1);
        gridView.setColumnWidth(GridView.AUTO_FIT);
        gridView.setHorizontalSpacing(10);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        initAdapter();

        Spinner spinner = (Spinner) view.findViewById(R.id.sp_filterForEvent);

        List<String>  filterList = new ArrayList<>();
        filterList.add("По алфавиту");
        filterList.add("Последние случившиеся");
        filterList.add("Самые популярные");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, filterList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(dataAdapter);


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        sortOnAlphabet();
                        break;
                    case 1:
                        sortOnLast();
                        break;
                    case 2:
                        sortOnPopularity();
                        break;
                    case 3:
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                event = events.get(position);
                DialogEvent dialogEvent = new DialogEvent(getActivity());
                dialogEvent.initDialogAddPastEvent(event);
                dialogEvent.show();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                event = events.get(position);
                DialogEvent dialogEvent = new DialogEvent(getActivity());
                dialogEvent.initDialogCustomizationEvent(event);
                dialogEvent.show();
                return true;
            }
        });

        return view;
    }

    private void sortOnPopularity() {
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                int countEvent1 = lhs.getListHappenedEvent().size();
                int countEvent2 = rhs.getListHappenedEvent().size();

                int result = 0;

                if (countEvent1 == countEvent2) {
                    result = 0;
                }else if (countEvent1 > countEvent2) {
                    result = -1;
                }else if (countEvent1 < countEvent2) {
                    result = 1;
                }
                return result;
            }
        });
        mainListEventAdapter.notifyDataSetChanged();
    }

    private void sortOnLast() {
        Collections.sort(events, new Comparator<Event>() {
            @Override
            public int compare(Event lhs, Event rhs) {
                int countPastList1 = lhs.getListHappenedEvent().size() - 1;
                int countPastList2 = rhs.getListHappenedEvent().size() - 1;
                if (countPastList1 < 0 && countPastList2 < 0) return 0;
                if (countPastList2 < 0 && countPastList1 > 0) return -1;
                if (countPastList1 < 0 && countPastList2 > 0) return 1;

                PastEvent pastEvent1 = lhs.getListHappenedEvent().get(countPastList1);
                PastEvent pastEvent2 = rhs.getListHappenedEvent().get(countPastList2);
                return pastEvent1.getDateEvent().compareTo(pastEvent2.getLastModified());
            }
        });
        mainListEventAdapter.notifyDataSetChanged();
    }

    private void sortOnAlphabet() {
        events.sort("name");
//        Collections.sort(events, new Comparator<Event>() {
//            @Override
//            public int compare(Event lhs, Event rhs) {
//                return lhs.getName().compareTo(rhs.getName());
//            }
//        });
        mainListEventAdapter.notifyDataSetChanged();
    }

    private void initAdapter() {
        mainListEventAdapter = new MainListEventAdapter(getActivity(), R.layout.item_tile, events);
        gridView.setAdapter(mainListEventAdapter);
    }

    @Override
    public void onStart() {
        Log.d("Lod", "Start Fragment List Event");
        getFragmentManager().beginTransaction().commit();
        super.onStart();
    }
}
