package ru.lod_misis.ithappened.fragments;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.DialogEvent;
import ru.lod_misis.ithappened.EditingEvents;
import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.adapter.MainListEventAdapter;
import ru.lod_misis.ithappened.model.Event;


public class FragmentMain extends Fragment {
    private GridView gridView;
    private MainListEventAdapter mainListEventAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        getActivity().setTitle("События");

        gridView = (GridView) view.findViewById(R.id.gv_main);
        gridView.setNumColumns(1);
        gridView.setColumnWidth(GridView.AUTO_FIT);
        gridView.setHorizontalSpacing(10);
        gridView.setStretchMode(GridView.STRETCH_COLUMN_WIDTH);
        initAdapter();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Event event = Controller.getAllEvent().get(position);
                DialogEvent dialogEvent = new DialogEvent(getActivity());
                dialogEvent.initDialogAddPastEvent(event);
                dialogEvent.show();
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//                Event event = Controller.getEventListFromDB().get(position);
//
//                Bundle bundle = new Bundle();
//                bundle.putString("nameEvent", event.getName());
//
//                FragmentEditingEvents fragmentEditingEvents = new FragmentEditingEvents();
//                fragmentEditingEvents.setArguments(bundle);
//                FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.fl_fragmentContainer, fragmentEditingEvents).commit();
//
//                MainActivity.fab.setVisibility(View.INVISIBLE);

                Event event = Controller.getAllEvent().get(position);
                Intent intent = new Intent(getActivity(), EditingEvents.class);
                intent.putExtra("Name",event.getName());
                intent.putExtra("id", event.getId());
                startActivity(intent);
                return false;
            }
        });

        return view;
    }

    private void initAdapter() {
        mainListEventAdapter = new MainListEventAdapter(getActivity(), R.layout.item_tile, Controller.find("isMain = ", new String[]{}, "", "", "" ));
        gridView.setAdapter(mainListEventAdapter);
    }
}