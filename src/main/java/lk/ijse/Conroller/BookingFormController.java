package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import lk.ijse.Mail.RoomMail;
import lk.ijse.Mail.TotalMail;
import lk.ijse.Mail.VehicallMail;
import lk.ijse.dto.BookingDto;
import lk.ijse.dto.PaymentDTO;
import lk.ijse.dto.RoomManageDto;
import lk.ijse.dto.VehicaleManageDto;
import lk.ijse.model.*;
import lombok.SneakyThrows;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Pattern;

public class BookingFormController implements Initializable {
    public static String Id;
    @FXML
    private DatePicker Date;

    @FXML
    public Label H_cost;

    @FXML
    private TextField ID;

    @FXML
    private TextField Name;

    @FXML
    private Label Other_cost;

    @FXML
    private TextField R_hour;

    @FXML
    private TextField Room;

    @FXML
    private Label Total;

    @FXML
    public Label V_cost;

    @FXML
    private TextField V_hour;

    @FXML
    private TextField search;

    @FXML
    private TextField vehicle;

    private double TotalCost;

    String type = "0";

    @FXML
    private Button campbtn;

    @FXML
    private Button forestbtn;

    @FXML
    private Button hikebtn;

    private String SetPID;

    public static int  count = 0;

    public BookingFormController() throws SQLException {
    }
    String Types = "";

    @FXML
    void CampbtnOnActhion(ActionEvent event) {
        campbtn.setStyle("-fx-background-color: #51ADE5");
        TotalCost += 10000;
        type = "10000";
        Types = "Camp";
    }

    @FXML
    void CartbtnOnActhion(ActionEvent event) throws SQLException {

        try {


            if (Validate()) {
                Other_cost.setText("1000");

                var mdel = new BookingModel();
                double total = mdel.getTotal(TotalCost, Room.getText(), R_hour.getText(), vehicle.getText(), V_hour.getText());
                Total.setText("" + total);
                V_cost.setText(BookingModel.VehicalCost);
                H_cost.setText(BookingModel.HotelCost);
                //BookingDto dto = new BookingDto(ID.getText(),Date.getValue(),type,R_hour.getText(),V_hour.getText());
                System.out.println(total);
                try {
                    System.out.println("try1");
                    if (total > 0) {
                        int Vehicle = Integer.parseInt(V_hour.getText());
                        int Hotel = Integer.parseInt(R_hour.getText());
                        String DATE = String.valueOf(Date.getValue());
                        SetName();
                        BookingDto dto = new BookingDto(SetPID, DATE, Types, Room.getText(), Hotel, vehicle.getText(), Vehicle, TotalCost);

                        try {
                            System.out.println("try2");
                            String Check = mdel.Check(Room.getText());

                            if (Check == null) {
                                try {
                                    System.out.println("try3");
                                    String Check2 = mdel.Check2(vehicle.getText());
                                    System.out.println(Check2);

                                    if (Check2 == null) {
                                        try {
                                            boolean isSave = mdel.Book(dto);
                                            if (isSave) {
                                                new Alert(Alert.AlertType.INFORMATION, "Booking add succcessful").show();
                                                // room manage ekai vehicale mannage ekai add karanna ona data pass wena widiyata
                                                RoomManageDto roomManageDto = new RoomManageDto(Room.getText(), SetPID);
                                                VehicaleManageDto vehicalDto = new VehicaleManageDto(vehicle.getText(), SetPID);

                                                var model = new RoomManageModel();
                                                String date = String.valueOf(Date.getValue());
                                                PaymentDTO paymentDTO = new PaymentDTO(SetPID, DATE, ID.getText(), Total.getText());
                                                boolean issave = model.getSave(roomManageDto);
                                                boolean isVSaveave = mdel.getSave(vehicalDto);

                                                var model2 = new PaymentModel();
                                                model2.setData(paymentDTO);
                                                if (issave) {
                                                    System.out.println("save in management");
                                                    Totalsed(dto);
                                                }
                                            } else {
                                                new Alert(Alert.AlertType.INFORMATION, "Booking not successful").show();
                                            }
                                        } catch (SQLException e) {
                                            // new SQLException(e)
                                            e.printStackTrace();
                                        }
                                    } else {
                                        new Alert(Alert.AlertType.INFORMATION, "Vehicle is already booked. You must send a Mail to Customer").show();
                                    }
                                } catch (SQLException e) {
                                    throw new SQLException(e);
                                }
                            } else {
                                new Alert(Alert.AlertType.INFORMATION, "Room is already booked. You must send a Mail to Customer").show();
                            }
                        } catch (SQLException e) {
                            throw new SQLException(e);
                        }
                    }
                } catch (NumberFormatException e) {
                    new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
                }
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Please filling the all blanks / only Id").show();
        }
    }

    @FXML
    void IdOnActhion(ActionEvent event) throws SQLException {
        var model = new BookingModel();

        try {
            Name.setText(model.getName(ID.getText()));
            System.out.println(Name.getText());
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Can't find member\nAdd a member to system first");
        }
    }

    @FXML
    void ForestbtnOnActhion(ActionEvent event) {
        forestbtn.setStyle("-fx-background-color: #51ADE5");
        TotalCost += 10000;
        type = "10000";
        Types = "Forest";
    }
    void SetName() throws SQLException { }
    @FXML
    void R_sendbtnOnActhion(ActionEvent event) throws Exception {
        var model = new BookingModel();

        try {
            String mail = model.getMail(ID.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Send Mail");
            alert.setContentText("Are you sure you want to send a Mail?");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                RoomMail.setEmailCom(mail,Name.getText());
                new Alert(Alert.AlertType.INFORMATION,"Mail send").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void V_sendbtnOnActhion(ActionEvent event) {
        var model = new BookingModel();

        try {
            String mail = model.getMail(ID.getText());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Send Mail");
            alert.setContentText("Are you sure you want to send a Mail?");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            if (result == ButtonType.OK) {
                VehicallMail.setEmailCom(mail,Name.getText());
                new Alert(Alert.AlertType.INFORMATION,"Mail send").show();
            }
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    void Totalsed(BookingDto dto){
        var model = new BookingModel();

        try {
            String mail = model.getMail(ID.getText());
            TotalMail.setEmailCom(mail,dto);
        } catch (Exception e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    @FXML
    void hikebtnOnActhion(ActionEvent event) {

        hikebtn.setStyle("-fx-background-color: #51ADE5");
        TotalCost += 10000;
        type = "10000";
        Types = "Hike";

    }


    @FXML
    void searchOnActhion(ActionEvent event) throws SQLException {
        var model = new BookingModel();
        try {
            List<String> dto = model.search(search.getText());
            System.out.println(dto);
            ID.setText(dto.get(0));
            Name.setText(dto.get(1));
            Date.setValue(LocalDate.parse(dto.get(2)));
            Room.setText(dto.get(4));
            R_hour.setText(dto.get(5));
            vehicle.setText(dto.get(6));
            V_hour.setText(dto.get(7));
            setOther(dto.get(3));
            Total.setText(dto.get(8));
            H_cost.setText("");
            V_cost.setText("");
        } catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION,"Can't Find Booking").show();
        }
    }

    private void setOther(String s) {
        System.out.println(s);
        if (s.equals("Hike")){
            hikebtn.setStyle("-fx-background-color: #51ADE5");
            campbtn.setStyle("-fx-background-color: #FFFF");
            forestbtn.setStyle("-fx-background-color: #FFFF");
        } else if (s.equals("Camp")) {
            hikebtn.setStyle("-fx-background-color: #FFFF");
            campbtn.setStyle("-fx-background-color: #51ADE5");
            forestbtn.setStyle("-fx-background-color: #FFFF");
        } else if (s.equals("Forest")) {
            hikebtn.setStyle("-fx-background-color: #FFFF");
            campbtn.setStyle("-fx-background-color: #FFFF");
            forestbtn.setStyle("-fx-background-color: #51ADE5");
        }
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        first();
        //---------------------------------------------ID auto ------------------------------------------------------------------------

        AutoID = TextFields.bindAutoCompletion(ID,IdSet);

        ID.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        autoCompletionLearnWordIDS(ID.getText().trim());
                        break;
                    default:
                        break;
                }
            }
        });

        //---------------------------------------------VID auto ------------------------------------------------------------------------

        AutoVid = TextFields.bindAutoCompletion(vehicle,vIdSet);

        vehicle.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        autoCompletionLearnWordVid(vehicle.getText().trim());
                        break;
                    default:
                        break;
                }
            }
        });

        //---------------------------------------------VID auto ------------------------------------------------------------------------

        AutoVid = TextFields.bindAutoCompletion(vehicle,vIdSet);

        ID.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        autoCompletionLearnWordVid(vehicle.getText().trim());
                        break;
                    default:
                        break;
                }
            }
        });

        //-----------------------------------------------room---------------------------------------------------------------

        AutoRID = TextFields.bindAutoCompletion(Room,RIdSet);

        Room.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        autoCompletionLearnWordRoom(Room.getText().trim());
                        break;
                    default:
                        break;
                }
            }
        });

        count = BookingModel.getRowCounnt();
        SetPID = "B0"+count;


        Other_cost.setText(type);
        Total.setText("0");
        H_cost.setText("0");
        V_cost.setText("0");
    }
    private AutoCompletionBinding<String> AutoID;
    private String[] ides = MemberModel.getAllIds();

    private Set<String> IdSet = new HashSet<>(Arrays.asList(ides));

    private void autoCompletionLearnWordIDS(String trim) {
        IdSet.add(trim);

        if (AutoID != null){
            AutoID.dispose();
        }
        AutoID = TextFields.bindAutoCompletion(ID, IdSet);
    }
//------------------------------------vehicale------------------------------------------
    private AutoCompletionBinding<String> AutoVid;
    private String[] Vid = VehicaleModel.getAllIds();

    private Set<String> vIdSet = new HashSet<>(Arrays.asList(Vid));

    private void autoCompletionLearnWordVid(String trim) {
        vIdSet.add(trim);

        if (AutoVid != null){
            AutoVid.dispose();
        }
        AutoVid = TextFields.bindAutoCompletion(vehicle, vIdSet);
    }
//--------------------------------------------------------Room _----------------------------------------------------

    private AutoCompletionBinding<String> AutoRID;
    private String[] Rides = RoomModel.getAllIds();

    private Set<String> RIdSet = new HashSet<>(Arrays.asList(Rides));

    private void autoCompletionLearnWordRoom(String trim) {
        RIdSet.add(trim);

        if (AutoRID != null){
            AutoRID.dispose();
        }
        AutoRID = TextFields.bindAutoCompletion(Room, RIdSet);
    }

    boolean Validate(){
        if (isValidDate()){

            if (isValidHhour()){

                if (isValidVhour()){
                    return true;
                }
                else {
                    new Alert(Alert.AlertType.INFORMATION,"Please enter only hours for vehicle").show();
                    return false;
                }
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"Please enter only hours for Hotel").show();
                return false;
            }
        }
        else {
            new Alert(Alert.AlertType.INFORMATION,"Please enter only valid Date").show();
            return false;
        }
    }

    private boolean isValidDate() {
        String input = String.valueOf(Date.getValue());
        // Define the regex pattern for a date in the format "YYYY-MM-DD"
        String regex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

        // Check if the input matches the regex pattern
        return Pattern.matches(regex, input);
    }

    private boolean isValidHhour() {
        String input = R_hour.getText();
        // Define the regex pattern for a date in the format "DD/MM/YYYY"
        String regex = "\\d";

        // Check if the input matches the regex pattern
        return Pattern.matches(regex, input);
    }

    private boolean isValidVhour() {
        String input = V_hour.getText();
        // Define the regex pattern for a date in the format "DD/MM/YYYY"
        String regex = "\\d";

        // Check if the input matches the regex pattern
        return Pattern.matches(regex, input);
    }
    @SneakyThrows
    void first(){
        if (Id != null){
            search.setText(Id);
            searchOnActhion(null);
            Id = null;
        }
    }
}
