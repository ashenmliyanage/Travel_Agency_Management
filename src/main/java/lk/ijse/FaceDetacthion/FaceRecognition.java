package lk.ijse.FaceDetacthion;

import org.opencv.core.*;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FaceRecognition extends JFrame {

    private JLabel cameraScreen;
    private JButton captureButton;
    private JButton loadReferenceButton;
    private VideoCapture videoCapture;
    private CascadeClassifier faceCascade;
    private Mat referenceImage;

    Mat image;

    public FaceRecognition(Mat image) {
        JFrame jFrame = new JFrame();
        this.image = image;
        setLayout(new BorderLayout());

        cameraScreen = new JLabel();
        add(cameraScreen, BorderLayout.CENTER);

        captureButton = new JButton("Capture Photo");
        captureButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                capturePhoto();
            }
        });

        loadReferenceButton = new JButton("Sacn");
        loadReferenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadReferenceImage();
            }
        });

        JPanel buttonPanel = new JPanel();
        //buttonPanel.add(captureButton);
        buttonPanel.add(loadReferenceButton);

        JButton button = new JButton("Exit");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                videoCapture.release();
            }
        });

        add(buttonPanel, BorderLayout.SOUTH);

        setSize(new Dimension(800, 600));
//        setDefaultCloseOperation(JFrame.);

        setVisible(true);

        // Load the pre-trained Haarcascades face classifier
        faceCascade = new CascadeClassifier();
        faceCascade.load("E:\\face\\src\\main\\resources\\haarcascade_frontalface_default.xml");

        startCamera();
    }

    private void startCamera() {
        videoCapture = new VideoCapture(0);

        if (!videoCapture.isOpened()) {
            System.out.println("Error: Camera not found!");
            System.exit(1);
        }

        Mat frame = new Mat();
        MatOfRect faces = new MatOfRect();

        while (true) {
            videoCapture.read(frame);

            if (!frame.empty()) {
                detectAndDrawFaces(frame, faces);
                displayFrame(frame);
            }

            try {
                Thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void detectAndDrawFaces(Mat frame, MatOfRect faces) {
        faceCascade.detectMultiScale(frame, faces, 1.1, 3, 0, new Size(30, 30), new Size());

        for (Rect rect : faces.toArray()) {
            Imgproc.rectangle(frame, new Point(rect.x, rect.y),
                    new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 2);

            if (referenceImage != null) {
                // Compare the detected face with the reference image
                Mat detectedFace = new Mat(frame, rect);
                if (areImagesEqual(referenceImage, detectedFace)) {
                    System.out.println("Person is in front of the camera!");
                } else {
                    System.out.println("Person is NOT in front of the camera.");
                }
            }
        }
    }

    private void displayFrame(Mat frame) {
        ImageIcon image = matToImageIcon(frame);
        cameraScreen.setIcon(image);
    }

    private void capturePhoto() {
        Mat frame = new Mat();
        videoCapture.read(frame);

        if (!frame.empty()) {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String photoName = "E:\\face\\src\\images\\captured_photo_" + timeStamp + ".png";
            Imgcodecs.imwrite(photoName, frame);
            System.out.println("Photo captured: " + photoName);
        }
    }

    private void loadReferenceImage() {
       referenceImage = this.image;
    }

    private boolean areImagesEqual(Mat img1, Mat img2) {
        // Resize the images to a common size
        Size commonSize = new Size(640, 480);  // Adjust the size as needed
        Imgproc.resize(img1, img1, commonSize);
        Imgproc.resize(img2, img2, commonSize);

        Mat diff = new Mat();
        Core.absdiff(img1, img2, diff);

        Scalar sumDiffScalar = Core.sumElems(diff);
        double sumDiff = sumDiffScalar.val[0];

        return sumDiff == 0;
    }

    public static ImageIcon matToImageIcon(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".png", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();

        try (ByteArrayInputStream bis = new ByteArrayInputStream(byteArray)) {
            BufferedImage bufferedImage = ImageIO.read(bis);
            return new ImageIcon(bufferedImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

}
