package edu.tlyon.familymap.webAccess.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import edu.tlyon.familymap.model.Event;
import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Settings;
import edu.tlyon.familymap.ui.Utils;
import edu.tlyon.familymap.webAccess.ServerFacade;

/**
 * Created by tlyon on 3/22/16.
 * Used to download events from the server. Different from the nested class in SignInFragment.
 * At the end of the task, the user is taken back to the main activity. SwapToMapFragment() is not called.
 */
public class GetEventsTask extends AsyncTask<String, Integer, JSONObject>{
    private Context context;

    public GetEventsTask(Context context){
        this.context = context;
    }

    @Override
    protected JSONObject doInBackground(String... params) {
        return ServerFacade.getInstance().getEvents();
    }

    @Override
    protected void onPostExecute(JSONObject jsonObject) {
        try{
            //error occurred
            if(jsonObject.has("message")){
                String message = jsonObject.getString("message");
                Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();
            }
            //events downloaded
            else{
                //add events to model
                addEventsToModel(jsonObject);
                ModelData.getInstance().populateEventTypes();
                ModelData.getInstance().populatePersonEventsMap();
                Settings.getInstance().updateFilterSettings();
                Toast.makeText(this.context, "Done loading data!", Toast.LENGTH_SHORT).show();
                Utils.startTopActivity(context,true);
            }
        }
        catch (JSONException ex){
            Log.e("GetEventsTask","Error downloading events");
            Toast.makeText(this.context, "An error occurred while downloading events",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void addEventsToModel(JSONObject jsonObject) throws JSONException{
        JSONArray data = jsonObject.getJSONArray("data");
        for(int i=0;i<data.length();i++){
            JSONObject event = data.getJSONObject(i);
            ModelData.getInstance().addEvent(new Event(event));
        }
    }
}
