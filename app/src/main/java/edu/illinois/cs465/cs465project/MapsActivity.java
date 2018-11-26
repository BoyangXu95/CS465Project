package edu.illinois.cs465.cs465project;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
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
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;


public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, View.OnClickListener{

    private GoogleMap mMap;
    private ImageButton add;
    private ImageButton menu;
    private DrawerLayout drawer;

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
    }

    public void onClick(View v) {
        if (v.getId() == R.id.add) {
            Intent intent = new Intent(this, EventCreateActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.hamburger){
            drawer.openDrawer(Gravity.START);
        }
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
        mMap.addMarker(new MarkerOptions()
                        .position(teamoji)
                        .title("Marker in friends")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

        LatLng grainger = new LatLng(40.112510, -88.226773);
        mMap.addMarker(new MarkerOptions().position(grainger).title("Marker in private"));

        LatLng union = new LatLng(40.109432, -88.227126);
        mMap.addMarker(new MarkerOptions()
                        .position(union)
                        .title("Marker in Interest")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        LatLng under = new LatLng(40.104861, -88.227137);
        mMap.addMarker(new MarkerOptions().position(under).title("Marker in private"));

        LatLng bookstore = new LatLng(40.108534, -88.229278);
        mMap.addMarker(new MarkerOptions()
                        .position(bookstore)
                        .title("Marker in friends")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));


        LatLng siebel = new LatLng(40.113908, -88.225008);
        mMap.addMarker(new MarkerOptions().position(siebel).title("Marker in private"));

        mMap.moveCamera(CameraUpdateFactory.newLatLng(union));
        mMap.setMinZoomPreference(15.0f);
    }
}
