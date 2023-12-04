package lk.ijse.QrCode;

import java.sql.*;

public class DatabaseConnecter {
    private static Connection connection;
    public static Connection getConnection() {
        if (connection == null) {
            String url = "jdbc:mysql://localhost:3306/travel_Agancy";
            String username = "root";
            String password = "Ijse@1234";

            try {
                connection = DriverManager.getConnection(url, username, password);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void setDetails(String details) {
        getConnection();
        try {
            Statement statement = connection.createStatement();
            statement.execute(details);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String[][] getDetails(String name,int columnCount){
        int rowsCount = checkRowsCount(name);
        String[][] details = new String[rowsCount][columnCount];
        try {
            connection = DatabaseConnecter.getConnection();
            String sql = "select * from "+name;
            Statement statement =  connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);

            int i = 0;
            while (resultSet.next()){
                for (int j = 0; j < columnCount; j++) {
                    details[i][j] = resultSet.getString(j+1);
                }
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return details;
    }

    public static int checkRowsCount(String name) {
        int rows = 0;
        try {
            connection = DatabaseConnecter.getConnection();

            String sql = "SELECT COUNT(*) FROM "+name;


            PreparedStatement preparedStatement = connection.prepareStatement(sql);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                rows = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rows;
    }
}
