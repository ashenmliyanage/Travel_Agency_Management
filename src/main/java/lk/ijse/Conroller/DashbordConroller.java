package lk.ijse.Conroller;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.dto.employeedto;
import lk.ijse.model.DashbordModel;
import lk.ijse.model.EmployeeModel;
import lombok.SneakyThrows;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.scene.control.Label;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.awt.Desktop;
import java.net.URI;

public class DashbordConroller implements Initializable {

    public TextField search;
    @FXML
    private Label lblDate;

    @FXML
    private Label lbltime;

    //ashen
    @FXML
    private Label Aus;

    @FXML
    private Label Brazil;

    @FXML
    private Label Loc1;

    @FXML
    private Label Loc2;

    @FXML
    private Label Loc5;

    @FXML
    private Label Name1;

    @FXML
    private Label Name2;

    @FXML
    private Label Name3;

    @FXML
    private Label Name4;

    @FXML
    private Label Name5;

    @FXML
    private Label Rusia;

    @FXML
    private Label USA;

    @FXML
    private Label UserName;

    @FXML
    private Label loc3;

    @FXML
    private Label loc4;

    @FXML
    private Button sideMore;

    @FXML
    private AnchorPane sidePane;

    public static AnchorPane newSidepane;

    public static boolean flag = true;

    public void setSidePane() {
        this.sidePane.setVisible(false);
    }
    public DashbordConroller(){
    }

    public AnchorPane getSidePane() {
        return sidePane;
    }

    public void sideMorebtnOActhion(ActionEvent actionEvent) throws IOException {
        exitbtn.setVisible(false);
        sideMore.setVisible(false);
        sidePane.setVisible(true);

        // Load the new FXML scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/SidebarOnDashbordForm.fxml"));
        AnchorPane newScene = fxmlLoader.load();

        // Initialize the controller with any required data
        SidebarOnDashbordFormController controller = fxmlLoader.getController();
        controller.initData(sidePane, sideMore, exitbtn);

        // Apply a scale-in animation to the new scene (slower)
        ScaleTransition scaleTransition = new ScaleTransition(Duration.seconds(10000), newScene);
        scaleTransition.setFromX(0.1);
        scaleTransition.setFromY(0.1);
        scaleTransition.setToX(1.0);
        scaleTransition.setToY(1.0);

        // Apply a translate-in animation to the new scene (slower)
        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(10000), newScene);
        translateTransition.setFromX(newScene.getBoundsInLocal().getWidth());
        translateTransition.setToX(0);

        // Apply a fade-in animation to the new scene (slower)
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(10000), newScene);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);

        // Sequentially play the scale, translate, and fade transitions
        SequentialTransition sequentialTransition = new SequentialTransition(scaleTransition, translateTransition, fadeTransition);

        // Clear and set the sidePane with the new scene
        sidePane.getChildren().clear();
        sidePane.getChildren().setAll(newScene);

        Scene scene = new Scene(sidePane, 800, 600);
        Stage primaryStage = null;
        primaryStage.setScene(scene);

        primaryStage.setTitle("FXML Cover Transition Example");
        primaryStage.show();

        // Start with the sequential transition to create the zoom, slide, and fade-in effect (slower)
        sequentialTransition.play();
    }


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateRealTime();
        emp();
        this.UserName.setText(LoginFormController.UserName  );
        newSidepane = sidePane;

            int[] count = DashbordModel.setCout();
            USA.setText(""+count[0]);
            Rusia.setText(""+count[1]);
            Brazil.setText(""+count[2]);
            Aus.setText(""+count[3]);

            String[] name = DashbordModel.setName();
            String[] ads = DashbordModel.setLoc();
            Name1.setText(name[0]);
            Loc1.setText(ads[0]);
            Name2.setText(name[1]);
            Loc2.setText(ads[1]);
            Name3.setText(name[2]);
            loc3.setText(ads[2]);
            Name4.setText(name[3]);
            loc4.setText(ads[3]);
            Name5.setText(name[4]);
            Loc5.setText(ads[4]);
    }
    @FXML
    private Button exitbtn;

    String Name;

    public void initData(String name) {
        this.Name = Name;
    }

    public void exitbtnOnActhion(ActionEvent actionEvent) {
        System.exit(0);
    }
    private void openWebsite(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void Hmore(ActionEvent actionEvent) {
        openWebsite("https://www.lonelyplanet.com/articles/best-hikes-in-sri-lanka");
    }

    public void Cmore(ActionEvent actionEvent) {
        openWebsite("https://dayouting.lk/visit/Camping-Places");
    }

    public void Fmore(ActionEvent actionEvent) {
        openWebsite("https://www.getyourguide.com/southern-province-sri-lanka-l2095/jungle-tours-tc190/");
    }

    public void ViewAllbtnOnActhion(ActionEvent actionEvent) throws IOException {

        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/MemberForm.fxml"));
        MainDashbordFormController.pane.getChildren().clear();
        MainDashbordFormController.pane.getChildren().add(parent);
    }

    /*-----DATE AND TIME GENERATE------*/
    public String timeNow() {
        SimpleDateFormat dateFormat=new SimpleDateFormat("hh:mm:ss");
        //System.out.println(dateFormat.format(new Date()));
        return dateFormat.format(new Date()) ;
    }
    private void generateRealTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm:ss");
        lblDate.setText(LocalDate.now().toString());
        Timeline timeline = new Timeline(new KeyFrame(javafx.util.Duration.ZERO, e -> {

            lbltime.setText(timeNow());
            // lblTime.setText(LocalDateTime.now().format(formatter));

        }),
                new KeyFrame(Duration.seconds(1)));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    @FXML
    private Label vehicale;

    @FXML
    private Label Emp;

    void emp() throws SQLException {
        var Model = new DashbordModel();
        int emp = Model.getEmpCount();
        Emp.setText(""+emp);
        vehicale();
    }
    void vehicale() throws SQLException {
        var Model = new DashbordModel();
        int vehicale = Model.getVehicaleC();
        this.vehicale.setText(""+vehicale);
    }

    public void searchbarOnActhion(ActionEvent actionEvent) {
        if (!search.getText().isEmpty()) {
            String substring = search.getText().substring(0, 2);

            try {
                String id = search.getText();
                FXMLLoader fxmlLoader;
                AnchorPane newScene;
                switch (substring) {
                    case "E0":
                        EmoloyeeManageFormController.Id = search.getText();
                        fxmlLoader = new FXMLLoader(getClass().getResource("/View/EmoloyeeManageForm.fxml"));
                        newScene = fxmlLoader.load();
                        MainDashbordFormController.pane.getChildren().clear();
                        MainDashbordFormController.pane.getChildren().setAll(newScene);
                        break;
                    case "R0":
                        AddRoomFormController.Id = search.getText();
                        fxmlLoader = new FXMLLoader(getClass().getResource("/View/AddRoom.fxml"));
                        newScene = fxmlLoader.load();
                        MainDashbordFormController.pane.getChildren().clear();
                        MainDashbordFormController.pane.getChildren().setAll(newScene);
                        break;
                    case "V0":
                        CarManageFormController.Id = id;
                        fxmlLoader = new FXMLLoader(getClass().getResource("/View/CarManage.fxml"));
                        newScene = fxmlLoader.load();
                        MainDashbordFormController.pane.getChildren().clear();
                        MainDashbordFormController.pane.getChildren().setAll(newScene);
                        break;
                    case "B0":
                        BookingFormController.Id = id;
                        fxmlLoader = new FXMLLoader(getClass().getResource("/View/BookingForm.fxml"));
                        newScene = fxmlLoader.load();
                        MainDashbordFormController.pane.getChildren().clear();
                        MainDashbordFormController.pane.getChildren().setAll(newScene);
                        break;

                    case "M0":
                        MemberFormController.Id = id;
                        fxmlLoader = new FXMLLoader(getClass().getResource("/View/MemberForm.fxml"));
                        newScene = fxmlLoader.load();
                        MainDashbordFormController.pane.getChildren().clear();
                        MainDashbordFormController.pane.getChildren().setAll(newScene);
                        break;

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }
}
