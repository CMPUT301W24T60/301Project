package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ScanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        //code starts here
        //this code implements the navigation function do not modify
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent;
            int itemID = item.getItemId();
            if(itemID == R.id.navigation_User){
                intent = new Intent(ScanActivity.this, UsersProfileActivity.class);
                startActivity(intent);
                return true;
            }
            if(itemID == R.id.navigation_home){
                intent = new Intent(ScanActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            if(itemID == R.id.navigation_event_list){
                intent = new Intent(ScanActivity.this, EventsActivity.class);
                startActivity(intent);
                return true;
            }
            if(itemID == R.id.navigation_event_map){
                intent = new Intent(ScanActivity.this, MapActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });
        //navigation end here


        //code ends up here
    }
}