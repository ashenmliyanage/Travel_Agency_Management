package lk.ijse.model;

import lk.ijse.DB.DBConnection;
import lk.ijse.QrCode.QrCodeScanner;
import lk.ijse.dto.MemberDto;
import lk.ijse.dto.employeedto;

import java.sql.*;

public class EmployeeModel {
    public static String genarateId() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int x = 0;
        if (resultSet.next()){
            x = resultSet.getInt(1);
        }
        String Id = "E0"+x;
        while (true){
            var model = new EmployeeModel();
            employeedto name = model.getIDs(Id);

            if (name == null){
                return Id;
            }
            else {
                Id = "E0"+ ++x;
            }
        }
    }

    public boolean saveEmployee(final employeedto dto) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "INSERT INTO employee VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);

        preparedStatement.setString(1,dto.getId());
        preparedStatement.setString(2,dto.getName());
        preparedStatement.setString(3, dto.getBirthday());
        preparedStatement.setString(4, dto.getAddress());
        preparedStatement.setString(5, String.valueOf(dto.getAge()));
        preparedStatement.setString(6, String.valueOf(dto.getContact()));
        preparedStatement.setString(7, String.valueOf(dto.getSalary()));
        preparedStatement.setString(8, dto.getType());

        boolean isSaved = preparedStatement.executeUpdate() > 0;

        return isSaved;
    }

    public employeedto getIDs(String id) throws SQLException {

        Connection connection =DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setString(1,id);

        ResultSet resultSet = pstm.executeQuery();

        employeedto dto = null;

        if(resultSet.next()) {
            String cus_id = resultSet.getString(1);
            String cus_name = resultSet.getString(2);
            String birthday = resultSet.getString(3);
            String address = resultSet.getString(4);
            int age = Integer.parseInt(resultSet.getString(5));
            int number = Integer.parseInt(resultSet.getString(6));
            double salary = Double.parseDouble(resultSet.getString(7));
            String type = resultSet.getString(8);

            dto = new employeedto(cus_id,cus_name,address,age,type,birthday,number,salary);
        }
        return dto;
    }

    public employeedto serch(String id) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,id);

        ResultSet resultSet = ptsm.executeQuery();

        employeedto dto = null;
        if(resultSet.next()){
            String emo_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String birthday = resultSet.getString(3);
            String address = resultSet.getString(4);
            int age = Integer.parseInt(resultSet.getString(5));
            int number = Integer.parseInt(resultSet.getString(6));
            double salary = Double.parseDouble(resultSet.getString(7));
            String type = resultSet.getString(8);

            dto = new employeedto(emo_id,name,address,age,type,birthday,number,salary);
        }
        return dto;

    }


    public static employeedto Serch(String id) throws SQLException {

        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT * FROM employee WHERE employee_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);
        ptsm.setString(1,id);

        ResultSet resultSet = ptsm.executeQuery();

        employeedto dto = null;
        if(resultSet.next()){
            String emo_id = resultSet.getString(1);
            String name = resultSet.getString(2);
            String birthday = resultSet.getString(3);
            String address = resultSet.getString(4);
            int age = Integer.parseInt(resultSet.getString(5));
            int number = Integer.parseInt(resultSet.getString(6));
            double salary = Double.parseDouble(resultSet.getString(7));
            String type = resultSet.getString(8);

            dto = new employeedto(emo_id,name,address,age,type,birthday,number,salary);
        }
        return dto;

    }
    public boolean update(
            /*String empId,String name,String birthday,String address, int Age, int Number, double salary, String type*/
            employeedto dto
    ) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "UPDATE employee SET name = ?, birthday = ?, address = ?, age = ?, contact_number = ?, salary = ?, Type = ? WHERE employee_id = ?";
        PreparedStatement ptsm = connection.prepareStatement(sql);

        ptsm.setString(1,dto.getName());
        ptsm.setString(2,dto.getBirthday());
        ptsm.setString(3, dto.getAddress());
        ptsm.setString(4, String.valueOf(dto.getAge()));
        ptsm.setString(5, String.valueOf(dto.getContact()));
        ptsm.setString(6, String.valueOf(dto.getSalary()));
        ptsm.setString(7,dto.getType());
        ptsm.setString(8,dto.getId());


        return ptsm.executeUpdate() > 0;
    }

    public boolean delete(String id) throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "DELETE FROM employee WHERE employee_id = ?";
        PreparedStatement pstm = connection.prepareStatement(sql);

        pstm.setString(1,id);

        return pstm.executeUpdate() > 0;
    }

    public static String[] getAllID() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT employee_id FROM employee";
        ResultSet resultSet = connection.prepareStatement(sql).executeQuery();

        String[] id = null;
        int i = 0;
        id = new String[getRowCount()];

        while (resultSet.next()){
            id[i] = resultSet.getString(1);
            i++;
        }
        return id;
    }

    public static int getRowCount() throws SQLException {
        Connection connection = DBConnection.getInstance().getConnection();

        String sql = "SELECT COUNT(*) FROM employee";
        PreparedStatement pstm = connection.prepareStatement(sql);

        ResultSet resultSet = pstm.executeQuery();

        int row = 0;

        if (resultSet.next()){
            row = resultSet.getInt(1);
        }
        return row;
    }

    public employeedto QrScan() throws SQLException {
        String[] data = getAllID();
        QrCodeScanner.QrScanner();
        String value = QrCodeScanner.value;

        employeedto isSearch = serch(value);
        return isSearch;
    }
}
