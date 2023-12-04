package lk.ijse.Conroller.Popup;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class addFormContoller {
    public Stage stage = new Stage();
        public void okey() throws IOException {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/View/Popup/add.fxml"));
            Parent root = loader.load();

            // Create a Scene
            Scene scene = new Scene(root);

            // Set the stage title and scene
            stage.setTitle("Added");
            stage.initStyle(StageStyle.UNDECORATED);
            stage.setScene(scene);

            // Show the stage
            stage.show();
        }

    public void btnOnActhon(ActionEvent actionEvent) {
            stage.close();
    }
}





