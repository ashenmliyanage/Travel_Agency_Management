package lk.ijse.QrCode;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import com.jfoenix.controls.JFXButton;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.awt.*;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class QrCodeScanner {
    public static ArrayList<Integer> scannedValues = new ArrayList<>();
    //String[][] details = DatabaseConnecter.getDetails("user",4);
    public static String value;


    public static void QrScanner(){
        JButton button = new JButton("Exit");
        Webcam webcam = Webcam.getDefault();   //Generate Webcam Object
        webcam.setViewSize(new Dimension(640,480));
        WebcamPanel webcamPanel = new WebcamPanel(webcam);
        webcamPanel.setMirrored(false);
        button.setSize(100,100);
        webcamPanel.add(button);
        JFrame jFrame = new JFrame();
        jFrame.add(webcamPanel);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                webcam.close();
                jFrame.setVisible(false);
            }
        });
        jFrame.setSize(640,480);
//        jFrame.pack();
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
                    jFrame.setVisible(true);
                    jFrame.dispose();
                    webcam.close();
                    break;
                }

            }catch (NotFoundException e ) {
                //new Alert(Alert.AlertType.INFORMATION,"Try using search bar").show();
            }
        } while(true);
}
}