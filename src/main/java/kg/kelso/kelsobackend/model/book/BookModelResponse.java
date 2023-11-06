package kg.kelso.kelsobackend.model.book;


import kg.kelso.kelsobackend.model.author.AuthorModel;
import kg.kelso.kelsobackend.model.genre.GenreModel;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookModelResponse {

    Long id;
    String isbn;

    String title;

    BigInteger price;

    Integer pageCount;

    String coverType;

    Boolean available;

    AuthorModel author;

    List<GenreModel> genres;
}
