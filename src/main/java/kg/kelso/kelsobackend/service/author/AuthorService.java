package kg.kelso.kelsobackend.service.author;

import kg.kelso.kelsobackend.entities.author.Author;
import kg.kelso.kelsobackend.model.author.AuthorModel;
import kg.kelso.kelsobackend.util.exception.NotFoundException;

import java.util.List;

public interface AuthorService {

    AuthorModel getById(Long id) throws NotFoundException;

    Long save(String authorName);

    Author getByName(String name);

    List<AuthorModel> getAll();

}
