package com.example.myapplication;
import android.graphics.Bitmap;

/*
Simple data holder for Bitmap that represents QR code
 */
public class QRCode {

    private Bitmap qrCodeImage;

    public QRCode(Bitmap qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }

    public Bitmap getQrCodeImage() {
        return qrCodeImage;
    }

    public void setQrCodeImage(Bitmap qrCodeImage) {
        this.qrCodeImage = qrCodeImage;
    }
}