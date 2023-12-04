package lk.ijse.QrCode;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
public class ReadQrCode {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
//
//        // Step 1: Scan a QR code
//        System.out.println("Scan a QR code (provide the path to the image):");
//        String qrCodeImagePath = scanner.nextLine();
//
//        try {
//            String scannedData = scanQRCode(qrCodeImagePath);
//            if (scannedData != null) {
//                System.out.println("Scanned QR code data: " + scannedData);
//            } else {
//                System.out.println("No QR code found or could not decode the QR code.");
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Step 2: Generate a new QR code
        System.out.println("Enter data to generate a new QR code:");
        String newData = scanner.nextLine();
        System.out.println("Input a file name");
        String name = scanner.nextLine();
        String outputImagePath = "E:\\Final\\src\\main\\java\\lk\\ijse\\QR\\" + name + ".png";

        try {
            generateQRCode(newData, outputImagePath);
            System.out.println("New QR code generated and saved to: " + outputImagePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Scan a QR code
    public static String scanQRCode(String qrCodeImagePath) throws Exception {
        BufferedImage image = ImageIO.read(new File(qrCodeImagePath));
        if (image == null) {
            return null;
        }

        BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(image)));
        Map<DecodeHintType, Object> hints = new HashMap<>();
        hints.put(DecodeHintType.CHARACTER_SET, "UTF-8");

        Result result = new MultiFormatReader().decode(binaryBitmap, hints);
        return result.getText();
    }

    // Generate a QR code
    public static void generateQRCode(String data, String outputPath) throws Exception {
        int width = 300;
        int height = 300;

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(data, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }

        File qrCodeFile = new File(outputPath);
        ImageIO.write(qrImage, "PNG", qrCodeFile);
    }
}