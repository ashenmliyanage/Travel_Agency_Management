package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import lk.ijse.FaceDetacthion.FaceRecognition;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class FaceUnLockFormController implements Initializable {

    public TextField name;

    public static Mat imageMat;
    public void SubmitbtnOnActhion(ActionEvent actionEvent) {
        String name = this.name.getText();
        imageMat = Imgcodecs.imread("E:\\Final\\src\\main\\resources\\users\\"+name+".jpg");

        if (imageMat.empty()){
            new Alert(Alert.AlertType.INFORMATION,"Can't Find member").show();
        }
        if (!imageMat.empty()){
            thread.start();
        }
    }
        Thread thread = new Thread(() ->{
            new FaceRecognition(imageMat);
        });



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }
}
