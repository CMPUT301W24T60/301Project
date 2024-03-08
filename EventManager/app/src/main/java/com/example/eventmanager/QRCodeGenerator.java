package com.example.eventmanager;

// Function to generate a QR Code Bitmap

import android.graphics.Bitmap;
import android.graphics.Color;
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