package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import lk.ijse.model.HotelModel;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HotelFormController implements Initializable {
    AnchorPane anchorPane;

    @FXML
    private Label Loc;

    @FXML
    private Label Name;

    @FXML
    private Label Room;

    int[] count = HotelModel.getCount();

    public HotelFormController() throws SQLException {
    }

    @FXML
    void Anuradapura(ActionEvent event) {
        Loc.setText("Anuradapura");
        Name.setText("Heritage Hotel");
        Room.setText(""+count[0]);
    }

    @FXML
    void Colombo(ActionEvent event) {
        Loc.setText("Colombo");
        Name.setText("Pearl Royal");
        Room.setText(""+count[4]);
    }

    @FXML
    void Galle(ActionEvent event) {
        Loc.setText("Galle");
        Name.setText("Le Grand");
        Room.setText(""+count[3]);
    }

    @FXML
    void Katharagama(ActionEvent event) {
        Loc.setText("Katharagama");
        Name.setText("Livinya Holiday");
        Room.setText(""+count[2]);
    }

    @FXML
    void NuwaraEliya(ActionEvent event) {
        Loc.setText("Nuwara Eliya");
        Name.setText("The Edgware");
        Room.setText(""+count[1]);
    }

    public void initData(AnchorPane mainPane) {
        this.anchorPane = mainPane;
    }

    public void ManageBtnOnActhion(ActionEvent actionEvent) throws IOException {
//        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/AddRoom.fxml"));
//        this.anchorPane.getChildren().clear();
//        this.anchorPane.getChildren().add(parent);
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/AddRoom.fxml"));
        AnchorPane newScene = fxmlLoader.load();
        ((AddRoomFormController)(fxmlLoader.getController())).initData(anchorPane);
        this.anchorPane.getChildren().clear();
        this.anchorPane.getChildren().setAll(newScene);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Anuradapura(null);
    }
}
