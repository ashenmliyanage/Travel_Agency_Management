package lk.ijse.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Userdto {
    private String User_id;
    private String Name;
    private String Mail;
    private String password;
}
