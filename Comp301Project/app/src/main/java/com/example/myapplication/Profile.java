package com.example.myapplication;

import android.location.Location;

public class Profile {
    private String Username;
    private Location Place;
    //private Picture; figure out pictures here

    public Location getPlace() {
        return Place;
    }
    public String getUsername() {
        return Username;
    }
    public void setUsername(String username){
        Username=username;
    }
    public void setPlace(Location place){
        Place=place;
    }
}
