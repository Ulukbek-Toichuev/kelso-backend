package kg.kelso.kelsobackend.service.book;


import kg.kelso.kelsobackend.dao.AuthorDao;
import kg.kelso.kelsobackend.dao.BookDao;
import kg.kelso.kelsobackend.dao.GenreDao;
import kg.kelso.kelsobackend.entities.author.Author;
import kg.kelso.kelsobackend.entities.book.Book;
import kg.kelso.kelsobackend.entities.genre.Genre;
import kg.kelso.kelsobackend.model.author.AuthorModel;
import kg.kelso.kelsobackend.model.book.BookModelRequest;
import kg.kelso.kelsobackend.model.book.BookModelResponse;
import kg.kelso.kelsobackend.model.genre.GenreModel;
import kg.kelso.kelsobackend.util.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    @Override
    public Long save(BookModelRequest model) throws NotFoundException {
        return bookDao.save(new Book(null, model.getIsbn(), model.getTitle(), model.getPrice()
                , model.getPageCount(), model.getCoverType(), model.getAvailableCount() > 0, model.getAvailableCount()
                , mapToAuthor(model), mapToGenreList(model.getGenres()), new Timestamp(System.currentTimeMillis()), null, null)).getBook_id();
    }

    @Override
    public List<BookModelResponse> getAll(){
        return bookDao.getAll().stream().map(book -> new BookModelResponse(
                book.getBook_id(), book.getIsbn(), book.getTitle(), book.getPrice(), book.getPage_count(), book.getCover_type().getTitle(), book.getIs_available()
                , new AuthorModel(book.getAuthor().getId(), book.getAuthor().getName())
                , book.getGenres().stream().map(genre -> new GenreModel(genre.getGenre_id(), genre.getName())).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    @Override
    public BookModelResponse getById(Long id) throws NotFoundException {
        return mapToBookModel(bookDao.findById(id).orElseThrow(() -> new NotFoundException("Book with id: " + id + " not found")));
    }

    @Transactional
    @Override
    public void updatePrice(BookModelRequest model) {
        if (bookDao.existsById(model.getId()))
            bookDao.updatePrice(model.getPrice(), model.getId(), new Timestamp(System.currentTimeMillis()));
    }

    @Override
    public List<BookModelResponse> getByAuthorId(Long id){
        if (authorDao.existsById(id))
            return bookDao.getByAuthorId(id).stream().map(this::mapToBookModel).collect(Collectors.toList());
        return null;
    }

    @Override
    public List<BookModelResponse> getByGenreId(Long id){
        return bookDao.getByGenreId(id).stream().map(this::mapToBookModel).collect(Collectors.toList());
    }

    private BookModelResponse mapToBookModel(Book book) {
        return new BookModelResponse(book.getBook_id(), book.getIsbn(), book.getTitle(), book.getPrice(), book.getPage_count()
                , book.getCover_type().getTitle(), book.getIs_available(), new AuthorModel(book.getAuthor().getId(), book.getAuthor().getName()),
                book.getGenres().stream().map(genre -> new GenreModel(genre.getGenre_id(), genre.getName())).collect(Collectors.toList()));
    }

    private List<Genre> mapToGenreList(List<GenreModel> genreModels) {
        return genreModels.stream().map(genreModel -> genreDao.findById(genreModel.getId()).orElseThrow()).toList();
    }

    private Author mapToAuthor(BookModelRequest model) throws NotFoundException {
        Optional<Author> authorOptional = authorDao.findById(model.getAuthorId());
        return authorOptional.orElseThrow(() -> new NotFoundException("Author with id: " + model.getAuthorId() + " not found"));
    }
}
