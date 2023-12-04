package lk.ijse.Conroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import lk.ijse.Camara.Camara;
import lk.ijse.dto.MemberDto;
import lk.ijse.dto.tm.Membertm;
import lk.ijse.model.MemberModel;
import lk.ijse.model.VehicaleModel;
import lombok.SneakyThrows;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;
import org.opencv.core.Core;

import java.awt.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MemberFormController implements Initializable {
    public static String Id;
    @FXML
    private TextField Address;

    @FXML
    private TextField ID;

    @FXML
    private TextField Name;

    @FXML
    private TextField Number;

    @FXML
    private TextField search;

    public MemberFormController() throws SQLException {
    }

    @FXML
    void AddbtnOnActhon(ActionEvent event) throws SQLException {

        if (validMail()) {
            MemberDto dto = new MemberDto(ID.getText(), Name.getText(), Address.getText(), Number.getText());

            try {
                var model = new MemberModel();
                boolean isSave = model.save(dto);

                if (isSave) {
                    new Alert(Alert.AlertType.INFORMATION, "Save is successful").show();
                    clearData();
                    initialize(null, null);
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Already Use that id. Use another ID").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void searchOnActhion(ActionEvent event) throws SQLException {
        var model = new MemberModel();
        try {
            MemberDto dto = model.Search(search.getText());

            if (dto != null){
                search.clear();
                ID.setText(dto.getMember_id());
                Name.setText(dto.getName());
                Address.setText(dto.getCountry());
                Number.setText(dto.getMail());
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void deletebtnOnActhion(ActionEvent event) throws SQLException {
        var model = new MemberModel();
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Send Mail");
            alert.setContentText("Are you sure you want to Delete a Room?");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                boolean isDelete = model.Delete(ID.getText());
                if (isDelete) {
                    new Alert(Alert.AlertType.INFORMATION,"Delete is Successful").show();
                    clearData();
                    initialize(null,null);
                }
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Delete is not Successful").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void updatebtnOnActhion(ActionEvent event) throws SQLException {
        if (validMail()) {
            MemberDto dto = new MemberDto(ID.getText(), Name.getText(), Address.getText(), Number.getText());

            try {
                var model = new MemberModel();
                boolean isUpdate = model.Update(dto);

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Update is Successful").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Update is not Successful").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }
    void clearData(){
        ID.clear();
        Name.clear();
        Address.clear();
        Number.clear();
    }
    @FXML
    private TableColumn<?, ?> IDClom;

    @FXML
    private TableColumn<?, ?> NameColm;

    @FXML
    private TableView<Membertm> table;

    void LoadAllMember() throws SQLException {
        var model = new MemberModel();

        ObservableList<Membertm> list = FXCollections.observableArrayList();

        try {
            List<MemberDto> dtos = model.getAllmember();

            for (MemberDto dto : dtos) {
                list.add(
                        new Membertm(
                                dto.getMember_id(),
                                dto.getName()
                        )
                );
            }
            table.setItems(list);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }
    private void setCellValueFactory(){
        IDClom.setCellValueFactory(new PropertyValueFactory<>("id"));
        NameColm.setCellValueFactory(new PropertyValueFactory<>("UserName"));
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first();
        ID.setText(MemberModel.genarete());
        ID.setEditable(false);
        //---------------------------------------------ID auto ------------------------------------------------------------------------

        AutoID = TextFields.bindAutoCompletion(search,IdSet);

        search.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        autoCompletionLearnWordIDS(search.getText().trim());
                        break;
                    default:
                        break;
                }
            }
        });


        IDClom.setStyle("-fx-background-color: rgba(79, 174, 206, 0.70);" +
                "-fx-font-family: 'Martel Sans';" +
                "-fx-font-size: 20px;" +
                "-fx-font-style: normal;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: #353535;"+
                "-fx-alignment: center;"
        );
        NameColm.setStyle("-fx-background-color: rgba(79, 174, 206, 0.70);" +
                "-fx-font-family: 'Martel Sans';" +
                "-fx-font-size: 20px;" +
                "-fx-font-style: normal;" +
                "-fx-font-weight: 700;" +
                "-fx-text-fill: #353535;" +
                "-fx-alignment: center;"
        );
        LoadAllMember();
        setCellValueFactory();
    }

    private AutoCompletionBinding<String> AutoID;
    private String[] ides = MemberModel.getAllIds();

    private Set<String> IdSet = new HashSet<>(Arrays.asList(ides));

    private void autoCompletionLearnWordIDS(String trim) {
        IdSet.add(trim);

        if (AutoID != null){
            AutoID.dispose();
        }
        AutoID = TextFields.bindAutoCompletion(search, IdSet);
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
        String email = Number.getText();
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        boolean verify =  email.matches(emailRegex);
        if (verify){
            if (this.Name.getText().matches("^[A-Z][a-zA-Z\\s]*$")) {
                if (this.Address.getText().matches("^[A-Z][a-zA-Z\\s]*$")) {
                    return true;
                }
                else {
                    new Alert(Alert.AlertType.WARNING,"Please enter valid Address").show();
                    return false;
                }
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

    public void editbtnOnActhion(ActionEvent actionEvent) {
        ID.setEditable(true);
    }

    public void photobtnOnActhion(ActionEvent actionEvent) {
        thread.start();
    }
    Thread thread = new Thread(() ->{
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                Camara main = new Camara();

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        main.startCamara();
                    }
                }).start();
            }
        });
    });
    @SneakyThrows
    private void first() {
        if (Id != null){
            searchOnActhion(null);
            Id = null;
        }
    }
}
