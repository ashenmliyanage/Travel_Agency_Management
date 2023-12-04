package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MainDashbordFormController implements Initializable{

    @FXML
    private AnchorPane ChangePane;

    @FXML
    private ImageView bCir;

    @FXML
    private ImageView cCir;

    @FXML
    private ImageView dCir;

    @FXML
    private ImageView eCircale;

    @FXML
    private ImageView hCir;

    @FXML
    private ImageView mCir;

    @FXML
    private ImageView pCir;

    @FXML
    private ImageView employee;

    @FXML
    private ImageView Dashbord;

    @FXML
    private ImageView booking;

    @FXML
    private ImageView hotel;

    @FXML
    private AnchorPane MainPane;

    @FXML
    private ImageView car;

    @FXML
    private ImageView member;

    @FXML
    private ImageView Payment;



    public void OnDashBord(MouseEvent mouseEvent) {
        dCir.setVisible(true);
    }

    public void ExitDashbord(MouseEvent mouseEvent) {
        dCir.setVisible(false);
    }

    public void OnBooking(MouseEvent mouseEvent) {
        bCir.setVisible(true);
    }

    public void ExitBooking(MouseEvent mouseEvent) {
        bCir.setVisible(false);
    }

    public void ExitHotel(MouseEvent mouseEvent) {
        hCir.setVisible(false);
    }

    public void OnHotel(MouseEvent mouseEvent) {
        hCir.setVisible(true);
    }

    public void OnCar(MouseEvent mouseEvent) {
        cCir.setVisible(true);
    }

    public void ExitCar(MouseEvent mouseEvent) {
        cCir.setVisible(false);
    }

    public void OnMember(MouseEvent mouseEvent) {
        mCir.setVisible(true);
    }

    public void ExitMember(MouseEvent mouseEvent) {
        mCir.setVisible(false);
    }

    public void OnPay(MouseEvent mouseEvent) {
        pCir.setVisible(true);
    }

    public void exitPay(MouseEvent mouseEvent) {
        pCir.setVisible(false);
    }

    public void OnMouce(MouseEvent mouseEvent) {
        eCircale.setVisible(true);
    }

    public void ExitMouse(MouseEvent mouseEvent) {
        eCircale.setVisible(false);
    }

    public  static AnchorPane pane;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane = ChangePane;
        eCircale.setVisible(false);
        dCir.setVisible(true);
        bCir.setVisible(false);
        hCir.setVisible(false);
        cCir.setVisible(false);
        mCir.setVisible(false);
        pCir.setVisible(false);
        employee.setVisible(false);
        Dashbord.setVisible(true);
        booking.setVisible(false);
        hotel.setVisible(false);
        car.setVisible(false);
        member.setVisible(false);
        Payment.setVisible(false);
//        Parent parent = null;
//        try {
//            parent = FXMLLoader.load(this.getClass().getResource("/View/Dashbord.fxml"));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        this.ChangePane.getChildren().clear();
//        this.ChangePane.getChildren().add(parent);

        try {
            Dashbord();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void empBtnOnActhion(ActionEvent actionEvent) throws IOException {
        Payment.setVisible(false);
        booking.setVisible(false);
        dCir.setVisible(false);
        eCircale.setVisible(true);
        employee.setVisible(true);
        Dashbord.setVisible(false);
        hotel.setVisible(false);
        car.setVisible(false);
        member.setVisible(false);
        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/EmoloyeeManageForm.fxml"));
        this.ChangePane.getChildren().clear();
        this.ChangePane.getChildren().add(parent);
    }

    Thread continey = new Thread(() ->{
        while (true){
            dCir.setVisible(true);
            if (shouldExit()) {
                break;
            }
        }
    });
    private static boolean shouldExit() {
        // Your exit condition logic here
        return true; // Change this condition as needed
    }

    public void DashbordbtnOnActhion(ActionEvent actionEvent) throws IOException {
        Payment.setVisible(false);
        booking.setVisible(false);
        employee.setVisible(false);
        Dashbord.setVisible(true);
        hotel.setVisible(false);
        car.setVisible(false);
        dCir.setVisible(false);
        member.setVisible(false);
        Dashbord();
    }

    public void Dashbord() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/Dashbord.fxml"));
        AnchorPane newScene = fxmlLoader.load();
        ((DashbordConroller)(fxmlLoader.getController())).initData(Name);
        this.ChangePane.getChildren().clear();
        this.ChangePane.getChildren().setAll(newScene);
    }

    public void bookngbtnActhon(ActionEvent actionEvent) throws IOException {
        Payment.setVisible(false);
        booking.setVisible(true);
        employee.setVisible(false);
        Dashbord.setVisible(false);
        hotel.setVisible(false);
        car.setVisible(false);
        dCir.setVisible(false);
        member.setVisible(false);
        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/BookingForm.fxml"));
        this.ChangePane.getChildren().clear();
        this.ChangePane.getChildren().add(parent);
    }
    public void hotelbtnOnActhion(ActionEvent actionEvent) throws IOException {
        Payment.setVisible(false);
        Dashbord.setVisible(false);
        booking.setVisible(false);
        employee.setVisible(false);
        Dashbord.setVisible(false);
        hotel.setVisible(true);
        dCir.setVisible(false);
        car.setVisible(false);
        member.setVisible(false);
//        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/HotelForm.fxml"));
//        this.ChangePane.getChildren().clear();
//        this.ChangePane.getChildren().add(parent);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/HotelForm.fxml"));
        AnchorPane newScene = fxmlLoader.load();
        ((HotelFormController)(fxmlLoader.getController())).initData(ChangePane);
        this.ChangePane.getChildren().clear();
        this.ChangePane.getChildren().setAll(newScene);
    }

    public void carOnActhion(ActionEvent actionEvent) throws IOException {
        Payment.setVisible(false);
        Dashbord.setVisible(false);
        booking.setVisible(false);
        employee.setVisible(false);
        Dashbord.setVisible(false);
        hotel.setVisible(false);
        dCir.setVisible(false);
        car.setVisible(true);
        member.setVisible(false);
        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/CarManage.fxml"));
        this.ChangePane.getChildren().clear();
        this.ChangePane.getChildren().add(parent);
    }

    public void memberBtnOnActhion(ActionEvent actionEvent) throws IOException {
        Payment.setVisible(false);
        Dashbord.setVisible(false);
        booking.setVisible(false);
        employee.setVisible(false);
        Dashbord.setVisible(false);
        hotel.setVisible(false);
        dCir.setVisible(false);
        car.setVisible(false);
        member.setVisible(true);
        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/MemberForm.fxml"));
        this.ChangePane.getChildren().clear();
        this.ChangePane.getChildren().add(parent);
    }
    String Name;
    public void initData(String Name) {
        this.Name = Name;
    }

    public void paymentOnClick(ActionEvent actionEvent) throws IOException {
        Payment.setVisible(true);
        Dashbord.setVisible(false);
        booking.setVisible(false);
        employee.setVisible(false);
        Dashbord.setVisible(false);
        hotel.setVisible(false);
        dCir.setVisible(false);
        car.setVisible(false);
        member.setVisible(false);
        Parent parent = FXMLLoader.load(this.getClass().getResource("/View/PaymentForm.fxml"));
        this.ChangePane.getChildren().clear();
        this.ChangePane.getChildren().add(parent);
    }
}
