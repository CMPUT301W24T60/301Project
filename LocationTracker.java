/* LocationTracker

   Author: Vasu Aggarwal
   Project: Event Manager
   Course: CMPUT 301

   Description: The code below implements the location tracker functionality of the project. it uses geolocation API to verify that attendees are physically present at the event location during check-in.

   when the app starts, the location tracker initializes with event location coordinates and radius.
   the app constantly checks the device's location changes using GPS.
   it alerts the admin when the device is within the specified radius of the event location.
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

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {}

            @Override
            public void onProviderEnabled(String provider) {}

            @Override
            public void onProviderDisabled(String provider) {}
        };
    }

    // Start location updates
    public void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Location permissions not granted
            Log.e(TAG, "Location permissions not granted.");
            return;
        }
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocationListener);
    }

    // Stop location updates
    public void stopLocationUpdates() {
        mLocationManager.removeUpdates(mLocationListener);
    }

    // Check if the current location is within the event location radius
    private boolean isWithinEventLocation(double currentLatitude, double currentLongitude) {
        float[] distance = new float[2];
        Location.distanceBetween(eventLatitude, eventLongitude, currentLatitude, currentLongitude, distance);
        return distance[0] <= eventLocationRadius;
    }

    // Method to check if an attendee is present at the event
    public boolean isAttendeePresent() {
        return attendeePresent;
    }
}

