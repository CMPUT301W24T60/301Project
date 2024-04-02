package com.example.eventmanager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.eventmanager.AdminPackage.AdminActivity;
import com.example.eventmanager.AttendeePackage.AttendeeActivity;
import com.example.eventmanager.OrganizerPackage.OrganizerActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        // Initialize DatabaseManager
        DatabaseManager database = new DatabaseManager();

        // Get device ID
        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

        // Get buttons from the layout
        Button attend = findViewById(R.id.button7);
        Button organize = findViewById(R.id.button8);
        Button admin = findViewById(R.id.button9);

        attend.setOnClickListener(view -> {
            database.checkIfUserIdExists(androidId, exists -> {
                if (exists) {
                    // User exists, proceed to AttendeeActivity
                    Intent attendIntent = new Intent(MainActivity.this, AttendeeActivity.class);
                    startActivity(attendIntent);
                } else {
                    // User does not exist, proceed to UserRegistration
                    Intent registrationIntent = new Intent(MainActivity.this, UserRegistration.class);
                    startActivity(registrationIntent);
                }
            });
        });

        organize.setOnClickListener(view -> {
            database.checkIfUserIdExists(androidId, exists -> {
                if (exists) {
                    // User exists, proceed to AttendeeActivity
                    Intent attendIntent = new Intent(MainActivity.this, OrganizerActivity.class);
                    startActivity(attendIntent);
                } else {
                    // User does not exist, proceed to UserRegistration
                    Intent registrationIntent = new Intent(MainActivity.this, UserRegistration.class);
                    startActivity(registrationIntent);
                }
            });
        });

        admin.setOnClickListener(view -> {
            database.checkIfUserIdExists(androidId, exists -> {
                if (exists) {
                    // User exists, check for admin rights
                    database.getUserType(androidId, new DatabaseManager.UserTypeCallback() {
                        @Override
                        public void onUserTypeReceived(String userType) {
                            if (userType != null) {
                                // Handle the received user type
                                Log.d("UserType", "User type is: " + userType);

                                // Check if the user type is not "admin"
                                if (!userType.equals("admin")) {
                                    // Show a toast message and return
                                    Toast.makeText(MainActivity.this, "You don't have Admin rights", Toast.LENGTH_SHORT).show();
                                    return;
                                }

                                // User is an admin, start AdminActivity
                                Intent attendIntent = new Intent(MainActivity.this, AdminActivity.class);
                                startActivity(attendIntent);
                            } else {
                                Log.d("UserType", "User does not have a type set.");
                                // Optionally, handle the scenario when userType is not set
                            }
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.d("UserType","Error loading the userType from database.");
                        }
                    });
                } else {
                    // User does not exist, proceed to UserRegistration
                    Intent registrationIntent = new Intent(MainActivity.this, UserRegistration.class);
                    startActivity(registrationIntent);
                }
            });
        });
    }
}