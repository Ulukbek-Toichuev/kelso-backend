package kg.kelso.kelsobackend.service.book;

import kg.kelso.kelsobackend.entities.book.Book;
import kg.kelso.kelsobackend.models.book.BookModelRequest;
import kg.kelso.kelsobackend.models.book.BookPreviewModelResponse;
import kg.kelso.kelsobackend.util.exception.NotFoundException;

import java.util.List;

public interface BookService {

    Long save(BookModelRequest model) throws NotFoundException;

    List<BookPreviewModelResponse> getPreviewAll();

    BookPreviewModelResponse getById(Long id);


}
