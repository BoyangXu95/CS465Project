package edu.illinois.cs465.cs465project;

import java.util.List;
import java.util.ArrayList;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.CheckBox;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.widget.ImageButton;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener{

    private GoogleMap mMap;
    private ImageButton add;
    List <Marker> friends = new ArrayList<>();
    List <Marker> privates = new ArrayList<>();
    List <Marker> interests = new ArrayList<>();
    private ImageButton menu;
    private DrawerLayout drawer;
    private ImageButton current_location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        add = (ImageButton) findViewById(R.id.add);
        add.setOnClickListener(this);
        menu = (ImageButton) findViewById(R.id.hamburger);
        menu.setOnClickListener(this);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        current_location = (ImageButton) findViewById(R.id.current_location);
        current_location.setOnClickListener(this);
    }

//    public void onClick(View v) {
//        if (v.getId() == R.id.add) {
//            Intent intent = new Intent(this, EventCreateActivity.class);
//            startActivity(intent);
//        }
//    }


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
        friends1 = mMap.addMarker(new MarkerOptions()
                .position(teamoji)
                .title("Marker in friends")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        friends.add(friends1);

        LatLng grainger = new LatLng(40.112510, -88.226773);
        private1 = mMap.addMarker(new MarkerOptions().position(grainger).title("Marker in private"));
        privates.add(private1);
        LatLng union = new LatLng(40.109432, -88.227126);
        interest1 = mMap.addMarker(new MarkerOptions()
                .position(union)
                .title("Marker in Interest")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        interests.add(interest1);


        LatLng under = new LatLng(40.104861, -88.227137);
        private2 = mMap.addMarker(new MarkerOptions().position(under).title("Marker in private"));

        privates.add(private2);
        LatLng bookstore = new LatLng(40.108534, -88.229278);
        friends2=mMap.addMarker(new MarkerOptions()
                .position(bookstore)
                .title("Marker in friends")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        friends.add(friends2);

        LatLng siebel = new LatLng(40.113908, -88.225008);
        private3 = mMap.addMarker(new MarkerOptions().position(siebel).title("Marker in private"));

        privates.add(private3);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(union));
        mMap.setMinZoomPreference(15.0f);
    }
    public void onCheckboxClicked(View view) {
        boolean checked = ((CheckBox) view).isChecked();
        switch(view.getId()) {
            case R.id.checkbox_friends:
                if (checked){
                    for(Marker mkr:privates){
                        mkr.setVisible(false);
                    }
                    for(Marker mkr:interests){
                        mkr.setVisible(false);
                    }
                }
                else {
                    for(Marker mkr:privates){
                        mkr.setVisible(true);
                    }
                    for(Marker mkr:interests){
                        mkr.setVisible(true);
                    }
                }
                break;
            case R.id.checkbox_interested:
                if (checked){
                    for(Marker mkr:privates){
                        mkr.setVisible(false);
                    }
                    for(Marker mkr:friends){
                        mkr.setVisible(false);
                    }
                }
                else {
                    for(Marker mkr:privates){
                        mkr.setVisible(true);
                    }
                    for(Marker mkr:friends){
                        mkr.setVisible(true);
                    }
                }
                break;
            case R.id.checkbox_private:
                if (checked){
                    for(Marker mkr:interests){
                        mkr.setVisible(false);
                    }
                    for(Marker mkr:friends){
                        mkr.setVisible(false);
                    }
                }
                else{
                    for(Marker mkr:interests){
                        mkr.setVisible(true);
                    }
                    for(Marker mkr:friends){
                        mkr.setVisible(true);
                    }
                }
                break;
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
