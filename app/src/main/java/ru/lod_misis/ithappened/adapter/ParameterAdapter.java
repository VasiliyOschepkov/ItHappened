package ru.lod_misis.ithappened.adapter;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import ru.lod_misis.ithappened.R;
import ru.lod_misis.ithappened.model.Parameter;

public class ParameterAdapter extends ArrayAdapter<Parameter> {
    private Activity context;
    private  int resource;
    private List<Parameter> parameters;

    public ParameterAdapter(Context context, int resource, List<Parameter> parameters) {
        super(context, resource, parameters);
        this.context = (Activity) context;
        this.resource = resource;
        this.parameters = parameters;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        convertView = inflater.inflate(resource, null);

        TextView nameParameter = (TextView) convertView.findViewById(R.id.tv_nameParameter);
        nameParameter.setText(parameters.get(position).getNameParameter());

        TextView typeParameter = (TextView) convertView.findViewById(R.id.tv_typeParameter);
        switch (parameters.get(position).getTypeParameter()) {
            case Parameter.TYPE_NUMBER:
                typeParameter.setText("Тип: Численное значение");
                break;
            case Parameter.TYPE_MARK:
                typeParameter.setText("Тип: Шкала оценки");
                break;
            case Parameter.TYPE_COMMENT:
                typeParameter.setText("Тип: Комментарий");
                break;
        }

        return convertView;
    }
}
