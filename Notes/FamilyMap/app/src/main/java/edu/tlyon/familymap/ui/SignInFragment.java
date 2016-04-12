package edu.tlyon.familymap.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.Event;
import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Person;
import edu.tlyon.familymap.model.User;
import edu.tlyon.familymap.webAccess.ServerFacade;

import edu.tlyon.familymap.webAccess.tasks.GetUserPersonTask;

/**
 * Created by tlyon on 3/15/16.
 * UIFragment used the MainActivity to allow the user to sign in.
 */
public class SignInFragment extends android.support.v4.app.Fragment {
    private static final String ARG_TITLE = "title";
    private String title;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText serverHostEditText;
    private EditText serverPortEditText;
    private Button signInButton;

    public SignInFragment() {

    }

    public static SignInFragment newInstance(String title) {
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_in, container, false);

        this.usernameEditText = (EditText) v.findViewById(R.id.username_edit_text);
        this.passwordEditText = (EditText) v.findViewById(R.id.password_edit_text);
        this.serverHostEditText = (EditText) v.findViewById(R.id.server_host_edit_text);
        this.serverPortEditText = (EditText) v.findViewById(R.id.server_port_edit_text);
        this.signInButton = (Button) v.findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasEmptyFields()) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    String host = serverHostEditText.getText().toString();
                    String port = serverPortEditText.getText().toString();
                    ServerFacade.getInstance().setHost(host);
                    ServerFacade.getInstance().setPort(port);
                    new SignInTask(getContext()).execute(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
            }
        });

        return v;
    }

    /**
     * Checks to see if any of the EditTexts have empty fields
     */
    private boolean hasEmptyFields() {
        return usernameEditText.toString().matches("") || passwordEditText.toString().matches("") ||
                serverHostEditText.toString().matches("") || serverPortEditText.toString().matches("");
    }

    /**
     * Signs the user in to the FamilyMap server
     */
    private class SignInTask extends AsyncTask<String, Integer, JSONObject> {
        private Context context;

        public SignInTask(Context context) {
            this.context = context;
        }

        // Process result from doInBackground
        @Override
        protected void onPostExecute(JSONObject object) {
            if (object.has("message")) {
                try {
                    String message = object.getString("message");
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                } catch (JSONException ex) {
                    Log.e("SignInFragment", "No field 'message' in JSONObject", ex);
                }
            } else {
                try {
                    // Login successful. Get authorizationCode
                    String authorizationCode = object.getString("Authorization");
                    String personId = object.getString("personId");
                    User user = new User(authorizationCode);
                    ModelData.getInstance().setCurrentUser(user);
                    // Display toast with user's first and last name
                    new GetUserPersonTask(this.context).execute(authorizationCode, personId);
                    //get all people associated with current user
                    new GetPeopleTask(this.context).execute();
                } catch (JSONException ex) {
                    Log.e("SignInFragment", "Error in getting string from field", ex);
                }
            }
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            return ServerFacade.getInstance().login(username, password);
        }
    }

    /**
     * Async task that downloads all events from the server. Different than webAccess.tasks.GetEventsTask
     * because swapToMapFragment is called at the end
     */
    private class GetEventsTask extends AsyncTask<String, Integer, JSONObject> {
        private Context context;

        public GetEventsTask(Context context) {
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            return ServerFacade.getInstance().getEvents();
        }

        @Override
        protected void onPostExecute(JSONObject jsonObject) {
            try {
                //error occurred
                if (jsonObject.has("message")) {
                    String message = jsonObject.getString("message");
                    Toast.makeText(this.context, message, Toast.LENGTH_SHORT).show();
                }
                //events downloaded
                else {
                    //add events to model
                    addEventsToModel(jsonObject);
                    ModelData.getInstance().populateEventTypes();
                    ModelData.getInstance().populatePersonEventsMap();
                    ((MainActivity) getActivity()).swapToMapFragment();
                }
            } catch (JSONException ex) {
                Log.e("GetEventsTask", "Error downloading events");
                Toast.makeText(this.context, "An error occurred while downloading events",
                        Toast.LENGTH_SHORT).show();
            }
        }

        private void addEventsToModel(JSONObject jsonObject) throws JSONException {
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
                JSONObject event = data.getJSONObject(i);
                ModelData.getInstance().addEvent(new Event(event));
            }
        }
    }

    /**
     * Async task that downloads all people from the server. Different than webAccess.tasks.GetEventsTask
     * because swapToMapFragment is called at the end
     */
    public class GetPeopleTask extends AsyncTask<String, Integer, JSONObject> {
        private Context context;

        public GetPeopleTask(Context context) {
            this.context = context;
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            return ServerFacade.getInstance().getPeople();
        }

        @Override
        protected void onPostExecute(JSONObject object) {
            //error occurred
            if (object.has("message")) {
                try {
                    Toast.makeText(this.context, object.getString("message"), Toast.LENGTH_SHORT).show();
                } catch (JSONException ex) {
                    Toast.makeText(this.context, "An error has occurred", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            //got json object of jsonarray of people
            else {
                try {
                    addPersonsToModel(object);
                    ModelData.getInstance().populateMaternalAncestors();
                    ModelData.getInstance().populatePaternalAncestors();
                    new GetEventsTask(context).execute();
                } catch (JSONException ex) {
                    Log.e("GetPeopleTask", "Error occurred parsing persons data", ex);
                    Toast.makeText(this.context, "Error occurred with the data", Toast.LENGTH_SHORT).show();
                }
            }
        }

        /**
         * Takes the JSONObject returned from the server, parses it, and adds the Person objects to the Model
         *
         * @param object JSONObject with JSONArray of Person objects returned from the server
         */
        private void addPersonsToModel(JSONObject object) throws JSONException {
            assert object != null;
            JSONArray data = object.getJSONArray("data");
            for (int i = 0; i < data.length(); i++) {
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
                Log.e("GetPeopleTask", "Added " + newPerson.getFirstName() + " " + newPerson.getLastName());
                ModelData.getInstance().addPerson(newPerson);
            }
        }
    }


}
