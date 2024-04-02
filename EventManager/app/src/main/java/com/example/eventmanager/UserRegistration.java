package com.example.eventmanager;

import static android.service.controls.ControlsProviderService.TAG;

import android.annotation.SuppressLint;
import android.util.Log;
import android.widget.EditText;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class UserRegistration extends AppCompatActivity {

    private ImageView emptyProfilePic;
    private EditText editName;
    private EditText editNumber;
    private EditText editEmail;
    private EditText editHomepageWebsite;
    private Button editProfileConfirmButton;
    private SwitchCompat switchCompat;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        DatabaseManager database = new DatabaseManager();
        // Get device ID
        @SuppressLint("HardwareIds") String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.get_user_info);

        // Initialize views
        emptyProfilePic = findViewById(R.id.empty_profile_pic);
        editName = findViewById(R.id.edit_name);
        editNumber = findViewById(R.id.edit_number);
        editEmail = findViewById(R.id.edit_email);
        editHomepageWebsite = findViewById(R.id.edit_homepage_website);
        editProfileConfirmButton = findViewById(R.id.edit_profile_confirm_button);
        switchCompat = findViewById(R.id.edit_profile_switchcompat);


        // Set OnClickListener for the ImageView to select an image
        emptyProfilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageChooser();
            }
        });

        // Set OnClickListener for the "Generate" button
        editProfileConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get values from EditText fields
                String name = editName.getText().toString().trim();
                String number = editNumber.getText().toString().trim();
                String email = editEmail.getText().toString().trim();
                String homepage = editHomepageWebsite.getText().toString().trim();
                boolean switchValue = switchCompat.isChecked();

                // Check if any of the fields are empty
                if (name.isEmpty() || number.isEmpty() || email.isEmpty() || homepage.isEmpty()) {
                    // If any field is empty, show an error message
                    Toast.makeText(getApplicationContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Perform your desired action with the retrieved values
                // Here, you can proceed to upload the image to Firebase Storage along with other details
                if (imageUri == null) {
                    ProfilePicGenerator picGen = new ProfilePicGenerator();
                    // Generate bitmap URI using the utility class
                    imageUri = picGen.generateBitmapUriFromHash(getApplicationContext(), androidId, name);
                }

                //upload user document
                database.addUser(androidId, name, email, number, "", homepage, switchValue);

                StorageManager storage = new StorageManager();
                storage.uploadImage(imageUri, new StorageManager.UploadImageCallback() {
                    @Override
                    public void onUploadSuccess(Uri firebaseUri) {
                        // The image is uploaded successfully, and firebaseUri is the URI of the image in storage
                        // Perform any action you need with the URI (e.g., display the image)
                        // Call the setUserProfilePic method
                        database.setUserProfilePic(androidId, firebaseUri.toString(), new DatabaseManager.UserProfileImageCallback() {

                            @Override
                            public void onProfilePicReceived(String profileUrl) {
                                // Profile picture URL updated successfully
                                Log.d(TAG, "Profile picture URL updated: " + profileUrl);
                                // Do something with the updated profile picture URL
                            }

                            @Override
                            public void onError(Exception e) {
                                // Error occurred during updating profile picture URL
                                Log.e(TAG, "Error updating profile picture URL", e);
                                // Handle the error appropriately
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        // Handle any errors during upload or URI retrieval
                    }
                });

                finish();

            }
        });
    }

    // Method to open image chooser
    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // Handle result from image chooser
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            emptyProfilePic.setImageURI(imageUri);
        }
    }
}