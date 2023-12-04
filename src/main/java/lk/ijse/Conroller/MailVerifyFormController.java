package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class MailVerifyFormController {

    @FXML
    private AnchorPane Pane;

    @FXML
    private TextField text;

    @FXML
    void resend(ActionEvent event) throws Exception {
        LoginFormController.MailSend();
    }

    @FXML
    void verify(ActionEvent event) throws IOException {
        String code = LoginFormController.random+"";
        if(text.getText().equals(code)){
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainDashbord.fxml"));
            AnchorPane newScene = fxmlLoader.load();
            ((MainDashbordFormController)(fxmlLoader.getController())).initData(LoginFormController.UserName);
            this.Pane.getChildren().clear();
            this.Pane.getChildren().setAll(newScene);
        }
    }
}
