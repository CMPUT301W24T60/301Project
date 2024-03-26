package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class SingleEventAdapter implements Adapter {
private int Ausercode;
private Context Context;
private Event OneEvent;

public SingleEventAdapter (Context context, Event event, int usercode){
            super();
            Ausercode=usercode;
            Context=context;
            OneEvent=event;
        }
        public Context getContext(){return this.Context;}
        public int getAusercode(){return this.Ausercode;}


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//        return super.getView(position, convertView, parent);
            View view;

            if(convertView== null){
                view = LayoutInflater.from(this.getContext()).inflate(R.layout.content_long_clicked_event_page, parent,false);
            }
            else {
                view = convertView;
            }

            TextView EventName = view.findViewById(R.id.OneEventName);
            TextView EventDes = view.findViewById(R.id.OneEventDes);
            TextView Eventdate=view.findViewById(R.id.OneEventDate);
            TextView EventLocal=view.findViewById(R.id.OneEventLocation);
          //  ImageView EventQRcode=view.findViewById(R.id.QRcodeforOneEvent);

            assert OneEvent != null;
            if (this.getAusercode()==1) {
                EventName.setText(OneEvent.getEvent_Title());
                EventDes.setText(OneEvent.getEvent_Description());
                Eventdate.setText(OneEvent.getEvent_Date().toString());
                EventLocal.setText(OneEvent.getEvent_Location().toString());
                // applie QR code resourse  EventQRcode.setImageResource(R.drawable);
            }
            if (this.getAusercode()==2 || this.getAusercode()==3) {
                EventName.setText(OneEvent.getEvent_Title());
                EventDes.setText(OneEvent.getEvent_Description());
                Eventdate.setText(OneEvent.getEvent_Date().toString());
                EventLocal.setText(OneEvent.getEvent_Location().toString());
                // add in attendees list
                // add in notic=fication options
                // applie QR code resourse  EventQRcode.setImageResource(R.drawable);
            }
            return view;
        }
    }

