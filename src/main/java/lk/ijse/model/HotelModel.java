package lk.ijse.model;

import lk.ijse.DB.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class HotelModel {
    public static int[] getCount() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT locathion FROM room";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        int[] count = new int[5];
        while (resultSet.next()){
            if(resultSet.getString(1).equals("Anuradapura")){
                count[0]++;
            }
            else if (resultSet.getString(1).equals("Nuwara Eliya")){
                count[1]++;
            } else if (resultSet.getString(1).equals("Katharagama")) {
                count[2]++;
            } else if (resultSet.getString(1).equals("Galle")) {
                count[3]++;
            } else if (resultSet.getString(1).equals("Colombo")) {
                count[4]++;
            }
        }
        return count;
    }
}
