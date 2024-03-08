package com.example.myapplication;

import android.location.Location;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    private String Event_Title;
    private ArrayList<User> Attendees;
    private QRCode Event_QRCode;
    private String Event_Description;
    private Date Event_Date;
    private Location Event_Location;
    public String getEvent_Title() {
        return Event_Title;
    }

    public void setEvent_Title(String event_Title) {
        Event_Title = event_Title;
    }

    public String getEvent_Description() {
        return Event_Description;
    }
    public void setEvent_Description(String event_Description) {
        Event_Description = event_Description;
    }

    public Date getEvent_Date() {
        return Event_Date;
    }
    public void setEvent_Date(Date event_Date) {
        Event_Date = event_Date;
    }

    public Location getEvent_Location() {
        return Event_Location;
    }

    public void setEvent_Location(Location event_Location) {
        Event_Location = event_Location;
    }



    public QRCode getEvent_QRCode() {
        return Event_QRCode;
    }

    public ArrayList<User> getAttendees() {
        return Attendees;
    }

    public void addAttendees(User a) {
        if (!Attendees.contains(a)) {
            Attendees.add(a);
            //send message thar user is added
        }
        else{
            //send you are already added to the user
        }
        if(!Attendees.contains(a)){
            //send message to user that something went wrong
        }
    }
    public void delAttendees(User b){
        if(Attendees.contains(b)){
            Attendees.remove(b);
            // send message that user is not attenind this event
        }
        else{
            //send message that you are not attending this event
        }

    }

    public QRCode GenQRcode(){
        //call QR code Generator
        QRCode Unique_QRcode;
        Unique_QRcode=Event_QRCode;
        return Unique_QRcode;
    }

}
