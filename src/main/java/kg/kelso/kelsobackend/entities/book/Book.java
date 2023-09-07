package kg.kelso.kelsobackend.entities.book;

import jakarta.persistence.*;
import kg.kelso.kelsobackend.entities.author.Author;
import kg.kelso.kelsobackend.enums.CoverType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String isbn;

    String title;

    Integer page_count;

    @Enumerated(EnumType.STRING)
    CoverType cover_type;

    Boolean is_available;

    Integer available_count;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    Timestamp cdt;

    Timestamp mdt;

    Timestamp rdt;

}
