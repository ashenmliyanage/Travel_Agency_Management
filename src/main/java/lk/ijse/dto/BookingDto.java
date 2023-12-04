package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookingDto {
    private String Id;
    private String date;
    private String type;
    private String Hotel_Id;
    private int hHotel;
    private String vehicle_id;
    private int hVehicle;
    private double total;
}
