package com.example.eventmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        Spinner spinner = findViewById(R.id.spinner_user_type);

        // Set the OnItemSelectedListener
        spinner.setOnItemSelectedListener(this);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_types, android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
    }

    // Implement the onItemSelected method
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // Handle the item selection here
        String selectedItem = (String) parent.getItemAtPosition(position);

    }

    // Implement the onNothingSelected method
    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Handle case when nothing is selected
    }
}