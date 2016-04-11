package edu.tlyon.familymap.webAccess.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Person;
import edu.tlyon.familymap.ui.MainActivity;
import edu.tlyon.familymap.webAccess.ServerFacade;

/**
 * Created by tlyon on 3/22/16.
 */
public class GetPeopleTask extends AsyncTask<String, Integer, JSONObject> {
    private Context context;

    public GetPeopleTask(Context context){
        this.context = context;
    }
    @Override
    protected JSONObject doInBackground(String... params) {
        return ServerFacade.getInstance().getPeople();
    }

    @Override
    protected void onPostExecute(JSONObject object) {
        //error occurred
        if(object.has("message")){
            try {
                Toast.makeText(this.context, object.getString("message"), Toast.LENGTH_SHORT).show();
            }
            catch (JSONException ex){
                Toast.makeText(this.context, "An error has occurred", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        //got json object of jsonarray of people
        else{
            try {
                addPersonsToModel(object);
                ModelData.getInstance().populateMaternalAncestors();
                ModelData.getInstance().populatePaternalAncestors();
                new GetEventsTask(context).execute();
            }
            catch (JSONException ex){
                Log.e("GetPeopleTask","Error occurred parsing persons data",ex);
                Toast.makeText(this.context, "Error occurred with the data", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void addPersonsToModel(JSONObject object) throws JSONException{
        assert object != null;
        JSONArray data = object.getJSONArray("data");
        for(int i=0;i<data.length();i++) {
            JSONObject personObject = data.getJSONObject(i);
            assert personObject != null;
            Iterator<String> keys = personObject.keys();
            Person newPerson = new Person();
            while (keys.hasNext()) {
                String key = keys.next();
                assert key != null;
                String value = personObject.getString(key);
                assert value != null;
                switch (key) {
                    case "descendant":
                        newPerson.setDescendant(value);
                        break;
                    case "personID":
                        newPerson.setPersonId(value);
                        break;
                    case "firstName":
                        newPerson.setFirstName(value);
                        break;
                    case "lastName":
                        newPerson.setLastName(value);
                        break;
                    case "gender":
                        newPerson.setGender(value);
                        break;
                    case "father":
                        newPerson.setFatherId(value);
                        break;
                    case "mother":
                        newPerson.setMotherId(value);
                        break;
                    case "spouse":
                        newPerson.setSpouseId(value);
                        break;
                }
            }
//            Log.d("GetPeopleTask","Added " + newPerson.getFirstName() + " " + newPerson.getLastName());
            ModelData.getInstance().addPerson(newPerson);
        }
    }
}
