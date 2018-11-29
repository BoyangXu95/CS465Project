package edu.illinois.cs465.cs465project;

import java.util.List;
import java.util.ArrayList;

import android.content.res.Resources;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.AdapterView;
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
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
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

import java.util.ArrayList;
import java.util.List;
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

    List<Marker> markers = new ArrayList<>();

    private PopupWindow popupWindow;
    private LayoutInflater layoutInflater;
    private RelativeLayout relativeLayout;

    private EventCreateActivity createEvent;

    private Button createEventButton;
    private ListView mylist;

    public void createNewEvent(Event newEvent) {
        LatLng newEventPosition = new LatLng(40.110090, -88.229600);
        Marker newEventMarker = mMap.addMarker(new MarkerOptions()
                .position(newEventPosition)
                .title("Marker in friends")
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
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

        mylist = (ListView) findViewById(R.id.drawerlist);
        mylist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                        // Toast.LENGTH_SHORT).show();

            }
        });

//        final LayoutInflater factory = getLayoutInflater();
//
//        final View createView = factory.inflate(R.layout.activity_event_create, null);
//
//        createEventButton = (Button) createView.findViewById(R.id.create_event);
//        createEventButton.setOnClickListener(this);
    }


    public boolean onMarkerClick(final Marker marker) {
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        Event curEvent = (Event) marker.getTag();

        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.event_popup, null);

        popupWindow = new PopupWindow(container, 400, 1000, true);
        popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY, Resources.getSystem().getDisplayMetrics().widthPixels / 2 - 200 , Resources.getSystem().getDisplayMetrics().heightPixels / 2 - 200);

        TextView text = (TextView) popupWindow.getContentView().findViewById(R.id.popup);
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
        Marker friends1, friends2, private1,private2, private3, interest1;
        LatLng teamoji = new LatLng(40.110095, -88.229681);
        Marker teamojiM = mMap.addMarker(new MarkerOptions()
                        .position(teamoji)
                        .title("Marker in friends")
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        );
        Event teamojiEvent = new Event("anyone wants boba?");
        teamojiM.setTag(teamojiEvent);
        markers.add(teamojiM);
        friends.add(teamojiM);

        LatLng grainger = new LatLng(40.112510, -88.226773);
        Marker ggM = mMap.addMarker(new MarkerOptions().position(grainger).title("Marker in private"));
        Event ggEvent = new Event("CS465 study session");
        ggM.setTag(ggEvent);
        markers.add(ggM);
        privates.add(ggM);

        LatLng union = new LatLng(40.109432, -88.227126);
        Marker unionM = mMap.addMarker(new MarkerOptions()
                        .position(union)
                        .title("Marker in Interest")
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
        );
        Event unionEvent = new Event("CSSA interviews");
        unionM.setTag(unionEvent);
        markers.add(unionM);
        interests.add(unionM);

        LatLng ugl = new LatLng(40.104861, -88.227137);
        Marker uglM = mMap.addMarker(new MarkerOptions().position(ugl).title("Marker in private"));
        Event uglEvent = new Event("someone makes me focus plz");
        uglM.setTag(uglEvent);
        markers.add(uglM);
        privates.add(uglM);

        LatLng bookstore = new LatLng(40.108534, -88.229278);
        Marker bookstoreM = mMap.addMarker(new MarkerOptions()
                        .position(bookstore)
                        .title("Marker in friends")
//                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN))
        );
        Event bookstoreEvent = new Event("starbucks~");
        bookstoreM.setTag(bookstoreEvent);
        markers.add(bookstoreM);
        friends.add(bookstoreM);

        LatLng siebel = new LatLng(40.113908, -88.225008);
        Marker siebelM = mMap.addMarker(new MarkerOptions().position(siebel).title("Marker in private"));
        Event siebelEvent = new Event("CS461 we need help with mp4");
        siebelM.setTag(siebelEvent);
        markers.add(siebelM);
        privates.add(siebelM);

        mMap.setOnMarkerClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(union));
        mMap.setMinZoomPreference(15.0f);
    }

    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        CheckBox friendsCheckBox = (CheckBox) findViewById(R.id.checkbox_friends);
        CheckBox interestsCheckBox = (CheckBox) findViewById(R.id.checkbox_interested);
        CheckBox privatesCheckBox = (CheckBox) findViewById(R.id.checkbox_private);

        if (!friendsCheckBox.isChecked() && !privatesCheckBox.isChecked() && !interestsCheckBox.isChecked()){
            for(Marker mkr:markers) {
                mkr.setVisible(true);
            }
            return;
        }

        for(Marker mkr:markers) {
            mkr.setVisible(false);
        }

        if (friendsCheckBox.isChecked()){
            for(Marker mkr:friends) {
                mkr.setVisible(true);
            }
        }
        if (privatesCheckBox.isChecked()){
            for(Marker mkr:privates) {
                mkr.setVisible(true);
            }
        }
        if (interestsCheckBox.isChecked()){
            for(Marker mkr:interests) {
                mkr.setVisible(true);
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
            LatLng union = new LatLng(40.109432, -88.227126);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(union, 15.0f));
        }
        else if (v.getId() == R.id.hamburger){
            drawer.openDrawer(Gravity.START);
        }

    }

}
