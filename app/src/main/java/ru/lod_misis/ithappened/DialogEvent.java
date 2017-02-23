package ru.lod_misis.ithappened;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import ru.lod_misis.ithappened.adapter.FilterEventAdapter;
import ru.lod_misis.ithappened.adapter.HistoryEventAdapter;
import ru.lod_misis.ithappened.adapter.SpinnerAdapter;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.PastEvent;

public class DialogEvent extends Dialog {

    private SimpleDateFormat format = new SimpleDateFormat("E, dd.MM.yyyy");

    public DialogEvent(Context context) {
        super(context);
    }

    public void initDialogAddEvent() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_event);

        final EditText name = (EditText) findViewById(R.id.et_nameEvent);
        final EditText description = (EditText) findViewById(R.id.et_descriptionEvent);
        TextView cancel = (TextView) findViewById(R.id.tv_cancel);
        TextView add = (TextView) findViewById(R.id.tv_add);

        final RadioButton ratingBar = (RadioButton) findViewById(R.id.rb_ratingBar);
        final RadioButton number = (RadioButton) findViewById(R.id.rb_number);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (name.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "Пустое названия события!", Toast.LENGTH_SHORT).show();
                }else if (!ratingBar.isChecked() && !number.isChecked()){
                    Toast.makeText(getContext(), "Не указан способ кастомизации!", Toast.LENGTH_SHORT).show();
                }else {
                    String nameEvent = name.getText().toString();
                    String descriptionEvent = description.getText().toString();
                    boolean isMark = ratingBar.isChecked();
                    boolean isNumber = number.isChecked();
                    boolean isMain = false;
                    Date lastModified = new Date();
                    Event event = new Event(nameEvent, descriptionEvent, isMain, isMark, isNumber, lastModified);
                    Controller.saveEvent(event);
                    Toast.makeText(getContext(), "Событие добавлено!", Toast.LENGTH_SHORT).show();
                    cancel();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    public void initDialogAddPastEvent(final Event event) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_past_event);

        TextView tv_nameEvent = (TextView) findViewById(R.id.tv_nameEvent);
        final TextView tv_maxChar = (TextView) findViewById(R.id.tv_maxChar);
        tv_maxChar.setText("0/140");

        TextView tv_numberParameter = (TextView) findViewById(R.id.tv_numberParameter);
        final EditText et_numberParameter = (EditText) findViewById(R.id.et_numberParameter);

        TextView tv_commentParameter = (TextView) findViewById(R.id.tv_commentParameter);
        final EditText et_commentParameter = (EditText) findViewById(R.id.et_commentParameter);

        et_commentParameter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                tv_maxChar.setText(s.length() + "/140");
                if (s.length() == 140) tv_maxChar.setTextColor(Color.RED);
                tv_maxChar.setTextColor(Color.BLACK);
            }
        });

        TextView tv_ratingParameter = (TextView) findViewById(R.id.tv_ratingParameter);
        final RatingBar rb_ratingParameter = (RatingBar) findViewById(R.id.rb_ratingParameter);
        rb_ratingParameter.setNumStars(5);

        final Spinner sp_changeDataFix = (Spinner) findViewById(R.id.sp_changeDataFix);

        final List<String> listDate = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Calendar calendar = new GregorianCalendar();
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            listDate.add(format.format(calendar.getTime()));
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, listDate);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_changeDataFix.setAdapter(dataAdapter);

        TextView tv_cancel = (TextView) findViewById(R.id.tv_cancelV2);
        TextView tv_ok = (TextView) findViewById(R.id.tv_okV2);

        RelativeLayout rl_numberParameter = (RelativeLayout) findViewById(R.id.rl_numberParameter);
        RelativeLayout rl_commentParameter = (RelativeLayout) findViewById(R.id.rl_commentParameter);
        RelativeLayout rl_ratingParameter = (RelativeLayout) findViewById(R.id.rl_ratingParameter);

        tv_nameEvent.setText(event.getName());
        tv_numberParameter.setText(event.getTitleNumberParameter());
        tv_commentParameter.setText(event.getTitleCommentParameter());
        tv_ratingParameter.setText(event.getTitleRatingParameter());

        if (!event.isNumber()) {
            rl_numberParameter.setVisibility(View.GONE);
        }

        if (!event.isComment()) {
            rl_commentParameter.setVisibility(View.GONE);
        }

        if (!event.isMark()) {
            rl_ratingParameter.setVisibility(View.GONE);
        }

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numberParameter = 0;
                if (event.isNumber()) {
                    if (et_numberParameter.getText().toString().length() != 0) numberParameter = Integer.parseInt(et_numberParameter.getText().toString());
                }

                String commentParameter = "";
                if (event.isComment()) {
                    commentParameter = et_commentParameter.getText().toString();
                }

                int ratingBarPastEvent = 0;
                if (event.isMark()) {
                    ratingBarPastEvent = (int) rb_ratingParameter.getRating();
                }



                Date lastModified = new Date();
                Date timeHappenedEvent = new Date();
                PastEvent pastEvent = new PastEvent(timeHappenedEvent, ratingBarPastEvent, numberParameter, commentParameter, lastModified);
                Controller.savePastEvent(event, pastEvent);
                cancel();
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    public void initDialogCustomizationEvent(final Event event) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_customization_event);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final EditText name = (EditText) findViewById(R.id.et_dialog_customization_event_name);
        final EditText description = (EditText) findViewById(R.id.et_dialog_customization_event_description);
        final TextView cancel = (TextView) findViewById(R.id.tv_dialog_customization_event_cancel);
        TextView add = (TextView) findViewById(R.id.tv_dialog_customization_event_add);
        TextView addEventOnMainWindow = (TextView) findViewById(R.id.tv_dialog_customization_event_add_event_on_mainWindow);

        final List<Event> eventList = Controller.getListEventsMainFromDB();
        if (eventList.size() == 6) {
            addEventOnMainWindow.setVisibility(View.GONE);
        }else {
            spinner.setVisibility(View.GONE);
        }

        SpinnerAdapter adapter = new SpinnerAdapter(getContext(), android.R.layout.simple_spinner_item, Controller.getArrayNameEventIsMainTrue(eventList));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        name.setText(event.getName());
        description.setText(event.getDescription());
        final int[] count = new int[] {0};
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (count[0] == 0) {
                    SpinnerAdapter.flag = true;
                    count[0] = 1;
                    return;
                }
                Event selectedEvent = eventList.get(position);
                selectedEvent.setMain(false);
                event.setMain(true);
                Controller.updateEvent(selectedEvent);
                Controller.updateEvent(event);
                Toast.makeText(getContext(), "Выбранное событие помещенно на главный экран", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        addEventOnMainWindow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                event.setMain(true);
                Controller.updateEvent(event);
                Toast.makeText(getContext(), "Выбранное событие помещенно на главный экран", Toast.LENGTH_SHORT).show();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!event.getName().equals(name.getText().toString()) || !event.getDescription().equals(description.getText().toString())) {
                    event.setName(name.getText().toString());
                    event.setDescription(description.getText().toString());
                    Controller.updateEvent(event);
                    cancel();
                }else {
                    cancel();
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });
    }

    public void initDialogFilterHistoryEvents() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_filter_history_event);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final ListView listView = (ListView) findViewById(R.id.lv_filterOnEvent);
        final TextView beforeData = (TextView) findViewById(R.id.tv_before);
        final TextView afterData = (TextView) findViewById(R.id.tv_after);
        final LinearLayout linearLayout = (LinearLayout) findViewById(R.id.ll_filterOnData);

        final String[] filter = new String[] {"По событию", "По дате"};
        ArrayAdapter adapterForSpinner = new ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, filter);
        adapterForSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterForSpinner);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (filter[position].equals(filter[0])) {
                    beforeData.setVisibility(View.GONE);
                    afterData.setVisibility(View.GONE);
                    linearLayout.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                } else {
                    beforeData.setVisibility(View.VISIBLE);
                    afterData.setVisibility(View.VISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                    listView.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }

    public void initDialogFilterOnEvent(Activity activity, final List<PastEvent> listForAdapter, final HistoryEventAdapter historyEventAdapter) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_on_event);

        final List<String> listIdCheckedEvent = new ArrayList<>();

        TextView cancel = (TextView) findViewById(R.id.tv_cancel);
        TextView ok = (TextView) findViewById(R.id.tv_ok);

        final ListView listView = (ListView) findViewById(R.id.lv_events);
        FilterEventAdapter filterEventAdapter = new FilterEventAdapter(activity, R.layout.item_filter_on_event, Controller.getEventListFromDB(), listIdCheckedEvent);
        listView.setAdapter(filterEventAdapter);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<PastEvent> pastEventList = Controller.getFilterPastEvent(listIdCheckedEvent);
                listForAdapter.clear();
                listForAdapter.addAll(pastEventList);
                if (pastEventList.size() == 0) Log.d("Lod", "empty list");
                for (PastEvent p : pastEventList) {
                    Log.d("Lod", String.valueOf(p.getId()));
                }
                historyEventAdapter.notifyDataSetChanged();
                cancel();
            }
        });
    }

    public void initDialogFilterOnDate(final List<PastEvent> listForAdapter, final HistoryEventAdapter historyEventAdapter) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.filter_on_data);

        final DatePicker datePicker_on = (DatePicker) findViewById(R.id.dp_on);
        final DatePicker datePicker_from = (DatePicker) findViewById(R.id.dp_from);

        datePicker_on.setVisibility(View.GONE);
        datePicker_from.setVisibility(View.GONE);

        final Calendar calendar_on = Calendar.getInstance();
        final Calendar calendar_from = Calendar.getInstance();

        final Date[] date = new Date[2];

        final TextView tv_label_on = (TextView) findViewById(R.id.tv_label_ot);
        final TextView tv_label_from = (TextView) findViewById(R.id.tv_label_do);
        final TextView cancel = (TextView) findViewById(R.id.btn_cancel);
        TextView ok = (TextView) findViewById(R.id.btn_ok);

        tv_label_on.setVisibility(View.GONE);
        tv_label_from.setVisibility(View.GONE);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_filter_on_data);

        final String[] mark = new String[1];

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                tv_label_on.setVisibility(View.GONE);
                tv_label_from.setVisibility(View.GONE);
                datePicker_on.setVisibility(View.GONE);
                datePicker_from.setVisibility(View.GONE);

                switch (checkedId) {
                    case R.id.rb_day:
                        mark[0] = "day";
                        break;
                    case R.id.rb_week:
                        mark[0] = "week";
                        break;
                    case R.id.rb_mounth:
                        mark[0] = "month";
                        break;
                    case R.id.rb_allTime:
                        mark[0] = "allTime";
                        break;
                    case R.id.rb_yourPeriod:
                        datePicker_on.setVisibility(View.VISIBLE);
                        datePicker_from.setVisibility(View.VISIBLE);
                        tv_label_on.setVisibility(View.VISIBLE);
                        tv_label_from.setVisibility(View.VISIBLE);
                        mark[0] = "yourPeriod";
                        break;
                }
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancel();
            }
        });

        Calendar today = Calendar.getInstance();

        datePicker_on.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar_on.set(year, monthOfYear, dayOfMonth);
                        date[0] = calendar_on.getTime();
                        Log.d("Lod", date[0].toString());
                    }
                });

        datePicker_from.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {

                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        calendar_from.set(year, monthOfYear, dayOfMonth);
                        date[1] = calendar_from.getTime();
                        Log.d("Lod", date[1].toString());
                    }
                });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mark[0]) {
                    case "day":
                        Controller.sortOnDateDay(listForAdapter);
                        break;
                    case "week":
                        Controller.sortOnDateWeek(listForAdapter);
                        break;
                    case "month":
                        Controller.sortOnDateMonth(listForAdapter);
                        break;
                    case "allTime":
                        Controller.sortOnDateAllTime(listForAdapter);
                        break;
                    case "yourPeriod":
                        Controller.sortOnDateYourPeriod(listForAdapter, date[0], date[1]);
                        break;
                }

                historyEventAdapter.notifyDataSetChanged();
                cancel();
            }
        });
    }
}