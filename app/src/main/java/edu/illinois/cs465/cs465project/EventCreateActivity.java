package edu.illinois.cs465.cs465project;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class EventCreateActivity extends Activity implements View.OnClickListener {

    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        back = (ImageButton) findViewById(R.id.back);
        back.setOnClickListener(this);
    }
    public void onClick(View v) {
        if (v.getId() == R.id.back) {
            this.finish();
        }
    }
}
