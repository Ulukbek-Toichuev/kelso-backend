package kg.kelso.kelsobackend.controller;

import kg.kelso.kelsobackend.models.book.BookModelRequest;
import kg.kelso.kelsobackend.models.book.BookModelResponse;
import kg.kelso.kelsobackend.service.book.BookService;
import kg.kelso.kelsobackend.util.exception.NotFoundException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/book")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookController {

    @Autowired
    BookService service;

    @PostMapping("/save")
    public Long save(@RequestBody BookModelRequest model) throws NotFoundException {
        return service.save(model);
    }

    @GetMapping()
    public List<BookModelResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public BookModelResponse getById(@PathVariable Long id) throws NotFoundException {
        return service.getById(id);
    }

    @PostMapping("/update-price")
    public void updatePrice(@RequestBody BookModelRequest model) {
        service.updatePrice(model);
    }

    @GetMapping("/author/{id}")
    public List<BookModelResponse> getByAuthorId(@PathVariable Long id){
        return service.getByAuthorId(id);
    }

    @GetMapping("/genre/{id}")
    public List<BookModelResponse> getByGenreId(@PathVariable Long id){
        return service.getByGenreId(id);
    }
}
