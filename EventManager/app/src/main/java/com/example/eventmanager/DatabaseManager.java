package com.example.eventmanager;
import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.WriteBatch;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

/** @noinspection unchecked*/
public class DatabaseManager {

    private FirebaseFirestore db;
    public DatabaseManager() {
        // Initialize Firestore
        db = FirebaseFirestore.getInstance();
    }



    public void addEvent(String eventTitle, String eventDescription, String checkInQRCode, String promoQRCode,
                         String eventDate, String organizer, int signupLimit, String posterUrl) {

        // Create a new document with the specified fields
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("EventTitle", eventTitle);
        eventData.put("EventDescription", eventDescription);
        eventData.put("checkInQRCode", checkInQRCode);  // "" if not created
        eventData.put("promoQRCode", promoQRCode);  // "" if not created
        eventData.put("EventDate", eventDate);
        eventData.put("SignedAttendees", new ArrayList<String>());
        eventData.put("CheckedInAttendees", new ArrayList<String>());
        eventData.put("geoLocation", new ArrayList<GeoPoint>());
        eventData.put("organizer", organizer);
        eventData.put("attendCount", new ArrayList<Integer>());
        eventData.put("signupLimit", signupLimit);
        eventData.put("signupCount", 0);
        eventData.put("checkInCount", 0);
        eventData.put("PosterUrl", posterUrl);

        // Add the document to the "event" collection
        db.collection("event").add(eventData)
                .addOnSuccessListener(documentReference -> {
                    // Document added successfully
                    String eventId = documentReference.getId();
                    Log.d("EventManager", "Document added successfully with ID: " + eventId);

                    // Find user document and update its organizeEvents array
                    DocumentReference userRef = db.collection("user").document(organizer);
                    userRef.update("organizeEvents", FieldValue.arrayUnion(eventId))
                            .addOnSuccessListener(aVoid -> Log.d("EventManager", "User document updated successfully with new event"))
                            .addOnFailureListener(e -> Log.e("EventManager", "Error updating user document", e));
                })
                .addOnFailureListener(e -> Log.e("EventManager", "Error adding event document", e));
    }







    public void updateEvent(String EventId, String eventTitle, String eventDescription,
                            String eventDate, String organizer, int signupLimit, String posterUrl) {

        // Create a map with the updated data
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("EventTitle", eventTitle);
        eventData.put("EventDescription", eventDescription);
        eventData.put("EventDate", eventDate);
        eventData.put("organizer", organizer); // organizer is the ID of the organizer not name (use device ID)
        eventData.put("signupLimit", signupLimit); // signupLimit = -1 when user does not select limit
        eventData.put("PosterUrl", posterUrl); // PosterUrl = "" when user deletes picture. Otherwise get Uri from StorageManager

        // Update the document in the "event" collection with the specified eventId
        db.collection("event")
                .document(EventId)
                .update(eventData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Event updated successfully
                        Log.d(TAG, "Document updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error updating document
                        Log.e(TAG, "Error updating document", e);
                    }
                });
    }
    // call this updateEvent when user does not change poster image
    public void updateEvent(String EventId, String eventTitle, String eventDescription,
                            String eventDate, String organizer, int signupLimit) {

        // Create a map with the updated data
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("EventTitle", eventTitle);
        eventData.put("EventDescription", eventDescription);
        eventData.put("EventDate", eventDate);
        eventData.put("organizer", organizer); // organizer is the ID of the organizer not name (use device ID)
        eventData.put("signupLimit", signupLimit); // signupLimit = -1 when user does not select limit

        // Update the document in the "event" collection with the specified eventId
        db.collection("event")
                .document(EventId)
                .update(eventData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Event updated successfully
                        Log.d(TAG, "Document updated successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error updating document
                        Log.e(TAG, "Error updating document", e);
                    }
                });
    }







    public void addUser(String userId, String name, String email, String phoneNumber,
                        String profilePicUrl, String homePageUrl, Boolean enableLocationTracking) {
        // Create a new document with the specified fields
        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", userId);  //use device id
        userData.put("userType", "user");
        userData.put("name", name);
        userData.put("email", email);
        userData.put("phoneNumber", phoneNumber);
        userData.put("profilePicUrl", profilePicUrl); // "" when user does not select picture. Otherwise get Uri from StorageManager
        userData.put("homePageUrl", homePageUrl);
        userData.put("enableLocationTracking", enableLocationTracking);
        userData.put("attendSignedUpEvents", new ArrayList<String>());
        userData.put("attendCurrentEvents", new ArrayList<String>());
        userData.put("organizeEvents", new ArrayList<String>());
        userData.put("notificationList", new ArrayList<String>());

        // Add the document to the "user" collection
        db.collection("user")
                .document(userId)
                .set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Document added successfully
                        Log.d(TAG, "Document added successfully");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error adding document
                        Log.e(TAG, "Error adding document", e);
                    }
                });
    }






    public void updateUser(String userId, String name, String email, String phoneNumber, String profilePicUrl, Boolean enableLocationTracking, String homePageUrl) {
        // Create a map with the updated data
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);
        userData.put("phoneNumber", phoneNumber);
        userData.put("profilePicUrl", profilePicUrl);
        userData.put("homePageUrl", homePageUrl);
        userData.put("enableLocationTracking", enableLocationTracking);

        // Directly update the document using userId as the document ID
        db.collection("user").document(userId).update(userData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document updated successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating document", e));
    }

    public void updateUser(String userId, String name, String email, String phoneNumber, String homePageUrl, Boolean enableLocationTracking) {
        // Create a map with the updated data
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("email", email);
        userData.put("phoneNumber", phoneNumber);
        userData.put("homePageUrl", homePageUrl);
        userData.put("enableLocationTracking", enableLocationTracking);

        // Directly update the document using userId as the document ID
        db.collection("user").document(userId).update(userData)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Document updated successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating document", e));
    }





    // helper function for AttendeeSignsEvent function
    private void getUserDocumentAndUpdateAttendEvents(String AttendeeId, String EventId) {
        // Query to find the user document where the attendeeId field matches the provided value
        Query query = db.collection("user").whereEqualTo("userId", AttendeeId);

        // Execute the query
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                if (!queryDocumentSnapshots.isEmpty()) {
                    // Retrieve the first user document found
                    DocumentSnapshot userDocumentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                    // Get reference to the user document
                    DocumentReference userRef = userDocumentSnapshot.getReference();

                    // Update the attendSignedUpEvents array field in the user document
                    userRef.update("attendSignedUpEvents", FieldValue.arrayUnion(EventId))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // User document updated successfully
                                    Log.d(TAG, "attendSignedUpEvents array updated successfully for user " + AttendeeId);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Error updating user document
                                    Log.e(TAG, "Error updating attendSignedUpEvents array for user " + AttendeeId, e);
                                }
                            });
                } else {
                    // No user document found
                    Log.d(TAG, "No user document found with attendeeId = " + AttendeeId);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle errors
                Log.e(TAG, "Error getting user document: ", e);
            }
        });
    }

    // Call this function after attendee signs into event
    public void AttendeeSignsEvent(String EventId, String AttendeeId) {
        // Get reference to the event document
        DocumentReference eventRef = db.collection("event").document(EventId);

        // Update the array field by adding the item and increment signupCount
        Map<String, Object> eventUpdates = new HashMap<>();
        eventUpdates.put("SignedAttendees", FieldValue.arrayUnion(AttendeeId));
        eventUpdates.put("signupCount", FieldValue.increment(1));

        // Update the event document with the array addition and signupCount increment
        eventRef.update(eventUpdates)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Event document updated successfully
                        Log.d(TAG, "Array field updated successfully for document " + EventId);

                        // Now, find the user document by querying the "user" collection
                        getUserDocumentAndUpdateAttendEvents(AttendeeId, EventId);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Error updating event document
                        Log.e(TAG, "Error updating array field for document " + EventId, e);
                    }
                });
    }





    public void checkInAttendeeToEvent(String AttendeeId, String EventId, GeoPoint geoPoint) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference eventRef = db.collection("event").document(EventId);

        eventRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                List<String> checkedInAttendees = documentSnapshot.get("CheckedInAttendees", List.class);
                List<String> signedAttendees = documentSnapshot.get("SignedAttendees", List.class);
                List<GeoPoint> geoLocations = documentSnapshot.get("geoLocation", List.class);
                List<Integer> attendCounts = documentSnapshot.get("attendCount", List.class);

                WriteBatch batch = db.batch();

                if (checkedInAttendees != null && !checkedInAttendees.contains(AttendeeId)) {
                    // Attendee not checked in yet, move from SignedAttendees to CheckedInAttendees
                    if (signedAttendees != null) {
                        batch.update(eventRef, "SignedAttendees", FieldValue.arrayRemove(AttendeeId));
                    }

                    batch.update(eventRef, "CheckedInAttendees", FieldValue.arrayUnion(AttendeeId));

                    // Update GeoPoint and attendCount
                    if (geoLocations != null) {
                        geoLocations.add(geoPoint);
                    }
                    if (attendCounts != null) {
                        attendCounts.add(1);
                    }
                    batch.update(eventRef, "geoLocation", geoLocations);
                    batch.update(eventRef, "attendCount", attendCounts);

                    // Update the attendCurrentEvents array in the user document
                    updateUserAttendCurrentEvents(AttendeeId, EventId);
                } else if (checkedInAttendees != null) {
                    // Attendee already checked in, update information
                    int index = checkedInAttendees.indexOf(AttendeeId);

                    if (geoLocations != null && index < geoLocations.size()) {
                        geoLocations.set(index, geoPoint);
                    }
                    if (attendCounts != null && index < attendCounts.size()) {
                        int currentCount = attendCounts.get(index);
                        attendCounts.set(index, currentCount + 1);
                    }
                    batch.update(eventRef, "geoLocation", geoLocations);
                    batch.update(eventRef, "attendCount", attendCounts);
                }

                batch.commit()
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "Attendee check-in process successful"))
                        .addOnFailureListener(e -> Log.e(TAG, "Error in attendee check-in process", e));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle errors
                Log.e(TAG, "Error fetching event document", e);
            }
        });
    }

    private void updateUserAttendCurrentEvents(final String AttendeeId, final String EventId) {
        // Query to find the user document where the userId field matches the provided AttendeeId
        Query query = db.collection("user").whereEqualTo("userId", AttendeeId);

        // Execute the query
        query.get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                // Check if the query returned any documents
                if (!queryDocumentSnapshots.isEmpty()) {
                    // Assuming userId is unique, get the first document
                    DocumentSnapshot userDocument = queryDocumentSnapshots.getDocuments().get(0);
                    DocumentReference userRef = userDocument.getReference();

                    // Update the attendCurrentEvents array in the found user document
                    userRef.update("attendCurrentEvents", FieldValue.arrayUnion(EventId))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    // Log success
                                    Log.d(TAG, "attendCurrentEvents array updated successfully for user " + AttendeeId);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    // Log failure
                                    Log.e(TAG, "Error updating attendCurrentEvents array for user " + AttendeeId, e);
                                }
                            });
                } else {
                    // Handle the case where no user document is found
                    Log.d(TAG, "No user document found with userId = " + AttendeeId);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Handle errors in querying the user collection
                Log.e(TAG, "Error querying user collection for userId = " + AttendeeId, e);
            }
        });
    }









    // returns array of notifications
    public ArrayList<String> getUserNotifications(String userId) {
        Task<QuerySnapshot> query = db.collection("user").whereEqualTo("userId", userId).get();
        ArrayList<String> notifications = new ArrayList<>();

        try {
            // Wait for the query to complete
            QuerySnapshot querySnapshot = Tasks.await(query);
            for (QueryDocumentSnapshot document : querySnapshot) {
                // Assuming 'notification' field is an array of strings
                List<String> notificationList = (List<String>) document.get("notificationList");
                if (notificationList != null) {
                    notifications.addAll(notificationList);
                }
            }
        } catch (ExecutionException | InterruptedException e) {
            // Handle exceptions
            Log.e(TAG, "notification query failed");
        }
        return notifications;
    }

    public void addNotificationToSignedAttendees(String eventId, final String notification) {
        // Get the event document
        DocumentReference eventRef = db.collection("event").document(eventId);

        eventRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    List<String> attendees = (List<String>) documentSnapshot.get("SignedAttendees");
                    if (attendees != null) {
                        for (String userId : attendees) {
                            // For each attendee, get the user document and update the notificationList
                            DocumentReference userRef = db.collection("user").document(userId);
                            userRef.update("notificationList", FieldValue.arrayUnion(notification))
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Notification added to user " + userId))
                                    .addOnFailureListener(e -> Log.e(TAG, "Error adding notification to user " + userId, e));
                        }
                    } else {
                        Log.d(TAG, "No attendees found for event " + eventId);
                    }
                } else {
                    Log.d(TAG, "Event document does not exist: " + eventId);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error fetching event document: " + eventId, e);
            }
        });
    }

    public void addNotificationToCheckedInAttendees(String eventId, final String notification) {
        // Get the event document
        DocumentReference eventRef = db.collection("event").document(eventId);

        eventRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()) {
                    List<String> checkedInAttendees = (List<String>) documentSnapshot.get("CheckedInAttendees");
                    if (checkedInAttendees != null) {
                        for (String userId : checkedInAttendees) {
                            // For each checked-in attendee, get the user document and update the notificationList
                            DocumentReference userRef = db.collection("user").document(userId);
                            userRef.update("notificationList", FieldValue.arrayUnion(notification))
                                    .addOnSuccessListener(aVoid -> Log.d(TAG, "Notification added to checked-in user " + userId))
                                    .addOnFailureListener(e -> Log.e(TAG, "Error adding notification to checked-in user " + userId, e));
                        }
                    } else {
                        Log.d(TAG, "No checked-in attendees found for event " + eventId);
                    }
                } else {
                    Log.d(TAG, "Event document does not exist: " + eventId);
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e(TAG, "Error fetching event document: " + eventId, e);
            }
        });
    }








    public void updateQRCode(String eventId, String qrCodeType, String qrCodeValue) {
        DocumentReference eventRef = db.collection("events").document(eventId);
        eventRef.update(qrCodeType, qrCodeValue)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "QR Code updated successfully"))
                .addOnFailureListener(e -> Log.e(TAG, "Error updating QR Code", e));
    }

    public interface QRCodeCallback {
        void onQRCodeReceived(String qrCode);
        void onError(Exception e);
    }

    public void getQRCode(String eventId, String qrCodeType, QRCodeCallback callback) {
        DocumentReference eventRef = db.collection("events").document(eventId);
        eventRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String qrCode = documentSnapshot.getString(qrCodeType);
                callback.onQRCodeReceived(qrCode);
            } else {
                Log.d(TAG, "No such document");
                callback.onQRCodeReceived(null); // or pass a default value
            }
        }).addOnFailureListener(e -> {
            Log.e(TAG, "Error fetching document", e);
            callback.onError(e);
        });
    }

    //  sample usage of getting data from database:
    //getCheckInQRCodes("eventId", new CheckInQRCodeCallback() {
    //    @Override
    //    public void onQRCodeReceived(String qrCode) {
    //        if (qrCode != null) {
    //            // Use the QR code here
    //        } else {
    //            // Handle document not existing
    //        }
    //    }
    //
    //    @Override
    //    public void onError(Exception e) {
    //        // Handle error
    //    }
    //});








    public interface EventPosterCallback {
        void onPosterReceived(String posterUrl);
        void onError(Exception e);
    }

    // Function to set the poster attribute
    public void setEventPoster(String eventId, String posterUrl, final EventPosterCallback callback) {
        DocumentReference eventRef = db.collection("event").document(eventId);
        eventRef.update("PosterUrl", posterUrl)
                .addOnSuccessListener(aVoid -> callback.onPosterReceived(posterUrl))
                .addOnFailureListener(callback::onError);
    }

    // Function to get the poster attribute
    public void getEventPoster(String eventId, final EventPosterCallback callback) {
        DocumentReference eventRef = db.collection("event").document(eventId);
        eventRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String posterUrl = documentSnapshot.getString("PosterUrl");
                callback.onPosterReceived(posterUrl);
            } else {
                Log.d("Database", "No such document");
                callback.onPosterReceived(null); // or invoke onError
            }
        }).addOnFailureListener(e -> {
            Log.e("Database", "Error fetching document", e);
            callback.onError(e);
        });
    }




    public interface UserProfileImageCallback {
        void onProfilePicReceived(String posterUrl);
        void onError(Exception e);
    }

    // Function to set the poster attribute
    public void setUserProfilePic(String userId, String profilePicUrl, final UserProfileImageCallback callback) {
        DocumentReference eventRef = db.collection("user").document(userId);
        eventRef.update("profilePicUrl", profilePicUrl)
                .addOnSuccessListener(aVoid -> callback.onProfilePicReceived(profilePicUrl))
                .addOnFailureListener(callback::onError);
    }

    // Function to get the poster attribute
    public void getUserProfilePic(String userId, final EventPosterCallback callback) {
        DocumentReference eventRef = db.collection("user").document(userId);
        eventRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String profilePicUrl = documentSnapshot.getString("profilePicUrl");
                callback.onPosterReceived(profilePicUrl);
            } else {
                Log.d("Database", "No such document");
                callback.onPosterReceived(null); // or invoke onError
            }
        }).addOnFailureListener(e -> {
            Log.e("Database", "Error fetching document", e);
            callback.onError(e);
        });
    }

    //  sample usage of getting data from database:
    //getCheckInQRCodes("eventId", new CheckInQRCodeCallback() {
    //    @Override
    //    public void onQRCodeReceived(String qrCode) {
    //        if (qrCode != null) {
    //            // Use the QR code here
    //        } else {
    //            // Handle document not existing
    //        }
    //    }
    //
    //    @Override
    //    public void onError(Exception e) {
    //        // Handle error
    //    }
    //});




    // delete event from the event collection
    public void deleteEvent(String eventId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference eventRef = db.collection("event").document(eventId);

        eventRef.delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Event successfully deleted"))
                .addOnFailureListener(e -> Log.e(TAG, "Error deleting event", e));
    }
    // remove event from all users
    public void deleteEventFromUsers(String EventId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        // Reference to the event document
        DocumentReference eventRef = db.collection("event").document(EventId);

        // Fetch the event document
        eventRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                List<String> signedAttendees = (List<String>) documentSnapshot.get("SignedAttendees");
                List<String> checkedInAttendees = (List<String>) documentSnapshot.get("CheckedInAttendees");
                String organizer = documentSnapshot.getString("organizer");

                // Combine the lists of attendees, ensuring no duplicates
                Set<String> allAttendees = new HashSet<>();
                if (signedAttendees != null) {
                    allAttendees.addAll(signedAttendees);
                }
                if (checkedInAttendees != null) {
                    allAttendees.addAll(checkedInAttendees);
                }
                if (organizer != null) {
                    allAttendees.add(organizer);
                }

                // For each attendee, update their user document
                for (String userId : allAttendees) {
                    DocumentReference userDocRef = db.collection("user").document(userId);
                    updateUserData(userDocRef, EventId);
                }
            } else {
                Log.d(TAG, "Event document does not exist: " + EventId);
            }
        }).addOnFailureListener(e -> Log.e(TAG, "Error fetching event document", e));
    }

    private void updateUserData(DocumentReference userDocRef, String EventId) {
        userDocRef.update("attendSignedUpEvents", FieldValue.arrayRemove(EventId))
                .addOnSuccessListener(aVoid -> Log.d(TAG, "EventId removed from attendSignedUpEvents for user " + userDocRef.getId()))
                .addOnFailureListener(e -> Log.e(TAG, "Error removing EventId from attendSignedUpEvents for user " + userDocRef.getId(), e));
        userDocRef.update("attendCurrentEvents", FieldValue.arrayRemove(EventId))
                .addOnSuccessListener(aVoid -> Log.d(TAG, "EventId removed from attendCurrentEvents for user " + userDocRef.getId()))
                .addOnFailureListener(e -> Log.e(TAG, "Error removing EventId from attendCurrentEvents for user " + userDocRef.getId(), e));
        userDocRef.update("organizeEvents", FieldValue.arrayRemove(EventId))
                .addOnSuccessListener(aVoid -> Log.d(TAG, "EventId removed from organizeEvents for user " + userDocRef.getId()))
                .addOnFailureListener(e -> Log.e(TAG, "Error removing EventId from organizeEvents for user " + userDocRef.getId(), e));
    }





    //This section removes the
    public void deleteUser(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("user").document(userId);

        userRef.delete()
                .addOnSuccessListener(aVoid -> Log.d(TAG, "User successfully deleted"))
                .addOnFailureListener(e -> Log.e(TAG, "Error deleting user", e));
    }

    public void removeUserFromEvent(String eventId, String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference eventRef = db.collection("event").document(eventId);

        eventRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                List<String> signedAttendees = (List<String>) documentSnapshot.get("SignedAttendees");
                List<String> checkedInAttendees = (List<String>) documentSnapshot.get("CheckedInAttendees");
                List<GeoPoint> geoLocations = (List<GeoPoint>) documentSnapshot.get("geoLocation");
                List<Integer> attendCount = (List<Integer>) documentSnapshot.get("attendCount");

                WriteBatch batch = db.batch();

                // Remove userId from SignedAttendees and decrement signupCount if necessary
                if (signedAttendees != null && signedAttendees.contains(userId)) {
                    batch.update(eventRef, "SignedAttendees", FieldValue.arrayRemove(userId));
                    batch.update(eventRef, "signupCount", FieldValue.increment(-1));
                }

                // Remove userId from CheckedInAttendees, related attendCount, geoLocation, and decrement checkInCount if necessary
                if (checkedInAttendees != null && checkedInAttendees.contains(userId)) {
                    int index = checkedInAttendees.indexOf(userId);
                    if (index != -1) {
                        if (geoLocations != null && index < geoLocations.size()) {
                            geoLocations.remove(index);
                        }
                        if (attendCount != null && index < attendCount.size()) {
                            attendCount.remove(index);
                        }
                        batch.update(eventRef, "CheckedInAttendees", FieldValue.arrayRemove(userId));
                        batch.update(eventRef, "geoLocation", geoLocations);
                        batch.update(eventRef, "attendCount", attendCount);
                        batch.update(eventRef, "checkInCount", FieldValue.increment(-1));
                    }
                }

                // Commit the batch
                batch.commit()
                        .addOnSuccessListener(aVoid -> Log.d(TAG, "User removed from event successfully"))
                        .addOnFailureListener(e -> Log.e(TAG, "Error removing user from event", e));
            } else {
                Log.d(TAG, "No such event document exists");
            }
        }).addOnFailureListener(e -> Log.e(TAG, "Error fetching event document", e));
    }

    public void processAndRemoveUserFromAllEvents(String userId) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference userRef = db.collection("user").document(userId);

        userRef.get().addOnSuccessListener(userDocumentSnapshot -> {
            if (userDocumentSnapshot.exists()) {
                List<String> signedUpEvents = (List<String>) userDocumentSnapshot.get("attendSignedUpEvents");
                List<String> currentEvents = (List<String>) userDocumentSnapshot.get("attendCurrentEvents");
                List<String> organizeEvents = (List<String>) userDocumentSnapshot.get("organizeEvents");

                // Remove user from signed up and current events
                if (signedUpEvents != null) {
                    for (String eventId : signedUpEvents) {
                        removeUserFromEvent(eventId, userId);
                    }
                }
                if (currentEvents != null) {
                    for (String eventId : currentEvents) {
                        removeUserFromEvent(eventId, userId);
                    }
                }

                // Delete events the user has organized
                if (organizeEvents != null) {
                    for (String eventId : organizeEvents) {
                        deleteEvent(eventId);
                        deleteEventFromUsers(eventId);
                    }
                }
            } else {
                Log.d(TAG, "User document does not exist: " + userId);
            }
        }).addOnFailureListener(e -> Log.e(TAG, "Error fetching user document", e));
    }
}








