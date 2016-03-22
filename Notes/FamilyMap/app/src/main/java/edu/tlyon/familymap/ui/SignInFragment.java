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

import org.json.JSONException;
import org.json.JSONObject;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.User;
import edu.tlyon.familymap.webAccess.ServerFacade;
import edu.tlyon.familymap.webAccess.tasks.GetUserPersonTask;

/**
 * Created by tlyon on 3/15/16.
 */
public class SignInFragment extends android.support.v4.app.Fragment {
    private static final String ARG_TITLE = "title";
    private String title;
    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText serverHostEditText;
    private EditText serverPortEditText;
    private Button signInButton;

    public SignInFragment(){

    }

    public static SignInFragment newInstance(String title){
        SignInFragment fragment = new SignInFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            title = getArguments().getString(ARG_TITLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_sign_in,container,false);

        this.usernameEditText = (EditText)v.findViewById(R.id.usernameEditText);
        this.passwordEditText = (EditText)v.findViewById(R.id.passwordEditText);
        this.serverHostEditText = (EditText)v.findViewById(R.id.serverHostEditText);
        this.serverPortEditText = (EditText)v.findViewById(R.id.serverPortEditText);
        this.signInButton = (Button)v.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (hasEmptyFields()) {
                    Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
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

    // TODO: 3/21/16 Put in check for empty credentials
    private boolean hasEmptyFields(){
        if(usernameEditText.toString().equals(null) || passwordEditText.toString().equals(null)||
                serverHostEditText.toString().equals(null) || serverPortEditText.toString().equals(null))
            return true;
        else return false;
    }

    private class SignInTask extends AsyncTask<String, Integer, JSONObject> {
        private Context context;

        public SignInTask(Context context){
            this.context = context;
        }
        // Process result from doInBackground
        @Override
        protected void onPostExecute(JSONObject object) {
            if(object.has("message")){
                try {
                    String message = object.getString("message");
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                }
                catch (JSONException ex){
                    Log.e("SignInFragment","No field 'message' in JSONObject",ex);
                }
            }
            else {
                try {
                    // Login successful. Get authorizationCode
                    String authorizationCode = object.getString("Authorization");
                    String personId = object.getString("personId");
                    User user = new User(authorizationCode);
                    ModelData.getInstance().setCurrentUser(user);
                    // Display toast with user's first and last name
                    new GetUserPersonTask(this.context).execute(authorizationCode, personId);
                    // TODO: 3/21/16 Move to mapFragment
                    ((MainActivity)getActivity()).swapToMapFragment();

                }
                catch (JSONException ex){
                    Log.e("SignInFragment","Error in getting string from field",ex);
                }

            }
        }

        @Override
        protected JSONObject doInBackground(String... params) {
            String username = params[0];
            String password = params[1];
            return ServerFacade.getInstance().login(username,password);
//            HttpClient httpClient = new HttpClient();
//            long totalSize = 0;
//            for(int i=0;i<params.length;i++){
//                String urlContent = httpClient.getURL(params[i]);
//                if(urlContent != null){
//                    totalSize += urlContent.length();
//                }
//                int progress = 0;
//                if(i == params.length -1){
//                    progress = 100;
//                }
//                else {
//                    float cur = i+1;
//                    float total = params.length;
//                    progress = (int)((cur / total) * 100);
//                }
//                publishProgress(progress);
//            }
//            return totalSize;
        }
    }
}
