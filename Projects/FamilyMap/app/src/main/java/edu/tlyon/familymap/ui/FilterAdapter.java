package edu.tlyon.familymap.ui;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.Settings;

/**
 * Created by tlyon on 4/9/16.
 * ArrayAdapter class to handle the dynamic list of event types
 */
public class FilterAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> eventTypes;
    private int layoutResourceId = R.layout.filter_list_item;


    public FilterAdapter(Context context, int layoutResourceId, List<String> eventTypes) {
        super(context, layoutResourceId, eventTypes);
        this.layoutResourceId = layoutResourceId;
        this.eventTypes = eventTypes;
        this.context = context;

        //add gender, mother side, father side filters (if not already there)
        if (!eventTypes.contains("male")) {
            eventTypes.add("mother's side");
            eventTypes.add("father's side");
            eventTypes.add("male");
            eventTypes.add("female");
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        try {
            View row = convertView;
            Holder holder;
            if (row == null) {
                LayoutInflater inflater = ((Activity) context).getLayoutInflater();
                row = inflater.inflate(layoutResourceId, parent, false);

                holder = new Holder();
                holder.rowCheckBox = (CheckBox) row.findViewById(R.id.filter_item_switch);
                holder.rowText = (TextView) row.findViewById(R.id.filter_item_title);
                holder.rowText.setText(eventTypes.get(position));
                final String eventType = (String) holder.rowText.getText();
                holder.rowCheckBox.setChecked(Settings.getInstance().getFilterDisplaySettings().get(eventType));

                holder.rowCheckBox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Settings.getInstance().getFilterDisplaySettings().put(eventType, ((CheckBox) v).isChecked());
                    }
                });
                row.setTag(holder);
                return row;
            } else {
                return row;
            }
        }
        catch (NullPointerException ex){
            Log.e("FilterAdapter","Null pointer on getFilterDisplay",ex);
        }
        return null;
    }

    private class Holder {
        CheckBox rowCheckBox;
        TextView rowText;
    }
}
