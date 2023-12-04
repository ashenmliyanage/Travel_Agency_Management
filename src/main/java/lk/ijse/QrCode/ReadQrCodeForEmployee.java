package lk.ijse.QrCode;//package lk.ijse.QrCode;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class ReadQrCodeForEmployee {
    public static void CreateQr(String Name , String ID){
        String outputImagePath = "E:\\Final\\src\\main\\java\\lk\\ijse\\QR\\" + Name + ".png";
        try {
            generateQRCode(ID, outputImagePath);
            System.out.println("New QR code generated and saved to: " + outputImagePath);
        } catch (Exception e) {
           e.printStackTrace();
        }

    }

    public static void generateQRCode(String id, String path) throws WriterException, IOException {

        int width = 300;
        int height = 300;

        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        com.google.zxing.common.BitMatrix bitMatrix = qrCodeWriter.encode(id, BarcodeFormat.QR_CODE, width, height, hints);

        BufferedImage qrImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0x000000 : 0xFFFFFF);
            }
        }

        File qrCodeFile = new File(path);
        ImageIO.write(qrImage, "PNG", qrCodeFile);

    }
//    static class Main{
//        public static void main(String[] args) {
//            ReadQrCodeForEmployee.CreateQr("As","ID");
//        }
//    }
}
