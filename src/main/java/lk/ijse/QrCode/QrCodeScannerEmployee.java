package lk.ijse.QrCode;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

import javax.swing.*;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class QrCodeScannerEmployee {
    public static ArrayList<Integer> scannedValues = new ArrayList<>();
    String[][] details = DatabaseConnecter.getDetails("employee",8);
    public static String value;

    public static void QrScanner(){
        Webcam webcam = Webcam.getDefault();   //Generate Webcam Object
        webcam.setViewSize(new Dimension(320,240));
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setMirrored(false);
        JFrame jFrame = new JFrame();
        jFrame.add(webcamPanel);
        jFrame.pack();
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        jFrame.setVisible(true);

        do {
            try {
                BufferedImage image = webcam.getImage();
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result = new MultiFormatReader().decode(bitmap);
                if(result.getText() != null) {
                    System.out.println(result.getText());
                     value = result.getText();

                    //LocalDate date = LocalDate.now();
                    //LocalTime time = LocalTime.now();

                    /*if (!scannedValues.contains(value)) {
                        scannedValues.add(value);
                    } else {
                        scannedValues.remove(Integer.valueOf(value));
                    }*/

                    //DBC.setDetails("INSERT INTO turtlescare.ticket (issueDate, issueTime, code) VALUES ('"+ date +"', '"+ time +"', '" + value +"')");
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    jFrame.setVisible(false);
                    jFrame.dispose();
                    webcam.close();
                    break;
                }

            }catch (NotFoundException e ) {
                //pass
            }
        } while(true);
}
}