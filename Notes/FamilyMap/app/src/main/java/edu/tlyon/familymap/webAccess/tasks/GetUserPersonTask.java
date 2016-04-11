package edu.tlyon.familymap.webAccess.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Person;
import edu.tlyon.familymap.webAccess.ServerFacade;

/**
 * Created by tlyon on 3/16/16.
 */
public class GetUserPersonTask extends AsyncTask<String, Void, Person> {
    private Context context;
    public GetUserPersonTask(Context context){
        this.context = context;
    }
    @Override
    protected void onPostExecute(Person person) {
        ModelData.getInstance().getCurrentUser().setOwnPerson(person);
        new GetPeopleTask(context).execute();
    }

    @Override
    protected Person doInBackground(String... params) {
        String personId = params[1];
        return ServerFacade.getInstance().getPersonWithId(personId);
    }
}
