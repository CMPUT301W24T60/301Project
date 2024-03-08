package com.example.eventmanager;

import java.util.ArrayList;

public class Organizer extends User {
    private ArrayList<Event> Events;

    public ArrayList<Event> getEvents() {
        return Events;
    }

    public void setEvents(ArrayList<Event> events) {
        Events = events;
    }
}

