package com.example.myapplication;

import android.graphics.Bitmap;
import android.location.Location;
import android.util.Log;

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
        } else {
            //send you are already added to the user
        }
        if (!Attendees.contains(a)) {
            //send message to user that something went wrong
        }
    }

    public void delAttendees(User b) {
        if (Attendees.contains(b)) {
            Attendees.remove(b);
            // send message that user is not attenind this event
        } else {
            //send message that you are not attending this event
        }

    }


    public class QRCode {
        private Bitmap bitmap;

        public QRCode(Bitmap bitmap) {
            this.bitmap = bitmap;
        }

        // Getter and Setter for the Bitmap if needed
        public Bitmap getBitmap() {
            return bitmap;
        }

        public void setBitmap(Bitmap bitmap) {
            this.bitmap = bitmap;
        }
    }

    public QRCode genQRcode() {
        // Assuming 'Event Details or URL' is a placeholder for actual event details.
        String eventDetails = "Title: " + getEvent_Title() + ", Desc: " + getEvent_Description();

        // Generate the QR code Bitmap.
        QR_Code_Generator generator = new QR_Code_Generator();
        Bitmap qrCodeBitmap = generator.generateQRCode(eventDetails, 500, 500);

        // If qrCodeBitmap is not null, it means QR code generation was successful.
        if (qrCodeBitmap != null) {
            this.Event_QRCode = new QRCode(qrCodeBitmap);
        } else {
            // Handle error notifying the user.
            Log.e("Event", "QR code generation failed.");
            // Set the Event_QRCode to null to indicate failure.
            this.Event_QRCode = null;
        }

        // Return the QRCode object, which will be null if the generation failed.
        return this.Event_QRCode;
    }
}