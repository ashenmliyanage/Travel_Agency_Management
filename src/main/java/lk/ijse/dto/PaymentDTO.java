package lk.ijse.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    private String BookingId;
    private String Date;
    private String Name;
    private String Total;

}
