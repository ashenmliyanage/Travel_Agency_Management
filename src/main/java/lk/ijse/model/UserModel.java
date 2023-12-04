package lk.ijse.model;

import lk.ijse.Conroller.AdduserFormController;
import lk.ijse.Conroller.LoginFormController;
import lk.ijse.DB.DBConnection;
import lk.ijse.dto.Userdto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserModel {
    public int getCount() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM user";
        PreparedStatement pstm = connection.prepareStatement(sql);

        int x = 0;
        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()){
            x = resultSet.getInt(1);
        }
        return x;
    }

    public boolean isSave(Userdto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "INSERT INTO user VALUES(?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
            try {
                pstm.setString(1, dto.getUser_id());
            } catch (SQLException e) {
                int x = AdduserFormController.userID;
                String name = "U0"+ ++x;
                AdduserFormController.userName = name;
                pstm.setString(1, name);
            }
        pstm.setString(2,dto.getName());
        pstm.setString(3, dto.getMail());
        pstm.setString(4,dto.getPassword());

        return pstm.executeUpdate() > 0;
    }

    public boolean update(String text, String text1) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "UPDATE user SET name = ?, password = ? WHERE name = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);
        pstm.setString(2,text1);
        pstm.setString(3, LoginFormController.UserName);

        return pstm.executeUpdate() > 0;
    }
}
