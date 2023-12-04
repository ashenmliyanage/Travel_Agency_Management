package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import lk.ijse.model.PaymentModel;
import lombok.Data;
import lombok.SneakyThrows;

@Data
public class LaodDataFormController {
    @FXML
    private ImageView Image;
    @FXML
    public Label BookngId;

    @FXML
    public Label Date;

    @FXML
    public Label Name;

    @FXML
    public Label Total;

    @FXML
    public static Button delete;


    @SneakyThrows
    @FXML
    void deleteOnActhion(ActionEvent event) {
        var model = new PaymentModel();

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Delete Customer");
        alert.setContentText("Are you sure you want to delete this customer?");
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (result == ButtonType.OK) {
            model.delete(BookngId.getText());
        }

    }
}
