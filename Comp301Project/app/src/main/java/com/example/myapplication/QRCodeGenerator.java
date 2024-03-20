/* QRCodeGenerator
 * 
 * Author: Bashir Bello
 *
 * Project: EventManager
 *
 * Course: CMPUT 301
 *
 * QR Code Generation: Upon the creation of a new event by an organizer, 
 * a unique QR code is generated, encapsulating essential event details. 
 *
 * QR Code Reuse
 *
 * QR Code Sharing: To aid in the marketing and communication of
 * event details.
 * 
 * Add to build.gradle: 
 * implementation 'com.google.zxing:core:3.4.1'
 *  implementation 'com.journeyapps:zxing-android-embedded:4.2.0'
 * *  Responsibilities:
        Generate QR codes for new events.
        Possibly regenerate or decode QR codes if necessary.
        Collaborators:
        Event
        QRCodeManager
 */

package com.example.myapplication;

 // Function to generate a QR Code Bitmap
 import android.content.Context;
 import com.example.myapplication.Event
 import android.content.Intent;
 import android.graphics.Bitmap;
import android.graphics.Color;
 import android.net.Uri;
 import android.provider.MediaStore;
 import android.util.Log;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {
    public Bitmap generateQRCode(String content, int width, int height) {
        QRCodeWriter writer = new QRCodeWriter();
        try {
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            com.google.zxing.common.BitMatrix bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, width, height);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            Log.e("QRCodeGenerator", "Error generating QR code: " + e.getMessage());
            return null;
        }
    }
}


// TODO: retrieve and reuse QR code - viewmodel?

// share QR code
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

public class QRCodeSharing {
    public void shareQRCode(Context context, Bitmap qrCodeBitmap, String title) {
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), qrCodeBitmap, title, null);
        Uri screenshotUri = Uri.parse(path);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("image/png");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        context.startActivity(Intent.createChooser(sharingIntent, "Share QR Code Using"));
    }
}

