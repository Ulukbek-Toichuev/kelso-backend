package kg.kelso.kelsobackend.service.book;


import kg.kelso.kelsobackend.dao.author.AuthorDao;
import kg.kelso.kelsobackend.dao.book.BookDao;
import kg.kelso.kelsobackend.dao.genre.GenreDao;
import kg.kelso.kelsobackend.entities.author.Author;
import kg.kelso.kelsobackend.entities.book.Book;
import kg.kelso.kelsobackend.entities.genre.Genre;
import kg.kelso.kelsobackend.models.author.AuthorModel;
import kg.kelso.kelsobackend.models.book.BookModelRequest;
import kg.kelso.kelsobackend.models.book.BookPreviewModelResponse;
import kg.kelso.kelsobackend.models.genre.GenreModel;
import kg.kelso.kelsobackend.util.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookServiceImpl implements BookService{

    BookDao bookDao;
    AuthorDao authorDao;
    GenreDao genreDao;

    @Override
    public Long save(BookModelRequest model) throws NotFoundException {
        return bookDao.save(new Book(null, model.getIsbn(), model.getTitle(), model.getPrice()
                , model.getPageCount(), model.getCoverType(), model.getAvailableCount() > 0, model.getAvailableCount()
                , mapToAuthor(model), mapToGenreList(model.getGenres()), new Timestamp(System.currentTimeMillis()), null, null)).getBook_id();
    }

    @Override
    public List<BookPreviewModelResponse> getPreviewAll(){
        return bookDao.getAllPreview().stream().map(book -> new BookPreviewModelResponse(
                book.getIsbn(), book.getTitle(), book.getPrice(), book.getPage_count(), book.getCover_type().getTitle()
                , new AuthorModel(book.getAuthor().getName())
                , book.getGenres().stream().map(genre -> new GenreModel(genre.getGenre_id(), genre.getName())).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    @Override
    public BookPreviewModelResponse getById(Long id) {
        return null;
    }

    private List<Genre> mapToGenreList(List<GenreModel> genreModels) {
        return genreModels.stream().map(genreModel -> genreDao.findById(genreModel.getId()).orElseThrow()).toList();
    }

    private Author mapToAuthor(BookModelRequest model) throws NotFoundException {
        Optional<Author> authorOptional = authorDao.findById(model.getAuthorId());
        return authorOptional.orElseThrow(() -> new NotFoundException("Author with id: " + model.getAuthorId() + " not found"));
    }
}
