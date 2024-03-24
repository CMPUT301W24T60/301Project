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

    public EventArrayAdapter(Context context, ArrayList<Event> events){
        super(context,0, events);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        View view;

        if(convertView== null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.content_eventsdata, parent,false);
        }
        else {
            view = convertView;
        }
            Event event=getItem(position);

            TextView EventName = view.findViewById(R.id.EventName);
            TextView EventDes = view.findViewById(R.id.EventDes);
            TextView Eventdate=view.findViewById(R.id.EventDate);
            TextView EventLocal=view.findViewById(R.id.EventLocation);

        assert event != null;
        EventName.setText(event.getEvent_Title());
        EventDes.setText(event.getEvent_Description());
        Eventdate.setText(event.getEvent_Date().toString());
        EventLocal.setText(event.getEvent_Location().toString());
        return view;
    }
}
