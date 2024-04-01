package com.example.eventmanager;
import android.graphics.Bitmap;

import com.google.firebase.firestore.GeoPoint;

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
    private String EventId;
    private String EventTitle;
    private String EventDescription;
    private String checkInQRCode;  //base64string representation
    private String promoQRCode;   //base64string representation
    private String EventDate;
    private ArrayList<User> SignedAttendees;
    private ArrayList<User> CheckedInAttendees;
    private User organizer;
    private ArrayList<Integer> attendCount; // number of times a attendee has checked in. parallel with CheckedInAttendees.
    private ArrayList<GeoPoint> geoLocation; // (latitude, longitude) from which attendee has checked in. parallel with CheckedInAttendees.
    private int signupLimit;
    private int signupCount;
    private int checkInCount;
    private String PosterUrl;


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

    public User getOrganizer() {
        return organizer;
    }
    public void setOrganizer(User organizer) {
        this.organizer = organizer;
    }

    public ArrayList<Integer> getAttendCount() {
        return attendCount;
    }

    public void setAttendCount(ArrayList<Integer> attendCount) {
        this.attendCount = attendCount;
    }

    public ArrayList<User> getSignedAttendees() {
        return SignedAttendees;
    }

    public void setSignedAttendees(ArrayList<User> signedAttendees) {
        SignedAttendees = signedAttendees;
    }

    public ArrayList<User> getCheckedInAttendees() {
        return CheckedInAttendees;
    }

    public void setCheckedInAttendees(ArrayList<User> checkedInAttendees) {
        CheckedInAttendees = checkedInAttendees;
    }

    public int getSignupLimit() {
        return signupLimit;
    }

    public void setSignupLimit(int signupLimit) {
        this.signupLimit = signupLimit;
    }

    public int getSignupCount() {
        return signupCount;
    }

    public void setSignupCount(int signupCount) {
        this.signupCount = signupCount;
    }

    public int getCheckInCount() {
        return checkInCount;
    }

    public void setCheckInCount(int checkInCount) {
        this.checkInCount = checkInCount;
    }

    public String getEventDate() {
        return EventDate;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public String getPosterUrl() {
        return PosterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        PosterUrl = posterUrl;
    }

    public String getEventId() {
        return EventId;
    }

    public void setEventId(String eventId) {
        EventId = eventId;
    }

    public ArrayList<GeoPoint> getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(ArrayList<GeoPoint> geoLocation) {
        this.geoLocation = geoLocation;
    }

    public String getCheckInQRCode() {
        return checkInQRCode;
    }

    public void setCheckInQRCode(String checkInQRCode) {
        this.checkInQRCode = checkInQRCode;
    }

    public String getPromoQRCode() {
        return promoQRCode;
    }

    public void setPromoQRCode(String promoQRCode) {
        this.promoQRCode = promoQRCode;
    }
}