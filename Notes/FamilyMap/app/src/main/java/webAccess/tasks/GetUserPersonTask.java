package webAccess.tasks;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import model.ModelData;
import model.Person;
import webAccess.ServerFacade;

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
        String firstName = person.getFirstName();
        String lastName = person.getLastName();
        Toast.makeText(this.context, "Welcome "+ firstName + " " + lastName + "!", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Person doInBackground(String... params) {
        String personId = params[1];
        return ServerFacade.getInstance().getPersonWithId(personId);
    }
}
