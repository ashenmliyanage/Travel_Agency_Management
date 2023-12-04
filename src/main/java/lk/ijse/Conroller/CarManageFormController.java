package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import lk.ijse.dto.VehicalDto;
import lk.ijse.model.RoomModel;
import lk.ijse.model.VehicaleModel;
import javafx.scene.image.Image;
import lombok.SneakyThrows;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CarManageFormController implements Initializable {
    public static String Id;
    @FXML
    private TextField ID;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private TextField Number;

    @FXML
    private TextField price;

    @FXML
    private TextField search;

    @FXML
    private TextField type;

    @FXML
    private ImageView Image;

    private InputStream is;

    public CarManageFormController() throws SQLException {
    }

    @FXML
    void AddbtnOnActhon(ActionEvent event) throws SQLException {
        double Price = Double.parseDouble(price.getText());
        String Check = VehicaleModel.Check(ID.getText());

        if (isValidNumber()) {
            if (Check == null) {

                try {
                    var model = new VehicaleModel();
                    boolean isSave = model.save(new VehicalDto(ID.getText(), Price, type.getText(), Number.getText(), is));

                    try {
                        if (isSave) {
                            new Alert(Alert.AlertType.INFORMATION, "Save is successful").show();
                            clearData();
                        }
                    } catch (Exception e) {
                        new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                    }
                } catch (Exception e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            } else {
                new Alert(Alert.AlertType.INFORMATION, "Already Used. Please try a another ID").show();
            }
        }
    }

    @FXML
    void DeletebtnOnActhion(ActionEvent event) throws SQLException {
        var model = new VehicaleModel();
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Send Mail");
            alert.setContentText("Are you sure you want to Delete a vehicle?");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                boolean isDelete = model.Delete(ID.getText());
                if (isDelete) {
                    new Alert(Alert.AlertType.INFORMATION,"Delete Successful").show();
                    clearData();
                }
            }

            else{
                new Alert(Alert.AlertType.INFORMATION,"Not Successful").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void UpdatebtnOnActhion(ActionEvent event) throws SQLException {

        if (isValidNumber()) {
            var model = new VehicaleModel();
            double Price = Double.parseDouble(price.getText());

            try {
                boolean isUpdate = model.Update(new VehicalDto(ID.getText(), Price, type.getText(), Number.getText(), is));

                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Update Successful").show();
                } else {
                    new Alert(Alert.AlertType.INFORMATION, "Not successful").show();
                }
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void impoerbtnActhion(ActionEvent event) {
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
            Image.setImage(image);
            Image.setPreserveRatio(true);
            Image.setFitWidth(610);
            Image.setFitHeight(552);

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

    void clearData(){
        ID.clear();
        type.clear();
        price.clear();
        Number.clear();
        Image.setImage(null);
    }

    @FXML
    void searchOnActhion(ActionEvent event) throws SQLException {
        var model = new VehicaleModel();
        //double Price = Double.parseDouble(price.getText());
        try {
            VehicalDto vehicalDto = model.Search(search.getText());
            if (vehicalDto != null) {
                ID.setText(vehicalDto.getVehicale_id());
                type.setText(vehicalDto.getType());
                price.setText(String.valueOf(vehicalDto.getPrice()));
                Number.setText(vehicalDto.getNumber());
                InputStream Input = vehicalDto.getImage();
                javafx.scene.image.Image image = new Image(Input);
                Image.setImage(image);
                search.clear();
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    private AutoCompletionBinding<String> AutoID;
    private String[] ides = VehicaleModel.getAllIds();

    private Set<String> IdSet = new HashSet<>(Arrays.asList(ides));
    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first();
        ID.setText(VehicaleModel.gernarate());
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

    }

    private void autoCompletionLearnWordIDS(String trim) {
        IdSet.add(trim);

        if (AutoID != null){
            AutoID.dispose();
        }
        AutoID = TextFields.bindAutoCompletion(search, IdSet);
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
        String input = Number.getText();
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
