/*
 * LoginActivity
 * This class represents the login activity for authenticating users.
 */

package com.mp012202200038_01.BasicEventManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

/**
 * The type Login activity.
 */
public class LoginActivity extends AppCompatActivity {
    /**
     * The Edit text name.
     */
    EditText editTextName, /**
     * The Edit text email.
     */
    editTextEmail, /**
     * The Edit text username.
     */
    editTextUsername;
    /**
     * The Button enter.
     */
    Button buttonEnter;
    /**
     * The constant MyPREFERENCES.
     */
    public static final String MyPREFERENCES = "MyPrefs" ;
    /**
     * The constant Email.
     */
    public static final String Email = "emailKey";
    /**
     * The constant Username.
     */
    public static final String Username = "usernameKey";

    /**
     * The Sharedpreferences.
     */
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.inputName);
        editTextUsername = findViewById(R.id.inputEmail);
        buttonEnter = findViewById(R.id.submitButton);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        buttonEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ph = editTextEmail.getText().toString();
                String e = editTextUsername.getText().toString();

                SharedPreferences.Editor editor = sharedpreferences.edit();

                editor.putString(Email, ph);
                editor.putString(Username, e);
                editor.apply();

                // Check if the user is admin and redirect accordingly
                if (isAdmin(ph, e)) {
                    // Redirect to AdminActivity
                    Intent adminIntent = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(adminIntent);
                } else {
                    // Redirect to MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });
    }

    private boolean isAdmin(String username, String email) {
        // Replace "adminUsername" and "adminEmail" with actual admin credentials
        return "adminUsername".equals(username) && "adminEmail".equals(email);
    }
}