package edu.tlyon.familymap.ui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import edu.tlyon.familymap.R;

public class MainActivity extends AppCompatActivity {

    private SignInFragment signInFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 3/15/16 Check for user already signed in
        setContentView(R.layout.activity_main);
        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();
        this.signInFragment = (SignInFragment)fm.findFragmentById(R.id.signInFrameLayout);
        if(signInFragment == null){
            this.signInFragment = SignInFragment.newInstance("Sign In");
            fm.beginTransaction()
                    .add(R.id.signInFrameLayout,this.signInFragment)
                    .commit();
        }

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }

}
