package kg.kelso.kelsobackend.entities.book;

import jakarta.persistence.*;
import kg.kelso.kelsobackend.entities.author.Author;
import kg.kelso.kelsobackend.entities.genre.Genre;
import kg.kelso.kelsobackend.enums.CoverType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.List;

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
    Long book_id;

    String isbn;

    String title;

    BigInteger price;

    Integer page_count;

    @Enumerated(EnumType.STRING)
    CoverType cover_type;

    Boolean is_available;

    Integer available_count;

    @ManyToOne
    @JoinColumn(name = "author_id")
    Author author;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "books_genre",
               joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name = "genre_id"))
    List<Genre> genres;

    Timestamp cdt;

    Timestamp mdt;

    Timestamp rdt;

}
