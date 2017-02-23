package ru.lod_misis.ithappened;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.UUID;

import ru.lod_misis.ithappened.adapter.ParameterAdapter;
import ru.lod_misis.ithappened.model.Event;
import ru.lod_misis.ithappened.model.Parameter;

public class EditingEvents extends AppCompatActivity {
    boolean isNew = true;
    ArrayList<Parameter> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editing_events);
        setTitle("Редактирование события");

        final String[] typeParameter = new String[1];
        final Integer[] index = new Integer[1];

        final EditText nameEvent = (EditText) findViewById(R.id.et_eventName);
        final EditText nameParameter = (EditText) findViewById(R.id.et_parameter);

        final RadioGroup radioGroup = (RadioGroup) findViewById(R.id.rg_parameter);

        final RadioButton scale = (RadioButton) findViewById(R.id.rb_scale);
        final RadioButton number = (RadioButton) findViewById(R.id.rb_number);
        final RadioButton comment = (RadioButton) findViewById(R.id.rb_comment);

//        final RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.rl_image);

        TextView addParameter = (TextView) findViewById(R.id.tv_addParameter);

        ImageView delete = (ImageView) findViewById(R.id.iv_delete);
        ImageView ok = (ImageView) findViewById(R.id.iv_ok);

        final ListView listView = (ListView) findViewById(R.id.lv_parameters);
        final ParameterAdapter parameterAdapter = new ParameterAdapter(this, R.layout.item_parameter, list);
        listView.setAdapter(parameterAdapter);

        nameEvent.setText("");
        final int[] id = new int[1];
        id[0] = UUID.randomUUID().hashCode();

        Intent intent = getIntent();
        if (intent.getExtras() != null) {
            isNew = false;
            nameEvent.setText(intent.getExtras().getString("Name"));
            id[0] = intent.getExtras().getInt("id");
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_scale:
                        typeParameter[0] = "Шкала оценки";
                        break;
                    case R.id.rb_number:
                        typeParameter[0] = "Численное значение";
                        break;
                    case R.id.rb_comment:
                        typeParameter[0] = "Комментарий";
                        break;
                }
            }
        });

        addParameter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.size() == 3) return;
                if (nameParameter.getText().toString().replaceAll("[\\s\\d]", "").length() == 0) return;

                if ((scale.isChecked() || number.isChecked() || comment.isChecked()) && !nameParameter.getText().toString().isEmpty()) {
                    Parameter parameter = new Parameter(nameParameter.getText().toString(), typeParameter[0]);
                    list.add(parameter);

                    if (scale.isChecked()) {
                        scale.setVisibility(View.GONE);
                    } else if (number.isChecked()) {
                        number.setVisibility(View.GONE);
                    } else {
                        comment.setVisibility(View.GONE);
                    }

                    nameParameter.setText("");
                }
            }
        });

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNew) {
                    Controller.saveEvent(new Event(nameEvent.getText().toString(), "Test description"));
                } else {
                    for (Parameter parameter : list) {
                        if (parameter.getTypeParameter().equals("Численное значение")) {
                            Controller.setNumberParameterEvent(parameter, id[0]);
                        } else if (parameter.getTypeParameter().equals("Шкала оценки")) {
                            Controller.setRatingParameterEvent(parameter, id[0]);
                        } else if (parameter.getTypeParameter().equals("Комментарий")) {
                            Controller.setCommentParameterEvent(parameter, id[0]);
                        }
                    }
                }
                finish();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < list.size(); i++) {
                    parent.getChildAt(i).setBackgroundColor(Color.parseColor("#ffffff"));
                }

                index[0] = position;
                view.setBackgroundColor(Color.parseColor("#f3f3f3"));
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Parameter parameter = list.get(index[0]);
                list.remove((int)index[0]);
                parameterAdapter.notifyDataSetChanged();

                    switch (parameter.getTypeParameter()) {
                        case "Численное значение":
                            number.setVisibility(View.VISIBLE);
                            number.setChecked(false);
                            break;
                        case "Шкала оценки":
                            scale.setVisibility(View.VISIBLE);
                            number.setChecked(false);
                            break;
                        case "Комментарий":
                            comment.setVisibility(View.VISIBLE);
                            number.setChecked(false);
                            break;
                    }
            }
        });
    }


}
