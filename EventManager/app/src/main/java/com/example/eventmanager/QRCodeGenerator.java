/* QRCodeGenerator
 * Generates a Bitmap of a QR code from a given string.
 *
 * Usage:
 * Bitmap qrCodeBitmap = new QR_Code_Generator().generateQRCode(content, width, height);
 * imageView.setImageBitmap(qrCodeBitmap);
 *
 * Parameters:
 * content - The string to encode in the QR code.
 * width - The desired width of the QR code image.
 * height - The desired height of the QR code image.
 */

package com.example.eventmanager;

import android.graphics.Bitmap;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.util.Hashtable;
public class QRCodeGenerator {
    private static final int WHITE = 0xFFFFFFFF;
    private static final int BLACK = 0xFF000000;

    public Bitmap generateQRCode(String content, int width, int height) {
        Hashtable<EncodeHintType, Object> hintMap = new Hashtable<>();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
        hintMap.put(EncodeHintType.MARGIN, 1); /* default = 4 */
        hintMap.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        MultiFormatWriter qrCodeWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hintMap);
            Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bitmap.setPixel(x, y, bitMatrix.get(x, y) ? BLACK : WHITE);
                }
            }
            return bitmap;
        } catch (WriterException e) {
            // Log the exception
            e.printStackTrace();
            return null;
        }
    }

    // example usage
    //QRCodeGenerator qrGenerator = new QRCodeGenerator();
    //Bitmap qrCodeBitmap = qrGenerator.generateQRCode("Your EventID + type for example: r782r3gff2(Promo/checkIn)", 300, 300);
    //imageView.setImageBitmap(qrCodeBitmap);
}
