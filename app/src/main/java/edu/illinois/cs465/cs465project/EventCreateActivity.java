package edu.illinois.cs465.cs465project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.location.Address;
import android.location.Geocoder;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.util.Arrays;
import java.util.List;


public class EventCreateActivity extends Activity implements View.OnClickListener {

    private ImageButton back;
    private Button createEventButton;
    private Geocoder geocoder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        geocoder = new Geocoder(this);

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);

        createEventButton = findViewById(R.id.create_event);
        createEventButton.setOnClickListener(this);

    }
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            this.finish();
        }

        if (v.getId() == R.id.create_event){
            String eventName = ((EditText)findViewById(R.id.event_name)).getText().toString();
            if (eventName.equals("")){
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, "event name required", duration);
                toast.show();
                return;
            }

            String locationName = ((EditText)findViewById(R.id.address_input)).getText().toString();
            if(locationName.equals("")){
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, "location required", duration);
                toast.show();
                return;
            }

            int maxSize = (int)((Spinner)findViewById(R.id.size_spinner)).getSelectedItem();

            List<Address> addressList;
            try {
                addressList = geocoder.getFromLocationName(locationName, 5);
            } catch (Exception e) {
                return;
            }

            if (addressList.isEmpty()){
                int duration = Toast.LENGTH_SHORT;
                Toast toast = Toast.makeText(this, "location invalid", duration);
                toast.show();
                return;
            }

            String hashtags = ((EditText)findViewById(R.id.hashtags)).getText().toString();

            Intent output = new Intent();
            output.putExtra("name", ((EditText)findViewById(R.id.event_name)).getText().toString());
            output.putExtra("private", ((Switch) findViewById(R.id.friends_only)).isChecked());
            output.putExtra("address", locationName);
            output.putExtra("lat", addressList.get(0).getLatitude());
            output.putExtra("lng", addressList.get(0).getLongitude());
            output.putExtra("max Size", maxSize);
            output.putExtra("hashtags", (hashtags.split(";")));
            output.putExtra("description", ((EditText)findViewById(R.id.description)).getText().toString());
            setResult(RESULT_OK, output);
            this.finish();
        }

    }
}
