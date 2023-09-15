package kg.kelso.kelsobackend.models.book;

import kg.kelso.kelsobackend.enums.CoverType;
import kg.kelso.kelsobackend.models.genre.GenreModel;
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
public class BookModelRequest {

    Long id;

    String isbn;

    String title;

    BigInteger price;

    Integer pageCount;

    CoverType coverType;

    Integer availableCount;

    Long authorId;

    List<GenreModel> genres;

}
