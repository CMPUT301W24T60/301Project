package com.example.myapplication;

<<<<<<< Updated upstream
import android.location.Location;
=======
import android.graphics.Bitmap;

import com.google.zxing.qrcode.encoder.QRCode;
>>>>>>> Stashed changes

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

    //  call QR_code_generator to generate QR code based on provided event details and return to event for
    // storage as bitmap
    public Bitmap GenQRcode() {
        QRCodeGenerator qrGenerator = new QRCodeGenerator();
        // Concatenate event details into a single string
        // Example: "Event Title: Example Event; Event Location;Date: 2024-03-06; Description: Example Description"
        String eventDetails = "Event Title: " + this.Event_Title + "; "Event Location:" + this.Event_Location +"; Date: " + this.Event_Date.toString() + "; Description: " + this.Event_Description;
        // Assuming default dimensions for the QR code
        int width = 500;
        int height = 500;

        // Generate the QR code with the event details
        Bitmap qrCodeBitmap = qrGenerator.generateQRCode(eventDetails, width, height);

        // store QR code within event class?
        // For demonstration, assuming there's a way to store or handle the Bitmap object:
        this.Event_QRCode = qrCodeBitmap; // Make sure the Event_QRCode property is of a type that can store a Bitmap

        return qrCodeBitmap;
    }

<<<<<<< Updated upstream
}
=======
>>>>>>> Stashed changes
