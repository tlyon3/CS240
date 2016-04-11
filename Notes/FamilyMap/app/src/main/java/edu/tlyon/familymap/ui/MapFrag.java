package edu.tlyon.familymap.ui;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.ArraySet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.CameraUpdateFactory;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.amazon.geo.mapsv2.model.Polyline;
import com.amazon.geo.mapsv2.model.PolylineOptions;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.tlyon.familymap.R;
import edu.tlyon.familymap.model.Event;
import edu.tlyon.familymap.model.ModelData;
import edu.tlyon.familymap.model.Person;
import edu.tlyon.familymap.model.Settings;

/**
 * Created by tlyon on 3/21/16.
 */
public class MapFrag extends Fragment {
    private static final String ARG_TITLE = "title";

    private AmazonMap map;
    private SupportMapFragment mapFragment;
    private ImageView icon;
    private TextView eventDetailName;
    private TextView eventDetailDate;
    private RelativeLayout eventDetails;
    private Map<Marker, Event> markerEventMap = new HashMap<>();
    private Map<Event, Marker> eventMarkerMap = new HashMap<>();
    private Event selectedEvent;
    private List<Polyline> lines = new ArrayList<>();
    private Map<Integer, Integer> lineColors = new HashMap<>();
    private int[] mapTypes = new int[]{
            AmazonMap.MAP_TYPE_NORMAL, AmazonMap.MAP_TYPE_SATELLITE, AmazonMap.MAP_TYPE_HYBRID
    };
    private Set<Marker> markersToRemove = new HashSet<>();

    public MapFrag() {
    }

    public static MapFrag newInstance(String title) {
        MapFrag mapFrag = new MapFrag();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        mapFrag.setArguments(args);
        return mapFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lineColors.put(Settings.getInstance().BLUE, Color.BLUE);
        lineColors.put(Settings.getInstance().GREEN, Color.GREEN);
        lineColors.put(Settings.getInstance().RED, Color.RED);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);
        //get all members
        this.eventDetailDate = (TextView) v.findViewById(R.id.eventDetailDate);
        this.eventDetailName = (TextView) v.findViewById(R.id.eventDetailText);
        this.icon = (ImageView) v.findViewById(R.id.iconImage);
        this.eventDetails = (RelativeLayout) v.findViewById(R.id.eventDetails);
        this.mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        //perform necessary setup
        this.eventDetailName.setText("Click on marker to see more detail");
        this.eventDetailDate.setText("");
        this.eventDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedEvent != null) {
                    Intent intent = new Intent(getActivity(), PersonActivity.class);
                    intent.putExtra("personId", selectedEvent.getPersonId());
                    getActivity().startActivity(intent);
                }
            }
        });

        this.mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(AmazonMap amazonMap) {
                map = amazonMap;
                map.setMapType(mapTypes[Settings.getInstance().getMapType()]);
                //zoom on selected event, if any
                if (selectedEvent != null) {
                    zoomToEvent(selectedEvent);
                    String firstName = ModelData.getInstance().getPersonIdMap().get(selectedEvent.getPersonId()).getFirstName();
                    String lastName = ModelData.getInstance().getPersonIdMap().get(selectedEvent.getPersonId()).getLastName();
                    eventDetailName.setText(firstName + " " + lastName);
                    String type = selectedEvent.getEventType();
                    String location = selectedEvent.getCity() + ", " + selectedEvent.getCountry();
                    String date = selectedEvent.getYear();
                    String details = type + ": " + location + "(" + date + ")";
                    eventDetailDate.setText(details);
                    Drawable eventIcon = new IconDrawable(getContext(), Iconify.IconValue.fa_map_marker).colorRes(R.color.colorPrimaryDark).sizeDp(40);
                    icon.setImageDrawable(eventIcon);
                    removeLines();
                    lines.clear();
                    drawLines(selectedEvent);

                }
                map.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        //this.map.addPolyLine
                        //
                        removeLines();
                        lines.clear();
                        Event event = markerEventMap.get(marker);
                        selectedEvent = event;
                        if(!markerEventMap.containsKey(event)){
                            removeAddedMarkers();
                        }
                        String firstName = ModelData.getInstance().getPersonIdMap().get(event.getPersonId()).getFirstName();
                        String lastName = ModelData.getInstance().getPersonIdMap().get(event.getPersonId()).getLastName();
                        eventDetailName.setText(firstName + " " + lastName);
                        String type = event.getEventType();
                        String location = event.getCity() + ", " + event.getCountry();
                        String date = event.getYear();
                        String details = type + ": " + location + "(" + date + ")";
                        eventDetailDate.setText(details);
                        Drawable eventIcon = new IconDrawable(getContext(), Iconify.IconValue.fa_map_marker).colorRes(R.color.colorPrimaryDark).sizeDp(40);
                        icon.setImageDrawable(eventIcon);

                        //draw lines
                        drawLines(selectedEvent);

                        //zoom to event
                        zoomToEvent(event);
                        return true;
                    }
                });
                loadMarkers();
            }
        });
        return v;
    }


    public void loadMarkers() {
        //this.map.addMarker(Options)
        Map<String, Event> eventMap = ModelData.getInstance().getEventIdMap();
        Iterator it = eventMap.entrySet().iterator();
        while (it.hasNext()) {
            //check
            Map.Entry pair = (Map.Entry) it.next();
            Event event = (Event) pair.getValue();
            String eventType = event.getEventType();
            String personId = event.getPersonId();
            if(personId.equals(ModelData.getInstance().getCurrentUser().getOwnPerson().getPersonId())){
                loadM(event);
            }

            //find if on mother's side or father's side
            //father's side
            if (ModelData.getInstance().getPaternalAncestors().contains(personId)) {
                if (Settings.getInstance().getFilterDisplaySettings().get("father's side") &&
                        Settings.getInstance().getFilterDisplaySettings().get(eventType)) {
                    //check should display male/female
                    String gender = ModelData.getInstance().getPersonIdMap().get(personId).getGender();
                    if (gender.equals("m") && Settings.getInstance().getFilterDisplaySettings().get("male"))
                        loadM(event);
                    if (gender.equals("f") && Settings.getInstance().getFilterDisplaySettings().get("female"))
                        loadM(event);
                }
            }

            //mother's side
            else {
                if (Settings.getInstance().getFilterDisplaySettings().get("mother's side") &&
                        Settings.getInstance().getFilterDisplaySettings().get(eventType)) {
                    //check should display male/female events
                    String gender = ModelData.getInstance().getPersonIdMap().get(personId).getGender();
                    if (gender.equals("m") && Settings.getInstance().getFilterDisplaySettings().get("male"))
                        loadM(event);
                    if (gender.equals("f") && Settings.getInstance().getFilterDisplaySettings().get("female"))
                        loadM(event);
                }
            }


        }
    }

    public Marker loadM(Event event) {
        float markerHue = getMarkerHue(event);
        Double latitude = Double.parseDouble(event.getLatitude());
        Double longitude = Double.parseDouble(event.getLongitude());
        //set up marker
        MarkerOptions opt = new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .title(event.getEventId())
                .icon(BitmapDescriptorFactory.defaultMarker(markerHue));
        //add marker to map
        Marker m = map.addMarker(opt);
        //link marker to event
        markerEventMap.put(m, event);
        eventMarkerMap.put(event, m);
        return m;
    }

    private void removeLines() {
        for (Polyline line : lines) {
            line.remove();
        }
    }

    private void drawLines(Event selectedEvent) {
        Person selectedPerson = ModelData.getInstance().getPersonIdMap().get(selectedEvent.getPersonId());
        if (!selectedPerson.getSpouseId().equals("") && Settings.getInstance().getDisplaySpouseLines()) {
            Person spouse = ModelData.getInstance().getPersonIdMap().get(selectedPerson.getSpouseId());
            Event spouseEvent = spouse.getLifeEvents().get(0);
            drawSpouseLine(selectedEvent, spouseEvent);
        }

        if (Settings.getInstance().getDisplayFamilyTreeLines()) {
            String motherId = selectedPerson.getMotherId();
            if (!motherId.equals("")) {
                Person mother = ModelData.getInstance().getPersonIdMap().get(motherId);
                drawParentLine(selectedEvent, mother, 15.0f);
            }
            String fatherId = selectedPerson.getFatherId();
            if (!fatherId.equals("")) {
                Person father = ModelData.getInstance().getPersonIdMap().get(fatherId);
                drawParentLine(selectedEvent, father, 15.0f);
            }
        }
        if (Settings.getInstance().getDisplayLifeStoryLines())
            drawLifeStoryLines(selectedEvent);
    }

    private void drawSpouseLine(Event event1, Event event2) {
        Marker marker1 = eventMarkerMap.get(event1);
        if (marker1 == null) {
            marker1 = loadM(event1);
            markersToRemove.add(marker1);
        }

        Marker marker2 = eventMarkerMap.get(event2);
        if (marker2 == null) {
            marker2 = loadM(event2);
            markersToRemove.add(marker2);
        }

        LatLng pos1 = marker1.getPosition();
        LatLng pos2 = marker2.getPosition();

        PolylineOptions opt = new PolylineOptions().add(pos1, pos2);
        opt.color(lineColors.get(Settings.getInstance().getSpouseLinesColor()));
        Polyline p = map.addPolyline(opt);
        lines.add(p);
    }

    private void drawParentLine(Event event, Person parent, float thickness) {
        Event parentFirstEvent = parent.getLifeEvents().get(0);

        Marker marker1 = eventMarkerMap.get(event);
        if (marker1 == null) {
            marker1 = loadM(event);
            markersToRemove.add(marker1);
        }

        Marker marker2 = eventMarkerMap.get(parentFirstEvent);
        if (marker2 == null) {
            marker2 = loadM(parentFirstEvent);
            markersToRemove.add(marker2);
        }

        LatLng pos1 = marker1.getPosition();
        LatLng pos2 = marker2.getPosition();

        PolylineOptions opt = new PolylineOptions().add(pos1, pos2);
        opt.color(lineColors.get(Settings.getInstance().getFamilyTreeLinesColor()));
        opt.width(thickness);

        Polyline p = map.addPolyline(opt);
        lines.add(p);

        String grandfatherId = parent.getFatherId();
        if (!grandfatherId.equals("")) {
            Person grandfather = ModelData.getInstance().getPersonIdMap().get(grandfatherId);
            float lineWidth = p.getWidth()/2;
            if (lineWidth < 0.0f)
                lineWidth = 1.0f;
            drawParentLine(parentFirstEvent, grandfather, lineWidth);
        }
        String grandmotherId = parent.getMotherId();
        if (!grandmotherId.equals("")) {
            Person grandmother = ModelData.getInstance().getPersonIdMap().get(grandmotherId);
            float lineWidth = p.getWidth()/2;
            if (lineWidth < 0.0f)
                lineWidth = 1.0f;
            drawParentLine(parentFirstEvent, grandmother, lineWidth);
        }
    }


    private void drawLifeStoryLines(Event event) {
        Person person = ModelData.getInstance().getPersonIdMap().get(event.getPersonId());
        List<Event> lifeEvents = person.getLifeEvents();
        for (int i = 0; i < lifeEvents.size() - 1; i++) {
            Event event1 = lifeEvents.get(i);
            Event event2 = lifeEvents.get(i + 1);
            Marker marker1 = eventMarkerMap.get(event1);
            if (marker1 == null) {
                marker1 = loadM(event1);
                markersToRemove.add(marker1);
            }

            Marker marker2 = eventMarkerMap.get(event2);
            if (marker2 == null) {
                marker2 = loadM(event2);
                markersToRemove.add(marker2);
            }

            LatLng pos1 = marker1.getPosition();
            LatLng pos2 = marker2.getPosition();

            PolylineOptions opt = new PolylineOptions().add(pos1, pos2);
            opt.color(lineColors.get(Settings.getInstance().getLifeStoryLinesColor()));
            Polyline p = map.addPolyline(opt);
            lines.add(p);
        }
    }

    public Event getSelectedEvent() {
        return selectedEvent;
    }

    public void setSelectedEvent(Event selectedEvent) {
        this.selectedEvent = selectedEvent;
    }

    public void zoomToEvent(Event event) {
        double lat = Double.parseDouble(event.getLatitude());
        double lon = Double.parseDouble(event.getLongitude());
        LatLng latLng = new LatLng(lat, lon);
        float zoom = 6.0f;
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }

    private float getMarkerHue(Event event){
        String eventType = event.getEventType();
        if(Settings.getInstance().getMarkerHueMap().containsKey(eventType)){
            return Settings.getInstance().getMarkerHueMap().get(eventType);
        }
        else{
            boolean validFloat = false;
            float markerHue = 0.0f;
            while(!validFloat){
                markerHue = (float) Math.random()*360;
                if(!Settings.getInstance().getMarkerHueMap().containsValue(markerHue))
                    validFloat = true;
            }
            Settings.getInstance().getMarkerHueMap().put(eventType,markerHue);
            return markerHue;
        }
    }

    private void removeAddedMarkers(){
        for(Marker marker:markersToRemove){
            marker.remove();
            eventMarkerMap.remove(markerEventMap.get(marker));
            markerEventMap.remove(marker);
        }
    }
}
