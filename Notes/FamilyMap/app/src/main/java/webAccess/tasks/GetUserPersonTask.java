package webAccess.tasks;

import android.os.AsyncTask;

import org.json.JSONObject;

import model.Person;
import webAccess.ServerFacade;

/**
 * Created by tlyon on 3/16/16.
 */
public class GetUserPersonTask extends AsyncTask<String, Void, Person> {

    @Override
    protected void onPostExecute(Person jsonObject) {

    }

    @Override
    protected Person doInBackground(String... params) {
        String personId = params[1];
        return ServerFacade.getInstance().getPersonWithId(personId);
    }
}
