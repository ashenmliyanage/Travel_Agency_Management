package lk.ijse.model;

import javafx.scene.control.Alert;
import lk.ijse.DB.DBConnection;
import lk.ijse.dto.PaymentDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PaymentModel {
    public ArrayList<PaymentDTO> LoadTable() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT * FROM payment";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        PaymentDTO dto = null;
        ArrayList<PaymentDTO> arrayList = new ArrayList<>();
        while (resultSet.next()){
            String pay_id = resultSet.getString(1);
            String memberId = resultSet.getString(2);
            String Name = getName(memberId);
            String booking_id = resultSet.getString(3);
            String Date = resultSet.getString(4);
            String total = resultSet.getString(5);

            dto = new PaymentDTO(booking_id,Date,Name,total);
            arrayList.add(dto);
        }
        return arrayList;
    }
    public String getName(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT name FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);
        ResultSet resultSet = pstm.executeQuery();
        String name = null;
        if (resultSet.next()){
            name = resultSet.getString(1);
        }
        if (name == null){
            return "Cannot find";
        }
        else {
            return name;
        }
    }

    public int setId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT Pay_id FROM payment ORDER BY Pay_id DESC LIMIT 1";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();
        String lastId = null;
        if (resultSet.next()){
            lastId = resultSet.getString(1);
        }
        String[] split = lastId.split("P00");
        int index = Integer.parseInt(split[1]);

        return index++;
    }

    public void setData(PaymentDTO paymentDTO) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO payment VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        //int x = count();
//        while (true){
//            try {
//                pstm.setString(1,"P0"+x);
//                break;
//            } catch (SQLException e) {
//                ++x;
//            }
//        }
        int x = setId();
        System.out.println("P00"+x);
        pstm.setString(1,"P00"+ ++x);
        pstm.setString(2,paymentDTO.getName());
        pstm.setString(3,paymentDTO.getBookingId());
        pstm.setString(4,paymentDTO.getDate());
        pstm.setString(5,paymentDTO.getTotal());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(new Date());
        pstm.setString(6,format);

        boolean isSave = pstm.executeUpdate() > 0;
        if (isSave){
            new Alert(Alert.AlertType.INFORMATION,"All save are Successful").show();
        }
        else{new Alert(Alert.AlertType.INFORMATION,"All save are not Successful").show();
        }
    }
    int count() throws SQLException {
       Connection connection = DBConnection.getInstance().getConnection();
       String sql = "SELECT COUNT(*) FROM payment";
       PreparedStatement pstm = connection.prepareStatement(sql);
       ResultSet resultSet = pstm.executeQuery();
       int x = 0;
       if (resultSet.next()){
           x = resultSet.getInt(1);
       }
       return x;
    }
    public ArrayList<Integer> chart() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT bDate FROM payment ORDER BY bDate";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        String [] dates = new String[count()];
        ArrayList<Integer> list = new ArrayList<>();
        int i = 0;
        while (resultSet.next()){
            dates[i++] = resultSet.getString(1);
        }
        i=0;
        Map<String, Integer> valueCounts = countEqualValues(dates);
        for (Map.Entry<String, Integer> entry : valueCounts.entrySet()) {
            System.out.println();
            System.out.println("Value: " + entry.getKey() + ", Count: " + entry.getValue());
            list.add(entry.getValue());
            System.out.println(list.get(i++));
        }
        return list;
    }

    private static Map<String, Integer> countEqualValues(String[] strings) {
        Map<String, Integer> valueCounts = new HashMap<>();

        for (String str : strings) {
            // Increment the count for the current value in the map
            valueCounts.put(str, valueCounts.getOrDefault(str, 0) + 1);
        }

        return valueCounts;
    }

    public double getTotal() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT total FROM payment";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        double total = 0;
        while (resultSet.next()){
            total+= resultSet.getDouble(1);
        }
        return total;
    }

    public boolean delete(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "DELETE FROM payment WHERE booking_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,id);

        return preparedStatement.executeUpdate() > 0;
    }

    public int CusCount() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM member";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        int count = 0 ;
        if (resultSet.next()){
            count = resultSet.getInt(1);
        }
        return count;
    }
}
