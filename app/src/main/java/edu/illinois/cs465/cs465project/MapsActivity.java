package edu.illinois.cs465.cs465project;

import java.util.List;
import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
    }


    public boolean onMarkerClick(final Marker marker) {
        layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        Event curEvent = (Event) marker.getTag();

        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.event_popup, null);

        popupWindow = new PopupWindow(container, 400, 400, true);
        popupWindow.showAtLocation(relativeLayout, Gravity.NO_GRAVITY, 500, 500);

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
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        Event teamojiEvent = new Event("teamoji");
        teamojiM.setTag(teamojiEvent);
        markers.add(teamojiM);
        friends.add(teamojiM);

        LatLng grainger = new LatLng(40.112510, -88.226773);
        Marker ggM = mMap.addMarker(new MarkerOptions().position(grainger).title("Marker in private"));
        Event ggEvent = new Event("gg");
        ggM.setTag(ggEvent);
        markers.add(ggM);
        privates.add(ggM);

        LatLng union = new LatLng(40.109432, -88.227126);
        Marker unionM = mMap.addMarker(new MarkerOptions()
                        .position(union)
                        .title("Marker in Interest")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        Event unionEvent = new Event("union");
        unionM.setTag(unionEvent);
        markers.add(unionM);
        interests.add(unionM);

        LatLng ugl = new LatLng(40.104861, -88.227137);
        Marker uglM = mMap.addMarker(new MarkerOptions().position(ugl).title("Marker in private"));
        Event uglEvent = new Event("ugl");
        uglM.setTag(uglEvent);
        markers.add(uglM);
        privates.add(uglM);

        LatLng bookstore = new LatLng(40.108534, -88.229278);
        Marker bookstoreM = mMap.addMarker(new MarkerOptions()
                        .position(bookstore)
                        .title("Marker in friends")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        Event bookstoreEvent = new Event("bookstore");
        bookstoreM.setTag(bookstoreEvent);
        markers.add(bookstoreM);
        friends.add(bookstoreM);

        LatLng siebel = new LatLng(40.113908, -88.225008);
        Marker siebelM = mMap.addMarker(new MarkerOptions().position(siebel).title("Marker in private"));
        Event siebelEvent = new Event("siebel");
        siebelM.setTag(siebelEvent);
        markers.add(siebelM);
        privates.add(siebelM);

        mMap.setOnMarkerClickListener(this);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(union));
        mMap.setMinZoomPreference(15.0f);
    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
//        switch(view.getId()) {
//            case R.id.checkbox_friends:
//                if (checked){
//                    for(Marker mkr:friends){
//                        mkr.setVisible(true);
//                    }
//                }
//                else {
//                    for(Marker mkr:friends){
//                        mkr.setVisible(false);
//                    }
//                }
//                break;
//            case R.id.checkbox_interested:
//                if (checked){
//                    for(Marker mkr:interests){
//                        mkr.setVisible(true);
//                    }
//                }
//                else {
//                    for(Marker mkr:interests){
//                        mkr.setVisible(false);
//                    }
//                }
//                break;
//            case R.id.checkbox_private:
//                if (checked){
//                    for(Marker mkr:privates){
//                        mkr.setVisible(true);
//                    }
//                }
//                else{
//                    for(Marker mkr:privates){
//                        mkr.setVisible(false);
//                    }
//                }
//                break;
//        }

        CheckBox friendsCheckBox = (CheckBox) findViewById(R.id.checkbox_friends);
        CheckBox interestsCheckBox = (CheckBox) findViewById(R.id.checkbox_interested);
        CheckBox privatesCheckBox = (CheckBox) findViewById(R.id.checkbox_private);

        if (!friendsCheckBox.isChecked() && !privatesCheckBox.isChecked() && !interestsCheckBox.isChecked()){
            for(Marker mkr:friends) {
                mkr.setVisible(true);
            }
            for(Marker mkr:interests) {
                mkr.setVisible(true);
            }
            for(Marker mkr:privates) {
                mkr.setVisible(true);
            }
            return;
        }

        if (friendsCheckBox.isChecked()){
            for(Marker mkr:friends) {
                mkr.setVisible(true);
            }
        } else {
            for(Marker mkr:friends) {
                mkr.setVisible(false);
            }
        }
        if (privatesCheckBox.isChecked()){
            for(Marker mkr:privates) {
                mkr.setVisible(true);
            }
        } else {
            for(Marker mkr:privates) {
                mkr.setVisible(false);
            }
        }
        if (interestsCheckBox.isChecked()){
            for(Marker mkr:interests) {
                mkr.setVisible(true);
            }
        } else {
            for(Marker mkr:interests) {
                mkr.setVisible(false);
            }
        }


    }

    public void onClick(View v) {
        if (v.getId() == R.id.add) {
            Intent intent = new Intent(this, EventCreateActivity.class);
            startActivity(intent);
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
