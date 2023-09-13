package kg.kelso.kelsobackend.controller;

import kg.kelso.kelsobackend.models.Genre.GenreModel;
import kg.kelso.kelsobackend.service.genre.GenreService;
import kg.kelso.kelsobackend.service.genre.GenreServiceImpl;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/genre")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GenreController {

    @Autowired
    GenreService service;

    @PostMapping("/save")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Long save(@RequestBody GenreModel model){
        return service.save(model);
    }

    @GetMapping("/all")
    public List<GenreModel> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GenreModel getById(@PathVariable Long id){
        return service.getById(id);
    }
}
