package kg.kelso.kelsobackend.model.subscription;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SubscribeModelResponse {

    Long id;

    Long userId;

    String status;

    BigDecimal deposit;

    BigDecimal price;

    Timestamp cdt;

    Timestamp edt;

    Timestamp mdt;

    Timestamp rdt;

}
