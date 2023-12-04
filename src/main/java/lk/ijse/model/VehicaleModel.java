package lk.ijse.model;

import lk.ijse.DB.DBConnection;
import lk.ijse.dto.VehicalDto;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicaleModel {
    public static String Check(String text) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT vehicle_id FROM vehicle WHERE vehicle_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);

        ResultSet resultSet = pstm.executeQuery();

        String name = null;
        if(resultSet.next()){
            name = resultSet.getString(1);
        }
        return name;
    }

    public static String[] getAllIds() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT vehicle_id FROM vehicle";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        List<String> ids = new ArrayList<>();

        while (resultSet.next()){
            ids.add(resultSet.getString(1));
        }
        String[] Ids = new String[ids.size()];
        for (int i = 0; i < ids.size(); i++) {
            Ids[i] = ids.get(i);
        }
        return Ids;
    }

    public boolean save(VehicalDto vehicalDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO vehicle VALUES(?, ?, ?, ?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,vehicalDto.getVehicale_id());
        pstm.setDouble(2,(vehicalDto.getPrice()));
        pstm.setString(3,vehicalDto.getType());
        pstm.setString(4,vehicalDto.getNumber());
        pstm.setBlob(5,vehicalDto.getImage());

        boolean resultSet = pstm.executeUpdate() > 0;

        return resultSet;
    }

    public VehicalDto Search(String data) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,data);

        ResultSet resultSet = pstm.executeQuery();

        VehicalDto dto = null;

        if (resultSet.next()){
            String Id = resultSet.getString(1);
            double price = Double.parseDouble(resultSet.getString(2));
            String type = resultSet.getString(3);
            String number = resultSet.getString(4);
            InputStream image = resultSet.getAsciiStream(5);

            dto = new VehicalDto(Id,price,type,number,image);
        }
        return dto;
    }

    public boolean Delete(String text) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM vehicle WHERE vehicle_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);

        boolean isDelete = pstm.executeUpdate() > 0;

        return isDelete;
    }

    public boolean Update(VehicalDto vehicalDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE vehicle SET price = ?, type = ?, number = ?, Image = ? WHERE vehicle_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1, String.valueOf(vehicalDto.getPrice()));
        pstm.setString(2,vehicalDto.getType());
        pstm.setString(3,vehicalDto.getNumber());
        pstm.setBlob(4,vehicalDto.getImage());
        pstm.setString(5,vehicalDto.getVehicale_id());

        return pstm.executeUpdate() > 0;
    }
    public static String gernarate() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM vehicle";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int x = 0;
        if (resultSet.next()){
            x = resultSet.getInt(1);
        }
        String Id = "V0"+x;
        while (true){
            String name = VehicaleModel.Check(Id);

            if (name == null){
                return Id;
            }
            else {
                Id = "V0"+ ++x;
            }
        }
    }
}
