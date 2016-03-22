package edu.tlyon.familymap.ui;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.Marker;

import edu.tlyon.familymap.R;

/**
 * Created by tlyon on 3/21/16.
 */
public class MapFrag extends Fragment{
    private static final String ARG_TITLE = "title";

    private AmazonMap map;
    private SupportMapFragment mapFragment;
    private Icon icon;
    private TextView eventDetailName;
    private TextView eventDetailDate;
    private RelativeLayout eventDetails;

    public MapFrag() {
    }

    public static MapFrag newInstance(String title){
        MapFrag mapFrag = new MapFrag();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        mapFrag.setArguments(args);
        return mapFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map,container,false);
        //get all members
        this.eventDetailDate = (TextView) v.findViewById(R.id.eventDetailDate);
        this.eventDetailName = (TextView) v.findViewById(R.id.eventDetailText);
        this.eventDetails = (RelativeLayout) v.findViewById(R.id.eventDetails);
        this.mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.mapFragment);
        //perform necessary setup
        this.eventDetailName.setText("Click on marker to see more detail");
        this.eventDetailDate.setText("");
        this.eventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO: 3/21/16 Go to Person activity
            }
        });

        this.mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(AmazonMap amazonMap) {
                map = amazonMap;
                map.setMapType(1);
                // TODO: 3/21/16 Set onMarkerClickListener
                map.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //this.map.addPolyLine
                        //
                        return false;
                    }
                });
            }
        });


        return v;
    }

    private void loadData(){
        // TODO: 3/21/16 Import from server
        //this.map.addMarker(Options)
        //store marker "title" as the event id
    }
}
