package lk.ijse.model;

import javafx.scene.control.Alert;
import lk.ijse.DB.DBConnection;
import lk.ijse.dto.RoomDto;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomModel {
    public static String[] getAllIds() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT room_id FROM room";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();
        String[] ids = null;
        List<String> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        ids = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ids[i] = list.get(i);
        }
        return ids;
    }

    public boolean save(RoomDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        System.out.println(dto.getImage());

        String sql = "INSERT INTO room VALUES(?, ?, ?, ?, ?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,dto.getRoom_id());
        pstm.setString(2,dto.getType());
        pstm.setString(3, dto.getLocation());
        pstm.setString(4,dto.getRoom_number());
        pstm.setString(5, String.valueOf(dto.getPrice()));
        pstm.setBlob(6,dto.getImage());

        return pstm.executeUpdate() > 0;
    }

    public String getuserID(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT room_id FROM room WHERE room_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    public boolean DeleteRoom(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM room WHERE room_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        boolean isDelete = pstm.executeUpdate() > 0;

        return isDelete;
    }

    public RoomDto getSerch(String text) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM room WHERE room_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);

        ResultSet resultSet = pstm.executeQuery();

        RoomDto dto = null;

        if (resultSet.next()){
            String id = resultSet.getString(1);
            String type = resultSet.getString(2);
            String loc = resultSet.getString(3);
            String number = resultSet.getString(4);
            double price = resultSet.getDouble(5);
            InputStream image = resultSet.getAsciiStream(6);

            dto = new RoomDto(id,type,loc,number,price,image);
        }
        return dto;
    }
    public boolean Update(RoomDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE room SET type = ?, locathion = ?, room_number = ?, price = ?,Image = ? WHERE room_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,dto.getType());
        pstm.setString(2,dto.getLocation());
        pstm.setString(3,dto.getRoom_number());
        pstm.setString(4, String.valueOf(dto.getPrice()));
        pstm.setBlob(5,dto.getImage());
        pstm.setString(6,dto.getRoom_id());

        boolean isUpdate = pstm.executeUpdate() > 0;

        return isUpdate;
    }

    public static void addImage(RoomDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO room(room_id,type,locathion,room_number,price,Image)VALUES(?,?,?,?,?,?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,dto.getRoom_id());
        pstm.setString(2,dto.getType());
        pstm.setString(3,dto.getLocation());
        pstm.setString(4,dto.getRoom_number());
        pstm.setString(5, String.valueOf(dto.getPrice()));
        pstm.setBlob(6,dto.getImage());

        int inserted = pstm.executeUpdate();

        if (inserted > 0){
            new Alert(Alert.AlertType.INFORMATION,"Save").show();
        }
    }
    private static int count = 0;
    public static String Genarate() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM room";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet resultSet = preparedStatement.executeQuery();
        int x = 0;
        if (resultSet.next()){
            x = resultSet.getInt(1);
            count = resultSet.getInt(1);
        }
        String Id = "R0"+x;
        boolean flag = true;
        while (true){
            var model = new RoomModel();
            String name = model.getuserID(Id);

            if (name == null){
                return Id;
            }
            else {
                Id = "R0"+ ++x;
            }
        }

    }
}
