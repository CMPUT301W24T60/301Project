package com.example.myapplication;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find the NavHostFragment
        NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        // Ensure the NavHostFragment is not null
        if (navHostFragment != null) {
            navController = navHostFragment.getNavController();
        } else {
            throw new RuntimeException("NavHostFragment not found!");
        }

        BottomNavigationView navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);

        // Set click listeners for each grid item to navigate to the corresponding Fragment
        findViewById(R.id.events_container).setOnClickListener(view -> navController.navigate(R.id.eventPageFragment));
        findViewById(R.id.map_container).setOnClickListener(view -> navController.navigate(R.id.mapViewFragment));
        findViewById(R.id.calendar_container).setOnClickListener(view -> navController.navigate(R.id.calendarFragment));
        findViewById(R.id.profile_container).setOnClickListener(view -> navController.navigate(R.id.userProfileFragment));
    }
}
