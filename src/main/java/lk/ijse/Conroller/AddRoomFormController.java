package lk.ijse.Conroller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lk.ijse.Mail.VehicallMail;
import lk.ijse.dto.RoomDto;
import lk.ijse.model.RoomModel;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import lombok.SneakyThrows;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.*;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddRoomFormController implements Initializable {

    public static String Id;
    @FXML
    private ImageView Image;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TextField ID;

    @FXML
    private TextField loc;

    @FXML
    private TextField number;

    @FXML
    private TextField price;

    @FXML
    private TextField search;

    @FXML
    private TextField type;

    @FXML
    private ComboBox<String> TypeCombo;

    @FXML
    private ComboBox<String> LocCOMBO;

    @FXML
    private ImageView ImportImage;
    AnchorPane main;

    public AddRoomFormController() throws SQLException {
    }

    @FXML
    void AddbtnOnActhion(ActionEvent event) throws SQLException {
        var model = new RoomModel();

        if (isValidNumber()) {
            try {
                System.out.println(this.ID.getText());
                String Id = model.getuserID(ID.getText());

                if (Id == null) {

                    double Price = Double.parseDouble(price.getText());
                    //System.out.println(is);
                    RoomDto dto = new RoomDto(ID.getText(), TypeCombo.getValue(), LocCOMBO.getValue(), number.getText(), Price, is);
                    boolean isSave = model.save(dto);

                    try {
                        if (isSave) {
                            new Alert(Alert.AlertType.INFORMATION, "Added Successful ! ").show();
                            clearText();
                        }
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                    }
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Already Use that ID \n please use another UNIQUE id").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void DeletebtnOnActhion(ActionEvent event) throws SQLException {
        var model = new RoomModel();

        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Send Mail");
            alert.setContentText("Are you sure you want to Delete a Room?");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                boolean isDelete = model.DeleteRoom(ID.getText());
                if (isDelete){
                    new Alert(Alert.AlertType.INFORMATION,"Delete Successfully").show();
                    clearText();
                }
            }
            //boolean isDelete = model.DeleteRoom(ID.getText());

        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    File file = null;
    String path = null;
    String frme= null;
    int s = 0;
    byte[] primage = null;

    public InputStream is;
    @FXML
    void ImportbtnOnActhion(ActionEvent event) throws SQLException {

        //JFileChooser fileChooser = new JFileChooser();
        FileChooser fileChooser = new FileChooser();
        //FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter("PNG JPG AND JPEG","png","jpeg","jpg");
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Image Files", "*.jpg", "*.jpeg", "*.png");
        //fileChooser.addChoosableFileFilter(fileNameExtensionFilter);
        fileChooser.getExtensionFilters().add(filter);
        //int load = fileChooser.showOpenDialog(null);
        java.io.File selectedFile = fileChooser.showOpenDialog(null);

//        if (selectedFile == fileChooser.APPROVE_OPTION){
//            file = fileChooser.getSelectedFile();
//            path = file.getAbsolutePath();
//            ImageIcon li = new ImageIcon(path);
//            //Image image1 = new Image(li.getImage().toString());
//            //Image image = li.getImage();
//            //.getScaledInstance(100,100, Image.SCALE_SMOOTH);
//            System.out.println(li.getImage().toString());
//            //images.setImage(image1);
//        }


        if (selectedFile != null) {
            String path = selectedFile.getAbsolutePath();

            // Load the image using JavaFX
            Image image = new Image("file:" + path);

            // Display the image using an ImageView
            ImageView imageView = new ImageView(image);

            // You can add the ImageView to your layout or do further processing here
            // For example, add it to a VBox
            //VBox vbox = new VBox(imageView);
            //Scene imageScene = new Scene(vbox);
            //Stage imageStage = new Stage();
            // imageStage.setScene(imageScene);
            //imageStage.show();
            ImportImage.setImage(image);
            ImportImage.setPreserveRatio(true);
            ImportImage.setFitWidth(610);
            ImportImage.setFitHeight(552);

            System.out.println("Image path : " + path);
            //System.out.println("Image Name : " + file.getName());

            File f = new File(path);
                try {
                    is = new FileInputStream(f);
                    //var model = new RoomDto("1001","g0","galle","4030",12,is);
                    //RoomModel.addImage(model);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
        }

    }


    @FXML
    void UpdatebtnOnActhion(ActionEvent event) {

        if (isValidNumber()) {
            double Price = Double.parseDouble(price.getText());
            var dto = new RoomDto(ID.getText(), TypeCombo.getValue(), LocCOMBO.getValue(), number.getText(), Price, is);
            try {
                var Model = new RoomModel();
                boolean isUpdate = Model.Update(dto);
                System.out.println(isUpdate);
                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Update Is Successful").show();
                    clearText();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Something Wrong").show();
                }

            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void searchOnActhion(ActionEvent event) throws SQLException {
        var model = new RoomModel();

        try {
            RoomDto dto = model.getSerch(search.getText());
            System.out.println(dto);

            if (dto != null){
                ID.setText(dto.getRoom_id());
                TypeCombo.setValue(dto.getType());
                LocCOMBO.setValue(dto.getLocation());
                number.setText(dto.getRoom_number());
                price.setText(String.valueOf(dto.getPrice()));
                InputStream Input = dto.getImage();
                javafx.scene.image.Image image = new Image(Input);
                ImportImage.setImage(image);
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"That is has not found").show();
                search.clear();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            search.clear();
        }
    }


    public void backbtnOnActhion(ActionEvent actionEvent) throws IOException {

//        Parent parent = FXMLLoader.load(getClass().getResource("/View/HotelForm.fxml"));
//        main.getChildren().clear();
//        main.getChildren().add(parent);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HotelForm.fxml"));
        AnchorPane newScene = fxmlLoader.load();
        ((HotelFormController)(fxmlLoader.getController())).initData(main);
        this.main.getChildren().clear();
        this.main.getChildren().setAll(newScene);
    }

    public void initData(AnchorPane anchorPane) {
        this.main = anchorPane;
    }

    void clearText(){
        ID.clear();
        TypeCombo.setValue(null);
        LocCOMBO.setValue(null);
        number.clear();
        price.clear();
        ImportImage.setImage(null);
    }

    //---------------------------------------------- Auto For Ids -------------------------------------------------
    private AutoCompletionBinding<String> AutoID;
    private String[] ides = RoomModel.getAllIds();

    private Set<String> IdSet = new HashSet<>(Arrays.asList(ides));

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ID.setText(RoomModel.Genarate());

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

        ObservableList<String> list = FXCollections.observableArrayList();
        list.add("Family Room");
        list.add("Double Room");
        list.add("Single Room");
        TypeCombo.setItems(list);

        ObservableList<String> list1 = FXCollections.observableArrayList();
        list1.add("Galle");
        list1.add("Nuwara Eliya");
        list1.add("Anuradapura");
        list1.add("Colombo");
        list1.add("Katharagama");
        LocCOMBO.setItems(list1);

        first();
    }
    //-------------------------------------------------IDS trim-------------------------------------------------
    private void autoCompletionLearnWordIDS(String  trim) {
        IdSet.add(trim);

        if (AutoID != null){
            AutoID.dispose();
        }
        AutoID = TextFields.bindAutoCompletion(search, IdSet);
    }

    public void loadImage(){
        //byte[] image =
    }

    private boolean isValidNumber(){
        Pattern pattern = Pattern.compile("^[0-9]+(\\.[0-9]{1,2})?$");
        Matcher matcher = pattern.matcher(price.getText());
        if (matcher.matches()){
            if (isValidInteger()){
                return true;
            }
            else {
                new Alert(Alert.AlertType.WARNING,"Please enter valid Room Number").show();
                return false;
            }
        }
        else {
            new Alert(Alert.AlertType.WARNING,"Please enter valid price").show();
            return false;
        }
    }
    private static final String INTEGER_PATTERN = "^\\d+$";

    public boolean isValidInteger() {
        String input = number.getText();
        Pattern pattern = Pattern.compile(INTEGER_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return matcher.matches();
    }
    @SneakyThrows
    void first(){
        if (Id != null){
            search.setText(Id);
            searchOnActhion(new ActionEvent());
            Id = null;
        }
    }
}
