package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class EventArrayAdapter extends ArrayAdapter{
    private ArrayList<Event> Events;
    private Context context;

    public EventArrayAdapter(Context context, ArrayList<Event> events){
        super(context,0, events);
        this.Events = events;
        this.context = context;
    }


    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        // Inflate the view if it doesn't exist
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.fragment_event_page_list, parent, false);
        }

        // Get the event at the current position
        Event event = (Event) getItem(position);  // This replaces Event.get(position)

        // Find the TextViews by ID
        TextView eventNameTextView = convertView.findViewById(R.id.EventName);  // Make sure these IDs exist
        TextView eventDesTextView = convertView.findViewById(R.id.EventDes);    // Make sure these IDs exist

        // Set the text for each TextView
        if (eventNameTextView != null && eventDesTextView != null && event != null) {
            eventNameTextView.setText(event.getEvent_Title());
            eventDesTextView.setText(event.getEvent_Description());
        }

        // Return the inflated and updated view
        return convertView;
    }

}
