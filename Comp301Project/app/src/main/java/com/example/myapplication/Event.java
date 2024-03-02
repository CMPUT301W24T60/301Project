package com.example.myapplication;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    public String getEvent_Title() {
        return Event_Title;
    }

    public void setEvent_Title(String event_Title) {
        Event_Title = event_Title;
    }

    /*Event:
          Responsibilities:
            Store details about an event (title, description, date, location, QR code, etc.).
            Generate a unique QR code for check-ins.
            Track and update attendee check-ins.
          Collaborators:
            QRCodeGenerator
            DatabaseManager (Firebase)
            EventOrganizer
            LocationTracker
            * */
    private String Event_Title;

    public String getEvent_Description() {
        return Event_Description;
    }

    public void setEvent_Description(String event_Description) {
        Event_Description = event_Description;
    }

    private String Event_Description;

    public Date getEvent_Date() {
        return Event_Date;
    }

    public void setEvent_Date(Date event_Date) {
        Event_Date = event_Date;
    }

    private Date Event_Date;

    public Location getEvent_Location() {
        return Event_Location;
    }

    public void setEvent_Location(Location event_Location) {
        Event_Location = event_Location;
    }

    private Location Event_Location;

    public QRCode getEvent_QRCode() {
        return Event_QRCode;
    }

    private QRCode Event_QRCode;

    public ArrayList<Users> getAttendees() {
        return Attendees;
    }

    private ArrayList<User> Attendees;
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
