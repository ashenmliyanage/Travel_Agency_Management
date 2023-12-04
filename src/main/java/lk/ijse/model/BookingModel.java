package lk.ijse.model;

import javafx.scene.control.Alert;
import lk.ijse.Conroller.BookingFormController;
import lk.ijse.DB.DBConnection;
import lk.ijse.dto.BookingDto;
import lk.ijse.dto.VehicaleManageDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingModel {
    public static int getRowCounnt() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM booking";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int count = 0;
        if (resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count;
    }

    public static String VehicalCost;
    public static String HotelCost;

    public double getTotal(double totalCost, String hotel_id, String hHour, String car, String hCar) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT price FROM room WHERE room_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,hotel_id);

        ResultSet resultSet = pstm.executeQuery();

        double hotelprice= 0;
        try {
            if (resultSet.next()){
                hotelprice = resultSet.getDouble(1);
            }
            System.out.println("Ho - "+hotelprice);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }

        if(hotelprice == 0){
            new Alert(Alert.AlertType.INFORMATION,"cant find Room").show();
            return 0;
        }

        sql = "SELECT price FROM vehicle WHERE vehicle_id = ?";
        PreparedStatement pstm1 = connection.prepareStatement(sql);
        pstm1.setString(1,car);

        double carPrice = 0;

        resultSet = pstm1.executeQuery();
        if (resultSet.next()){
            carPrice = resultSet.getDouble(1);
        }
        System.out.println("car - "+carPrice);
        if (carPrice == 0){
            new Alert(Alert.AlertType.INFORMATION,"Cant find vehicle").show();
            return 0;
        }

        totalCost+=(carPrice*Double.parseDouble(hCar))+(hotelprice*Double.parseDouble(hHour));

        VehicalCost = ""+carPrice*Double.parseDouble(hCar);
        HotelCost = ""+hotelprice*Double.parseDouble(hHour);
        return totalCost;
    }
    //moo1
    public String getName(String text) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT name FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);

        ResultSet resultSet = pstm.executeQuery();

        String name = null;

        if (resultSet.next()){
            name = resultSet.getString(1);
        }
        return name;
    }

    public boolean Book(BookingDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String Sql = "INSERT INTO booking VALUES(?, ?, ?, ?, ?, ? ,? , ?)";
        PreparedStatement pstm = connection.prepareStatement(Sql);
       try {
           pstm.setString(1,dto.getId());
       } catch (SQLException e) {
           pstm.setString(1,"BOO"+ ++BookingFormController.count);
       }
        pstm.setString(2,dto.getDate());
        pstm.setString(3,dto.getType());
        pstm.setString(4,dto.getHotel_Id());
        pstm.setString(5, String.valueOf(dto.getHHotel()));
        pstm.setString(6,dto.getVehicle_id());
        pstm.setString(7, String.valueOf(dto.getHVehicle()));
        pstm.setString(8, String.valueOf(dto.getTotal()));

        return pstm.executeUpdate() > 0;
    }

    public String Check(String text) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT Room_id FROM room_manage WHERE Room_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);

        ResultSet resultSet = pstm.executeQuery();
        String name = null;
        if (resultSet.next()){
            name = resultSet.getString(1);
        }

        return name;
    }

    public String Check2(String text) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT v_id FROM vehicale_manage WHERE v_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);

        ResultSet resultSet = pstm.executeQuery();

        String name = null;
        if (resultSet.next()){
            name = resultSet.getString(1);
        }
        return name;
    }

    public boolean getSave(VehicaleManageDto vehicalDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO vehicale_manage VALUES(?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,vehicalDto.getV_id());
        pstm.setString(2,vehicalDto.getMember_id());

        boolean isSave = pstm.executeUpdate() > 0;

        return isSave;
    }

    public String getMail(String text) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT mail FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);

        ResultSet resultSet = pstm.executeQuery();

        String mail = null;

        if (resultSet.next()){
            mail = resultSet.getString(1);
        }
        System.out.println(mail);

        return mail;
    }

    public List<String> search(String text) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM booking WHERE booking_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);
        ResultSet resultSet = pstm.executeQuery();
        List<String> dto = new ArrayList<>();
        if(resultSet.next()){
            dto.add(getId(resultSet.getString(1)));
            dto.add(getMName(dto.get(0)));
            dto.add(resultSet.getString(2));
            dto.add(resultSet.getString(3));
            dto.add(resultSet.getString(4));
            dto.add(resultSet.getString(5));
            dto.add(resultSet.getString(6));
            dto.add(resultSet.getString(7));
            dto.add(resultSet.getString(8));
        }
        return dto;
    }

    private String getMName(String s) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT name FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,s);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }

    private String getId(String string) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT member_id FROM payment WHERE booking_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,string);
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            return resultSet.getString(1);
        }
        return null;
    }
}
