/* QRCodeGenerator
 * 
 * Author: Bashir Bello
 *
 * Project: EventManager
 *
 * Course: CMPUT 301
 *
 * Description: This portion of the EventManager app is designed to enhance the event management experience by allowing organizers to generate, reuse, and share QR codes for events. These functionalities are critical for efficient attendee management and for promoting events through various channels. The implementation covers three key features:
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
 * 
 */
package com.example.myapplication;


 // Function to generate a QR Code Bitmap
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

public class QR_Code_Generator {
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
