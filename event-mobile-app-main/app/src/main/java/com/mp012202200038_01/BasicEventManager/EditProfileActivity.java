/**
 * Activity for editing the user's profile, including name, email, and profile picture.
 * Allows users to update their profile information and choose a new profile image from their device.
 * If a user does not select an image, a default avatar is generated based on their name.
 */
package com.mp012202200038_01.BasicEventManager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

/**
 * The type Edit profile activity.
 */
public class EditProfileActivity extends AppCompatActivity {

    /**
     * The Edit name.
     */
    EditText editName, /**
     * The Edit email.
     */
    editEmail;
    /**
     * The Profile image.
     */
    ImageView profileImage;
    /**
     * The Save button.
     */
    Button saveButton, /**
     * The Back button.
     */
    backButton, /**
     * The Remove profile image button.
     */
    removeProfileImageButton;
    /**
     * The Reference.
     */
    DatabaseReference reference;
    /**
     * The User id.
     */
    String userId; // Unique ID for the user
    private static final int PICK_IMAGE_REQUEST = 1; // Image pick request code

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        // Initialize Firebase reference
        reference = FirebaseDatabase.getInstance().getReference("users");
        userId = getIntent().getStringExtra("userId");

        // Initialize UI components
        profileImage = findViewById(R.id.profileImage);
        editName = findViewById(R.id.editUsername);
        editEmail = findViewById(R.id.editEmail);
        saveButton = findViewById(R.id.saveButton);
        backButton = findViewById(R.id.backtpButton);
        removeProfileImageButton = findViewById(R.id.removeProfileImageButton);

        // prefill from login
        showData();

        // Load current profile image
        loadProfileImage();

        // Back button returns to the profile view
        backButton.setOnClickListener(v -> finish());

        // Save button updates user information in Firebase
        saveButton.setOnClickListener(v -> saveChanges());

        // remove profile image
        removeProfileImageButton.setOnClickListener(v -> removeProfilePicture());

        // open device files for profile image
        profileImage.setOnClickListener(v -> openImageSelector());
    }

    private void openImageSelector() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            Picasso.get().load(imageUri).into(profileImage);
        }
    }

    private void loadDefaultImage() {
        String name = editName.getText().toString();
        String avatarUrl = "https://ui-avatars.com/api/?name=" + Uri.encode(name);
        Picasso.get().load(avatarUrl).into(profileImage);
    }

    // Call this method when you want to remove the profile picture
    private void removeProfilePicture() {
        loadDefaultImage();
    }
    private void loadProfileImage() {
        // Placeholder URL, replace with actual URL if available
        String imageUrl = getIntent().getStringExtra("profileImageUrl");
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get().load(imageUrl).into(profileImage);
        }
    }

    /**
     * On profile image click.
     *
     * @param view the view
     */
    public void onProfileImageClick(View view) {
        openImageSelector();
    }

    private void saveChanges() {
        String newName = editName.getText().toString().trim();
        String newEmail = editEmail.getText().toString().trim();

        // Validate inputs
        if (newName.isEmpty() || newEmail.isEmpty()) {
            Toast.makeText(EditProfileActivity.this, "Name and email cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        // Update Firebase database
        reference.child(userId).child("name").setValue(newName);
        reference.child(userId).child("email").setValue(newEmail)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(EditProfileActivity.this, "Profile updated", Toast.LENGTH_SHORT).show();
                        finish(); // Go back to the previous Activity
                    } else {
                        Toast.makeText(EditProfileActivity.this, "Failed to update profile", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Method to pre-fill the name and email fields
    private void showData() {
        Intent intent = getIntent();
        editName.setText(intent.getStringExtra("name"));
        editEmail.setText(intent.getStringExtra("email"));
        // String profileImageUrl = fetchProfileImageUrl();
        // Picasso.get().load(profileImageUrl).into(profileImage);
    }
}
