package kg.kelso.kelsobackend.service.book;

import kg.kelso.kelsobackend.models.book.BookModelRequest;
import kg.kelso.kelsobackend.models.book.BookModelResponse;
import kg.kelso.kelsobackend.util.exception.NotFoundException;

import java.util.List;

public interface BookService {

    Long save(BookModelRequest model) throws NotFoundException;

    List<BookModelResponse> getAll();

    BookModelResponse getById(Long id) throws NotFoundException;

    void updatePrice(BookModelRequest model);

    List<BookModelResponse> getByAuthorId(Long id);

    List<BookModelResponse> getByGenreId(Long id);

}
