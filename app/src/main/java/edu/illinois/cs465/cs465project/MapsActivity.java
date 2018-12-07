package edu.illinois.cs465.cs465project;

import java.util.List;
import java.util.ArrayList;
import java.lang.Object;

import 	android.os.CountDownTimer;
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

import java.util.concurrent.TimeUnit;
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

    public void createNewEvent( final Event newEvent) {
        LatLng newEventPosition = new LatLng(40.110090, -88.229600);
        final Marker newEventMarker = mMap.addMarker(new MarkerOptions()
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


        popupWindow = new PopupWindow(container, 1000, 1500, true);
        popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
        TextView eventName = (TextView) popupWindow.getContentView().findViewById(R.id.popup);
        TextView eventDescription = (TextView) popupWindow.getContentView().findViewById(R.id.description);
        TextView eventPeople = (TextView) popupWindow.getContentView().findViewById(R.id.people);
        TextView eventHashtags = (TextView) popupWindow.getContentView().findViewById(R.id.hashtags);
        TextView eventDuration = (TextView) popupWindow.getContentView().findViewById(R.id.duration);
        TextView eventAddress = (TextView) popupWindow.getContentView().findViewById(R.id.address);
        if(curEvent.getRemainTime() == 1){
            eventDuration.setText(Integer.toString(curEvent.getRemainTime()) + " minute left until event finish!");
        } else {
            eventDuration.setText(Integer.toString(curEvent.getRemainTime()) + " minutes left until event finish!");
        }
        interestedButton = popupWindow.getContentView().findViewById(R.id.interested_button);
        interestedButton.setOnClickListener(this);

        if (curEvent.isInterested()){
            interestedButton.setText("Unlike");
        }

        eventName.setText(curEvent.getName());
        eventPeople.setText(curEvent.getNumberOfPeople()+" people");
        eventAddress.setText(curEvent.getAddress());
        eventDescription.setText(curEvent.getDescription());
        List<String> curHashTags = curEvent.getHashtags();
        String hashtags="";
        for(int i = 0; i <curHashTags.size(); i++){
            String temp = "#";
            temp = temp + curHashTags.get(i)+" ";
            hashtags+=temp;
        }
        eventHashtags.setText(hashtags);
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
        final Marker teamojiM = mMap.addMarker(new MarkerOptions()
                        .position(teamoji)

        );
        final Event teamojiEvent = new Event("Chatting");
        teamojiEvent.setDescription("Just Chilling, it's at second floor of teamoji");
        teamojiEvent.setNumberOfPeople(3);
        teamojiEvent.addHashTag("Movie");
        teamojiEvent.setFriendsGoing(true);
        teamojiEvent.setDuration(1);
        long teamojiDuration = TimeUnit.HOURS.toMillis(teamojiEvent.getDuriation());
        new CountDownTimer(teamojiDuration, 1000){
            public void onTick(long millisUntilFinished) {
                teamojiEvent.setRemainTime(millisUntilFinished);
            }
            public void onFinish() {
                markers.remove(teamojiM);
                if(teamojiEvent.isInterested())
                    interests.remove(teamojiM);
                if(teamojiEvent.isFriendsGoing())
                    friends.remove(teamojiM);
                if(teamojiEvent.isPrivateEvent())
                    privates.remove(teamojiM);
                teamojiM.remove();
            }
        }.start();
        teamojiEvent.setAddress("617 E Green St, Champaign, IL 61820");
        teamojiEvent.setLocation(teamoji);
        teamojiM.setTag(teamojiEvent);


        markers.add(teamojiM);

        friends.add(teamojiM);

        LatLng grainger = new LatLng(40.112510, -88.226773);
        final Marker ggM = mMap.addMarker(new MarkerOptions()
                                    .position(grainger)
        );
        final Event ggEvent = new Event("CS465 study session");
        ggEvent.addHashTag("FreeFood");
        ggEvent.addHashTag("Study");
        ggEvent.setNumberOfPeople(10);
        ggEvent.setPrivateEvent(true);
        ggEvent.setLocation(grainger);
        ggEvent.setDescription("Study for CS465 final exam, fourth floor");
        ggEvent.setDuration(2);
        ggEvent.setAddress("1301 W Springfield Ave, Urbana, IL 61801");
        long ggDuration = TimeUnit.HOURS.toMillis(ggEvent.getDuriation());
        new CountDownTimer(ggDuration, 1000){
            public void onTick(long millisUntilFinished) {
                ggEvent.setRemainTime(millisUntilFinished);
            }

            public void onFinish() {
                markers.remove(ggM);
                if(ggEvent.isInterested())
                    interests.remove(ggM);
                if(ggEvent.isFriendsGoing())
                    friends.remove(ggM);
                if(ggEvent.isPrivateEvent())
                    privates.remove(ggM);
                ggM.remove();
            }

        }.start();
        ggM.setTag(ggEvent);
        markers.add(ggM);
        privates.add(ggM);

        LatLng union = new LatLng(40.109432, -88.227126);
        final Marker unionM = mMap.addMarker(new MarkerOptions()
                        .position(union)
        );
        final Event unionEvent = new Event("CSSA interviews");
        unionEvent.addHashTag("FreeFood");
        unionEvent.addHashTag("professional");
        unionEvent.setNumberOfPeople(50);
        unionEvent.setInteretedEvent(true);
        unionEvent.setLocation(union);
        unionEvent.setDescription("Interviews, It is at basement of union");
        unionM.setTag(unionEvent);
        unionEvent.setDuration(2);
        unionEvent.setAddress("1401 W Green St, Urbana, IL 61801");
        long unionDuration = TimeUnit.HOURS.toMillis(unionEvent.getDuriation());
        new CountDownTimer(unionDuration, 1000){
            public void onTick(long millisUntilFinished) {
                unionEvent.setRemainTime(millisUntilFinished);
            }

            public void onFinish() {
                markers.remove(unionM);
                if(unionEvent.isInterested())
                    interests.remove(unionM);
                if(unionEvent.isFriendsGoing())
                    friends.remove(unionM);
                if(unionEvent.isPrivateEvent())
                    privates.remove(unionM);
                unionM.remove();
            }

        }.start();
        markers.add(unionM);
        interests.add(unionM);

        LatLng ugl = new LatLng(40.104861, -88.227137);
        final Marker uglM = mMap.addMarker(new MarkerOptions()
                                    .position(ugl)
        );
        final Event uglEvent = new Event("Review for final&Chilling");
        uglEvent.addHashTag("BoardGame");
        uglEvent.addHashTag("Review");
        uglEvent.setDescription("Review for finals, at first floor of UGL.");
        uglEvent.setNumberOfPeople(4);
        uglEvent.setPrivateEvent(true);
        uglEvent.setLocation(ugl);
        uglEvent.setDuration(4);
        uglEvent.setAddress("1402 W Gregory Dr, Urbana, IL 61801");
        long uglDuration = TimeUnit.HOURS.toMillis(uglEvent.getDuriation());
        new CountDownTimer(uglDuration, 1000){
            public void onTick(long millisUntilFinished) {
                uglEvent.setRemainTime(millisUntilFinished);
            }

            public void onFinish() {
                markers.remove(uglM);
                if(uglEvent.isInterested())
                    interests.remove(uglM);
                if(uglEvent.isFriendsGoing())
                    friends.remove(uglM);
                if(uglEvent.isPrivateEvent())
                    privates.remove(uglM);
                uglM.remove();
            }

        }.start();
        uglM.setTag(uglEvent);
        markers.add(uglM);
        privates.add(uglM);

        final LatLng bookstore = new LatLng(40.108534, -88.229278);
        final Marker bookstoreM = mMap.addMarker(new MarkerOptions()
                        .position(bookstore)
        );
        final Event bookstoreEvent = new Event("Starbucks");
        bookstoreEvent.addHashTag("BoardGame");
        bookstoreEvent.addHashTag("Coffee");
        bookstoreEvent.setNumberOfPeople(8);
        bookstoreEvent.setFriendsGoing(true);
        bookstoreEvent.setLocation(bookstore);
        bookstoreEvent.setDescription("Drink coffee, It's at the coner of the startbucks");
        bookstoreEvent.setDuration(3);
        bookstoreEvent.setAddress("809 S Wright St, Champaign, IL 61820");
        long bookstoreDuration = TimeUnit.HOURS.toMillis(bookstoreEvent.getDuriation());
        new CountDownTimer(bookstoreDuration, 1000){
            public void onTick(long millisUntilFinished) {
                bookstoreEvent.setRemainTime(millisUntilFinished);
            }

            public void onFinish() {
                markers.remove(bookstoreM);
                if(bookstoreEvent.isInterested())
                    interests.remove(bookstoreM);
                if(bookstoreEvent.isFriendsGoing())
                    friends.remove(bookstoreM);
                if(bookstoreEvent.isPrivateEvent())
                    privates.remove(bookstoreM);
                bookstoreM.remove();
            }

        }.start();
        bookstoreM.setTag(bookstoreEvent);
        markers.add(bookstoreM);
        friends.add(bookstoreM);

        LatLng siebel = new LatLng(40.113908, -88.225008);
        final Marker siebelM = mMap.addMarker(new MarkerOptions()
                                        .position(siebel)
//                                        .title("Marker in private")
        );
        final Event siebelEvent = new Event("CS461 mp4");
        siebelEvent.addHashTag("Dinner");
        siebelEvent.setNumberOfPeople(3);
        siebelEvent.setDescription("Do mps, it is at basement of siebel");
        siebelEvent.setPrivateEvent(true);
        siebelEvent.setLocation(siebel);
        siebelM.setTag(siebelEvent);
        siebelEvent.setDuration(5);
        siebelEvent.setAddress("201 N Goodwin Ave, Urbana, IL 61801");
        long siebelDuration = TimeUnit.HOURS.toMillis(siebelEvent.getDuriation());
        new CountDownTimer(siebelDuration, 1000){
            public void onTick(long millisUntilFinished) {
                siebelEvent.setRemainTime(millisUntilFinished);
            }

            public void onFinish() {
                markers.remove(siebelM);
                if(siebelEvent.isInterested())
                    interests.remove(siebelM);
                if(siebelEvent.isFriendsGoing())
                    friends.remove(siebelM);
                if(siebelEvent.isPrivateEvent())
                    privates.remove(siebelM);
                siebelM.remove();

            }

        }.start();
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
                    double lat = data.getDoubleExtra("lat",40.109000);
                    double lng = data.getDoubleExtra("lng", -88.220000);
                    String address = data.getStringExtra("address");
                    LatLng newEventLocation = new LatLng(lat, lng);
                    final Marker newEventMarker = mMap.addMarker(new MarkerOptions()
                            .position(newEventLocation)
                    );

//                    Event newEvent = new Event(newEventName);
//                    Event(String name, int numberOfPeople, String description, int startingHour, int startingMinute, String owner){
//
                    final Event newEvent = new Event(newEventName, data.getIntExtra("max Size", 2), data.getStringExtra("description"), data.getIntExtra("duration", 1), "tester");
                    newEvent.setAddress(address);
                    String[] hashtags = data.getStringArrayExtra("hashtags");
                    for (int i = 0; i < hashtags.length; i ++)
                        newEvent.addHashTag(hashtags[i]);
                    newEvent.setPrivateEvent(data.getBooleanExtra("private", false));
                    if (data.getBooleanExtra("private", false))
                        privates.add(newEventMarker);
                    // hard coded username
                    newEvent.setLocation(new LatLng(lat, lng));
                    newEventMarker.setTag(newEvent);
                    long eventDuration = TimeUnit.HOURS.toMillis(newEvent.getDuriation());

                    new CountDownTimer(eventDuration, 1000){
                        public void onTick(long millisUntilFinished) {
                            newEvent.setRemainTime(millisUntilFinished);
                        }
                        public void onFinish() {
                            markers.remove(newEventMarker);
                            if(newEvent.isInterested())
                                interests.remove(newEventMarker);
                            if(newEvent.isFriendsGoing())
                                friends.remove(newEventMarker);
                            if(newEvent.isPrivateEvent())
                                privates.remove(newEventMarker);
                            newEventMarker.remove();
                        }
                    }.start();
                    markers.add(newEventMarker);

                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lng), 15.0f));

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

            Event curEvent = (Event)curMarker.getTag();
            boolean intereted = (curEvent.isInterested());
            curEvent.setInteretedEvent(!intereted);

            Context context = getApplicationContext();

            CharSequence text;

            interestedButton = popupWindow.getContentView().findViewById(R.id.interested_button);

            if (curEvent.isInterested()){
                interests.add(curMarker);
                text = "Event liked!";
                interestedButton.setText("UNLIKE");
            } else {
                interests.remove(curMarker);
                text = "Event unliked!";
                interestedButton.setText("LIKE");
            }

            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
            filterChanged();
        }
    }

    public void searchEvent(String query){
        LayoutInflater layoutInflater = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.event_popup, null);
        //final PopupWindow popupWindow = new PopupWindow(container, 1000, 1500, true);
        int eventIndex = 0;

        for (int i = 0; i < markers.size(); i++){
            if (((Event) markers.get(i).getTag()).getName().toLowerCase().contains(query.toLowerCase())){
                eventIndex = i;
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom((((Event) markers.get(eventIndex).getTag()).getLocation()), 15.0f));

                Event curEvent = (Event) markers.get(i).getTag();
                popupWindow = new PopupWindow(container, 1000, 1500, true);
                popupWindow.showAtLocation(relativeLayout, Gravity.CENTER, 0, 0);
                TextView eventName = (TextView) popupWindow.getContentView().findViewById(R.id.popup);
                TextView eventDescription = (TextView) popupWindow.getContentView().findViewById(R.id.description);
                TextView eventPeople = (TextView) popupWindow.getContentView().findViewById(R.id.people);
                TextView eventHashtags = (TextView) popupWindow.getContentView().findViewById(R.id.hashtags);
                TextView eventDuration = (TextView) popupWindow.getContentView().findViewById(R.id.duration);
                TextView eventAddress = (TextView) popupWindow.getContentView().findViewById(R.id.address);
                eventDuration.setText("Duration: "+Long.toString(curEvent.getDuriation()) + "hours");
                interestedButton = popupWindow.getContentView().findViewById(R.id.interested_button);
                interestedButton.setOnClickListener(this);

                if (curEvent.isInterested()){
                    interestedButton.setText("Unlike");
                }

                eventName.setText(curEvent.getName());
                eventPeople.setText("Number of People: "+curEvent.getNumberOfPeople()+" people");
                eventDescription.setText("Description: "+curEvent.getDescription());
                eventAddress.setText(curEvent.getAddress());
                List<String> curHashTags = curEvent.getHashtags();
                String hashtags="";
                for(int tmp = 0; tmp <curHashTags.size(); tmp++){
                    String temp = "#";
                    temp = temp + curHashTags.get(tmp)+ " ";
                    hashtags+=temp;
                }
                eventHashtags.setText("Hashtags: " + hashtags);
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

