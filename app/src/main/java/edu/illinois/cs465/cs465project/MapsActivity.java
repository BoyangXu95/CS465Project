package edu.illinois.cs465.cs465project;

import java.util.List;
import java.util.ArrayList;

import android.app.SearchManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Camera;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.CheckBox;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.util.zip.Inflater;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener, GoogleMap.OnMarkerClickListener {

    private GoogleMap mMap;
    private ImageButton add;
    List <Marker> friends = new ArrayList<>();
    List <Marker> privates = new ArrayList<>();
    List <Marker> interests = new ArrayList<>();
    private ImageButton menu;
    private DrawerLayout drawer;
    private ImageButton current_location;
    private Marker curMarker;

    List<Marker> markers = new ArrayList<>();
    List<String> checkedHashtags = new ArrayList<>();

    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;

    private EventCreateActivity createEvent;

    private Button createEventButton;
    private Button interestedButton;

    private RelativeLayout relative;
    private SearchView searchView;

    public void createNewEvent(Event newEvent) {
        LatLng newEventPosition = new LatLng(40.110090, -88.229600);
        Marker newEventMarker = mMap.addMarker(new MarkerOptions()
                .position(newEventPosition)
                .title("Marker in friends")
        );
        newEventMarker.setTag(newEvent);
        markers.add(newEventMarker);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        relativeLayout = (RelativeLayout) findViewById(R.id.relative);

        add = (ImageButton) findViewById(R.id.add);
        add.setOnClickListener(this);
        menu = (ImageButton) findViewById(R.id.hamburger);
        menu.setOnClickListener(this);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        current_location = (ImageButton) findViewById(R.id.current_location);
        current_location.setOnClickListener(this);

        searchView = (SearchView) findViewById(R.id.search);
        searchView.setQueryHint("Search nearby events");
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchEvent(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO
                return false;
            }
        });
    }


    public boolean onMarkerClick(final Marker marker) {
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        Event curEvent = (Event) marker.getTag();
        curMarker = marker;

        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.event_popup, null);

        popupWindow = new PopupWindow(container, 400, 1000, true);
        popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY, Resources.getSystem().getDisplayMetrics().widthPixels / 2 - 200 , Resources.getSystem().getDisplayMetrics().heightPixels / 2 - 200);

        TextView text = (TextView) popupWindow.getContentView().findViewById(R.id.popup);
        interestedButton = popupWindow.getContentView().findViewById(R.id.interested_button);
        interestedButton.setOnClickListener(this);

        text.setText(curEvent.getName());
        container.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                popupWindow.dismiss();
                return true;
            }
        });

        return false;
    }

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera. In this case,
         * we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to install
         * it inside the SupportMapFragment. This method will only be triggered once the user has
         * installed Google Play services and returned to the app.
         */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);  //Set the map type to normal

        // Add a marker in teamoji and move the camera
        LatLng teamoji = new LatLng(40.110095, -88.229681);
        Marker teamojiM = mMap.addMarker(new MarkerOptions()
                        .position(teamoji)

        );
        Event teamojiEvent = new Event("test");
        teamojiEvent.addHashTag("Movie");
        teamojiEvent.setFriendsGoing(true);
        teamojiEvent.setLocation(teamoji);
        teamojiM.setTag(teamojiEvent);
        markers.add(teamojiM);
        friends.add(teamojiM);

        LatLng grainger = new LatLng(40.112510, -88.226773);
        Marker ggM = mMap.addMarker(new MarkerOptions()
                                    .position(grainger)
        );
        Event ggEvent = new Event("CS465 study session");
        ggEvent.addHashTag("FreeFood");
        ggEvent.setPrivateEvent(true);
        ggEvent.setLocation(grainger);
        ggM.setTag(ggEvent);
        markers.add(ggM);
        privates.add(ggM);

        LatLng union = new LatLng(40.109432, -88.227126);
        Marker unionM = mMap.addMarker(new MarkerOptions()
                        .position(union)
        );
        Event unionEvent = new Event("CSSA interviews");
        unionEvent.addHashTag("FreeFood");
        unionEvent.setInteretedEvent(true);
        unionEvent.setLocation(union);
        unionM.setTag(unionEvent);
        markers.add(unionM);
        interests.add(unionM);

        LatLng ugl = new LatLng(40.104861, -88.227137);
        Marker uglM = mMap.addMarker(new MarkerOptions()
                                    .position(ugl)
        );
        Event uglEvent = new Event("someone makes me focus plz");
        uglEvent.addHashTag("BoardGame");
        uglEvent.setPrivateEvent(true);
        uglEvent.setLocation(ugl);
        uglM.setTag(uglEvent);
        markers.add(uglM);
        privates.add(uglM);

        LatLng bookstore = new LatLng(40.108534, -88.229278);
        Marker bookstoreM = mMap.addMarker(new MarkerOptions()
                        .position(bookstore)
        );
        Event bookstoreEvent = new Event("starbucks~");
        bookstoreEvent.addHashTag("BoardGame");
        bookstoreEvent.setFriendsGoing(true);
        bookstoreEvent.setLocation(bookstore);
        bookstoreM.setTag(bookstoreEvent);
        markers.add(bookstoreM);
        friends.add(bookstoreM);

        LatLng siebel = new LatLng(40.113908, -88.225008);
        Marker siebelM = mMap.addMarker(new MarkerOptions()
                                        .position(siebel)
//                                        .title("Marker in private")
        );
        Event siebelEvent = new Event("CS461 we need help with mp4");
        siebelEvent.addHashTag("Dinner");
        siebelEvent.setPrivateEvent(true);
        siebelEvent.setLocation(siebel);
        siebelM.setTag(siebelEvent);
        markers.add(siebelM);
        privates.add(siebelM);

        mMap.setOnMarkerClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(union));
        mMap.setMinZoomPreference(15.0f);
    }

    public void onCheckboxClicked(View view) {
        filterChanged();
    }

    public void onHashTagClicked(View view) {
        Button b = (Button) view;
        String text = b.getText().toString();
        if (b.isSelected()) {
            checkedHashtags.remove(text);
            b.setSelected(false);
            b.setTextColor(Color.parseColor("#000000"));
        } else {
            checkedHashtags.add(text);
            b.setSelected(true);
            b.setTextColor(Color.parseColor("#FF0000"));
        }
        filterChanged();
    }

    private void filterChanged() {
        CheckBox friendsCheckBox = (CheckBox) findViewById(R.id.checkbox_friends);
        CheckBox interestsCheckBox = (CheckBox) findViewById(R.id.checkbox_interested);
        CheckBox privatesCheckBox = (CheckBox) findViewById(R.id.checkbox_private);

        if (!friendsCheckBox.isChecked() && !privatesCheckBox.isChecked() && !interestsCheckBox.isChecked()) {
            if (checkedHashtags.isEmpty()){
                for(Marker mkr:markers) {
                    mkr.setVisible(true);
                }
                return;
            }
            for(Marker mkr:markers) {
                mkr.setVisible(false);
            }
            for (Marker mkr : markers) {
                Event data = (Event) mkr.getTag();
                if (data.hasHashTags(checkedHashtags)) {
                    mkr.setVisible(true);
                }
            }
            return;
        }

        for(Marker mkr:markers) {
            mkr.setVisible(false);
        }

        if (friendsCheckBox.isChecked()){
            for (Marker mkr : friends) {
                Event data = (Event) mkr.getTag();
                if (data.hasHashTags(checkedHashtags)) {
                    mkr.setVisible(true);
                }
            }
        }
        if (privatesCheckBox.isChecked()){
            for (Marker mkr : privates) {
                Event data = (Event) mkr.getTag();
                if (data.hasHashTags(checkedHashtags)) {
                    mkr.setVisible(true);
                }
            }
        }
        if (interestsCheckBox.isChecked()){
            for (Marker mkr : interests) {
                Event data = (Event) mkr.getTag();
                if (data.hasHashTags(checkedHashtags)) {
                    mkr.setVisible(true);
                }
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case (0) : {
                if (resultCode == Activity.RESULT_OK) {
                    String newEventName = data.getStringExtra("name");
                    LatLng newEventLocation = new LatLng(40.109000, -88.220000);
                    Marker newEventMarker = mMap.addMarker(new MarkerOptions()
                            .position(newEventLocation)
//                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    );
                    Event newEvent = new Event(newEventName);
                    newEvent.setPrivateEvent(data.getBooleanExtra("private", false));
                    if (data.getBooleanExtra("private", false))
                        privates.add(newEventMarker);
                    // hard coded username
                    newEvent.setOwner("tester");
                    newEventMarker.setTag(newEvent);
                    markers.add(newEventMarker);
                }
            }
        }
    }

    public void onClick(View v) {
        if (v.getId() == R.id.add) {
            Intent intent = new Intent(this, EventCreateActivity.class);
            startActivityForResult(intent, 0);
        }
        else if (v.getId() == R.id.current_location) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(((Event) markers.get(2).getTag()).getLocation(), 15.0f));
        }
        else if (v.getId() == R.id.hamburger){
            drawer.openDrawer(Gravity.START);
        }
        else if (v.getId() == R.id.interested_button){
            Context context = getApplicationContext();
            CharSequence text = "Event liked";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();

            Event curEvent = (Event)curMarker.getTag();
            boolean intereted = (curEvent.isInterested());
            curEvent.setInteretedEvent(!intereted);

            if (curEvent.isInterested()){
                interests.add(curMarker);
            } else {
                interests.remove(curMarker);
            }
            filterChanged();
        }
    }

    public void searchEvent(String query){
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.event_popup, null);
        final PopupWindow popupWindow = new PopupWindow(container, 400, 1000, true);
        TextView text = (TextView) popupWindow.getContentView().findViewById(R.id.popup);
        int eventIndex = 0;

        for (int i = 0; i < markers.size(); i++){
            if (((Event) markers.get(i).getTag()).getName().equals(query)){
                eventIndex = i;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((((Event) markers.get(eventIndex).getTag()).getLocation()), 15.0f));
                popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY, Resources.getSystem().getDisplayMetrics().widthPixels / 2 - 200 , Resources.getSystem().getDisplayMetrics().heightPixels / 2 - 200);
                text.setText(query);
                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        popupWindow.dismiss();
                        return true;
                    }
                });
                return;
            }
        }
        Toast.makeText(getBaseContext(), "No match! Create One?", Toast.LENGTH_LONG).show();

    }
}
