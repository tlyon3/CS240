package edu.tlyon.familymap.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.Menu;

import edu.tlyon.familymap.model.ModelData;

/**
 * Created by tlyon on 3/22/16.
 */
public class Utils {

    public static void startTopActivity(Context context, boolean newInstance){
        Intent intent = new Intent(context,MainActivity.class);
        if(newInstance){
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        else{
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }
        context.startActivity(intent);
    }

    public static void setShowMenuItems(Menu menu, boolean visible){
        for(int i=0;i<menu.size();i++){
            menu.getItem(i).setVisible(visible);
        }
    }

    public static void logout(Activity activity){
        ModelData.getInstance().setCurrentUser(null);
        ModelData.getInstance().getEventIdMap().clear();
        ModelData.getInstance().getEventTypes().clear();
        ModelData.getInstance().getPersonEventsMap().clear();
        ModelData.getInstance().getPersonIdMap().clear();
        ModelData.getInstance().getFamilyTree().clear();
        ModelData.getInstance().getMaternalAncestors().clear();
        ModelData.getInstance().getPaternalAncestors().clear();
        Intent intent = new Intent(activity,MainActivity.class);
        activity.startActivity(intent);
    }
}
