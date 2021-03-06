package edu.tlyon.familymap.ui.recyclerview;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.HashMap;
import java.util.List;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.Event;
import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Person;

/**
 * Created by tlyon on 4/6/16.
 * Same concept as ExpandableListAdapter, but optimized for SearchActivity.
 */
public class SearchExpandableListAdapter extends BaseExpandableListAdapter{
    private Context context;
    private List<String> expandableListTitle;
    private HashMap <String,List<String>> expandableListDetail;

    public SearchExpandableListAdapter(Context context, List<String> expandableListTitle, HashMap<String, List<String>> expandableListDetail) {
        this.context = context;
        this.expandableListTitle = expandableListTitle;
        this.expandableListDetail = expandableListDetail;
    }
    @Override
    public int getGroupCount() {
        return this.expandableListTitle.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return  this.expandableListTitle.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.expandableListDetail.get(this.expandableListTitle.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String listTitle = (String) getGroup(groupPosition);
        //set up if family
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_group,null);
        }
        TextView listTitleTextView = (TextView) convertView.findViewById(R.id.list_title);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(listTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String id = (String) getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_item, null);
        }
        String group = (String)getGroup(groupPosition);
        //person results
        if(group.equals("People")){
            Person person = ModelData.getInstance().getPersonIdMap().get(id);
            ImageView personIcon = (ImageView) convertView.findViewById(R.id.recycler_child_icon_image);
            TextView name = (TextView) convertView.findViewById(R.id.recycler_child_event_details_top);
            TextView detailsBottom = (TextView) convertView.findViewById(R.id.recycler_child_event_details_bottom);
            detailsBottom.setText("");

            //set icon
            if(person.getGender().equals("m")){
                Drawable icon = new IconDrawable(context, Iconify.IconValue.fa_male).colorRes(R.color.male_icon).sizeDp(40);
                personIcon.setImageDrawable(icon);
            }
            else{
                Drawable icon = new IconDrawable(context,Iconify.IconValue.fa_female).colorRes(R.color.female_icon).sizeDp(40);
                personIcon.setImageDrawable(icon);
            }
            //set name
            String fullName = person.getFirstName() + " " + person.getLastName();
            name.setText(fullName);
        }

        //event results
        else{
            Event event = ModelData.getInstance().getEventIdMap().get(id);
            ImageView icon = (ImageView) convertView.findViewById(R.id.recycler_child_icon_image);
            Drawable eventIcon = new IconDrawable(context, Iconify.IconValue.fa_map_marker).colorRes(R.color.colorPrimaryDark).sizeDp(40);
            icon.setImageDrawable(eventIcon);

            TextView detailsTop = (TextView) convertView.findViewById(R.id.recycler_child_event_details_top);
            TextView detailsBottom = (TextView) convertView.findViewById(R.id.recycler_child_event_details_bottom);
            String top = event.getEventType() + ": " + event.getCity() + ", " + event.getCountry() + " (" + event.getYear() + ")";
            detailsTop.setText(top);
            Person person = ModelData.getInstance().getPersonIdMap().get(event.getPersonId());
            String bottom = person.getFirstName() + " " + person.getLastName();
            detailsBottom.setText(bottom);
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
