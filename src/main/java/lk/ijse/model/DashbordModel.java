package lk.ijse.model;

import lk.ijse.DB.DBConnection;
import lk.ijse.QrCode.DatabaseConnecter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DashbordModel {
    public static int[] setCout() {
        int USA = 0;
        int rus = 0;
        int brazil = 0;
        int Aus=  0;

        String[][] data = DatabaseConnecter.getDetails("member",3);

        for (int i = 0; i < data.length; i++) {
            if (data[i][2].equals("USA")) {
                USA++;
            } else if (data[i][2].equals("Russia")) {
                rus++;
            } else if (data[i][2].equals("Brazil")) {
                brazil++;
            }
            else {
                if(data[i][2].equals("Australia")){
                    Aus++;
                }
            }
        }
        int[] count = new int[4];
        count[0] = USA;
        count[1] = rus;
        count[2] = brazil;
        count[3] = Aus;

        return count;
    }

    public static String[] setName() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT name FROM member ORDER BY member_id DESC LIMIT 5";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        String[] name = new String[5];
        int i = 0;
        while (resultSet.next()){
            name[i] = resultSet.getString(1);
            i++;
        }
        return name;
    }

    public static String[] setLoc() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT address FROM member ORDER BY member_id DESC LIMIT 5";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        String[] name = new String[5];
        int i = 0;
        while (resultSet.next()){
            name[i] = resultSet.getString(1);
            i++;
        }
        return name;
    }

    public int getEmpCount() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM employee";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }

    public int getVehicaleC() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM vehicle";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();

        if (resultSet.next()){
            return resultSet.getInt(1);
        }
        return 0;
    }
}
