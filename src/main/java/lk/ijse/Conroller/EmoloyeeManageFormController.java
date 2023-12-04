package lk.ijse.Conroller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import lk.ijse.DB.DBConnection;
import lk.ijse.QrCode.ReadQrCodeForEmployee;
import lk.ijse.dto.employeedto;
import lk.ijse.model.EmployeeModel;
import lombok.SneakyThrows;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.controlsfx.control.textfield.AutoCompletionBinding;
import org.controlsfx.control.textfield.TextFields;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmoloyeeManageFormController implements Initializable {

    @FXML
    private TextField Address;

    @FXML
    private TextField Age;

    @FXML
    private TextField ID;

    @FXML
    private TextField Name;

    @FXML
    private TextField Number;

    @FXML
    private TextField birthday;

    @FXML
    private TextField job_type;

    @FXML
    private TextField salary;

    @FXML
    private DatePicker new_birthday;

    @FXML
    private TextField search;

    public EmoloyeeManageFormController() throws SQLException {}

//-------------------------------------- for Employee Auto ------------------------------------------------------
    private AutoCompletionBinding<String> autoComplete;
    private String[] AutoData = {"Manager","Servant","Driver"};
    private Set<String> suggest =  new HashSet<>(Arrays.asList(AutoData));

//----------------------------------For Location Auto -----------------------------------------------------------

    private AutoCompletionBinding<String> LocationAutoFill;
    private String[] AutoLoc = {"Galle","Colombo","Hambanthota","Mathara"};
    private Set<String> locSuggest = new HashSet<>(Arrays.asList(AutoLoc));

//------------------------------For IDS Auto---------------------------------------------------------------------

    private AutoCompletionBinding<String> IDSAutoFill;
    private String[] IDS = EmployeeModel.getAllID();
    private Set<String> IdsSuggest = new HashSet<>(Arrays.asList(IDS));

    @FXML
    void addbtnOnActhion(ActionEvent event) throws SQLException, IOException {

        if (validNumber()) {

            try {

                String id = ID.getText();
                String name = Name.getText();
                String type = job_type.getText();
                String address = Address.getText();
                int number = Integer.parseInt(Number.getText());
                int age = Integer.parseInt(Age.getText());
                double salary = Double.parseDouble(this.salary.getText());
                String birthday = String.valueOf(new_birthday.getValue());
                System.out.println(birthday);
                var model = new EmployeeModel();

                try {
                    employeedto dto = model.getIDs(ID.getText());

                    if (dto == null) {
                        var add = new employeedto(id, name, address, age, type, birthday, number, salary);

                        var mode = new EmployeeModel();

                        try {
                            boolean isSave = mode.saveEmployee(add);
                            if (isSave) {
//                                addFormContoller popAdd = new addFormContoller();
//                                popAdd.okey();
                                new Alert(Alert.AlertType.INFORMATION,"Save Successful").show();
                                ReadQrCodeForEmployee.CreateQr(Name.getText(), ID.getText());
                                clearFields();
                            }
                        } catch (SQLException e) {
                            new Alert(Alert.AlertType.INFORMATION, "Added is not suspenseful \n Fill the all blanks").show();
                        }
//                        catch (IOExcept ion e) {
//                            new Alert(Alert.AlertType.ERROR, "Added is not suspenseful \n Fill the all blanks").show();
//                        }
                    } else {
                        new Alert(Alert.AlertType.INFORMATION, "Already Use").show();
                    }
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Fill the All tabs").show();
                }

            } catch (NumberFormatException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }

    }
    void clearFields() {

        ID.clear();
        Name.clear();
        birthday.clear();
        Address.clear();
        Age.clear();
        Number.clear();
        salary.clear();
        job_type.clear();
        new_birthday.setValue(null);
    }

    @SneakyThrows
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initData();
        ID.setText(EmployeeModel.genarateId());
        ID.setEditable(false);


//---------------------------------------------Job type ------------------------------------------------------------

        TextFields.bindAutoCompletion(job_type,"Manager","Servant","Driver");
        autoComplete = TextFields.bindAutoCompletion(job_type,suggest);
        job_type.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        autoCompletionLearnWord(job_type.getText().trim());
                        break;
                    default:
                        break;
                }
            }
        });

//------------------------------------------------- Location  ---------------------------------------------------------------------

        TextFields.bindAutoCompletion(Address,"Galle","Mathaara","Hambanthota","Colombo");
        LocationAutoFill = TextFields.bindAutoCompletion(Address,locSuggest);

        Address.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                switch (keyEvent.getCode()){
                    case ENTER:
                        autoCompletionLearnWordLOC(Address.getText().trim());
                        break;
                    default:
                        break;
                }
            }
        });

//------------------------------------------- Ids ----------------------------------------------------------------------------

        IDSAutoFill = TextFields.bindAutoCompletion(search,IdsSuggest);

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

    //------------------------------------JOB_TYPE---------------------------------------
    private void autoCompletionLearnWord(String trim) {
        suggest.add(trim);

        if (autoComplete != null){
            autoComplete.dispose();
        }
        autoComplete = TextFields.bindAutoCompletion(job_type, suggest);
    }

    //----------------------------------LOCATION-----------------------------------------
    private void autoCompletionLearnWordLOC(String trim) {
        locSuggest.add(trim);

        if (LocationAutoFill != null){
            LocationAutoFill.dispose();
        }
        LocationAutoFill = TextFields.bindAutoCompletion(Address, locSuggest);
    }
    //---------------------------------IDS-------------------------------------------

    private void autoCompletionLearnWordIDS(String trim){
        IdsSuggest.add(trim);

        if (IDSAutoFill != null){
            IDSAutoFill.dispose();
        }
        IDSAutoFill = TextFields.bindAutoCompletion(search, IdsSuggest);
    }

    @FXML
    void updteOnActhion(ActionEvent event) throws Exception {

        ID.setEditable(false);
        if (validNumber()) {
            var dto = new employeedto(ID.getText(), Name.getText(), Address.getText(), Integer.parseInt(Age.getText()), job_type.getText(), String.valueOf(new_birthday.getValue()), Integer.parseInt(Number.getText()), Double.parseDouble(salary.getText()));

            var model = new EmployeeModel();

            try {
                boolean isUpdate = model.update(dto);
                if (isUpdate) {
                    new Alert(Alert.AlertType.INFORMATION, "Update is Successful").show();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void searchOnActhion(ActionEvent event) throws SQLException {

        ID.setEditable(false);

        var model = new EmployeeModel();

        try {
            employeedto dto = model.serch(search.getText());

            if(dto != null){
                this.ID.setText(dto.getId());
                this.Name.setText(dto.getName());
                this.new_birthday.setValue(LocalDate.parse(dto.getBirthday()));
                this.Address.setText(dto.getAddress());
                this.Age.setText(String.valueOf(dto.getAge()));
                this.Number.setText(String.valueOf(dto.getContact()));
                this.job_type.setText(String.valueOf(dto.getSalary()));
                this.salary.setText(String.valueOf(dto.getSalary()));
            }
            else {
                new Alert(Alert.AlertType.INFORMATION,"Employee not found").show();
            }
            ID.setEditable(true);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    @FXML
    void deleteOnActhion(ActionEvent event) {

        ID.getText();

        var EmployeeModel = new EmployeeModel();
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Delete Employee");
            alert.setContentText("Are you sure you want to delete this Employee?");
            ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
            boolean isDelete = false;
            if (result == ButtonType.OK) {
                isDelete = EmployeeModel.delete(ID.getText());
            }
            if (isDelete) {
                new Alert(Alert.AlertType.INFORMATION,"Delete is Successful").show();
                clearFields();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    @FXML
    void QrbtnOnActhion(ActionEvent event) {
        var model = new EmployeeModel();
        try {
            employeedto dto = model.QrScan();
            if (dto != null) {
                this.ID.setText(dto.getId());
                this.Name.setText(dto.getName());
                this.new_birthday.setValue(LocalDate.parse(dto.getBirthday()));
                this.Address.setText(dto.getAddress());
                this.Age.setText(String.valueOf(dto.getAge()));
                this.Number.setText(String.valueOf(dto.getContact()));
                this.job_type.setText(String.valueOf(dto.getSalary()));
                this.salary.setText(String.valueOf(dto.getSalary()));
            }
            else{
                new Alert(Alert.AlertType.INFORMATION,"Not found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage());
        }
    }

    private boolean validNumber(){
         Pattern  p = Pattern.compile("[0-9]+");
        Matcher m  = p.matcher(Number.getText());
        if (m.find() && m.group().equals(Number.getText())){
            if (Number.getText().length() == 10) {
                Pattern loc = Pattern.compile("^[a-zA-Z0-9 ,.-]+$");
                Matcher matcher = loc.matcher(Address.getText());

                    if (matcher.find() && matcher.group().equals(Address.getText())){
                        if (isValidAge()) {

                            if (isValidSalary()){

                                if (isValidDate()){

                                    if (isValidName()){
                                        return true;
                                    }
                                    else {
                                        new Alert(Alert.AlertType.WARNING,"Please enter correct Name").show();
                                        return false;
                                    }
                                }
                                else {
                                    new Alert(Alert.AlertType.WARNING,"Please enter correct Date").show();
                                    return false;
                                }
                            }
                            else {
                                new Alert(Alert.AlertType.WARNING,"Please enter correct Salary").show();
                                return false;
                            }
                        }
                        else {
                            new Alert(Alert.AlertType.WARNING,"Please enter correct Age").show();
                            return false;
                        }
                    }else {
                        new Alert(Alert.AlertType.WARNING,"Please enter correct Address").show();
                        return false;
                    }
            }
            new Alert(Alert.AlertType.WARNING,"Please enter correct number").show();
            return false;
        }
        else {
            new Alert(Alert.AlertType.WARNING,"Please enter correct number").show();
            return false;
        }
    }

    private boolean isValidAge() {
        String input = Age.getText();
        // Define the regex pattern for a positive integer
        String regex = "^[1-9]\\d*$";

        // Specify the age criteria (for example, between 18 and 100)
        int minValidAge = 18;
        int maxValidAge = 100;

        // Check if the input matches the regex pattern and falls within the valid range
        return Pattern.matches(regex, input) && isInRange(Integer.parseInt(input), minValidAge, maxValidAge);
    }

    private static boolean isInRange(int value, int minValue, int maxValue) {
        return value >= minValue && value <= maxValue;
    }

    private boolean isValidSalary() {
        String input = this.salary.getText();
        // Define the regex pattern for a positive number (integer or decimal)
        String regex = "^[1-9]\\d*(\\.\\d+)?$";

        // Check if the input matches the regex pattern and falls within the valid range
        return Pattern.matches(regex, input);
    }

    private boolean isValidDate() {
        String input = String.valueOf(new_birthday.getValue());
        // Define the regex pattern for a date in the format "YYYY-MM-DD"
        String regex = "^(\\d{4})-(0[1-9]|1[0-2])-(0[1-9]|[12]\\d|3[01])$";

        // Check if the input matches the regex pattern
        return Pattern.matches(regex, input);
    }

    private boolean isValidName() {
        String input = Name.getText();
        // Define the regex pattern for a name
        String regex = "^[a-zA-Z]+(\\s[a-zA-Z]+)?(-[a-zA-Z]+)?$";

        // Check if the input matches the regex pattern
        return Pattern.matches(regex, input);
    }

    @FXML
    void eReportOnActhion(ActionEvent event) throws JRException, SQLException {
        generateReportInBackground("Employee.jrxml");
    }
    public void generateReportInBackground(String path) {
        Thread reportThread = new Thread(() -> {
            try {
                // Load the JasperReport template from the resources
                InputStream resourceAsStream = EmoloyeeManageFormController.class.getResourceAsStream("/Report/"+path);
                JasperDesign load = JRXmlLoader.load(resourceAsStream);
                JasperReport jasperReport = JasperCompileManager.compileReport(load);

                // Fill the JasperReport with data
                JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, DBConnection.getInstance().getConnection());

                // Display the JasperReport (Swing-related operation should be on the EDT)
                SwingUtilities.invokeLater(() -> JasperViewer.viewReport(jasperPrint, false));

            } catch (JRException | SQLException e) {
                e.printStackTrace();
            }
        });

        // Start the new thread
        reportThread.start();
    }

    @FXML
    void servantReport(ActionEvent event) {
        generateReportInBackground("Servent.jrxml");
    }
    @FXML
    void driverReport(ActionEvent event) {
        generateReportInBackground("Driver.jrxml");
    }
    @FXML
    void ReportbtnOnActhion(ActionEvent event) throws JRException, SQLException {
        InputStream resourceAsStream = getClass().getResourceAsStream("/Report/one.jrxml");
        JasperDesign load = JRXmlLoader.load(resourceAsStream);
        JRDesignQuery jrDesignQuery = new JRDesignQuery();
        jrDesignQuery.setText("SELECT * FROM employee WHERE employee_id = "+"\""+ID.getText()+"\"");
        load.setQuery(jrDesignQuery);

        JasperReport jasperReport = JasperCompileManager.compileReport(load);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,null,DBConnection.getInstance().getConnection());
        JasperViewer.viewReport(jasperPrint,false);
    }

    boolean flag = true;
    public void editbtnOnActhion(ActionEvent actionEvent) {
        ID.setEditable(flag);
    }

    public static String Id;
    @SneakyThrows
    public void initData() {
        System.out.println(Id);
        if (this.Id != null){
            search.setText(Id);
            System.out.println(Id);
            searchOnActhion(null);
            Id = null;
        }
    }
}
