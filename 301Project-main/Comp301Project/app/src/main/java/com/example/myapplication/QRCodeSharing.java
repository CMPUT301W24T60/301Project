package com.example.myapplication;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
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
