package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class UsersProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_profile);
        //code starts here
        //this code implements the navigation function do not modify
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent;
            int itemID = item.getItemId();
            if(itemID == R.id.navigation_event_list){
                intent = new Intent(UsersProfileActivity.this, EventsActivity.class);
                startActivity(intent);
                return true;
            }
            if(itemID == R.id.navigation_home){
                intent = new Intent(UsersProfileActivity.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            if(itemID == R.id.navigation_scan){
                intent = new Intent(UsersProfileActivity.this, ScanActivity.class);
                startActivity(intent);
                return true;
            }
            if(itemID == R.id.navigation_event_map){
                intent = new Intent(UsersProfileActivity.this, MapActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });
        //navigation end here

        // the following this to redirect to other activity that let u edit your profile
        Button editButton = findViewById(R.id.edit);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersProfileActivity.this, ProfileEditActivity.class);
                startActivity(intent);
            }
        });
        // redirect to edit end here


        //code ends up here
    }
}