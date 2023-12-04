package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.UserModel;

import java.io.IOException;
import java.sql.SQLException;

public class SidebarOnDashbordFormController{

    AnchorPane sidePane;
    javafx.scene.control.Button sidemore;
    private Button exitbtn;


    public void closebtnOnActhion(ActionEvent actionEvent) throws IOException {
//        Parent parent = FXMLLoader.load(getClass().getResource("/View/Nomal/Nomal.fxml"));
//        sidePane.getChildren().clear();
//        sidePane.getChildren().add(parent);
        sidePane.setVisible(false);
        sidemore.setVisible(true);
        exitbtn.setVisible(true);
    }

    public void initData(AnchorPane sidePane, Button sideMore, Button exitbtn) {
        this.sidePane = sidePane;
        this.sidemore = sideMore;
        this.exitbtn = exitbtn;
    }
    public void initData(AnchorPane sidePane){
        this.sidePane = sidePane;
    }

    public void addbtnOnActhion(ActionEvent actionEvent) throws IOException {
        //        //Parent parent = FXMLLoader.load(this.getClass().getResource("/View/SidebarOnDashbordForm.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/View/Adduser.fxml"));
        AnchorPane newScene = fxmlLoader.load();
        ((AdduserFormController)(fxmlLoader.getController())).initData(sidePane);
        this.sidePane.getChildren().clear();
        this.sidePane.getChildren().setAll(newScene);
    }

    @FXML
    private TextField password;

    @FXML
    private TextField uername;
    public void saveOnActhion(ActionEvent actionEvent) throws SQLException {
        if (validMail()) {
            var model = new UserModel();
            try {
                boolean update = model.update(uername.getText(), password.getText());

                if (update) {
                    new Alert(Alert.AlertType.INFORMATION, "Update successful").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.INFORMATION, e.getMessage()).show();
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

            if (this.uername.getText().matches("^[A-Z][a-zA-Z\\s]*$")) {
                return true;
            }
            else {
                new Alert(Alert.AlertType.WARNING,"Please enter valid Name").show();
                return false;
            }
        }

}
