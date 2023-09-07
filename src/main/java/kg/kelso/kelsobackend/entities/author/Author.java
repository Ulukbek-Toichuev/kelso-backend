package kg.kelso.kelsobackend.entities.author;


import jakarta.persistence.*;
import kg.kelso.kelsobackend.entities.book.Book;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String author_name;

    @OneToMany(mappedBy = "author")
    private List<Book> books;

    Timestamp cdt;

    Timestamp mdt;

    Timestamp rdt;

}
