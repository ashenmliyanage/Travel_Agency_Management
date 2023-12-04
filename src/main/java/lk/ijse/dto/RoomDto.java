package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.awt.*;
import java.io.InputStream;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoomDto {
    private String room_id;
    private String type;
    private String location;
    private String room_number;
    private double price;
    private InputStream image;
}
