package lk.ijse;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launcher extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception{
        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/LoginForm.fxml"));
        Scene scene = new Scene(parent);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setTitle("Login Page");
        stage.centerOnScreen();
        stage.setScene(scene);
        stage.show();
    }
}