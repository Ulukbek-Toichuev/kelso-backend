package kg.kelso.kelsobackend.service.author;

import kg.kelso.kelsobackend.entities.author.Author;
import kg.kelso.kelsobackend.models.author.AuthorModel;

import java.util.List;

public interface AuthorService {

    String getById(Long id);

    Long save(String authorName);

    Author getByName(String name);

    List<AuthorModel> getAll();

}
