package com.example.eventmanager;
import android.graphics.Bitmap;

import java.util.ArrayList;
import java.util.Date;

public class Event {
    /*Event:
          Responsibilities:
            Store details about an event (title, description, date, location, QR code, etc.).
            Generate a unique QR code for check-ins.
            Track and update attendee check-ins.
          Collaborators:
            QRCodeGenerator
            DatabaseManager (Firebase)s
            EventOrganizer
            LocationTracker
            * */
    private String EventTitle;
    private String EventDescription;
    private Date EventDate;
    private Bitmap EventQRCode;
    private ArrayList<User> Attendees;
    private Organizer organizer;

    //private Location Event_Location; to be added

    public String getEventTitle() {
        return EventTitle;
    }

    public void setEventTitle(String eventTitle) {
        EventTitle = eventTitle;
    }

    public String getEventDescription() {
        return EventDescription;
    }

    public void setEventDescription(String eventDescription) {
        EventDescription = eventDescription;
    }

    public Date getEventDate() {
        return EventDate;
    }

    public void setEventDate(Date eventDate) {
        EventDate = eventDate;
    }

    public Bitmap getEventQRCode() {
        return EventQRCode;
    }

    public void setEventQRCode(Bitmap eventQRCode) {
        EventQRCode = eventQRCode;
    }

    public void setAttendees(ArrayList<User> attendees) {
        Attendees = attendees;
    }

    public ArrayList<User> getAttendees() {
        return Attendees;
    }

    public Organizer getOrganizer() {
        return organizer;
    }
    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
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
            // send message that user is not attending this event
        }
        else{
            //send message that you are not attending this event
        }

    }


}