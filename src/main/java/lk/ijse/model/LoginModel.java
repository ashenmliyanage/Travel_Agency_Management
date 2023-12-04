package lk.ijse.model;

import lk.ijse.DB.DBConnection;
import lk.ijse.QrCode.DatabaseConnecter;
import lk.ijse.QrCode.QrCodeScanner;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LoginModel {
        public static boolean Check(String username , String Password){
            String[][] data = DatabaseConnecter.getDetails("user",4);

            for (int i = 0; i <data.length; i++) {
                if(data[i][1].equals(username)){
                    for (int j = 0; j < data.length; j++) {
                        if(data[j][3].equals(Password)){
                            return true;
                        }
                    }
                }
            }
            return false;
        }


    public static String getName(String username , String Password){
        String[][] data = DatabaseConnecter.getDetails("user",4);
        String Name;
        for (int i = 0; i <data.length; i++) {
            if(data[i][0].equals(username)){
                for (int j = 0; j < data.length; j++) {
                    if(data[j][3].equals(Password)){
                        Name = data[i][1];
                        return Name;
                    }
                }
            }
        }
        return null;
    }
    public static List QrLogin(){
            String[][] data = DatabaseConnecter.getDetails("user",4);
            String Name;
            List list = new ArrayList<>();
            QrCodeScanner.QrScanner();
            String value = QrCodeScanner.value;
            Name = value;
            for (int i = 0; i < data.length; i++) {
                if (data[i][0].equals(value)) {
                    list.add(true);
                    list.add(Name);
                    return list;
                }
            }
            return null;
        }


        public String  checkMail(String mail) throws SQLException {
            Connection connection = DBConnection.getInstance().getConnection();

            String sql = "SELECT mail FROM user WHERE mail = ?";
            PreparedStatement pmst =connection.prepareStatement(sql);
            pmst.setString(1,mail);

            ResultSet resultSet = pmst.executeQuery();

            String dto = "";

            if (resultSet.next()){
                String Mail = resultSet.getString(1);
                dto = Mail;
            }
            return dto;
        }

    public String getMailUserName(String mail) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT name FROM user WHERE mail = ?";
        PreparedStatement pmst =connection.prepareStatement(sql);
        pmst.setString(1,mail);

        ResultSet resultSet = pmst.executeQuery();

        String dto = "";

        if (resultSet.next()){
            String name = resultSet.getString(1);
            dto = name;
        }
        return dto;
    }
}
