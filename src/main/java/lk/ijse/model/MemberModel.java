package lk.ijse.model;

import lk.ijse.DB.DBConnection;
import lk.ijse.dto.MemberDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MemberModel {
    public static String[] getAllIds() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT member_id FROM member";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ResultSet resultSet = preparedStatement.executeQuery();

        List<String> list = new ArrayList<>();
        while (resultSet.next()){
            list.add(resultSet.getString(1));
        }
        String[] Id = new String[list.size()];

        for (int i = 0; i <list.size(); i++) {
            Id[i] = list.get(i);
        }
        return Id;
    }

    public boolean save(MemberDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String name = check(dto.getMember_id());

        if (name == null) {
            String sql = "INSERT INTO member VALUES(?, ?, ?, ?)";
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setString(1, dto.getMember_id());
            pstm.setString(2, dto.getName());
            pstm.setString(3, dto.getCountry());
            pstm.setString(4, dto.getMail());

            boolean isSave = pstm.executeUpdate() > 0;

            return isSave;
        }
        return false;
    }

    public String check(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT member_id FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        String name = null;
        if (resultSet.next()){
            name = resultSet.getString(1);
        }
        return name;
    }

    public MemberDto Search(String text) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        System.out.println(text);
        MemberDto dto = null;
        String name = check(text);
        System.out.println(name);
        if (name != null){
            String sql = "SELECT * FROM member WHERE member_id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1,text);

            ResultSet resultSet = preparedStatement.executeQuery();


            if(resultSet.next()){
                String id = resultSet.getString(1);
                String Name = resultSet.getString(2);
                String loc = resultSet.getString(3);
                String mail = resultSet.getString(4);

                dto = new MemberDto(id,Name,loc,mail);
            }
            return dto;
        }
        return dto;
    }

    public boolean Delete(String text) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM member WHERE member_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,text);

        boolean resultSet = pstm.executeUpdate() > 0;

        return resultSet;
    }

    public boolean Update(MemberDto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE member SET name = ?, address = ?, mail = ? WHERE member_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,dto.getName());
        preparedStatement.setString(2, dto.getCountry());
        preparedStatement.setString(3, dto.getMail());
        preparedStatement.setString(4, dto.getMember_id());

        boolean isUpdate = preparedStatement.executeUpdate() > 0;
        return isUpdate;
    }

    public List<MemberDto> getAllmember() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "select * from member order by member_id desc";
        PreparedStatement pstm = connection.prepareStatement(sql);

        List<MemberDto> MemDeto = new ArrayList<>();

        ResultSet resultSet = pstm.executeQuery();

        while (resultSet.next()){
            String ID = resultSet.getString(1);
            String Name = resultSet.getString(2);
            String loc = resultSet.getString(3);
            String mail = resultSet.getString(4);
            var dto = new MemberDto(ID,Name,loc,mail);
            MemDeto.add(dto);
        }
        return MemDeto;
    }
    public static String genarete() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();
        String sql = "SELECT COUNT(*) FROM member";
        PreparedStatement pstm = connection.prepareStatement(sql);
        ResultSet resultSet = pstm.executeQuery();
        int x = 0;
        if (resultSet.next()){
            resultSet.getInt(1);
        }
        String Id = "M0"+x;
        while (true){
            var model = new MemberModel();
            String name = model.check(Id);

            if (name == null){
                return Id;
            }
            else {
                Id = "R0"+ ++x;
            }
        }
    }
}
