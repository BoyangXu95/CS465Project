package edu.illinois.cs465.cs465project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class EventCreateActivity extends Activity implements View.OnClickListener {

    private ImageButton back;
    private Button createEventButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

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
            Intent output = new Intent();
            output.putExtra("name", "newEvent");
            setResult(RESULT_OK, output);
            this.finish();
        }

    }
}
