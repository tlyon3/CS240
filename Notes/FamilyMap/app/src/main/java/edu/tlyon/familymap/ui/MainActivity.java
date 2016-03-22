package edu.tlyon.familymap.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.amazon.geo.mapsv2.MapFragment;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.ModelData;

public class MainActivity extends AppCompatActivity {

    private SignInFragment signInFragment;
    private MapFrag mapFrag;
    private MapFragment mMapFragment;
    private static final String MAP_FRAGMENT_TAG = "mapfragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: 3/15/16 Check for user already signed in
        setContentView(R.layout.activity_main);
        android.support.v4.app.FragmentManager fm = this.getSupportFragmentManager();

        if(ModelData.getInstance().getCurrentUser()==null) {
            this.signInFragment = (SignInFragment) fm.findFragmentById(R.id.fragment_container);
            if (signInFragment == null) {
                this.signInFragment = SignInFragment.newInstance("Sign In");
                fm.beginTransaction()
                        .add(R.id.fragment_container, this.signInFragment)
                        .commit();
            }
        }
        else{
            this.mapFrag = MapFrag.newInstance("mapfrag");
            fm.beginTransaction()
                    .replace(R.id.fragment_container,this.mapFrag)
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
    public void swapToMapFragment() {
        if(this.mapFrag == null){
            this.mapFrag = MapFrag.newInstance("mapfrag");
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container,this.mapFrag)
                .commit();
    }

}
