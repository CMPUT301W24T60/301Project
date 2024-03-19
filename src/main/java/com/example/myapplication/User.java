package com.example.myapplication;

import android.location.Location;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class User {

    private ArrayList <String> Notifications;
    /*create notifications class that has a time added
    so the notifications can be sorted by time and by who sent them
     */
    private Profile UserProfile;
    private ArrayList<Event> UserEvent;

    public User(ArrayList<String> notifications) {
        UserProfile=new Profile();

    }
    // might have to overide this as well to form a system

    public void newNotification(String Notify){
        Notifications.add(Notify);
    }
   public void sortNotifications(){
        // add in functionalllity later
   }
   public void addEvent(Event NewEvent){
        UserEvent.add(NewEvent);
   }
   public void deleteEvent(Event deleteEvent) throws Exception{

        if (UserEvent.contains(deleteEvent)){
            UserEvent.remove(deleteEvent);
        }
        else{
            throw new Exception("no event by this title");


        }
   }

   public Profile getUserProfile(){
        return UserProfile;
    }


}
