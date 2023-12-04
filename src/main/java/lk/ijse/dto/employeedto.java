package lk.ijse.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bridj.ann.Constructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class employeedto{

    private String Id;
    private String Name;
    private String Address;
    private int age;
    private String type;
    private String birthday;
    private int contact;
    private double salary;

}
