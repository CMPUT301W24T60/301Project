package com.example.myapplication;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;


import android.view.View;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.myapplication.databinding.ActivityLongClickedEventPageBinding;

import java.util.ArrayList;
import java.util.Date;

public class longClickedEventPage extends AppCompatActivity {
   private SingleEventAdapter oneEventAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        //code starts here still need to write how the object is passed through
        Date b=new Date();
        Location l=new Location("Los angeles");
        ArrayList<User> c=new ArrayList<User>();
        Event a= new Event("title","des",b,l,c);
       // Events=conect to firedatabase and create list
        oneEventAdapter=new SingleEventAdapter(this,a, 1);


        //this code implements the navigation function do not modify
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Intent intent;
            int itemID = item.getItemId();
            if(itemID == R.id.navigation_User){
                intent = new Intent(longClickedEventPage.this, UsersProfileActivity.class);
                startActivity(intent);
                return true;
            }
            if(itemID == R.id.navigation_home){
                intent = new Intent(longClickedEventPage.this, MainActivity.class);
                startActivity(intent);
                return true;
            }
            if(itemID == R.id.navigation_scan){
                intent = new Intent(longClickedEventPage.this, ScanActivity.class);
                startActivity(intent);
                return true;
            }
            if(itemID == R.id.navigation_event_map){
                intent = new Intent(longClickedEventPage.this, MapActivity.class);
                startActivity(intent);
                return true;
            }

            return false;
        });


        //code ends up here
    }
}