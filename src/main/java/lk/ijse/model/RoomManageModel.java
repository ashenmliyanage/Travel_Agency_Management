package lk.ijse.model;

import lk.ijse.DB.DBConnection;
import lk.ijse.dto.RoomManageDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RoomManageModel {
    public boolean getSave(RoomManageDto roomManageDto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO room_manage VALUES(?, ?)";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,roomManageDto.getRoom_id());
        pstm.setString(2,roomManageDto.getBooking_id());

        return pstm.executeUpdate() > 0;
    }
}
