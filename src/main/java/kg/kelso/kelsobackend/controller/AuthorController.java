package kg.kelso.kelsobackend.controller;

import kg.kelso.kelsobackend.models.author.AuthorModel;
import kg.kelso.kelsobackend.service.author.AuthorService;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/author")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AuthorController {

    @Autowired
    AuthorService service;

    @PostMapping("/save")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public Long save(@RequestBody AuthorModel model){
        return service.save(model.getAuthorName());
    }


    @GetMapping("/all")
    public List<AuthorModel> getAll(){
        return service.getAll();
    }

    @GetMapping("/{id}")
    public AuthorModel getById(@PathVariable Long id){
        return new AuthorModel(service.getById(id));
    }

}
