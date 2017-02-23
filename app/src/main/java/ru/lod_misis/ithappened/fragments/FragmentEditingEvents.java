package ru.lod_misis.ithappened.fragments;


import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.adapter.ParameterAdapter;
import ru.lod_misis.ithappened.model.Parameter;

public class FragmentEditingEvents extends Fragment {

    ArrayList<Parameter> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editing_event, container, false);

        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        getActivity().setTitle("Редактирование события");

        final String[] typeParameter = new String[1];

        EditText nameEvent = (EditText) view.findViewById(R.id.et_eventName);
        final EditText nameParameter = (EditText) view.findViewById(R.id.et_parameter);

        final RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.rg_parameter);

        final RadioButton scale = (RadioButton) view.findViewById(R.id.rb_scale);
        final RadioButton number = (RadioButton) view.findViewById(R.id.rb_number);
        final RadioButton comment = (RadioButton) view.findViewById(R.id.rb_comment);

        TextView addParameter = (TextView) view.findViewById(R.id.tv_addParameter);

        ImageView delete = (ImageView) view.findViewById(R.id.iv_delete);
        ImageView ok = (ImageView) view.findViewById(R.id.iv_ok);

        ListView listView = (ListView) view.findViewById(R.id.lv_parameters);
        ParameterAdapter parameterAdapter = new ParameterAdapter(getActivity(), R.layout.item_parameter, list);
        listView.setAdapter(parameterAdapter);

        Bundle bundle = getArguments();
        nameEvent.setText(bundle.getString("nameEvent"));

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
//                if ((scale.isChecked() || number.isChecked() || comment.isChecked()) && nameParameter.getText().toString().isEmpty()) {
                Parameter parameter = new Parameter(nameParameter.getText().toString(), typeParameter[0]);
                list.add(parameter);

                if(scale.isChecked()) {
                    scale.setVisibility(View.GONE);
                }else if (number.isChecked()) {
                    number.setVisibility(View.GONE);
                }else {
                    comment.setVisibility(View.GONE);
                }
//                }
            }
        });



        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        return view;
    }
}
