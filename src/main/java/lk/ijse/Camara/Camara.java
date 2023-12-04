package lk.ijse.Camara;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Camara extends JFrame {

    private JLabel camaraScreen;
    private JButton btnCapture;
    private JButton btnClose; // New Close button

    private VideoCapture videoCapture;

    private Mat image;
    private boolean click;

    public Camara(){
        setLayout(null);
        camaraScreen = new JLabel();
        camaraScreen.setBounds(0,0,640,480);
        add(camaraScreen);

        setSize(new Dimension(640,560));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        btnCapture = new JButton("Capture");
        btnCapture.setBounds(300,480,80,40);
        add(btnCapture);

        btnClose = new JButton("Close");
        btnClose.setBounds(400, 480, 80, 40);
        add(btnClose);
        btnCapture.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                click = true;
            }
        });

        btnClose.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                dispose(); // Close only this form
                }
                });
    }

    public void startCamara() {
        videoCapture = new VideoCapture(0);
        image = new Mat();
        ImageIcon icon;
        byte[] imageData;
        while (true) {
            videoCapture.read(image);

            final MatOfByte buf = new MatOfByte();
            Imgcodecs.imencode(".jpg", image, buf);

            imageData = buf.toArray();
            icon = new ImageIcon(imageData);
            camaraScreen.setIcon(icon);
            if (click) {
                String name = JOptionPane.showInputDialog("Enter Image name");
                if (name == null) {
                    name = new SimpleDateFormat("yyyy-mm-dd-hh-mm-ss").format(new Date());
                }
                Imgcodecs.imwrite("E:\\Final\\src\\main\\resources\\Members_Photos\\" + name + ".jpg", image);

                click = false;
            }
        }
    }
}

