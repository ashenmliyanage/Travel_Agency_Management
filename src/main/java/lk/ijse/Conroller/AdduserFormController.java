package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.QrCode.QrcodeForUser;
import lk.ijse.dto.Userdto;
import lk.ijse.model.UserModel;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.sql.SQLException;

public class AdduserFormController {
    AnchorPane sidePane;
    public void initData(AnchorPane sidePane) {
        this.sidePane = sidePane;
    }


    @FXML
    private TextField mail;

    @FXML
    private TextField password;

    @FXML
    private TextField username;

    public void backbtnOnActhion(ActionEvent actionEvent) throws IOException {
        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/Dashbord.fxml"));
        MainDashbordFormController.pane.getChildren().clear();
        MainDashbordFormController.pane.getChildren().add(parent);
    }
    public static int userID;

    public static String userName;
    String Idgenarate() throws SQLException {
        var model = new UserModel();
        try {
            int count = model.getCount();
            userID = count;
            String name = "U0"+count;
            userName = name;
            return name;
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,e.getMessage()).show();
        }
        return null;
    }

    public void savebtnOnActhon(ActionEvent actionEvent) throws SQLException {

        if (validMail()) {
            String name = Idgenarate();
            Userdto dto = new Userdto(name, username.getText(), mail.getText(), password.getText());
            try {
                var model = new UserModel();
                boolean isSave = model.isSave(dto);
                if (isSave) {
                    QrcodeForUser.CreateQr(username.getText(), userName);
                    new Alert(Alert.AlertType.INFORMATION, "save is successful").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage());
            }
        }
    }

    private boolean validMail(){
//        Pattern p = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@ [a-zA-Z0-9]+([.][a-zA-z])");
//        Matcher m = p.matcher(Number.getText());
//        if (m.find() && m.group().equals(Number.getText())){
//            return true;
//        }
//        else {
//            new Alert(Alert.AlertType.WARNING,"Please enter a valid Gmail").show();
//            return false;
//        }
        String email = this.mail.getText();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        boolean verify =  email.matches(emailRegex);
        if (verify){
            if (this.username.getText().matches("^[A-Z][a-zA-Z\\s]*$")) {
                    return true;
                }
            else {
                new Alert(Alert.AlertType.WARNING,"Please enter valid Name").show();
                return false;
            }
        }
        else {
            new Alert(Alert.AlertType.WARNING,"Please enter valid mail").show();
            return false;
        }
    }
}

