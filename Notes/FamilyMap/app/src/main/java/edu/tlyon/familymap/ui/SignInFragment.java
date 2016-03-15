package edu.tlyon.familymap.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import edu.tlyon.familymap.R;

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
        signInButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // TODO: 3/15/16 Execute sign in code
            }
        });
        return v;
    }
}
