package com.example.eventmanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Button attend = findViewById(R.id.button7);
        Button organize = findViewById(R.id.button8);
        Button admin = findViewById(R.id.button9);

        attend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent attendIntent = new Intent(MainActivity.this, AttendeeActivity.class);
                startActivity(attendIntent);
            }
        });

        organize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent organizeIntent = new Intent(MainActivity.this, OrganizerActivity.class);
                startActivity(organizeIntent);
            }
        });

        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminIntent = new Intent(MainActivity.this, AdminActivity.class);
                startActivity(adminIntent);
            }
        });
    }
}