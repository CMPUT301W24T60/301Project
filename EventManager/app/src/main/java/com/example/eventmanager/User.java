package com.example.eventmanager;

import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;

public class User {

    private String userId;
    private String userType;
    private String name;
    private String email;
    private String phoneNumber;
    private String profilePicUrl;
    private String homePageUrl;
    private ArrayList<Event> attendSignedUpEvents;
    private ArrayList<Event> attendCurrentEvents;
    private ArrayList<Event> organizeEvents;
    private Boolean enableLocationTracking;
    private ArrayList<String> notificationList;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getHomePageUrl() {
        return homePageUrl;
    }

    public void setHomePageUrl(String homePageUrl) {
        this.homePageUrl = homePageUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public ArrayList<Event> getAttendSignedUpEvents() {
        return attendSignedUpEvents;
    }

    public void setAttendSignedUpEvents(ArrayList<Event> attendSignedUpEvents) {
        this.attendSignedUpEvents = attendSignedUpEvents;
    }

    public ArrayList<Event> getAttendCurrentEvents() {
        return attendCurrentEvents;
    }

    public void setAttendCurrentEvents(ArrayList<Event> attendCurrentEvents) {
        this.attendCurrentEvents = attendCurrentEvents;
    }

    public ArrayList<Event> getOrganizeEvents() {
        return organizeEvents;
    }

    public void setOrganizeEvents(ArrayList<Event> organizeEvents) {
        this.organizeEvents = organizeEvents;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getEnableLocationTracking() {
        return enableLocationTracking;
    }

    public void setEnableLocationTracking(Boolean enableLocationTracking) {
        this.enableLocationTracking = enableLocationTracking;
    }

    public ArrayList<String> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(ArrayList<String> notificationList) {
        this.notificationList = notificationList;
    }
}

