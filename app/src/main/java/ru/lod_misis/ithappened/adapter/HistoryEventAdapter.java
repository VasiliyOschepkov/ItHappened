package ru.lod_misis.ithappened.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;

import java.text.SimpleDateFormat;
import java.util.List;

import ru.lod_misis.ithappened.Controller;
import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.PastEvent;

public class HistoryEventAdapter extends BaseSwipeAdapter {
    private Context context;
    private List<PastEvent> listPastEvents;
    private SwipeLayout swipeLayout;
    private SimpleDateFormat format = new SimpleDateFormat("dd.MM.yy  HH:mm:ss");

    public HistoryEventAdapter(Context context, List<PastEvent> listPastEvents) {
        this.context = context;
        this.listPastEvents = listPastEvents;
    }

    @Override
    public int getSwipeLayoutResourceId(int position) {
        return R.id.swipe;
    }

    @Override
    public View generateView(int position, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_view_item, null);
        swipeLayout = (SwipeLayout) view.findViewById(getSwipeLayoutResourceId(position));
        return view;
    }

    @Override
    public void fillValues(final int position, View convertView) {
        TextView name = (TextView) convertView.findViewById(R.id.tv_name);
        TextView date = (TextView) convertView.findViewById(R.id.tv_data);
        TextView more = (TextView) convertView.findViewById(R.id.tv_other);
        TextView delete = (TextView) convertView.findViewById(R.id.tv_delete);

//        Event event = Controller.getEventOnDateFromDB(listPastEvents.get(position));
//        if (event != null) {
//            name.setText(event.getName());
//            date.setText(format.format(listPastEvents.get(position).getDateEventHappened()));
//        }

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                swipeLayout.close(false);
                listPastEvents.remove(position);
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getCount() {
        return listPastEvents.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
