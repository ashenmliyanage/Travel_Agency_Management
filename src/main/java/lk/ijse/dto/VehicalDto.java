package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VehicalDto {
    private String vehicale_id;
    private  double price;
    private String type;
    private String number;
    private InputStream image;
}
