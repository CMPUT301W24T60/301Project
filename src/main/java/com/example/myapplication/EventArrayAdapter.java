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

public class EventArrayAdapter extends ArrayAdapter<Event>{
    private ArrayList<Event> Events;
    private Context context;

    public EventArrayAdapter(Context context, ArrayList<Event> events){
        super(context,0, events);
        this.Events = events;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view = convertView;

        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.fragment_event_page, parent,false);
        }
        else {
            view = convertView;
        }

        Event event = getItem(position);

        TextView EventName = view.findViewById(R.id.EventName);
        TextView EventDes = view.findViewById(R.id.EventDes);

        EventName.setText(event.getEvent_Title());
        EventDes.setText(event.getEvent_Description());

        return view;
    }
}
