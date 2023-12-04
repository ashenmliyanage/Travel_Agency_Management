package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lk.ijse.Mail.Gmailer;
import lk.ijse.model.LoginModel;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginFormController implements Initializable {

    @FXML
    private Pane Qr;

    @FXML
    private Pane Uername;

    @FXML
    private Pane ForgrtMail;

    @FXML
    private AnchorPane AQrpane;

    public static String UserName;

    @FXML
    void QrOnActhion(ActionEvent event) {
        System.out.println("click");
        Uername.setVisible(false);
        Qr.setVisible(true);
        System.out.println("go");
    }

    @FXML
    void UserOnActhion(ActionEvent event) {
        Qr.setVisible(false);
        Uername.setVisible(true);
        ForgrtMail.setVisible(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Uername.setVisible(true);
        Qr.setVisible(false);
        Uername.setVisible(true);
        ForgrtMail.setVisible(false);
    }

    public void ForgetOnActhion(ActionEvent actionEvent) {
        ForgrtMail.setVisible(true);
        Uername.setVisible(false);
        Qr.setVisible(false);
    }


    @FXML
    private TextField mailenter;

    @FXML
    private AnchorPane Pane;

    public static String Mail;
    public static int random;

    public void SubmitbtnOnActhion(ActionEvent actionEvent) throws Exception {
        Random r = new Random();

        var model = new LoginModel();
        String mail = model.checkMail(mailenter.getText());
        System.out.println(mail);

        try {
            if (mail.equals(mailenter.getText())) {
                UserName  = model.getMailUserName(mail);
                this.random = r.nextInt(9000) + 1000;
                this.Mail = mailenter.getText();
                MailSend();
                Parent parent = FXMLLoader.load(this.getClass().getResource("/View/MailVerifyForm.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = (Stage) this.Pane.getScene().getWindow();
                stage.setTitle("DashBoard");
                stage.setScene(scene);
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"Mail is Wrong").show();
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    public void ScanbtnOnActhion(ActionEvent actionEvent) throws IOException {

        List data = LoginModel.QrLogin();
        boolean isOkey = (boolean) data.get(0);
        String name = (String) data.get(1);
        UserName = name;
        Stage stage = new Stage();
        if(isOkey){
//            Stage stage = new Stage();
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainDashbord.fxml"));
//            stage.setScene(new Scene(fxmlLoader.load()));
//            stage.initStyle(StageStyle.UNDECORATED);
//            ((MainDashbordFormController)(fxmlLoader.getController())).initData(name);
//            stage.setResizable(false);
//            stage.setTitle("Connect 4 Game - Player: " + name);
//            stage.show();
//            stage.centerOnScreen();
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainDashbord.fxml"));
            AnchorPane newScene = fxmlLoader.load();
            ((MainDashbordFormController)(fxmlLoader.getController())).initData(name);
            this.Pane.getChildren().clear();
            this.Pane.getChildren().setAll(newScene);
        }
        else {
            new Alert(Alert.AlertType.INFORMATION, "Login UnSuccesful").show();
        }

    }

    @FXML
    private PasswordField Password;

    @FXML
    private TextField Username;

    public void loginBtnOnActhion(ActionEvent actionEvent) throws IOException {
        boolean flag = LoginModel.Check(Username.getText(),Password.getText());
            if(flag){
                String name = LoginModel.getName(Username.getText(),Password.getText());
                UserName = Username.getText();
                Stage stage = new Stage();
//                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainDashbord.fxml"));
//                stage.setScene(new Scene(fxmlLoader.load()));
//                stage.initStyle(StageStyle.UNDECORATED);
//                ((MainDashbordFormController)(fxmlLoader.getController())).initData(name);
//                stage.setResizable(false);
//                stage.show();
//                stage.centerOnScreen();
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainDashbord.fxml"));
                AnchorPane newScene = fxmlLoader.load();
                ((MainDashbordFormController)(fxmlLoader.getController())).initData(name);
                this.Pane.getChildren().clear();
                this.Pane.getChildren().setAll(newScene);
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"Login Fail").show();
            }
        }


        public static void MailSend() throws Exception {
            Gmailer.setEmailCom(Mail, random);
        }

    public void facebtnOnActhion(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/faceUnLock.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) this.Pane.getScene().getWindow();
        stage.setTitle("DashBoard");
        stage.setScene(scene);
    }
}
