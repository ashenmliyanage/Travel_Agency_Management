package lk.ijse.Conroller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.model.PaymentModel;
import lombok.SneakyThrows;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PaymentFormController implements Initializable {
    public Label Total;
    @FXML
    private LineChart<?, ?> Chart;

    @FXML
    private ScrollPane SPn;

    @FXML
    private VBox Tablecolm;

    @FXML
    private Label customer;


    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTotal();
        custCount();
        var model = new PaymentModel();
        ArrayList<Integer> list = model.chart();
        XYChart.Series series = new XYChart.Series();
        Chart.setStyle("-fx-background-color: rgba(0,0,0,0)");

        //series.getData().add(new XYChart.Data("10", 7));

        try {
            for (int i = 0; i < 10; i++) {
                String index = ""+i;
                series.getData().add(new XYChart.Data(index,list.get(i)));
                System.out.println(list.get(i));
            }
        } catch (Exception e) {
           //throw new RuntimeException(e);
            System.out.println("add data more");
        }
        Chart.getData().add(series);
        loadtable();
        Tablecolm.setStyle("-fx-background-color: #8BB3F0;");
        SPn.setStyle("-fx-background-color: #8BB3F0;");
        Chart.setStyle("-fx-background-color: transparent;");

        Chart.lookup(".chart-plot-background").setStyle("-fx-background-color:transparent");
//        SPn.lookup(".chart-plot-background").setStyle("-fx-background-color:transparent");
//         Tablecolm.lookup(".chart-plot-background").setStyle("-fx-background-color:transparent");
    }

//    public void loadtable() throws SQLException, IOException {
//        var model = new  PaymentModel();
//        ArrayList<PaymentDTO> dt = model.LoadTable();
//        VBox vBox = new VBox();
//        vBox.setSpacing(4);
//        int arraySize = dt.size();
//        Node[] node = new Node[arraySize];
//        System.out.println(arraySize);
//
//        for (int i = 0; i < arraySize; i++) {
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/laodDataForm.fxml"));
//            LaodDataFormController load = new LaodDataFormController();
//            fxmlLoader.setController(load);
//            node [i] = fxmlLoader.load();
//            VBox.setMargin(node[i], new Insets(5,0,0,0));
//            vBox.getChildren().add(node[i]);
//
//            load.BookngId.setText(dt.get(i).getBookingId());
//            load.Date.setText(dt.get(i).getDate());
//            load.Name.setText(dt.get(i).getName());
//            load.Total.setText(dt.get(i).getTotal());
//        }
//        Tablecolm.getChildren().clear();
//        Tablecolm.getChildren().add(vBox);
//    }

    public void loadtable() throws SQLException, IOException {
        var model = new PaymentModel();
        ArrayList<PaymentDTO> dt = model.LoadTable();
        VBox vBox = new VBox();
        vBox.setSpacing(30);
        int arraySize = dt.size();
        Node[] node = new Node[arraySize];
        System.out.println(arraySize);

        for (int i = 0; i < arraySize; i++) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/laodDataForm.fxml"));
            // Load the FXML file and set the controller using FXMLLoader
            Node loadedNode = fxmlLoader.load();
            LaodDataFormController load = fxmlLoader.getController();
            VBox.setMargin(loadedNode, new Insets(5, 0, 0, 0));
            vBox.getChildren().add(loadedNode);

            // Populate data into the loaded controller
            load.BookngId.setText(dt.get(i).getBookingId());
            load.Date.setText(dt.get(i).getDate());
            load.Name.setText(dt.get(i).getName());
            load.Total.setText(dt.get(i).getTotal());
        }
        Tablecolm.getChildren().clear();
        Tablecolm.getChildren().add(vBox);
    }

    void setTotal() throws SQLException {
        var model = new PaymentModel();
        double total = model.getTotal();
        this.Total.setText(""+total);
    }

    void custCount() throws SQLException {
        var model = new PaymentModel();
        int count = model.CusCount();
        this.customer.setText(""+count);
    }

}
