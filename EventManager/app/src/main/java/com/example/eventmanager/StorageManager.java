package com.example.eventmanager;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class StorageManager {

    private FirebaseStorage storage;
    public static final int PICK_IMAGE_REQUEST = 1;

    public StorageManager() {
        storage = FirebaseStorage.getInstance();
    }

    public interface UploadImageCallback {
        void onUploadSuccess(Uri firebaseUri);
        void onError(Exception e);
    }

    public void uploadImage(Uri imageUri, final UploadImageCallback callback) {
        if (imageUri == null) {
            callback.onError(new Exception("Image URI is null"));
            return;
        }

        StorageReference storageRef = storage.getReference();
        StorageReference imageRef = storageRef.child("images/" + imageUri.getLastPathSegment());

        UploadTask uploadTask = imageRef.putFile(imageUri);

        uploadTask.addOnSuccessListener(taskSnapshot -> {
            imageRef.getDownloadUrl().addOnSuccessListener(callback::onUploadSuccess).addOnFailureListener(callback::onError);
        }).addOnFailureListener(callback::onError);
    }

    //override in the activity
    //storageManager.uploadImage(imageUri, new StorageManager.UploadImageCallback() {
    //    @Override
    //    public void onUploadSuccess(Uri firebaseUri) {
    //        // The image is uploaded successfully, and firebaseUri is the URI of the image in storage
    //        // Perform any action you need with the URI (e.g., display the image)
    //    }
    //
    //    @Override
    //    public void onError(Exception e) {
    //        // Handle any errors during upload or URI retrieval
    //    }
    //});





    public void loadImageIntoView(Uri imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl.toString()).into(imageView);
    }

    // Use this method to start the image picker intent
    public void openImageChooser(Activity activity) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    // This method gets called when the user selects an image override in activity to handle image selection.
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
//            Uri imageUri = data.getData();
//            StorageManager storageManager = new StorageManager();
//            storageManager.uploadImage(imageUri, new StorageManager.UploadImageCallback() {
//                @Override
//                public void onUploadSuccess(Uri firebaseUri) {
//                    // The image is uploaded successfully and you have the Firebase URI
//                    // You can now call loadImageIntoView or save the URI for later use
//                }
//
//                @Override
//                public void onError(Exception e) {
//                    // Handle the error
//                }
//            });
//        }
//    }

    public void displayImage(Uri firebaseUri, ImageView imageView) {
        StorageManager storageManager = new StorageManager();
        storageManager.loadImageIntoView(firebaseUri, imageView);
    }
}