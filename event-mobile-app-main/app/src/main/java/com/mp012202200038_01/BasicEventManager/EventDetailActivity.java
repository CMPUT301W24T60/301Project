/*
 * EventDetailActivity
 * This class represents the activity for displaying details of an event.
 */
package com.mp012202200038_01.BasicEventManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

/**
 * The type Event detail activity.
 */
public class EventDetailActivity extends AppCompatActivity {

    private String eventKey;
    private Event event;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Intent intent = getIntent();
        Event event = (Event) intent.getSerializableExtra("event");

        // Populate the views with the event data
        ((TextView) findViewById(R.id.event_title)).setText(event.getTitle());
        ((TextView) findViewById(R.id.event_day)).setText(event.getDay());
        ((TextView) findViewById(R.id.event_month)).setText(event.getMonth());
        ((TextView) findViewById(R.id.event_location)).setText(event.getLocation());
        ((TextView) findViewById(R.id.event_time)).setText(event.getTime());
        ((TextView) findViewById(R.id.event_description)).setText(event.getDesc());

        ImageView eventImage = findViewById(R.id.event_image);
        if (event.getPosterUrl() != null && !event.getPosterUrl().isEmpty()) {
            Picasso.get()
                    .load(event.getPosterUrl())
                    .placeholder(R.drawable.placeholder) // optional
                    .error(R.drawable.image) // optional
                    .into(eventImage);
        }
        // check in button logic
        Button checkInButton = findViewById(R.id.button_check_in);
        checkInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the event as checked-in
                // After checking in
                // When setting the result before finishing EventDetailActivity
                Intent returnIntent = new Intent();
                returnIntent.putExtra("eventId", event.getId()); // Use the actual ID of your event
                returnIntent.putExtra("isCheckedIn", true);
                setResult(Activity.RESULT_OK, returnIntent);
                Toast.makeText(EventDetailActivity.this, "Checked-in to Event", Toast.LENGTH_SHORT).show();
                finish();

            }
        });

        // Initial button state based on check-in status
        checkInButton.setEnabled(!event.isCheckedIn());
    }
}

