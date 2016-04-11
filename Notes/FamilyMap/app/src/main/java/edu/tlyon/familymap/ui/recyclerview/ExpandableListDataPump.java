package edu.tlyon.familymap.ui.recyclerview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import edu.tlyon.familymap.model.Event;
import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Person;

/**
 * Created by tlyon on 4/5/16.
 */
public class ExpandableListDataPump {
    public static HashMap<String, List<String>> getData(Person person){
        HashMap<String,List<String>> expandableListDetail = new HashMap<>();
        List<String> events = ModelData.getInstance().getPersonEventsMap().get(person.getPersonId());
        List<String> family = new ArrayList<>();
        //add parents to list
        if(!person.getFatherId().equals("")){
            family.add(person.getFatherId());
        }
        if(!person.getMotherId().equals("")){
            family.add(person.getMotherId());
        }
        //add spouse
        if(!person.getSpouseId().equals("")){
            family.add(person.getSpouseId());
        }
        Set<String> children = person.getChildren();
        //add children to list
        for(String childId:children){
            family.add(childId);
        }
        Collections.sort(events, new Comparator<String>() {
            @Override
            public int compare(String lhs, String rhs) {
                Event left = ModelData.getInstance().getEventIdMap().get(lhs);
                Event right = ModelData.getInstance().getEventIdMap().get(rhs);
                return Integer.parseInt(left.getYear()) - Integer.parseInt(right.getYear());
            }
        });

        expandableListDetail.put("Events", events);
        expandableListDetail.put("Family", family);

        return expandableListDetail;
    }


}
