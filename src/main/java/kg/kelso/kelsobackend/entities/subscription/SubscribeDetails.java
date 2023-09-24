package kg.kelso.kelsobackend.entities.subscription;

import jakarta.persistence.*;
import kg.kelso.kelsobackend.entities.book.Book;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "subscribe_details")
public class SubscribeDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long subscribe_id;

    @ManyToOne
    @JoinColumn(nullable = false)
    Subscribe subscribe;

    @ManyToOne
    @JoinColumn(nullable = false)
    Book book;

    Timestamp cdt;

    Timestamp mdt;

    Timestamp rdt;

}
