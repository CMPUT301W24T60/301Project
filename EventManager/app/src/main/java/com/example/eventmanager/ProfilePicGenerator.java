package com.example.eventmanager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ProfilePicGenerator {

    public static Uri generateBitmapUriFromHash(Context context, String userId, String profileName) {
        // Generate SHA-256 hash
        String hash = generateSHA256(userId + ":" + profileName);

        // Convert hash to binary
        String binaryHash = hexToBinary(hash);

        // Create 16x16 1-bit bitmap
        Bitmap bitmap = createBitmap(binaryHash);

        // Save bitmap to a file
        File file = saveBitmapToFile(context, bitmap);

        // Return the URI of the saved bitmap
        return Uri.fromFile(file);
    }

    private static String generateSHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String hexToBinary(String hex) {
        StringBuilder binary = new StringBuilder();
        for (int i = 0; i < hex.length(); i += 2) {
            String hexPair = hex.substring(i, i + 2);
            int decimal = Integer.parseInt(hexPair, 16);
            String binaryString = Integer.toBinaryString(decimal);
            binary.append(String.format("%8s", binaryString).replace(' ', '0'));
        }
        return binary.toString();
    }

    private static Bitmap createBitmap(String binaryHash) {
        Bitmap bitmap = Bitmap.createBitmap(16, 16, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        for (int i = 0; i < binaryHash.length(); i++) {
            int x = i % 16;
            int y = i / 16;
            int color = binaryHash.charAt(i) == '0' ? Color.WHITE : Color.BLACK;
            bitmap.setPixel(x, y, color);
        }
        return bitmap;
    }

    private static File saveBitmapToFile(Context context, Bitmap bitmap) {
        File file = new File(context.getFilesDir(), "profile_image.png");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    //example usage:
//    public class MainActivity extends AppCompatActivity {
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            setContentView(R.layout.activity_main);
//
//            // Sample userId and profileName
//            String userId = "exampleUserId";
//            String profileName = "exampleProfileName";
//
//            // Generate bitmap URI using the utility class
//            Uri bitmapUri = BitmapGenerator.generateBitmapUriFromHash(getApplicationContext(), userId, profileName);
//
//            // Upload the bitmap to storage
//            uploadBitmapToStorage(bitmapUri);
//        }
//
//        private void uploadBitmapToStorage(Uri bitmapUri) {
//            // Implement your code to upload the bitmap to storage here
//            // For example:
//            // StorageReference storageRef = FirebaseStorage.getInstance().getReference();
//            // storageRef.child("images/profile_image.png").putFile(bitmapUri);
//            Toast.makeText(this, "Bitmap uploaded to storage", Toast.LENGTH_SHORT).show();
//        }
//    }
}