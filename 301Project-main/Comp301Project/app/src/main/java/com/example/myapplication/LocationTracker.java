package com.example.myapplication; /**
 * LocationTracker
 * <p>
 * Author: Vasu Aggarwal
 * Project: Event Manager
 * Course: CMPUT 301
 * <p>
 * Description: The code below implements the location tracker functionality of the project. It uses geolocation API
 * to verify that attendees are physically present at the event location during check-in.
 * <p>
 * When the app starts, the location tracker initializes with event location coordinates and radius.
 * The app constantly checks the device's location changes using GPS.
 * It alerts the admin when the device is within the specified radius of the event location.
 */
import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;

public class LocationTracker {
    private static final String TAG = "LocationTracker";

    private Context mContext;
    private LocationManager mLocationManager;
    private LocationListener mLocationListener;

    // Event location coordinates (latitude and longitude)
    private double eventLatitude;
    private double eventLongitude;
    private float eventLocationRadius; // Radius around the event location within which the attendee is considered present

    // Flag to indicate if an attendee is present at the event
    private boolean attendeePresent;

    /**
     * Constructs a new LocationTracker.
     *
     * @param context            The context in which the location tracker is initialized.
     * @param eventLatitude      The latitude of the event location.
     * @param eventLongitude     The longitude of the event location.
     * @param eventLocationRadius The radius around the event location within which the attendee is considered present.
     */
    public LocationTracker(Context context, double eventLatitude, double eventLongitude, float eventLocationRadius) {
        this.mContext = context;
        this.eventLatitude = eventLatitude;
        this.eventLongitude = eventLongitude;
        this.eventLocationRadius = eventLocationRadius;

        // Initialize the flag to false initially
        this.attendeePresent = false;

        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        mLocationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "Current location: " + location.getLatitude() + ", " + location.getLongitude());
                // Check if the current location is within the event location radius
                if (isWithinEventLocation(location.getLatitude(), location.getLongitude())) {
                    // Set the attendeePresent flag to true if within event location
                    attendeePresent = true;
                } else {
                    // Set the attendeePresent flag to false if outside event location
                    attendeePresent = false;
                }
            }
        };
    }

    /**
     * Starts location updates to track the device's location changes.
     */
    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Location permissions not granted
            Log.e(TAG, "Location permissions not granted.");
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
    }

    /**
     * Stops location updates.
     */
    public void stopLocationUpdates() {
        mLocationManager.removeUpdates(mLocationListener);
    }

    /**
     * Checks if the current location is within the event location radius.
     *
     * @param currentLatitude  The latitude of the current location.
     * @param currentLongitude The longitude of the current location.
     * @return true if the current location is within the event location radius, false otherwise.
     */
    private boolean isWithinEventLocation(double currentLatitude, double currentLongitude) {
        float[] distance = new float[2];
        Location.distanceBetween(eventLatitude, eventLongitude, currentLatitude, currentLongitude, distance);
        return distance[0] <= eventLocationRadius;
    }

    /**
     * Checks if an attendee is present at the event.
     *
     * @return true if an attendee is present at the event, false otherwise.
     */
    public boolean isAttendeePresent() {
        return attendeePresent;
    }
}
