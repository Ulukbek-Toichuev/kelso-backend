package kg.kelso.kelsobackend.entities.subscription;

import jakarta.persistence.*;
import kg.kelso.kelsobackend.entities.user.User;
import kg.kelso.kelsobackend.enums.SubscribeStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "subscribe")
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long subscribe_id;

    @ManyToOne
    @JoinColumn(nullable = false, unique = true)
    User user;

    @Enumerated(EnumType.STRING)
    SubscribeStatus status;

    BigDecimal deposit;

    BigDecimal price;

    Timestamp cdt;

    Timestamp edt;

    Timestamp mdt;

    Timestamp rdt;

}
