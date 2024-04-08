/*
 * MainActivity
 * This class represents the main activity where users can view events and interact with their profile.
 */

package com.mp012202200038_01.BasicEventManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {
    private ArrayList<Event> events = new ArrayList<>();
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private EventDao eventDao;
    private EventDatabase db;
    /**
     * The Profile.
     */
    ImageView profile;
    private String username;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = EventDatabase.getDatabase(this); // This is correct now
        eventDao = db.eventDao(); // Corrected the assignment here

        initializeViews();
        initializeMockEvents(); // Use this method for now until backend integration
        setupActivityResultLauncher();

    }

    private void initializeViews() {
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EventAdapter(this, events);
        recyclerView.setAdapter(adapter);

        FloatingActionButton add = findViewById(R.id.new_event_button);
        add.setOnClickListener(view -> {
            Toast.makeText(MainActivity.this, "Not Admin! Cannot Add Events.", Toast.LENGTH_SHORT).show();
            // Implementation to add event
        });

        ImageView profile = findViewById(R.id.imgProfile);
        profile.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EditProfileActivity.class);
            intent.putExtra("username", username);
            intent.putExtra("email", email);
            startActivity(intent);
        });
    }

    private void initializeMockEvents() {
        // Mock data for demonstration
        events.add(new Event("1", "Unlocking SEO Secrets in 2024: Webinar", "Master the latest SEO strategies with digital marketing gurus, Alex Smith and Jordan Lee. Gain insights to dominate search rankings.", "14", "September", "Rogers Place, Edmonton. Check email for online access", "18:00", "Poster URL", "QR Code Data", true));
        events.add(new Event("2", "24HR Fest", "End-of-Year Music Extravaganza featuring live performances from top artists across genres. Join us for a night of unforgettable melodies and vibrant energy.", "25", "December", "Central Park Amphitheater, New York. Bring your festive spirit and love for music. Food and drinks available on site.", "18:00", "https://us.images.westend61.de/0001361979pw/crowd-at-music-concert-EYF03813.jpg ", "CONCERT2024_XYZ", true));
        events.add(new Event("3", "Next 20 Years of Moore's Law", "2024 Digital Innovation Summit: Unleashing Potential in Tech. Industry leaders share insights on AI, blockchain, and more. Network with professionals.", "15", "October", "Silicon Valley Conference Center, California. Perfect for tech entrepreneurs, developers, and enthusiasts. Interactive workshops and panels.", "09:00", "https://images.pexels.com/photos/159213/hall-congress-architecture-building-159213.jpeg?cs=srgb&dl=pexels-pixabay-159213.jpg&fm=jpg", "CONFERENCE2024_ABC", false));

        // Initialize the adapter with mock data
        this.adapter = new EventAdapter(this, events);
        RecyclerView recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged(); // Make sure adapter is notified about the check in

        adapter.setOnItemClickListener(event -> {
            Intent intent = new Intent(MainActivity.this, EventDetailActivity.class);
            intent.putExtra("event", event);
            startActivity(intent);
            // checked in event display
            activityResultLauncher.launch(intent);
        });
    }
    private void setupActivityResultLauncher() {
        activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        int eventId = result.getData().getIntExtra("eventId", -1);
                        boolean isCheckedIn = result.getData().getBooleanExtra("isCheckedIn", false);

                        // Assuming 'events' is the list that backs the adapter:
                        for (Event event : events) {
                            if (event.getId() == eventId) {
                                event.setIsCheckedIn(isCheckedIn);
                                adapter.notifyDataSetChanged();
                                break;
                            }
                        }
                    }
                }
        );
    }

}
    // TODO: Replace mock data with actual data fetching from your backend or local database


    // TODO: Implement methods for poster upload and handling QR code data