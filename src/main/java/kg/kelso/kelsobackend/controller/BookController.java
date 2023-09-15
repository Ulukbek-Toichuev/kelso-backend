package kg.kelso.kelsobackend.controller;

import kg.kelso.kelsobackend.models.book.BookModelRequest;
import kg.kelso.kelsobackend.models.book.BookPreviewModelResponse;
import kg.kelso.kelsobackend.models.genre.GenreModel;
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

    @GetMapping("/all/get-preview")
    public List<BookPreviewModelResponse> getPreviewAll() {
        return service.getPreviewAll();
    }
}
